#!/bin/bash
set -euo pipefail

# Script to fix references to gradleProperties in build files

echo "Fixing gradleProperties references in build files..."

# Find all build.gradle.kts files
build_files=$(find . -name "build.gradle.kts")

for build_file in $build_files; do
    echo "Processing $build_file"
    
    # Replace gradleProperties with project.property
    if grep -q "gradleProperties" "$build_file"; then
        sed -i.bak 's/gradleProperties\["/"project.property("/g' "$build_file"
        sed -i.bak 's/"]/")/g' "$build_file"
        echo "  Updated $build_file"
    fi
done

# Clean up backup files
find . -name "*.bak" -delete

echo "All gradleProperties references fixed!"