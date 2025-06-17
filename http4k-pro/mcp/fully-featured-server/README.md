# Fully-Featured MCP Server

This module demonstrates a real-world multi-MCP system with three specialized agents working together to handle a complex workflow: raising and processing health insurance claims.

## System Overview

This example shows how multiple MCP servers can work together in a coordinated system:

1. **Family Agent** - Acts as a personal assistant for health insurance claims
2. **Rainforest.com** - E-commerce platform for purchasing medical supplies
3. **Acme Health Insurance** - Insurance company system for processing claims

## Architecture

The system demonstrates a realistic scenario where a family needs to:
1. Purchase medical supplies through an e-commerce platform
2. Submit insurance claims for reimbursement
3. Track claim status and receive payments

## Components

### [FamilyAgent.kt](./src/main/kotlin/raise_insurance_claim/FamilyAgent.kt)
Personal agent that helps families manage their health insurance claims. Handles:
- Claim submission
- Document management
- Communication with insurance providers

### [RainforestDotCom.kt](./src/main/kotlin/raise_insurance_claim/RainforestDotCom.kt)
E-commerce MCP server simulating an online marketplace for medical supplies. Features:
- Product catalog browsing
- Order placement and tracking
- Receipt generation for insurance claims

### [AcmeHealthInsurance.kt](./src/main/kotlin/raise_insurance_claim/AcmeHealthInsurance.kt)
Insurance company MCP server that processes claims. Handles:
- Claim validation
- Coverage verification
- Payment processing

## Running the Example

```bash
./gradlew :http4k-pro:mcp:fully-featured-server:run
```

This will start all three MCP servers and demonstrate the complete workflow from purchase to claim processing.

## Use Case Flow

1. User purchases medical supplies via RainforestDotCom MCP
2. FamilyAgent MCP helps user submit insurance claim with purchase receipts
3. AcmeHealthInsurance MCP processes the claim and determines coverage
4. System coordinates between all three to complete the reimbursement process