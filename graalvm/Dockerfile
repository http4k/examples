FROM ghcr.io/graalvm/graalvm-ce:java11-21.3.0 as graalvm
RUN gu install native-image

COPY . /home/app/http4k-example
WORKDIR /home/app/http4k-example

RUN native-image --no-fallback --no-server -cp build/libs/Example-all.jar com.example.HelloWorldKt

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/http4k-example/com.example.helloworldkt /app/http4k-example
ENTRYPOINT ["/app/http4k-example"]
