# FROM eclipse-temurin:17-jdk-focal
 
# WORKDIR /app
 
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline
 
# COPY src ./src
 
# CMD ["./mvnw", "spring-boot:run"]

# Stage de build: Maven + JDK 17
FROM maven:3.8.7-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copia POM e wrapper para cache de dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copia o código fonte e empacota o jar
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage de runtime: JRE 17
FROM eclipse-temurin:17-jre AS runner
WORKDIR /app

# Copia o artefato gerado no build
COPY --from=builder /workspace/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]