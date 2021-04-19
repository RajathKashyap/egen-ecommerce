FROM openjdk:8


ARG JAR_FILE=target/ecommerce-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]