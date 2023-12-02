# http4k react project

This project brings together 2 Gradle modules:

1. `/backend` - Kotlin http4k app
2. `/frontend` - React app

## Development mode!

Run:
```shell
./gradlew check
```

... then either:

1. launch `Http4kReactMain` from your IDE - the UI lives at http://localhost:8000
1. enter development mode - the UI lives at http://localhost:3000
```shell
cd frontend
npm start
```

## Create production build

```shell
./gradlew clean distZip
```

The output ZIP lives in: `backend/build/distributions`
