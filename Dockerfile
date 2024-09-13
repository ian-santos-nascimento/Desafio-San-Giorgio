# Etapa 1: Build
FROM maven:3.9.9-amazoncorretto-17-al2023 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e o código-fonte da aplicação para o container
COPY pom.xml .
COPY src ./src

# Faz o build da aplicação
RUN mvn clean package -DskipTests

# Etapa 2: Execução
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa de build para o container
COPY --from=build /app/target/*.jar app.jar

# Define o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
