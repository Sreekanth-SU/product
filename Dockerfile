FROM openjdk:11-jdk-slim
EXPOSE 8080
COPY data.csv data.csv
ADD target/product-0.0.1-SNAPSHOT.jar product-api.jar
ENTRYPOINT ["java","-jar","/product-api.jar"]