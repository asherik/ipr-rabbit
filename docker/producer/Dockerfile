FROM ubuntu:20.04

# Java
RUN apt update \
  && apt install -y gnupg gnupg2 gnupg1 \
  && apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886 E1DF1F24 3DD9F856 \
  && apt-get update -y --fix-missing \
  && apt-get install -y default-jdk ca-certificates maven
ENV MAVEN_OPTS "-Xmx1g"
ENV JAVA_OPTS "-Xmx1g"
ENV JAVA_HOME "/usr/lib/jvm/default-java"
RUN java --version

# image layer
WORKDIR /app
ADD pom.xml /app
# Image layer
COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests
RUN cp  /app/target/*.jar /app.jar
RUN rm -R /app

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]