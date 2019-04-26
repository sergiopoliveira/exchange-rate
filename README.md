# Exchange Rate API

[![CircleCI](https://circleci.com/gh/sergiopoliveira/exchange-rate.svg?style=svg)](https://circleci.com/gh/sergiopoliveira/exchange-rate)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/105ab45f3e454636a2da593839548a53)](https://www.codacy.com/app/sergiopoliveira/exchange-rate?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=sergiopoliveira/exchange-rate&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/sergiopoliveira/exchange-rate/branch/master/graph/badge.svg)](https://codecov.io/gh/sergiopoliveira/exchange-rate)

Implementing https://exchangeratesapi.io/ api

Build and Run:

./mvnw package && java -jar target/exchange-rate-0.0.1-SNAPSHOT.jar
mvnw.cmd package && java -jar target/exchange-rate-0.0.1-SNAPSHOT.jar

DOCKER

docker build -f Dockerfile.dev . -t sergiopoliveira/exchange-rate-dev
docker run -p 8080:8080 sergiopoliveira/exchange-rate-dev:latest
