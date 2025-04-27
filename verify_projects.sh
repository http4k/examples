#!/bin/bash

# Script to verify all modules in the multi-module Gradle project
# This will run 'tasks' for each module to verify Gradle configuration

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# List of all modules to verify (from the matrix in GitHub workflow)
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

# Loop through each module and run gradle tasks
for module in "${MODULES[@]}"; do
  echo -e "\n\n${GREEN}Checking ${module}${NC}"
  echo "========================================"
  
  # Run just the tasks command to verify Gradle configuration
  if ./gradlew ${module}:tasks --console=plain --quiet | head -n 10; then
    echo -e "${GREEN}✓ Gradle check successful for ${module}${NC}"
    ((SUCCESS_COUNT++))
  else
    echo -e "${RED}✗ Gradle check failed for ${module}${NC}"
    ((FAILED_COUNT++))
    FAILED_MODULES="${FAILED_MODULES}\n  - ${module}"
  fi
done

# Print summary
echo -e "\n\n${GREEN}========================================"
echo "CHECK SUMMARY"
echo "========================================${NC}"
echo -e "Total modules: $((SUCCESS_COUNT + FAILED_COUNT))"
echo -e "${GREEN}Successful: ${SUCCESS_COUNT}${NC}"

if [ $FAILED_COUNT -gt 0 ]; then
  echo -e "${RED}Failed: ${FAILED_COUNT}${NC}"
  echo -e "${RED}Failed modules:${FAILED_MODULES}${NC}"
else
  echo -e "${GREEN}All Gradle checks successful!${NC}"
fi