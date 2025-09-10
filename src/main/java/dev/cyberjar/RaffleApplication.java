package dev.cyberjar;


import dev.cyberjar.config.FxmlView;
import dev.cyberjar.config.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class RaffleApplication extends Application {

    public static Stage stage;

    private final StageManager stageManager = StageManager.getInstance();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void stop() {
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stageManager.setStage(primaryStage);
        showHomeScene();
    }

    private void showHomeScene() {
        stageManager.switchScene(FxmlView.HOME);
    }

}
