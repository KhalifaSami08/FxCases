package be.sami;

import be.sami.Model.*;
import be.sami.Vue.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {

    public static final Stage primaryStage = Main.primaryStage; //Scenes
    private static Player player;
    private static Background backGroundColor;
    private static Boxes firstButtonClick;

    private VueGame game;
    private final VueConfiguration configuration;
    private VueBestScore bestScore;
    private final VueMenu menu;
    private static Scene menuScene;

    public Controller(Background backGroundColor, Player player) {
        Controller.backGroundColor = backGroundColor;
        Controller.player = player;

        game = new VueGame();
        game.getGameBoard().addObserver(game);

        configuration = new VueConfiguration();
        bestScore = new VueBestScore();
        menu = new VueMenu();
        menuScene = menu.getSceneMenu();
        setOnActionButtons();
        manageTimerGame();
        activeCheatCode();
    }

    public void ShowAll() {
        primaryStage.setScene(menu.getSceneMenu());
        primaryStage.show();
    }

    private void manageTimerGame() {

        Controller.primaryStage.setOnCloseRequest(e -> {
            System.out.println("Stage is closing");
            game.getTimer().cancel();
        });
        game.getbReturn().setOnAction(e -> {
            game.getTimer().purge();
            primaryStage.setScene(menuScene);
        });
    }

    private void setOnActionButtons() {

        menu.getBtnGameMenu().setOnAction(e -> {
            primaryStage.setScene(getSceneGame());
        });
        menu.getBtnConfigMenu().setOnAction(e -> {
            primaryStage.setScene(getSceneConfig());
        });
        menu.getBtnBestScoreMenu().setOnAction(e -> {
            primaryStage.setScene(getSceneScore());
        });

        configuration.getbThemeDark().setOnAction(new ButtonConfigHandlerColor());
        configuration.getbThemeGreen().setOnAction(new ButtonConfigHandlerColor());
        configuration.getbThemeRed().setOnAction(new ButtonConfigHandlerColor());

        configuration.getbEasy().setOnAction(new ButtonConfigHandlerDifficulty());
        configuration.getbMedium().setOnAction(new ButtonConfigHandlerDifficulty());
        configuration.getbHard().setOnAction(new ButtonConfigHandlerDifficulty());

        configuration.getbOk().setOnAction(new ButtonConfigOk());

        setonActionGameButtons();
    }

    private void setonActionGameButtons() {
        ArrayList<Boxes> allButtons = game.getGameBoard().getAllboxes();
        for (Boxes b :
                allButtons) {
            b.getButton().setOnAction(new ButtonLauchGameHandler(b));
        }
    }

    private void activeCheatCode(){

        game.getlName().setOnMouseClicked(e ->{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Cheat Code Activated ! ");
            a.showAndWait();
            for (Boxes boxes :
                    game.getGameBoard().getAllboxes()) {

                Button b = boxes.getButton();
                b.setOnMouseEntered(ev -> {
                    b.setBackground(FactoryLayout.firstBack);
                });
                b.setOnMouseExited(ev -> {
                    b.setBackground(FactoryLayout.secondBack);
                });
            }
            player.setCheatActivated(true);
        });
    }

    //GAME
    public class ButtonLauchGameHandler implements EventHandler<ActionEvent> {

        private final Button b;
        private final Boxes boxes;

        public ButtonLauchGameHandler(Boxes b) {
            this.boxes = b;
            this.b = b.getButton();
            firstButtonClick = game.getGameBoard().getFirstButtonClick();
        }

        @Override
        public void handle(ActionEvent actionEvent) {

            //Already checked or confirmed
            if (boxes.isOk()) {

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("You Cannot Change this Case ! \n The game has not started yet or button is already validated ! ");
                a.showAndWait();

            //click ok
            } else {

                //first click
                if (firstButtonClick == null) {

                    firstButtonClick = boxes;
                    firstButtonClick.setOk(true);
                    game.getGameBoard().changeButtonBackcolorOnClick(b);

                //second click
                } else {

                    //match
                    if (firstButtonClick.getButton().getText().equalsIgnoreCase(b.getText())) {

                        game.getGameBoard().validateAndOk(firstButtonClick,boxes, player);

                    //not match
                    } else {

                        game.getGameBoard().validateNotOk(firstButtonClick,boxes, player);

                    }
                    firstButtonClick = null;
                }
                //END GAME
                if (game.getGameBoard().checkEndGame()) {
                    endGame();
                }
            }
        }

        private void endGame(){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Win");
            a.setContentText("Congratulations ! you winned with " + player.getScore() + " points ! ");
            a.showAndWait();

            player.setSeconds(game.getTimerSeconds());
            AllPlayers.WriteFile(player);
            bestScore = new VueBestScore();
            primaryStage.setScene(getSceneMenu());
        }
    }

    //CONFIG
    public class ButtonConfigOk implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Alert a;
            if (configuration.gettXfEditName().getText().equals("")) {
                a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Not Complete");
                a.setContentText("Your name is empty ! ");
            } else {
                player.setName(configuration.gettXfEditName().getText());
                a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Ok");
                a.setContentText("Player Updated ! \n Your name is : " + player.getName() + "\n and Your difficulty is : " + player.getDifficulty());

                setGame(new VueGame());
                setonActionGameButtons();
                primaryStage.setScene(getSceneMenu());
            }
            a.showAndWait();
        }
    }

    public class ButtonConfigHandlerColor implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Background b = getPersonPref(actionEvent);
            backGroundColor = b;
            game.getvBxGame().setBackground(b);
            configuration.getvBxConfig().setBackground(b);
            configuration.getvBoxConfigHelp().setBackground(b);
            bestScore.getvBxScore().setBackground(b);
            menu.getvBxMenu().setBackground(b);
        }

        private Background getPersonPref(ActionEvent e) {
            Background b;

            if (e.getSource() == configuration.getbThemeDark()) {
                b = Theme.Dark;
            } else if (e.getSource() == configuration.getbThemeGreen()) {
                b = Theme.Green;
            } else if (e.getSource() == configuration.getbThemeRed()) {
                b = Theme.Red;
            } else {
                throw new RuntimeException("Error GET PREFERENCES");
            }
            return b;
        }
    }

    public class ButtonConfigHandlerDifficulty implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            player.setDifficulty(getNb(actionEvent));
            configuration.getlDiff().setText("Difficulty : " + getNb(actionEvent));
        }

        private Difficulty getNb(ActionEvent e) {
            Difficulty i;

            if (e.getSource() == configuration.getbEasy()) {
                i = Difficulty.EASY;
            } else if (e.getSource() == configuration.getbMedium()) {
                i = Difficulty.MEDIUM;
            } else if (e.getSource() == configuration.getbHard()) {
                i = Difficulty.HARD;
            } else {
                throw new RuntimeException("Error Get NB CASES");
            }
            return i;
        }
    }

    public static Scene getMenuScene() {
        return menuScene;
    }

    public void setGame(VueGame game) {
        this.game = game;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Background getBackGroundColor() {
        return backGroundColor;
    }

    public Scene getSceneMenu() {
        return menu.getSceneMenu();
    }

    public Scene getSceneGame() {
        return game.getSceneGame();
    }

    public Scene getSceneConfig() {
        return configuration.getSceneConfig();
    }

    public Scene getSceneScore() {
        return bestScore.getSceneScore();
    }
}
