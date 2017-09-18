This app is intended to send and receive messages using an external Artemis
broker.

A scheduled bean sends out simple text messages with timestamp.

## Prerequisites
 - Java (obviously)
 - Docker (tested using 17.06)
 - docker-compose (tested using 1.14.0)

## How to run
Start the Artemis using docker:

```
$ cd src/docker && docker-compose up
```

Then compile the swarm app:
```
$ ./gradlew wildfly-swarm-package
```

now run the app:

```
$ java -jar build/libs/swarm-artemis-send-receive-swarm.jar
```

