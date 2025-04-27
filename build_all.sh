#!/bin/bash

# Script to build all modules in the multi-module Gradle project
# This will run a clean build on each module and report success/failure

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Auto-discover modules by finding all build.gradle.kts files
echo "Discovering modules..."
# Use a more portable approach instead of mapfile (which is bash 4+ only)
MODULES=()
while IFS= read -r line; do
    MODULES+=("$line")
done < <(find http4k-core http4k-connect -type f -name "build.gradle.kts" | sed -e 's|/build.gradle.kts||' | sed -e 's|/|-|g' | awk '{print ":" $0}')

# Print discovered modules
echo -e "${GREEN}Found ${#MODULES[@]} modules:${NC}"
printf "  %s\n" "${MODULES[@]}"
echo ""

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