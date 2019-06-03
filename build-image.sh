mvn clean package -DskipTests=true;
echo "JASYPT_ENCRYTOR_PASSWORD";
read JASYPT_ENCRYTOR_PASSWORD;
docker build -t registry.gitlab.com/twitter-scheduler/schedule-a-tweet --build-arg JASYPT_ENCRYTOR_PASSWORD=${JASYPT_ENCRYTOR_PASSWORD} .;
docker push registry.gitlab.com/twitter-scheduler/schedule-a-tweet:latest
