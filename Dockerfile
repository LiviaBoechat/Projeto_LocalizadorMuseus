
# Primeiro estágio: Construir o pacote da aplicação
FROM maven:3-openjdk-17 AS build-image

# Defina o diretório de trabalho
WORKDIR /to-build-app

# Copie o arquivo pom.xml e baixe as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie todo o código-fonte
COPY src ./src

# Construa o pacote JAR, pulando os testes
RUN mvn package -DskipTests

# Segundo estágio: Criar a imagem final
FROM eclipse-temurin:17-jre-alpine

# Defina o diretório de trabalho
WORKDIR /app

# Copie o pacote JAR da etapa anterior
COPY --from=build-image /to-build-app/target/your-application.jar .

# Exponha a porta 8080 (a mesma em que a aplicação Spring Boot está configurada para ouvir)
EXPOSE 8080

# Comando de entrada para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "your-application.jar"]
