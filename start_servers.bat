@echo off
start cmd /k "cd authManager && mvn quarkus:dev"
start cmd /k "cd mailer && mvn quarkus:dev"
start cmd /k "cd keyManager && mvn quarkus:dev"
