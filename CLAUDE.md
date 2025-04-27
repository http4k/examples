# http4k Examples Maintenance

This document tracks the maintenance tasks performed on the http4k examples repository and outlines requirements for ongoing maintenance.

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
