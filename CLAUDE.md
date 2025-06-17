# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Common Development Commands

### Building and Testing
- **Build all modules**: `./build_all.sh` - Builds all modules individually with detailed success/failure reporting
- **Verify all modules**: `./verify_projects.sh` - Verifies Gradle configuration for all modules  
- **Build specific module**: `./gradlew :module-name:build` (e.g., `./gradlew :http4k-core-hello-world:build`)
- **Clean and build**: `./gradlew :module-name:clean :module-name:build`
- **Run tests only**: `./gradlew :module-name:test`
- **Run application**: `./gradlew :module-name:run` (for modules with application plugin)

### Module Discovery
Modules are auto-discovered by the build system. All directories containing `build.gradle.kts` files are automatically included as modules with names following the pattern `:section-subsection-module-name` (e.g., `:http4k-core-hello-world`, `:http4k-connect-using-connect-api-clients`).

## Architecture and Structure

### Repository Organization
This is a multi-module Gradle project with three main sections:
- **http4k-core**: Core http4k library examples demonstrating fundamental features
- **http4k-connect**: Examples using http4k-connect for external system integration  
- **http4k-pro**: Commercial http4k-pro library examples (AI, MCP, hot-reload features)

### Build System Architecture
- **Gradle 8.13** with Kotlin DSL throughout
- **Kotlin 2.1.20** and **Java 21** target
- Centralized version management in `gradle.properties`
- Automatic module discovery via `settings.gradle.kts` - scans for all `build.gradle.kts` files
- Common build configuration in root `build.gradle.kts` applies to all subprojects
- Individual modules define their specific plugins and dependencies

### Key Dependencies and Versions
All modules use BOM (Bill of Materials) for consistent versioning:
- **http4k BOM**: `org.http4k:http4k-bom:${http4kVersion}` 
- **JUnit BOM**: `org.junit:junit-bom:${junitVersion}`
- **http4k-pro modules**: Use `org.http4k.pro:*` artifacts (e.g., `http4k-ai-mcp-sdk`)

Current versions are defined in `gradle.properties`:
- http4kVersion=6.14.0.0
- kotlinVersion=2.1.21
- junitVersion=5.10.3

### Module Patterns
Each example module typically follows this structure:
- `src/main/kotlin/` - Application code, often with company/example package structure
- `src/test/kotlin/` - Tests using JUnit 5 and http4k-testing-hamkrest
- `build.gradle.kts` - Module-specific dependencies and plugins
- `README.md` - Module documentation with setup and usage instructions

### Testing Strategy
- **JUnit 5** platform used across all modules
- **http4k-testing-hamkrest** for HTTP testing assertions
- Tests run automatically in CI/CD pipeline for all modules
- Each module can be tested independently

### Continuous Integration
GitHub Actions workflow (`.github/workflows/build.yaml`) automatically:
- Discovers all modules dynamically
- Builds each module in parallel matrix jobs
- Uses Java 21 and Gradle caching
- Runs on all pushes and pull requests
- Continues building other modules even if one fails

### README Maintenance Requirements
- Each major section (core, connect, pro) must maintain a README.md listing all modules
- READMEs must be updated when modules are added, removed, or renamed
- Module descriptions should be brief but descriptive of the key demonstrated feature