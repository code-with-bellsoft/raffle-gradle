package dev.cyberjar.controller;


import dev.cyberjar.config.FxmlView;
import dev.cyberjar.config.StageManager;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class ToolbarController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private Button fullScreenButton;

    @FXML
    private Button exitButton;

    private final StageManager stageManager = StageManager.getInstance();

    private static final PseudoClass maximizeIcon = PseudoClass.getPseudoClass("max");
    private static final PseudoClass minimizeIcon = PseudoClass.getPseudoClass("min");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (stageManager.isStageFullScreen()) {
            setWindowedGraphicsAndAction();
        } else {
            setFullScreenGraphicsAndAction();
        }
    }


    public void goToHomeScreen() {
        stageManager.clearUserData();
        stageManager.setRaffleDurationMs(15);
        stageManager.switchToNextScene(FxmlView.HOME);
    }

    public void goFullScreen() {
        stageManager.switchToFullScreenMode();
        setWindowedGraphicsAndAction();
    }


    public void goWindowed() {
        stageManager.switchToWindowedMode();
        setFullScreenGraphicsAndAction();

    }


    public void quitApp() {
        stageManager.exit();
    }

    void setFullScreenGraphicsAndAction() {

        fullScreenButton.pseudoClassStateChanged(minimizeIcon, false);
        fullScreenButton.pseudoClassStateChanged(maximizeIcon, true);

        fullScreenButton.setOnAction(e -> goFullScreen());

    }

    public void setWindowedGraphicsAndAction() {

        fullScreenButton.pseudoClassStateChanged(maximizeIcon, false);
        fullScreenButton.pseudoClassStateChanged(minimizeIcon, true);

        fullScreenButton.setOnAction(e -> goWindowed());
    }

}
