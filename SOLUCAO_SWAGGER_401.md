# 🐛 Solução: Swagger UI - "Failed to load API definition" (401)

## ❌ Problema

Quando você acessa o Swagger UI, aparece o erro:

```
Failed to load API definition.
Errors
Fetch error
response status is 401 /v3/api-docs
```

## 🔍 Causa

O **interceptor de autenticação** está bloqueando o endpoint `/v3/api-docs`, que é necessário para o Swagger UI funcionar. Isso acontece porque a aplicação precisa ser **reiniciada** após alterações no `InterceptorConfig.java`.

## ✅ Solução Rápida

### Opção 1: Reiniciar pelo Terminal (Mais Rápido)

1. **Pare a aplicação**:
   - No terminal onde a aplicação está rodando, pressione `Ctrl+C`

2. **Inicie novamente**:
   ```bash
   cd sofrimento
   ./mvnw spring-boot:run
   ```

3. **Aguarde a mensagem**:
   ```
   Started SofrimentoApplication in X.XX seconds
   ```

4. **Atualize o navegador**:
   - Pressione `F5` ou `Ctrl+R` no Swagger UI
   - Ou acesse novamente: http://localhost:8080/swagger-ui/

### Opção 2: Reiniciar pelo VS Code

1. **Pare a aplicação**:
   - Na aba "Run and Debug" do VS Code
   - Clique no botão vermelho de "Stop" (quadrado)

2. **Inicie novamente**:
   - Clique em "Run" → "Run Without Debugging"
   - Ou pressione `Ctrl+F5`

3. **Atualize o navegador** após a aplicação iniciar

### Opção 3: Compilação Limpa (Se as outras não funcionarem)

```bash
cd sofrimento
./mvnw clean install -DskipTests
./mvnw spring-boot:run
```

---

## 🔧 Verificação: Confirme que as Rotas Estão Excluídas

Verifique se o arquivo `InterceptorConfig.java` está correto:

```java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(cognitoTokenValidationInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/auth/**",              // ✅ Autenticação
                "/swagger-ui.html",      // ✅ Swagger (legado)
                "/swagger-ui/**",        // ✅ Swagger UI
                "/v3/api-docs/**",       // ✅ Documentação OpenAPI
                "/v3/api-docs",          // ✅ Endpoint raiz
                "/swagger-resources/**", // ✅ Recursos
                "/webjars/**",           // ✅ Dependências
                "/configuration/**",     // ✅ Configuração
                "/swagger-config",       // ✅ Config
                "/api-docs/**"           // ✅ Alternativo
            ); 
}
```

---

## 🧪 Teste se Funcionou

### Teste 1: Acessar o endpoint de documentação diretamente

Abra no navegador:
```
http://localhost:8080/v3/api-docs
```

**✅ Deve aparecer**: Um grande JSON com toda a documentação da API

**❌ Se aparecer erro 401**: A configuração não foi aplicada, reinicie novamente

### Teste 2: Acessar o Swagger UI

Abra:
```
http://localhost:8080/swagger-ui/
```

**✅ Deve aparecer**: A interface do Swagger com todos os endpoints

**❌ Se aparecer erro**: Limpe o cache do navegador (`Ctrl+Shift+Delete`) e tente novamente

---

## 🚨 Problemas Comuns

### Problema 1: Ainda dá erro 401 após reiniciar

**Causa**: Arquivo não foi salvo ou aplicação não recarregou

**Solução**:
1. Verifique se salvou o `InterceptorConfig.java` (`Ctrl+S`)
2. Faça uma **compilação limpa**:
   ```bash
   ./mvnw clean compile
   ./mvnw spring-boot:run
   ```

### Problema 2: Erro "Unable to find a suitable main class"

**Causa**: Problema na compilação

**Solução**:
```bash
./mvnw clean install -DskipTests
./mvnw spring-boot:run
```

### Problema 3: Porta 8080 já está em uso

**Erro**: `Port 8080 is already in use`

**Solução**:
1. Mate o processo que está usando a porta:
   ```powershell
   # No Windows PowerShell:
   Get-Process -Name java | Stop-Process -Force
   ```
2. Ou mude a porta no `application.properties`:
   ```properties
   server.port=8081
   ```

### Problema 4: Cache do navegador

**Sintoma**: Ainda vê o erro mesmo após reiniciar

**Solução**:
- **Chrome/Edge**: `Ctrl+Shift+Delete` → Limpar cache
- **Firefox**: `Ctrl+Shift+Delete` → Limpar cache
- Ou abra em **modo anônimo**: `Ctrl+Shift+N`

---

## 📋 Checklist de Verificação

Antes de tentar novamente, confirme:

- [ ] O arquivo `InterceptorConfig.java` foi salvo
- [ ] A aplicação foi **parada completamente**
- [ ] A aplicação foi **iniciada novamente**
- [ ] Você aguardou a mensagem "Started SofrimentoApplication"
- [ ] Você **atualizou a página** no navegador
- [ ] O cache do navegador foi limpo (se necessário)

---

## ✅ Resultado Esperado

Após seguir os passos, o Swagger UI deve:

1. ✅ Carregar sem erros
2. ✅ Mostrar todos os controllers (Auth, Device, Person, etc.)
3. ✅ Permitir expandir cada endpoint
4. ✅ Mostrar o botão verde **"Authorize"** no topo
5. ✅ Mostrar descrições completas de cada endpoint

---

## 🎯 Próximo Passo

Quando o Swagger carregar corretamente:

1. **Fazer Login**: Use `POST /auth/login`
2. **Copiar Token**: Pegue o `IdToken` da resposta
3. **Autorizar**: Clique em "Authorize" e cole o token
4. **Testar**: Experimente qualquer endpoint!

---

## 📞 Ainda Não Funciona?

Se após todos os passos ainda não funcionar:

1. **Verifique os logs** da aplicação no terminal
2. **Procure por erros** com "ERROR" ou "Exception"
3. **Verifique se a porta 8080 está livre**:
   ```powershell
   netstat -ano | findstr :8080
   ```

---

**Status depois de reiniciar:** ✅ DEVE FUNCIONAR!

Última atualização: 01/10/2025
