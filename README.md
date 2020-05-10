# Oslo Bysykkel, stasjons-status

This project explores the [Open API of Oslo Bysykkel](https://oslobysykkel.no/apne-data/sanntid).

Kotlin and Micronaut is used to implement a server the provides 2 endpoints:

- one REST/json-endpoint that return a list of Stations, with StationId, StationName, AvaiableBikes and AvailableDocks
- and one simplistic html endpoint, utilizing a velocity template to display the station list in alphabetical order

### Prerequisites
```
Java version 11
```

### Running the server, run gradle from the project root:

```
gradlew clean build run
```

The server exposes the endpoints on HTTP:

http://localhost:8080/citybike/unformatted (returns the list in json format, best viewed in e.g. Postman)

http://localhost:8080/citybike/formatted (returns the list in simple html format, best viewed in a web browser)

Some web browsers will try to redirect the http call to a https call. This implementation does not support HTTPS.


