FROM openjdk:8-jdk-alpine
# VOLUME /tmp
ARG JASYPT_ENCRYTOR_PASSWORD
ADD target/schedule-a-tweet-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 80
ENV JAVA_OPTS="-Dspring.profiles.active=prod -Djasypt.encryptor.password=$JASYPT_ENCRYTOR_PASSWORD"
ENV GOOGLE_APPLICATION_CREDENTIALS=$SERVICE_ACCOUNT_PATH
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]