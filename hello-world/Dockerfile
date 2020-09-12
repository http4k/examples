FROM adoptopenjdk/openjdk11:jdk-11.0.1.13-alpine-slim
COPY build/distributions/HelloWorld.zip HelloWorld.zip
RUN unzip HelloWorld.zip
EXPOSE 8080
CMD HelloWorld/bin/HelloWorld
