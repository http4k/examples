#!/bin/bash
set -euo pipefail

# Script to restore dependencies and fix plugins in subproject build files

echo "Restoring dependencies for all subprojects..."

# Find all subproject build.gradle.kts files
build_files=$(find . -name "build.gradle.kts" -not -path "./build.gradle.kts")

for build_file in $build_files; do
    echo "Processing $build_file"
    
    # Get directory name for logging
    dir_name=$(dirname "$build_file")
    
    # Check if file is in git
    in_git=false
    original_kts=""
    original_gradle=""
    
    if git show HEAD:"${build_file#./}" > /dev/null 2>&1; then
        original_kts=$(git show HEAD:"${build_file#./}")
        in_git=true
    fi
    
    # Try with .gradle extension if .gradle.kts doesn't exist in git or is empty
    original_gradle_path="${build_file%.kts}"
    if ! $in_git && git show HEAD:"${original_gradle_path#./}" > /dev/null 2>&1; then
        original_gradle=$(git show HEAD:"${original_gradle_path#./}")
        in_git=true
    fi
    
    # Choose the source to use
    original_content=""
    if [ -n "$original_kts" ]; then
        original_content="$original_kts"
    elif [ -n "$original_gradle" ]; then
        original_content="$original_gradle"
    fi
    
    if [ -z "$original_content" ]; then
        echo "  Warning: Could not find original content for $build_file"
        continue
    fi
    
    # Detect application plugin
    has_application=false
    if echo "$original_content" | grep -q "application"; then
        has_application=true
    fi
    
    # Detect shadow plugin
    has_shadow=false
    if echo "$original_content" | grep -q "com.github.johnrengelman.shadow"; then
        has_shadow=true
    fi
    
    # Detect quarkus plugin
    has_quarkus=false
    if echo "$original_content" | grep -q "io.quarkus"; then
        has_quarkus=true
    fi
    
    # Detect ksp plugin
    has_ksp=false
    if echo "$original_content" | grep -q "com.google.devtools.ksp"; then
        has_ksp=true
    fi
    
    # Extract dependencies block as raw text
    if echo "$original_content" | grep -q "dependencies {"; then
        # Extract from start of dependencies to matching closing brace
        dependencies_text=$(echo "$original_content" | awk '/dependencies \{/,/^}/')
    else
        dependencies_text="dependencies {\n    // No dependencies found in original file\n}"
    fi
    
    # Extract application block if it exists
    application_block=""
    if echo "$original_content" | grep -q "application {"; then
        application_block=$(echo "$original_content" | awk '/application \{/,/^}/')
    fi
    
    # Create new content
    new_content="

plugins {
"
    
    # Add plugins
    if $has_application; then
        new_content+="    application
"
    fi
    
    if $has_shadow; then
        new_content+="    id(\"com.github.johnrengelman.shadow\")
"
    fi
    
    if $has_quarkus; then
        new_content+="    id(\"io.quarkus\")
"
    fi
    
    if $has_ksp; then
        new_content+="    id(\"com.google.devtools.ksp\")
"
    fi
    
    new_content+="}"
    
    # Add dependencies block
    new_content+="

$dependencies_text"
    
    # Add application block if it exists and isn't already in dependencies
    if [ -n "$application_block" ] && ! echo "$dependencies_text" | grep -q "application {"; then
        new_content+="

$application_block"
    fi
    
    # Write new content
    echo "$new_content" > "$build_file"
    echo "  Updated $build_file"
done

echo "Dependency restoration complete!"
