FROM openjdk:17-jdk-alpine
EXPOSE 5500
ADD target/MoneyTransfer-0.0.1-SNAPSHOT.jar mtransfer.jar
ENTRYPOINT ["java","-jar","/mtransfer.jar"]