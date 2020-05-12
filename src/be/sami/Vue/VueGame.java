package be.sami.Vue;

import be.sami.Controller;
import be.sami.Model.*;
import be.sami.Model.Observer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class VueGame implements Observer {

    private final GameBoard gameBoard;
    private final Label lName, lTimer;
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
        lTimer = FactoryLayout.createLabel("Time : " + timerSeconds);
        gameBoard = new GameBoard();

        initGame();
        initTimer();
    }

    private void initGame() {

        lScore = FactoryLayout.createLabel("Score : " + 0);
        bReturn = FactoryLayout.generateButtonReturnHome();

        vBxGame = FactoryLayout.createVBOX(lName, lTimer, gameBoard.getGridPane(), lScore, bReturn);
        SceneGame = new Scene(vBxGame, 600, 700);
    }

    /**
     *
     * Following Method depend of the difficulty of the player,and depending to it,
     * and it is used for displaying remaining time before the game start
     *
     */
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

                for (Cell allbox : gameBoard.getAllMyBoxes()) {

                    Button b = allbox.getButton();
                    Platform.runLater(() -> {
                        //if Game not started yet
                        if (timerSeconds < attributeVisibleButtonsTime()) {
                            allbox.setOk(true);
                            lTimer.setText("Time Left Before Start Game : " + (attributeVisibleButtonsTime() - timerSeconds));
                            b.setTextFill(FactoryLayout.firstBackGroundColor);

                            b.setOnMouseEntered(e -> {
                                b.setBackground(FactoryLayout.firstBack);
                                b.setTextFill(FactoryLayout.secondBackGroundColor);
                            });

                            b.setOnMouseExited(e -> {
                                b.setBackground(FactoryLayout.secondBack);
                                b.setTextFill(FactoryLayout.firstBackGroundColor);
                            });

                        //if Game started
                        } else if (attributeVisibleButtonsTime() == timerSeconds) {
                            allbox.setOk(false);
                            lTimer.setText("Time : " + timerSeconds);
                            b.setTextFill(FactoryLayout.secondBackGroundColor);

                            b.setOnMouseEntered(e -> {
                                b.setBackground(FactoryLayout.firstBack);
                                b.setTextFill(FactoryLayout.firstBackGroundColor);
                            });

                            b.setOnMouseExited(e -> {
                                b.setBackground(FactoryLayout.secondBack);
                                b.setTextFill(FactoryLayout.secondBackGroundColor);
                            });
                        }
                        //only for timer
                        else{
                            lTimer.setText("Time : " +timerSeconds);
                        }
                    });

                }
                timerSeconds++;
            }
        };
        getTimer().schedule(getTimerTask(), 1000, 1000);
    }

    public VBox getvBxGame() {
        return vBxGame;
    }

    public Scene getSceneGame() {
        return SceneGame;
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

    public Label getlName() {
        return lName;
    }

//  for Observer
    @Override
    public void modify(Object obj, int param) {

        if (obj instanceof Player) {
            Player player = (Player) obj;
            lScore.setText("Score : " + player.getScore());
        } else {

            ArrayList<Cell> cellOk = (ArrayList<Cell>) obj;

            if (param == 1) {
                cellOk.get(0).setOk(true);
                cellOk.get(1).setOk(true);
                gameBoard.changeButtonBackcolorOnClick(cellOk.get(1).getButton());
            } else if (param == 2) {
                cellOk.get(0).setOk(false);
                cellOk.get(1).setOk(false);
                gameBoard.resetButtonifClickNotMatch(cellOk.get(0).getButton(), cellOk.get(1).getButton());
            }

            System.out.println("Modifying GameBoard");
        }
    }
}

