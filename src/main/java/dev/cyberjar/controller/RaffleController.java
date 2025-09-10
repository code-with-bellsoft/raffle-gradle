package dev.cyberjar.controller;


import dev.cyberjar.config.StageManager;
import javafx.animation.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;


public class RaffleController implements Initializable {

    public static final int POTENTIAL_WINNERS_LIST = 300;
    public static int TARGET_DURATION_MS;

    @FXML
    private Label dataLabel;

    @FXML
    private Button presentButton;

    @FXML
    private ImageView topImage;

    @FXML
    private ImageView bottomImage;

    @FXML
    private Button repeatButton;

    private Set<String> names = new HashSet<>();

    StringProperty name = new SimpleStringProperty();

    private final StageManager stageManager = StageManager.getInstance();

    @FXML
    void congratulate(ActionEvent event) {
        name.setValue("Congratulations, " + name.getValue() + "!");
        presentButton.setVisible(false);
        presentButton.setDisable(true);
        repeatButton.setVisible(false);
        repeatButton.setDisable(true);
        playWinnerAnimation();

    }

    @FXML
    void removeCandidateAndRepeat() {
        names.remove(name.getValue());
        shuffleAndDisplayNames();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataLabel.textProperty().bind(name);
        this.names = stageManager.getUserData();
        setTargetDurationMs(stageManager.getRaffleDurationMs());
        shuffleAndDisplayNames();
    }


    public void shuffleAndDisplayNames() {
        List<String> intermediateNameHolder = new ArrayList<>(names);
        shuffle(intermediateNameHolder);

        while (intermediateNameHolder.size() < POTENTIAL_WINNERS_LIST) {
            List<String> tmp = new ArrayList<>(intermediateNameHolder);
            shuffle(tmp);
            intermediateNameHolder.addAll(tmp);
        }

        if (intermediateNameHolder.size() > POTENTIAL_WINNERS_LIST) {
            intermediateNameHolder = intermediateNameHolder.subList(0, POTENTIAL_WINNERS_LIST);
        }

        playAnimation(intermediateNameHolder);
    }


    private void playAnimation(List<String> shuffledNames) {

        double totalDuration = 0.0;

        Timeline rollingNames = new Timeline();

        Interpolator interpolator = new Interpolator() {
            @Override
            protected double curve(double t) {
                return Math.sin((t * Math.PI) / 2);
            }
        };

        for (int i = 0; i < shuffledNames.size(); i++) {

            double dur = interpolator.interpolate(1, 1000, (double) i / shuffledNames.size());
            totalDuration += dur;
            var index = i;
            KeyFrame frame = new KeyFrame(Duration.millis(totalDuration), e -> name.setValue(shuffledNames.get(index)));
            rollingNames.getKeyFrames().add(frame);
            if (totalDuration > TARGET_DURATION_MS) {
                break;
            }
        }

        TranslateTransition raffleTransition = new TranslateTransition();
        raffleTransition.setDuration(Duration.millis(TARGET_DURATION_MS));
        raffleTransition.setNode(presentButton);
        presentButton.setVisible(false);
        presentButton.setDisable(true);
        repeatButton.setVisible(false);
        repeatButton.setDisable(true);
        raffleTransition.setOnFinished(e -> {
            PauseTransition delay = new PauseTransition(Duration.millis(1000));
            delay.setOnFinished(ev -> {
                presentButton.setVisible(true);
                presentButton.setDisable(false);
                repeatButton.setVisible(true);
                repeatButton.setDisable(false);
            });
            delay.play();
        });
        raffleTransition.play();

        rollingNames.play();
    }


    private void shuffle(List<String> names) {
        Collections.shuffle(names);
    }

    private void playWinnerAnimation() {

        Image fireworks = new Image(String.valueOf(RaffleController.class
                .getClassLoader()
                .getResource("graphics/fireworks.gif")));
        topImage.setImage(fireworks);
        topImage.setFitHeight(500);
        topImage.setPreserveRatio(true);
        bottomImage.setImage(fireworks);
        bottomImage.setFitHeight(500);
        bottomImage.setPreserveRatio(true);


        ScaleTransition scale = new ScaleTransition(Duration.seconds(1), dataLabel);
        scale.setByX(0.5);
        scale.setByY(0.5);
        scale.setCycleCount(4);
        scale.setAutoReverse(true);
        scale.play();
    }

    public Set<String> getNames() {
        return names;
    }

    public int getTargetDurationMs() {
        return TARGET_DURATION_MS;
    }

    public void setTargetDurationMs(int targetDurationMs) {
        TARGET_DURATION_MS = targetDurationMs;
    }
}

