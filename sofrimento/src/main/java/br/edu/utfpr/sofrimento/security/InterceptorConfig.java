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
        System.out.println("🔧 Configurando interceptor com exclusões:");
        System.out.println("   - /auth/**");
        System.out.println("   - /swagger-ui.html");
        System.out.println("   - /swagger-ui/**");
        System.out.println("   - /v3/api-docs/**");
        System.out.println("   - /v3/api-docs");
        
        registry.addInterceptor(cognitoTokenValidationInterceptor)
                .addPathPatterns("/**") // Aplica o interceptor aos seus endpoints protegidos
                .excludePathPatterns(
                    "/auth/**",              // Endpoints de autenticação
                    "/swagger-ui.html",      // Página principal do Swagger UI
                    "/swagger-ui/**",        // Recursos do Swagger UI
                    "/v3/api-docs/**",       // Documentação OpenAPI JSON/YAML
                    "/v3/api-docs",          // Documentação OpenAPI raiz
                    "/swagger-resources/**", // Recursos do Swagger
                    "/webjars/**",           // Dependências do Swagger UI (CSS, JS)
                    "/configuration/**",     // Configuração do Swagger
                    "/swagger-config",       // Configuração do Swagger
                    "/api-docs/**"           // Docs alternativos
                ); 
    }
}