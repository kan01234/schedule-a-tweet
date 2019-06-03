from re6exp/debian-jessie-oracle-jdk-8

# VOLUME /tmp
WORKDIR "./app/"
ARG JASYPT_ENCRYTOR_PASSWORD
ADD target/schedule-a-tweet-0.0.1-SNAPSHOT.jar ./app.jar
COPY schedule-a-tweet-87b109e51784.json ./
EXPOSE 80
ENV JAVA_OPTS="-Dspring.profiles.active=prod -Djasypt.encryptor.password=$JASYPT_ENCRYTOR_PASSWORD"
ENV GOOGLE_APPLICATION_CREDENTIALS="/app/schedule-a-tweet-87b109e51784.json"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]
