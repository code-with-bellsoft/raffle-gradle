# Raffle-fx: an application choosing a random name/number from a list

A JavaFX application for shuffling a list of Strings to choose a winner participant.

## How to create a native image

Get Liberica Native Image Kit for Java 21 and set JAVA_HOME to it: https://bell-sw.com/pages/downloads/native-image-kit/#nik-23-(jdk-21)

Collect the metadata with the tracing agent and run the app through all execution paths:
```bash
./gradlew -Pagent run
```

Transfer the metadata into resources/META-INF/native-image:
```bash
./gradlew metadataCopy --task run --dir src/main/resources/META-INF/native-image
```

Add the data on toolbar usage manually to the resource-config JSON (this issue will be fixed later):
```json
{
    "pattern":"\\Qfxml/toolbar.fxml\\E"
  }
```

Build a native image:

```bash
./gradlew clean nativeCompile
```

Run the image:
```bash
./build/native/nativeCompile/raffle
```

The same application built with Maven: https://github.com/code-with-bellsoft/raffle