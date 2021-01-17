# http4k react project

This project brings together 2 Gradle modules:

1. `/backend` - Kotlin http4k app
2. `/frontend` - React app

## Development mode!

Run:
```shell
./gradlew check
```

... then either launch `RunnableEnvironment` from your IDE or enter development mode with:

```shell
cd frontend
yarn start
```

## Create production build

```shell
./gradlew clean distZip
```
