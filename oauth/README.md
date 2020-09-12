# http4k OAuth example
This app shows how to handle protecting resources using OAuth. There is also a test OAuth Server to do the validation of the login. http4k supports many providers out-of-the-box with the `http4k-security-oauth` module - eg. Google, Github, Auth0.

## Build/test locally

Run the `RunnableEnvironment` from your IDE. This will start both the app and the OAuth server. Then in a browser try and visit the protected resource at: [http://localhost:8080/]. The request will be redirected to the OAuth Server for 
