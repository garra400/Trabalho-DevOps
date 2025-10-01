# Documentação da API - Sistema de Gerenciamento de Dispositivos IoT

## 📚 Swagger/OpenAPI - Documentação Automática

Este projeto implementa documentação automática da API usando **SpringDoc OpenAPI 3** (Swagger).

### Acessando a Documentação

Após iniciar a aplicação, acesse:

- **Swagger UI (Interface Interativa)**: http://localhost:8080/swagger-ui/index.html (ou http://localhost:8080/swagger-ui/)
- **Documentação OpenAPI (JSON)**: http://localhost:8080/v3/api-docs

### Funcionalidades do Swagger

✅ **Interface Interativa**: Teste todos os endpoints diretamente pelo navegador  
✅ **Documentação Completa**: Todos os endpoints documentados com descrições, parâmetros e respostas  
✅ **Autenticação JWT**: Suporte para testar endpoints protegidos com token JWT  
✅ **Exemplos de Requisições**: Exemplos de payloads para cada endpoint  
✅ **Códigos de Resposta**: Documentação de todos os códigos HTTP possíveis

---

## 🔐 Sistema de Autenticação e Autorização

### Autenticação via AWS Cognito

O sistema utiliza **AWS Cognito** para autenticação de usuários e geração de tokens JWT.

#### Endpoints de Autenticação

##### 1. Login - Obter Token JWT

```http
POST /auth/login
Content-Type: application/json

{
  "username": "seu-usuario",
  "password": "sua-senha"
}
```

**Resposta de Sucesso (200):**
```json
{
  "AuthenticationResult": {
    "AccessToken": "eyJraWQiOiJ...",
    "IdToken": "eyJraWQiOiJ...",
    "RefreshToken": "eyJjdHkiOiJ...",
    "ExpiresIn": 3600,
    "TokenType": "Bearer"
  }
}
```

##### 2. Validar Token

```http
POST /auth/validate
Authorization: Bearer {seu-token}
```

##### 3. Renovar Token

```http
POST /auth/refresh
Content-Type: application/json

{
  "refreshToken": "seu-refresh-token",
  "username": "seu-usuario"
}
```

---

## 🔒 Autorização por Perfil (Role-Based Access Control)

### Como Funciona

O sistema implementa autorização baseada em **grupos do AWS Cognito**. Cada usuário pode pertencer a um ou mais grupos (roles), e endpoints específicos podem ser protegidos para aceitar apenas usuários com determinados roles.

### Usando a Anotação @RequireRole

A anotação `@RequireRole` pode ser aplicada em métodos ou classes de controllers:

```java
// Proteger um endpoint específico
@DeleteMapping("/{id}")
@RequireRole({"admin"})
public void delete(@PathVariable String id) {
    // Somente usuários do grupo "admin" podem acessar
}

// Proteger todos os endpoints de um controller
@RestController
@RequireRole({"admin", "manager"})
public class AdminController {
    // Todos os métodos exigem role "admin" OU "manager"
}
```

### Roles Disponíveis

Configure grupos no AWS Cognito e adicione usuários aos grupos desejados:

- **admin**: Acesso completo, incluindo operações de exclusão
- **manager**: Acesso de gerenciamento (criar, editar, listar)
- **user**: Acesso básico de leitura

### Exemplo de Uso

```java
@RestController
@RequestMapping("/device")
@SecurityRequirement(name = "bearerAuth")
public class DeviceController {
    
    // Qualquer usuário autenticado pode listar
    @GetMapping
    public Page<DeviceDTO> listAll() { ... }
    
    // Apenas admins e managers podem criar
    @PostMapping
    @RequireRole({"admin", "manager"})
    public DeviceDTO create(@RequestBody DeviceDTO dto) { ... }
    
    // Apenas admins podem deletar
    @DeleteMapping("/{id}")
    @RequireRole({"admin"})
    public void delete(@PathVariable String id) { ... }
}
```

---

## 🧪 Testando com Swagger UI

### 1. Autenticar no Sistema

1. Acesse http://localhost:8080/swagger-ui/
2. Encontre o endpoint `POST /auth/login` na seção "Autenticação"
3. Clique em "Try it out"
4. Insira suas credenciais:
   ```json
   {
     "username": "seu-usuario",
     "password": "sua-senha"
   }
   ```
5. Clique em "Execute"
6. Copie o valor de `IdToken` da resposta

### 2. Configurar Autenticação JWT

1. No topo da página do Swagger, clique no botão **"Authorize"** 🔓
2. Cole o token copiado no campo (sem a palavra "Bearer")
3. Clique em "Authorize" e depois "Close"
4. Agora todos os endpoints protegidos podem ser testados!

### 3. Testar Endpoints Protegidos

Todos os endpoints (exceto `/auth/**`) agora incluirão automaticamente o token JWT nas requisições.

---

## 📋 Endpoints Principais Documentados

### Device (Dispositivos IoT)

- `POST /device/{siloId}` - Criar dispositivo (requer autenticação)
- `GET /device` - Listar todos os dispositivos (requer autenticação)
- `GET /device/{deviceId}` - Buscar dispositivo por ID (requer autenticação)
- `GET /device/silo` - Listar dispositivos por silo (requer autenticação)
- `PUT /device/{deviceId}` - Atualizar dispositivo (requer autenticação)
- `DELETE /device/{deviceId}` - Deletar dispositivo (requer autenticação)

### Person (Pessoas)

- `POST /person` - Criar pessoa (requer autenticação)
- `GET /person` - Listar pessoas (requer autenticação)
- `GET /person/{id}` - Buscar pessoa por ID (requer autenticação)
- `PUT /person/{id}` - Atualizar pessoa (requer autenticação)
- `DELETE /person/{id}` - **Deletar pessoa (requer role "admin")**

### Auth (Autenticação)

- `POST /auth/login` - Login e obtenção de tokens (público)
- `POST /auth/validate` - Validar token JWT (público)
- `POST /auth/refresh` - Renovar token JWT (público)

---

## 🛡️ Segurança

### Validação de Token

Todos os endpoints (exceto `/auth/**` e rotas do Swagger) são protegidos por um interceptor que:

1. Valida a assinatura do token JWT com as chaves públicas do AWS Cognito
2. Verifica se o token não expirou
3. Extrai as claims do usuário (ID, email, grupos, etc.)
4. Verifica se o usuário tem os roles necessários (quando aplicável)

### Respostas de Erro

- **401 Unauthorized**: Token ausente, inválido ou expirado
- **403 Forbidden**: Token válido, mas usuário não possui permissão necessária

---

## ⚙️ Configuração

As configurações do Swagger estão em `application.properties`:

```properties
# Swagger/OpenAPI
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
```

As configurações do AWS Cognito:

```properties
aws.cognito.region=us-east-2
aws.cognito.url=https://cognito-idp.us-east-2.amazonaws.com
aws.cognito.userPoolId=us-east-2_4gsEUSZkk
aws.cognito.clientId=1m7jmbevcc0tanlm8qlfgfc83d
aws.cognito.clientSecret=1c4lhhvdfnk3vmekeivgk3p78hlqrhranhncapq9i05b3oo075cd
```

---

## 🚀 Executando o Projeto

```bash
# Compilar
./mvnw clean install

# Executar
./mvnw spring-boot:run
```

Acesse: http://localhost:8080/swagger-ui/

---

## 📝 Checklist de Implementação

### ✅ Documentação Swagger
- [x] Dependência SpringDoc OpenAPI adicionada
- [x] Configuração do SwaggerConfig criada
- [x] Interface Swagger UI disponível
- [x] DeviceController completamente documentado
- [x] AuthController documentado
- [x] Suporte a autenticação JWT na UI

### ✅ Autenticação JWT
- [x] Endpoint de login (`/auth/login`)
- [x] Geração de tokens JWT via AWS Cognito
- [x] Endpoint de validação de token (`/auth/validate`)
- [x] Endpoint de refresh token (`/auth/refresh`)

### ✅ Proteção de Rotas
- [x] Interceptor de validação de token implementado
- [x] Validação de assinatura JWT com chaves públicas do Cognito
- [x] Exclusão de rotas públicas (auth, swagger)
- [x] Autorização por perfil com anotação `@RequireRole`
- [x] Verificação de grupos do Cognito no token
- [x] Exemplo de uso em PersonController.delete (role "admin")

---

## 📚 Recursos Adicionais

- [SpringDoc OpenAPI Documentation](https://springdoc.org/)
- [AWS Cognito Developer Guide](https://docs.aws.amazon.com/cognito/)
- [JWT.io - Token Debugger](https://jwt.io/)

---

**Desenvolvido com ❤️ pela equipe UTFPR**
