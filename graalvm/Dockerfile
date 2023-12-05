FROM ghcr.io/graalvm/native-image-community:21 as graalvm

COPY . /home/app/http4k-example
WORKDIR /home/app/http4k-example

RUN native-image --static --no-fallback -cp build/libs/example.jar com.example.HelloWorldKt

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/http4k-example/com.example.helloworldkt /app/http4k-example
RUN chmod 777 /app/http4k-example
ENTRYPOINT ["/app/http4k-example"]
