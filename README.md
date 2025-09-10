# Raffle-fx: an application choosing a random name/number from a list

A JavaFX application for shuffling a list of Strings to choose a winner participant.

## Issue with building a native-image

Get Liberica Native Image Kit for Java 21 and set JAVA_HOME to it: https://bell-sw.com/pages/downloads/native-image-kit/#nik-23-(jdk-21)

The metadata is already collected. But if you want to collect it on your machine, build a JAR file:
```bash
./gradlew clean jar
```

Run the JAR with the tracing agent and run the app through all execution paths:
```bash
java -agentlib:native-image-agent=config-output-dir=./agent-data -jar build/libs/raffle-gradle-1.0-SNAPSHOT.jar
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

The image fails to start with the following exception

```bash
./build/native/nativeCompile/raffle
Exception in Application start method
Exception in thread "main" java.lang.RuntimeException: Exception in Application start method
        at javafx.graphics@21.0.8/com.sun.javafx.application.LauncherImpl.launchApplication1(LauncherImpl.java:893)
        at javafx.graphics@21.0.8/com.sun.javafx.application.LauncherImpl.lambda$launchApplication$2(LauncherImpl.java:196)
        at java.base@21.0.8/java.lang.Thread.runWith(Thread.java:1596)
        at java.base@21.0.8/java.lang.Thread.run(Thread.java:1583)
        at org.graalvm.nativeimage.builder/com.oracle.svm.core.thread.PlatformThreads.threadStartRoutine(PlatformThreads.java:902)
        at org.graalvm.nativeimage.builder/com.oracle.svm.core.thread.PlatformThreads.threadStartRoutine(PlatformThreads.java:878)
Caused by: java.lang.RuntimeException: javafx.fxml.LoadException: 
/fxml/home.fxml

        at dev.cyberjar.config.StageManager.loadRootNode(StageManager.java:104)
        at dev.cyberjar.config.StageManager.switchScene(StageManager.java:75)
        at dev.cyberjar.RaffleApplication.showHomeScene(RaffleApplication.java:33)
        at dev.cyberjar.RaffleApplication.start(RaffleApplication.java:29)
        at javafx.graphics@21.0.8/com.sun.javafx.application.LauncherImpl.lambda$launchApplication1$9(LauncherImpl.java:839)
        at javafx.graphics@21.0.8/com.sun.javafx.application.PlatformImpl.lambda$runAndWait$12(PlatformImpl.java:483)
        at javafx.graphics@21.0.8/com.sun.javafx.application.PlatformImpl.lambda$runLater$10(PlatformImpl.java:456)
        at java.base@21.0.8/java.security.AccessController.executePrivileged(AccessController.java:129)
        at java.base@21.0.8/java.security.AccessController.doPrivileged(AccessController.java:400)
        at javafx.graphics@21.0.8/com.sun.javafx.application.PlatformImpl.lambda$runLater$11(PlatformImpl.java:455)
        at javafx.graphics@21.0.8/com.sun.glass.ui.InvokeLaterDispatcher$Future.run(InvokeLaterDispatcher.java:95)
Caused by: javafx.fxml.LoadException: 
/fxml/home.fxml

        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.constructLoadException(FXMLLoader.java:2722)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.importClass(FXMLLoader.java:2994)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.processImport(FXMLLoader.java:2838)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.processProcessingInstruction(FXMLLoader.java:2773)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:2639)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.loadImpl(FXMLLoader.java:2563)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.load(FXMLLoader.java:2531)
        at dev.cyberjar.config.StageManager.load(StageManager.java:113)
        at dev.cyberjar.config.StageManager.loadRootNode(StageManager.java:102)
        ... 10 more
Caused by: java.lang.ClassNotFoundException: javafx.scene.control.Button
        at java.base@21.0.8/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:52)
        at java.base@21.0.8/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188)
        at java.base@21.0.8/java.lang.ClassLoader.loadClass(ClassLoader.java:121)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.loadTypeForPackage(FXMLLoader.java:3062)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.loadType(FXMLLoader.java:3051)
        at javafx.fxml@21.0.8/javafx.fxml.FXMLLoader.importClass(FXMLLoader.java:2992)
        ... 17 more
```


The same application built with Maven compiles to native image and runs without issues: https://github.com/code-with-bellsoft/raffle