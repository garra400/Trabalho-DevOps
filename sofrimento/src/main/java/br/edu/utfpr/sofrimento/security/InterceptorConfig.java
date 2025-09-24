package br.edu.utfpr.sofrimento.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração do interceptor
 * 
 * Este código configura um interceptor personalizado para validar tokens JWT em
 * requisições HTTP. O interceptor é aplicado a todos os endpoints que correspondem
 * ao padrão "/api/**".
 * 
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    // Injeção do interceptor personalizado
    @Autowired
    private CognitoTokenValidationInterceptor cognitoTokenValidationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cognitoTokenValidationInterceptor)
                .addPathPatterns("/**") // Aplica o interceptor aos seus endpoints protegidos
                .excludePathPatterns("/auth/**", "/swagger/**"); // Exclui endpoints públicos ou de erro, se necessário
    }
}