FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
COPY build/distributions/Example.zip Example.zip
RUN unzip Example.zip
EXPOSE 8080
CMD Example/bin/Example
