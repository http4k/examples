#!/bin/bash

# Script to build all projects with Gradle 8.13
# This will run a clean build on each project and report success/failure

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Find all directories containing gradle-wrapper.properties
GRADLE_DIRS=$(find /Users/david/dev/http4k/examples -name gradle-wrapper.properties -type f | xargs dirname | xargs dirname | xargs dirname | sort -u)

# Track success and failure
SUCCESS_COUNT=0
FAILED_COUNT=0
FAILED_PROJECTS=""

# Loop through each directory and run gradle build
for dir in $GRADLE_DIRS; do
  echo -e "\n\n${GREEN}Building ${dir}${NC}"
  echo "========================================"
  
  cd "$dir"
  
  # Run with clean build and quiet output (just show errors)
  if ./gradlew clean build --quiet; then
    echo -e "${GREEN}✓ Build successful for ${dir}${NC}"
    ((SUCCESS_COUNT++))
  else
    echo -e "${RED}✗ Build failed for ${dir}${NC}"
    ((FAILED_COUNT++))
    FAILED_PROJECTS="${FAILED_PROJECTS}\n  - ${dir}"
  fi
done

# Print summary
echo -e "\n\n${GREEN}========================================"
echo "BUILD SUMMARY"
echo "========================================${NC}"
echo -e "Total projects: $((SUCCESS_COUNT + FAILED_COUNT))"
echo -e "${GREEN}Successful: ${SUCCESS_COUNT}${NC}"

if [ $FAILED_COUNT -gt 0 ]; then
  echo -e "${RED}Failed: ${FAILED_COUNT}${NC}"
  echo -e "${RED}Failed projects:${FAILED_PROJECTS}${NC}"
else
  echo -e "${GREEN}All builds successful!${NC}"
fi