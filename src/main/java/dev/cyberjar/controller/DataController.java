package dev.cyberjar.controller;


import dev.cyberjar.config.FxmlView;
import dev.cyberjar.config.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class DataController implements Initializable {

    @FXML
    private TextArea text;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Button continueButton;

    private final StageManager stageManager = StageManager.getInstance();



    @FXML
    void goToStartScene() throws Exception {

        if (!text.getText().isEmpty()) {
            stageManager.collectUserData(text.getText()
                    .lines()
                    .collect(Collectors.toList()),
                    checkBox.isSelected());
            stageManager.switchToNextScene(FxmlView.START);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
