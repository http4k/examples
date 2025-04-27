#!/bin/bash

# Script to build all modules in the multi-module Gradle project
# This will run a clean build on each module and report success/failure

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# List of all modules to build (from the matrix in GitHub workflow)
MODULES=(
  ":core:aws-lambda-http"
  ":core:aws-lambda-custom-runtime"
  ":core:aws-lambda-events"
  ":core:aws-lambda-url"
  ":core:bearer-auth"
  ":core:datastar"
  ":core:graalvm"
  ":core:graphql"
  ":core:hello-world"
  ":core:hexagonal"
  ":core:hexagonal-arrow"
  ":core:hotwire"
  ":core:http4k-connect"
  ":core:json-api"
  ":core:migration-micronaut"
  ":core:migration-spring"
  ":core:migration-ktor"
  ":core:oauth"
  ":core:openapi"
  ":core:quarkus"
  ":core:react-app"
  ":core:typesafe-configuration"
  ":core:web-content"
  ":core:websockets"
  ":connect:connect-pattern"
  ":connect:custom-api-client-and-fake"
  ":connect:extending-connect-api-clients"
  ":connect:fakes-in-official-aws-sdk"
  ":connect:using-connect-api-clients"
)

# Track success and failure
SUCCESS_COUNT=0
FAILED_COUNT=0
FAILED_MODULES=""

# Start in the project root directory
cd "$(dirname "$0")"

# Loop through each module and run gradle build
for module in "${MODULES[@]}"; do
  echo -e "\n\n${GREEN}Building ${module}${NC}"
  echo "========================================"
  
  # Run with clean build and quiet output (just show errors)
  if ./gradlew ${module}:clean ${module}:build --quiet; then
    echo -e "${GREEN}✓ Build successful for ${module}${NC}"
    ((SUCCESS_COUNT++))
  else
    echo -e "${RED}✗ Build failed for ${module}${NC}"
    ((FAILED_COUNT++))
    FAILED_MODULES="${FAILED_MODULES}\n  - ${module}"
  fi
done

# Print summary
echo -e "\n\n${GREEN}========================================"
echo "BUILD SUMMARY"
echo "========================================${NC}"
echo -e "Total modules: $((SUCCESS_COUNT + FAILED_COUNT))"
echo -e "${GREEN}Successful: ${SUCCESS_COUNT}${NC}"

if [ $FAILED_COUNT -gt 0 ]; then
  echo -e "${RED}Failed: ${FAILED_COUNT}${NC}"
  echo -e "${RED}Failed modules:${FAILED_MODULES}${NC}"
else
  echo -e "${GREEN}All builds successful!${NC}"
fi