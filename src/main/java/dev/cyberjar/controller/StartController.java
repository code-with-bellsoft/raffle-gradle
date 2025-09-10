package dev.cyberjar.controller;


import dev.cyberjar.config.FxmlView;
import dev.cyberjar.config.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;


public class StartController implements Initializable {

    @FXML
    private Button startButton;

    @FXML
    private ComboBox<Integer> durationBox;

    private final StageManager stageManager = StageManager.getInstance();


    @FXML
    void setDuration() {
        stageManager.setRaffleDurationMs(durationBox.getValue());
    }

    @FXML
    void startRaffle() throws Exception {
        stageManager.switchToNextScene(FxmlView.RAFFLE);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Integer[] durationChoices = {5, 10, 15};
        durationBox.getItems().addAll(durationChoices);
    }
}
