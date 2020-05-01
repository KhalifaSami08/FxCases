package be.sami.Vue;

import be.sami.Controller;
import be.sami.Model.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Timer;
import java.util.TimerTask;


public class VueGame {

    private final GameBoard gameBoard;
    private final Label lName,lTimer;
    private Label lScore;
    private Button bReturn;

    private Timer timer;
    private TimerTask timerTask;
    private int timerSeconds;

    private VBox vBxGame;
    private Scene SceneGame;

    public VueGame() {

        timerSeconds = 0;
        timer = null;
        timerTask = null;

        lName = FactoryLayout.createLabel("Hello " + Controller.getPlayer().getName() + " ! ");
        lTimer = FactoryLayout.createLabel("Time : "+timerSeconds);
        gameBoard = new GameBoard();

        initGame();
        initTimer();
        activeCheatCode();
    }

    private int attributeVisibleButtonsTime() {
        int buttonsGameVisible;
        switch (Controller.getPlayer().getDifficulty()) {
            case EASY:
                buttonsGameVisible = 10;
                break;
            case MEDIUM:
                buttonsGameVisible = 15;
                break;
            case HARD:
                buttonsGameVisible = 20;
                break;
            default:
                buttonsGameVisible = 10000;
        }
        return buttonsGameVisible;
    }

    private void initTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater( ()-> { lTimer.setText("Time : "+timerSeconds); });

                for (Boxes allbox : gameBoard.getAllboxes()) {

                    Button b = allbox.getButton();

                    if (timerSeconds < attributeVisibleButtonsTime()) {
                        allbox.setOk(true);
                        b.setTextFill(FactoryLayout.firstBackGroundColor);

                        b.setOnMouseEntered(e -> {
                            b.setBackground(FactoryLayout.firstBack);
                            b.setTextFill(FactoryLayout.secondBackGroundColor);
                        });

                        b.setOnMouseExited( e -> {
                            b.setBackground(FactoryLayout.secondBack);
                            b.setTextFill(FactoryLayout.firstBackGroundColor);
                        });

                    } else if (attributeVisibleButtonsTime() == timerSeconds) {
                        allbox.setOk(false);
                        b.setTextFill(FactoryLayout.secondBackGroundColor);

                        b.setOnMouseEntered(e -> {
                            b.setBackground(FactoryLayout.firstBack);
                            b.setTextFill(FactoryLayout.firstBackGroundColor);
                        });

                        b.setOnMouseExited( e -> {
                            b.setBackground(FactoryLayout.secondBack);
                            b.setTextFill(FactoryLayout.secondBackGroundColor);
                        });
                    }
                }
                timerSeconds++;
            }
        };
        getTimer().schedule(getTimerTask(),1000,1000);
    }

    private void initGame() {

        lScore = FactoryLayout.createLabel("Score : " + 0);
        bReturn = FactoryLayout.generateButtonReturnHome();

        vBxGame = FactoryLayout.createVBOX(lName, lTimer, gameBoard.getGridPane(), lScore, bReturn);
        SceneGame = new Scene(vBxGame, 600, 700);
    }

    private void activeCheatCode(){

        lName.setOnMouseClicked(e ->{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Cheat Code Activated ! ");
            a.showAndWait();
            for (Boxes boxes :
                    gameBoard.getAllboxes()) {

                Button b = boxes.getButton();

                b.setOnMouseEntered(ev -> {
                    b.setBackground(FactoryLayout.firstBack);
//                  b.setTextFill(FactoryLayout.secondBackGroundColor);
                });
                b.setOnMouseExited(ev -> {
                    b.setBackground(FactoryLayout.secondBack);
                });
            }
        });

    }

    public VBox getvBxGame() {
        return vBxGame;
    }

    public Scene getSceneGame() {
        return SceneGame;
    }

    public Label getlScore() {
        return lScore;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Timer getTimer() {
        return timer;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public int getTimerSeconds() {
        return timerSeconds;
    }

    public Button getbReturn() {
        return bReturn;
    }
}

