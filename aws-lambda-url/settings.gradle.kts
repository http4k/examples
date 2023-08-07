pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    
}
rootProject.name = "http4k-aws-lambda-url"

include ("hello-function")
include ("deployment")
