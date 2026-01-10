FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar app.jar

# wait-for-mysql
ADD https://github.com/vishnubob/wait-for-it/raw/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

CMD ["sh", "-c", "\
until mysqladmin ping -h mysql -u fraud -pfraud --silent; do \
  echo 'Waiting for MySQL...'; \
  sleep 2; \
done; \
java -jar app.jar"]

