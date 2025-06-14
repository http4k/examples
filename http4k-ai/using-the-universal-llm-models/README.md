# Using the Universal LLM Models

This module demonstrates how to integrate Large Language Models (LLMs) with http4k's AI capabilities. It provides a simple example of building LLM-powered functionality using http4k's universal LLM interface.

## Overview

The example shows how to create an `LLMBackedQuiz` class that can interact with different LLM providers (Anthropic, OpenAI) through a unified interface. The implementation uses http4k's AI modules to abstract away provider-specific details, allowing you to switch between different LLM services with minimal code changes.

## Key Features

- **Universal LLM Interface**: Works with multiple LLM providers through a common API
- **Provider Support**: Includes examples for both Anthropic Claude and OpenAI GPT models
- **Testing**: Comprehensive test suite with fake LLM implementations for reliable testing
- **Error Handling**: Clean error handling using Result4k monads

This module serves as a foundation for building LLM-powered features in your http4k applications.
