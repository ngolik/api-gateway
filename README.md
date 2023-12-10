## Build docker image

#### For build and run whole application see README in `eureka`
Firstly need build a `jar`

```
mvn clean install
```

Then
```
docker build . -t ngolik/api-gateway
```