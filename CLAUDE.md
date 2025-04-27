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
- Identified 19 projects using Groovy DSL (build.gradle) that needed to be converted to Kotlin DSL (build.gradle.kts)
- Successfully converted 6 projects to Kotlin DSL:
  - `/Users/david/dev/http4k/examples/http4k-connect/connect-pattern`
  - `/Users/david/dev/http4k/examples/http4k-core/hello-world`
  - `/Users/david/dev/http4k/examples/http4k-core/web-content`
  - `/Users/david/dev/http4k/examples/http4k-core/websockets`
  - `/Users/david/dev/http4k/examples/http4k-core/graphql`
  - `/Users/david/dev/http4k/examples/http4k-core/oauth`
- All converted projects have passing tests
- Manual conversion process used for more reliable results
- Common patterns identified in build files:
  - Basic library projects
  - Application projects with main class
  - Projects with special dependencies

Remaining projects to be converted in future work:
- `/Users/david/dev/http4k/examples/http4k-connect/using-connect-api-clients`
- `/Users/david/dev/http4k/examples/http4k-core/aws-lambda-custom-runtime`
- `/Users/david/dev/http4k/examples/http4k-core/aws-lambda-events`
- `/Users/david/dev/http4k/examples/http4k-core/aws-lambda-http`
- `/Users/david/dev/http4k/examples/http4k-core/hexagonal-arrow`
- `/Users/david/dev/http4k/examples/http4k-core/hexagonal`
- `/Users/david/dev/http4k/examples/http4k-core/hotwire`
- `/Users/david/dev/http4k/examples/http4k-core/json-api`
- `/Users/david/dev/http4k/examples/http4k-core/openapi`
- `/Users/david/dev/http4k/examples/http4k-core/quarkus`
- `/Users/david/dev/http4k/examples/http4k-core/react-app`
- `/Users/david/dev/http4k/examples/http4k-core/typesafe-configuration`
- And others