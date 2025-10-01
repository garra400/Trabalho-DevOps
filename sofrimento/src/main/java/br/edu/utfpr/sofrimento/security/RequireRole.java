package br.edu.utfpr.sofrimento.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para proteger endpoints com base em roles/grupos do Cognito
 * 
 * Exemplo de uso:
 * @RequireRole({"admin", "manager"})
 * public ResponseEntity<?> deleteUser() { ... }
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
    /**
     * Lista de roles/grupos permitidos para acessar o endpoint
     * O usuário deve ter pelo menos um dos roles listados
     */
    String[] value();
}
