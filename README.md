# 🌾 Sistema de Monitoramento de Silos Agrícolas

Sistema desenvolvido para monitoramento e controle de silos agrícolas, permitindo o gerenciamento de propriedades, dispositivos, sensores e dados de armazenamento. O projeto utiliza Spring Boot e segue a metodologia **Kanban** para gestão de tarefas e **Git Flow** para controle de versão.

---

## ✨ Status do Projeto

**� EM DESENVOLVIMENTO** – Sistema em desenvolvimento ativo com funcionalidades principais implementadas.

* ✅ **API REST** com Spring Boot implementada
* ✅ **Autenticação** com AWS Cognito integrada
* ✅ **Documentação Swagger** configurada
* ✅ **Estrutura MVC** organizada e funcional
* ✅ **Metodologia Kanban** configurada para acompanhamento de tarefas
* ✅ **Fluxo Git Flow** definido para controle de branches e versões
* ⏳ **Testes unitários** em desenvolvimento
* ⏳ **Deploy** em configuração

---

## 🚀 Funcionalidades

* **Gestão de Propriedades**: Cadastro e gerenciamento de propriedades agrícolas
* **Controle de Silos**: Monitoramento de silos e suas capacidades
* **Gerenciamento de Dispositivos**: Controle de dispositivos IoT conectados
* **Monitoramento de Sensores**: Acompanhamento em tempo real de sensores de temperatura, umidade, etc.
* **Gestão de Usuários**: Sistema de autenticação e autorização com AWS Cognito
* **API RESTful**: Endpoints completos para todas as funcionalidades
* **Documentação Swagger**: Interface interativa para testes e documentação da API

---

## 🏗️ Tecnologias Utilizadas

* **Java 17** - Linguagem de programação
* **Spring Boot 3.x** - Framework principal
* **Spring Security** - Segurança e autenticação
* **Spring Data JPA** - Persistência de dados
* **AWS Cognito** - Autenticação e autorização
* **Swagger/OpenAPI** - Documentação da API
* **Maven** - Gerenciamento de dependências
* **H2/PostgreSQL** - Banco de dados

---

## 📦 Estrutura do Projeto

```
Trabalho-DevOps/
├── sofrimento/                           # Aplicação Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/br/edu/utfpr/sofrimento/
│   │   │   │   ├── SofrimentoApplication.java
│   │   │   │   ├── config/
│   │   │   │   │   └── SwaggerConfig.java
│   │   │   │   ├── controllers/          # Controllers REST
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── DeviceController.java
│   │   │   │   │   ├── PersonController.java
│   │   │   │   │   ├── PropertyController.java
│   │   │   │   │   ├── SensorController.java
│   │   │   │   │   ├── SiloController.java
│   │   │   │   │   └── UserController.java
│   │   │   │   ├── dtos/                 # Data Transfer Objects
│   │   │   │   ├── exception/            # Tratamento de exceções
│   │   │   │   ├── models/               # Entidades JPA
│   │   │   │   ├── repositories/         # Repositórios JPA
│   │   │   │   ├── security/             # Configurações de segurança
│   │   │   │   └── services/             # Lógica de negócio
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/                         # Testes unitários
│   ├── mvnw / mvnw.cmd                  # Maven Wrapper
│   └── pom.xml                          # Configuração Maven
├── README.md
├── HELP.md
├── SWAGGER_AUTH_DOCUMENTATION.md
└── shell.nix
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Executando a aplicação

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/garra400/Trabalho-DevOps.git
   cd Trabalho-DevOps/sofrimento
   ```

2. **Execute a aplicação**:
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Ou no Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

3. **Acesse a aplicação**:
   - API: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 📋 Endpoints da API

### Autenticação
- `POST /auth/login` - Login de usuário

### Gestão de Propriedades
- `GET /properties` - Listar propriedades
- `POST /properties` - Criar propriedade
- `GET /properties/{id}` - Buscar propriedade por ID
- `PUT /properties/{id}` - Atualizar propriedade
- `DELETE /properties/{id}` - Deletar propriedade

### Gestão de Silos
- `GET /silos` - Listar silos
- `POST /silos` - Criar silo
- `GET /silos/{id}` - Buscar silo por ID
- `PUT /silos/{id}` - Atualizar silo
- `DELETE /silos/{id}` - Deletar silo

### Gestão de Dispositivos e Sensores
- Endpoints similares para `devices`, `sensors` e `users`

Para documentação completa, acesse `/swagger-ui.html` após executar a aplicação.

---

## 📌 Workflow de Desenvolvimento

### Kanban

* Quadro dividido em **To Do**, **In Progress**, **Review** e **Done**
* Tarefas registradas como *issues* no repositório
* Uso de *labels* para classificação (bug, feature, enhancement, documentation)

### Git Flow

* **Branches principais:**
  * `main`: versão estável e de produção
  * `dev`: versão em desenvolvimento
  
* **Branches auxiliares:**
  * `feature/*`: novas funcionalidades
  * `release/*`: preparação de releases
  * `hotfix/*`: correções urgentes em produção

---

## 🤝 Como Contribuir

Este projeto utiliza o **sistema de forks** para contribuições. Siga estes passos:

### 1. Preparação inicial

1. **Faça um fork** do repositório para sua conta GitHub
2. **Clone seu fork** localmente:
   ```bash
   git clone https://github.com/SEU_USUARIO/Trabalho-DevOps.git
   cd Trabalho-DevOps
   ```

3. **Configure o repositório original** como upstream:
   ```bash
   git remote add upstream https://github.com/garra400/Trabalho-DevOps.git
   ```

### 2. Desenvolvendo uma feature

1. **Sincronize com o repositório original**:
   ```bash
   git checkout dev
   git pull upstream dev
   git push origin dev
   ```

2. **Crie uma branch** a partir de `dev`:
   ```bash
   git checkout -b feature/nome-da-feature
   ```

3. **Implemente sua alteração** seguindo os padrões do projeto

4. **Faça commits seguindo o padrão**:
   ```bash
   git commit -m "feat: descrição breve da funcionalidade"
   git commit -m "fix: descrição breve da correção"
   git commit -m "docs: atualização de documentação"
   ```

5. **Envie para seu fork**:
   ```bash
   git push origin feature/nome-da-feature
   ```

### 3. Solicitando integração

1. **Abra um Pull Request** do seu fork para a branch `dev` do repositório original
2. **Descreva claramente** as mudanças implementadas
3. **Aguarde a revisão** da equipe de desenvolvimento
4. **Faça ajustes** se solicitado pelos revisores

### 4. Fluxo de integração

* **Pull Requests** são revisados e aprovados na branch `dev`
* **Periodicamente**, mudanças aprovadas em `dev` são promovidas para `main` (produção)
* **Releases** são criadas a partir da branch `main`

---

## 📝 Padrões de Contribuição

### Mensagens de Commit
- `feat:` nova funcionalidade
- `fix:` correção de bug
- `docs:` mudanças na documentação
- `style:` formatação, ponto e vírgula, etc (sem mudança de código)
- `refactor:` refatoração de código
- `test:` adição ou modificação de testes
- `chore:` mudanças em ferramentas, configurações, etc

### Código
- Siga as convenções do Java e Spring Boot
- Mantenha os testes atualizados
- Documente métodos públicos com Javadoc
- Use nomes descritivos para variáveis e métodos

---

## 👥 Equipe de Desenvolvimento

Agradecemos a todos os contribuidores que ajudam a tornar este projeto melhor!

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
