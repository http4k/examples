# http4k-connect integrations and fake dependencies

This app is a simple proxy to list the contents of an S3 bucket. It utilises the S3 client and FakeS3 server from the `http4k-connect` project.

## Build/test locally

Run the `RunnableEnvironment` from your IDE. This will start both the app and the Fake S3 server. Then in a browser try and visit the resource at: [http://localhost:8080/]. T
