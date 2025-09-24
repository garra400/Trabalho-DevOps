package br.edu.utfpr.sofrimento.security;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CognitoTokenValidationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CognitoTokenValidationInterceptor.class);

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.url}")
    private String coginitoUrl;

    private final Map<String, PublicKey> publicKeyCache = new java.util.HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private PublicKey getPublicKey(String keyId) {
        System.out.println("Buscando chave pública para o kid: " + keyId);

        if (publicKeyCache.containsKey(keyId)) {
            return publicKeyCache.get(keyId);
        }

        try {
            String jwksUrl = String.format("%s/%s/.well-known/jwks.json", coginitoUrl, userPoolId);
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(jwksUrl, JsonNode.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode keys = response.getBody().get("keys");
                if (keys != null && keys.isArray()) {
                    for (JsonNode key : keys) {
                        if (key.has("kid") && key.get("kid").asText().equals(keyId)) {
                            String nStr = key.get("n").asText();
                            String eStr = key.get("e").asText();

                            byte[] nBytes = Base64.getUrlDecoder().decode(nStr);
                            byte[] eBytes = Base64.getUrlDecoder().decode(eStr);

                            java.security.spec.RSAPublicKeySpec spec = new java.security.spec.RSAPublicKeySpec(
                                    new java.math.BigInteger(1, nBytes), new java.math.BigInteger(1, eBytes));
                            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
                            PublicKey publicKey = keyFactory.generatePublic(spec);
                            publicKeyCache.put(keyId, publicKey);
                            return publicKey;
                        }
                    }
                }
            }
            logger.error("Chave pública não encontrada para kid: {}", keyId);

        } catch (Exception e) {
            logger.error("Erro ao buscar chaves públicas do Cognito: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                DecodedJWT jwt = JWT.decode(token);
                String keyId = jwt.getHeaderClaim("kid").asString();
                PublicKey publicKey = getPublicKey(keyId);

                if (publicKey != null) {
                    Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null); // Usar a chave pública para
                                                                                            // verificar
                    algorithm.verify(jwt);
                    request.setAttribute("cognitoUser", jwt.getClaims()); // Opcional: adicionar claims ao request
                    return true; // Token válido
                } else {
                    logger.error("Chave pública não encontrada para o token.");
                }

            } catch (JWTVerificationException e) {
                logger.error("Token JWT do Cognito inválido: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Erro ao validar token do Cognito: {}", e.getMessage());
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}