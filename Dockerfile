# Etapa 1: Build da aplicação
FROM maven:3.9.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copia o pom.xml do local correto
COPY sofrimento/pom.xml .

# Copia o código-fonte
COPY sofrimento/src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final (somente o jar)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia apenas o JAR gerado
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão (opcional)
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]