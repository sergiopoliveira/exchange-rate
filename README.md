# Exchange Rate API

[![CircleCI](https://circleci.com/gh/sergiopoliveira/exchange-rate.svg?style=svg)](https://circleci.com/gh/sergiopoliveira/exchange-rate)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/105ab45f3e454636a2da593839548a53)](https://www.codacy.com/app/sergiopoliveira/exchange-rate?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=sergiopoliveira/exchange-rate&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/sergiopoliveira/exchange-rate/branch/master/graph/badge.svg)](https://codecov.io/gh/sergiopoliveira/exchange-rate)

Implementing https://exchangeratesapi.io/ api

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

JDK 11 or Docker is required to run this project.

### Build and Run

#### Maven

Use Maven wrapper to build and run this project in Unix:

```
./mvnw package && java -jar target/exchange-rate-0.0.1-SNAPSHOT.jar
```

Or Windows:

```
mvnw.cmd package && java -jar target/exchange-rate-0.0.1-SNAPSHOT.jar
```

#### Docker

Pull image: 

```actuator 
docker pull sergiopoliveira/exchange-rate
```

Run it:

```
docker run -p 8080:8080 sergiopoliveira/exchange-rate
```

Access this to check everything is ok:

```
http://localhost:8080/actuator/health
```

## Running the tests

Tests created using JUnit and Mockito. Run them as:

```
./mvnw test
```

## Built With

*  [Spring Boot](http://www.start.spring.io/) - The web framework used
*  [Maven](https://maven.apache.org/) - Dependency Management

## Authors

*  **Sérgio Oliveira** - [sergiopoliveira](https://github.com/sergiopoliveira)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
