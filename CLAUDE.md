# http4k Examples Maintenance

This document tracks the maintenance tasks performed on the http4k examples repository.

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
- Converted all 19 Groovy DSL build scripts to Kotlin DSL (build.gradle.kts)
- Used targeted manual conversion for each project type
- All projects have passing tests and verify successfully
- Handled special cases for different project types:
  - Basic library projects
  - Application projects with main class
  - Custom task configurations (e.g., Zip task for Lambda functions)
  - Special framework integration (Quarkus)
  - Shadow JAR plugin
  
### Summary of improvements
- Standardized Gradle version to 8.13 across all projects
- Upgraded Kotlin version to 2.1.20 and related plugins
- Modernized build scripts with Kotlin DSL for better IDE support and type safety
- All changes verified with comprehensive checks
- Maintained documentation for future maintenance work