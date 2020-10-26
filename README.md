[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=maokejackson_microservices&metric=alert_status)](https://sonarcloud.io/dashboard?id=maokejackson_microservices)

## About

This is a simple microservice PoC project using Spring Cloud as technology stack.

### Application Modules

* microservice-discovery
* microservice-configuration
* microservice-gateway
* microservice-spring-boot-admin
* microservice-resource-movie
* microservice-resource-review
* microservice-gui

### Library Modules

* microservice-common-assembly
* microservice-common-resource-server
* microservice-common-swagger

## Generate Self-Signed Certificate

Create private/public key pair

```
keytool -genkeypair -alias dtxmaker -keyalg RSA -keysize 2048 -validity 9999 -dname "CN=localhost, O=DTXMaker, C=MY" -ext san=dns:localhost,ip:127.0.0.1 -keystore keystore.p12 -storetype pkcs12
```

Extract the public key

```
keytool -export -alias dtxmaker -keystore keystore.p12 -rfc -file server.crt
```

Create the Truststore

```
keytool -import -alias dtxmaker -file server.crt -storetype pkcs12 -keystore truststore.p12
```

Import the certificate to Java Keystore

```
keytool -import -trustcacerts -file server.crt -alias dtxmaker -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
```

Remove the certificate from Java Keystore

```
keytool -delete -alias dtxmaker -keystore $JAVA_HOME/jre/lib/security/cacerts
```
