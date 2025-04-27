#!/bin/bash

# Script to update Kotlin version to 2.1.20 in all projects
# KSP projects are handled separately

find /Users/david/dev/http4k/examples -name gradle.properties -type f | grep -v "custom-api-client-and-fake" | while read file; do
  echo "Updating Kotlin version in $file"
  sed -i '' 's/kotlinVersion=.*/kotlinVersion=2.1.20/g' "$file"
done

echo "All non-KSP projects updated to Kotlin 2.1.20"