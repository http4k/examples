#!/bin/bash

# Script to build all modules in the multi-module Gradle project
# This will run a clean build on each module and report success/failure

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# List of all modules to build (from the matrix in GitHub workflow)
MODULES=(
  ":http4k-core-aws-lambda-http"
  ":http4k-core-aws-lambda-custom-runtime"
  ":http4k-core-aws-lambda-events"
  ":http4k-core-aws-lambda-url"
  ":http4k-core-bearer-auth"
  ":http4k-core-datastar"
  ":http4k-core-graalvm"
  ":http4k-core-graphql"
  ":http4k-core-hello-world"
  ":http4k-core-hexagonal"
  ":http4k-core-hexagonal-arrow"
  ":http4k-core-hotwire"
  ":http4k-core-http4k-connect"
  ":http4k-core-json-api"
  ":http4k-core-migration-micronaut"
  ":http4k-core-migration-spring"
  ":http4k-core-migration-ktor"
  ":http4k-core-oauth"
  ":http4k-core-openapi"
  ":http4k-core-quarkus"
  ":http4k-core-react-app"
  ":http4k-core-typesafe-configuration"
  ":http4k-core-web-content"
  ":http4k-core-websockets"
  ":http4k-connect-connect-pattern"
  ":http4k-connect-custom-api-client-and-fake"
  ":http4k-connect-extending-connect-api-clients"
  ":http4k-connect-fakes-in-official-aws-sdk"
  ":http4k-connect-using-connect-api-clients"
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