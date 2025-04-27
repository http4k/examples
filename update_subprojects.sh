#!/bin/bash

# Script to update all projects to be compatible with the multi-module structure
# This will modify each build.gradle.kts file to remove duplication with the root build.gradle.kts

# Create directories for the new structure
mkdir -p /Users/david/dev/http4k/examples/connect
mkdir -p /Users/david/dev/http4k/examples/core

# Process each project and update its build.gradle.kts
find /Users/david/dev/http4k/examples -name "build.gradle.kts" -not -path "/Users/david/dev/http4k/examples/build.gradle.kts" | while read file_path; do
  project_dir=$(dirname "$file_path")
  
  echo "Processing $file_path"
  
  # Create a temporary file for the updated build script
  cat > "$file_path.tmp" << 'EOF'


plugins {
    // Apply additional plugins specific to this subproject if needed
}

dependencies {
    // Dependencies will be retained from the original file
}
EOF

  # Extract the dependencies block from the original file and append to the new file
  awk 'BEGIN {p=0;} 
    /dependencies\s*{/ {p=1; next;} 
    /^}/ { if (p==1) {p=0; next;}} 
    {if (p==1) print;}' "$file_path" > "$file_path.deps"
  
  # Extract plugin applications if any (for specific plugins like application or shadow)
  plugins=$(grep -A 10 "plugins" "$file_path" | grep -v "kotlin" | grep -v "jvm" | grep -v "^}")
  
  # Look for application plugin and mainClass
  has_application=$(grep -c "application" "$file_path")
  main_class=$(grep "mainClass" "$file_path" | grep -o "\"[^\"]*\"" | head -1)
  
  # Update the plugins block if needed
  if [ -n "$plugins" ] || [ "$has_application" -gt 0 ]; then
    sed -i '' 's/\/\/ Apply additional plugins specific to this subproject if needed//' "$file_path.tmp"
    if [ "$has_application" -gt 0 ]; then
      echo "    application" >> "$file_path.tmp"
      if [ -n "$main_class" ]; then
        echo -e "\napplication {\n    mainClass.set($main_class)\n}" >> "$file_path.tmp"
      fi
    fi
  fi
  
  # Append the dependencies
  if [ -s "$file_path.deps" ]; then
    echo -e "dependencies {\n$(cat "$file_path.deps")\n}" >> "$file_path.tmp"
  fi
  
  # Replace the original file with the new one
  mv "$file_path.tmp" "$file_path"
  rm -f "$file_path.deps"
done

# Remove project-specific settings files, gradle properties, and gradle wrappers
find /Users/david/dev/http4k/examples/http4k-connect /Users/david/dev/http4k/examples/http4k-core -name "settings.gradle.kts" -o -name "gradle.properties" | xargs rm -v

# Remove gradle wrapper directories from subprojects
find /Users/david/dev/http4k/examples/http4k-connect /Users/david/dev/http4k/examples/http4k-core -path "*/gradle/wrapper" -type d | xargs rm -rf

# Remove gradlew and gradlew.bat from subprojects
find /Users/david/dev/http4k/examples/http4k-connect /Users/david/dev/http4k/examples/http4k-core -name "gradlew" -o -name "gradlew.bat" | xargs rm -v

echo "Project restructuring completed"
