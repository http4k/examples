# http4k Datastar Examples

This module demonstrates the use of http4k's Datastar integration, a lightweight solution for building dynamic web applications with real-time updates using HTML over the wire patterns.

## Examples

### Game of Life

An interactive implementation of Conway's Game of Life using Datastar's fragment rendering capabilities.

- Run with: `./gradlew :http4k-core-datastar:run -PmainClass=gameoflife.MainKt`
- Access at: http://localhost:8080
- Features:
  - Real-time board updates using Server-Sent Events (SSE)
  - Interactive interface where users can click cells to create patterns
  - Multi-user support with session tracking
  - Automatic game evolution following Conway's Life rules

### Bad Apples Animation

A demonstration of real-time signal updates with an ASCII art animation player.

- Run with: `./gradlew :http4k-core-datastar:run -PmainClass=merge_signals.MainKt`
- Access at: http://localhost:8999
- Features:
  - Loads and plays a ZIP file containing ASCII art animation frames 
  - Demonstrates the use of Datastar's merge signals approach
  - Shows progress percentage during animation playback

### Fragment Merging Example

Shows how to work with Datastar's fragment merging capabilities for partial page updates.

- Run with: `./gradlew :http4k-core-datastar:run -PmainClass=merge_fragments.MainKt`
- Access at: http://localhost:8999
- Features:
  - User management interface with add/edit/list operations
  - Dynamic DOM updates without full page refreshes
  - Template composition with Handlebars

## Technical Overview

These examples showcase different patterns for building interactive web applications with http4k:

1. **Fragment Merging** - Server sends HTML fragments that replace parts of the DOM
2. **Signal Merging** - Server sends data signals that update specific parts of the DOM based on data attributes

## Requirements

- Kotlin 2.1.20+
- http4k-web-datastar module
- Java Virtual Thread support (JDK 21+ recommended)

## Related Documentation

- [http4k Datastar Documentation](https://www.http4k.org/guide/reference/datastar/)
- [Datastar JavaScript Library](https://github.com/starfederation/datastar)