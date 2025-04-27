# http4k Examples Maintenance

This document tracks the maintenance tasks performed on the http4k examples repository and outlines requirements for ongoing maintenance.

## Tasks

### 1. Gradle Version Update (2025-04-27)
- Updated all 29 projects to use Gradle 8.13
- Created verification script `/Users/david/dev/http4k/examples/verify_projects.sh`
- All 29 projects successfully updated and verified

### 2. Kotlin Version Update (2025-04-27)
- Updated all regular projects to Kotlin 2.1.20
- Found one project using KSP: `/Users/david/dev/http4k/examples/http4k-connect/custom-api-client-and-fake`
- Updated KSP project to Kotlin 2.1.20 and KSP version 2.1.20-1.0.31
- Verified KSP processing works correctly in the updated project
- All projects now using Kotlin 2.1.20 and functioning correctly

### 3. Convert to Kotlin DSL (2025-04-27)
- Converted all Groovy DSL build scripts (21 files) to Kotlin DSL (build.gradle.kts)
- Used targeted manual conversion for each project type
- All projects have passing tests and verify successfully
- Handled special cases for different project types:
  - Basic library projects
  - Application projects with main class
  - Custom task configurations (e.g., Zip task for Lambda functions)
  - Special framework integration (Quarkus)
  - Shadow JAR plugin
  - Multi-project builds (react-app)

### 4. Convert Settings Files to Kotlin DSL (2025-04-27)
- Converted all 16 settings.gradle files to settings.gradle.kts
- Updated special cases like Quarkus plugin management
- All projects continue to function correctly with the new settings files
  
### 5. Convert to Multi-Module Gradle Project (2025-04-27)
- Created root build.gradle.kts with common configuration
- Created root settings.gradle.kts to include all subprojects
- Set up central gradle.properties file with shared version properties
- Established Gradle wrapper in the root directory
- Created a script to clean up individual project build files:
  - Removed redundant plugin version declarations
  - Removed duplicate repository declarations
  - Removed redundant task configurations
  - Maintained project-specific configurations
- Created a script to restore project-specific dependencies:
  - Preserved original dependencies from git history
  - Fixed special plugins like application, quarkus, shadow
  - Ensured proper use of shared version properties
  - Handled special cases for datastar modules not in git history
- Addressed various challenges in the multi-module conversion:
  - Fixed incorrect property reference formats (gradleProperties vs project.property)
  - Fixed special cases like Ktor's version catalog and Micronaut dependencies
  - Resolved syntax errors in build files that were breaking the build
  - Verified the build with successful tests

### 6. Update Module Name Format (2025-04-27)
- Changed module naming from hierarchical format (":core:module-name") to flat format with dashes (":http4k-core-module-name")
- Updated settings.gradle.kts to use automatic project detection with directory walking
- Updated all build scripts (build_all.sh and verify_projects.sh) to use the new module name format
- Updated GitHub workflow matrix to use the new module names
- All 29 modules now use consistent naming format in all scripts and configuration files

### 7. Implement Automatic Module Discovery (2025-04-27)
- Modified GitHub workflow to automatically discover modules rather than using a hard-coded list
- Added a discovery job that finds all build.gradle.kts files and converts paths to module names
- Updated build_all.sh and verify_projects.sh to dynamically find modules using find command
- This makes the build system resilient to adding/removing modules without manual updates
- System now prints a list of discovered modules at the start of build or verification

## Maintenance Requirements

### README Files
- Each major section (core, connect) must have a README.md file that lists all modules in that section
- Each README should include:
  - Brief description of the section (core vs connect)
  - Complete list of all modules with short descriptions
  - Links to each module's individual README when available
  - Special notes for modules that require additional setup
- README files must be kept up-to-date when modules are added, removed, or renamed

## Project Structure
- The repository is organized as a multi-module Gradle project
- All modules use Gradle 8.13 with Kotlin DSL (build.gradle.kts)
- All modules use Kotlin 2.1.20
- Core build configuration is centralized in the root build.gradle.kts
- Module-specific plugins and dependencies are defined in each module's build file
- Module names follow the format ":http4k-[section]-[module-name]" (e.g., ":http4k-core-hello-world")

## Maintenance Verification
- Use verify_projects.sh to check that all modules build correctly
- Use build_all.sh to build all modules individually
- GitHub workflows automatically test all modules on push and PR
