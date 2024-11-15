FROM registry.access.redhat.com/ubi8/ubi-minimal
COPY build/Example-1.0.0-runner application
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
