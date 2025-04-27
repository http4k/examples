#!/bin/bash

# Script to update all Gradle wrapper versions to 8.13

find /Users/david/dev/http4k/examples -name gradle-wrapper.properties -type f | while read file; do
  echo "Updating $file to Gradle 8.13"
  sed -i '' 's|distributionUrl=https\\://services.gradle.org/distributions/gradle-.*-bin.zip|distributionUrl=https\\://services.gradle.org/distributions/gradle-8.13-bin.zip|g' "$file"
done

echo "All Gradle wrapper properties files updated to version 8.13"