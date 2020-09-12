FROM oracle/graalvm-ce:20.2.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/http4k-example
WORKDIR /home/app/http4k-example

RUN native-image --no-fallback --no-server -cp build/libs/Example-all.jar com.example.HelloWorldKt

RUN ls /home/app/http4k-example/
FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/http4k-example/http4k-example /app/http4k-example
ENTRYPOINT ["/app/http4k-example"]
