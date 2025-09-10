package dev.cyberjar.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class StageManager {

    private Stage primaryStage;

    private Set<String> userData = new HashSet<>();

    //default duration
    private int raffleDurationMs = 15000;

    private static volatile StageManager instance;

    public static StageManager getInstance() {
        StageManager localInstance = instance;
        if (localInstance == null) {
            synchronized (StageManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new StageManager();
                }
            }
        }
        return localInstance;
    }


    public Set<String> getUserData() {
        return userData;
    }

    public void collectUserData(List<String> list, boolean needMasking) {
        if (needMasking) {
            int i = 0;
            for (String email : list) {
                if (email.contains("@")) {
                    list.set(i, email.replaceAll(email.substring(
                            email.indexOf('@')), "*****"));
                } else {
                    list.set(i, email);
                }
                i++;
            }
        }

        userData.addAll(list);
    }

    public void clearUserData() {
        userData.clear();
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Parent rootNode = loadRootNode(view.getFxmlPath());


        Scene scene = new Scene(rootNode);
        String stylesheet = Objects.requireNonNull(getClass()
                        .getResource("/styles/styles.css"))
                .toExternalForm();

        scene.getStylesheets().add(stylesheet);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToNextScene(final FxmlView view) {

        Parent rootNode = loadRootNode(view.getFxmlPath());
        primaryStage.getScene().setRoot(rootNode);

        primaryStage.show();
    }


    private Parent loadRootNode(String fxmlPath) {
        Parent rootNode;
        try {
            rootNode = load(fxmlPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootNode;
    }

    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        loader.setClassLoader(getClass().getClassLoader());
        return loader.load();
    }

    public void switchToFullScreenMode() {
        primaryStage.setFullScreen(true);
    }

    public void switchToWindowedMode() {
        primaryStage.setFullScreen(false);
    }

    public boolean isStageFullScreen() {
        return primaryStage.isFullScreen();
    }

    public void exit() {
        primaryStage.close();
    }

    public int getRaffleDurationMs() {
        return raffleDurationMs;
    }

    public void setRaffleDurationMs(int raffleDurationS) {
        this.raffleDurationMs = raffleDurationS * 1000;
    }
}
