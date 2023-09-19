# VacoBuilt Exercise Demo

## Prereqs

1. PostgreSQL
   * port 5432
   * database named 'test'
   * username 'postgres'

## Setup

1. `./gradlew flywayMigrate` to create database schema
2. `./gradlew clean bootRun` to start application
