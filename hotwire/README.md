# http4k Hotwire Example

To demonstrate how simple it is to server [Hotwire](https://hotwire.dev/) webapps from an [http4k](https://http4k.org) application. Shamelessly stolen from [Josh Graham's post](https://delitescere.medium.com/hotwire-html-over-the-wire-2c733487268c).

## Build/test locally
In your IDE run the `HotwireKt` main or...

```shell script
./gradlew test distZip
unzip build/distributions/Example.zip
Example/bin/Example
```

...  then browse to [http://localhost:8080](http://localhost:8080)
