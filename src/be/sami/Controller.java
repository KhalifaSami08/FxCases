package be.sami;

import be.sami.Model.*;
import be.sami.Model.Cell;
import be.sami.Vue.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Controller {

    public static final Stage primaryStage = Main.primaryStage; //Scenes
    private static Player player;
    private static Background backGroundColor;

    private VueGame game;
    private final VueConfiguration configuration;
    private VueBestScore bestScore;
    private VueMenu menu;

    private static Scene menuScene;

    public Controller(Background backGroundColor, Player player) {
        Controller.backGroundColor = backGroundColor;
        Controller.player = player;

        menu = new VueMenu();
        menuScene = menu.getSceneMenu();
        configuration = new VueConfiguration();
        bestScore = new VueBestScore();

        constructGame();
        setOnActionButtons();
    }

//  Show to Main
    public void ShowAll() {
        primaryStage.setScene(menu.getSceneMenu());
        primaryStage.show();
    }

//  Closing Timer when Closing Game or Closing app
    private void manageTimerGame() {
        Controller.primaryStage.setOnCloseRequest(e -> {
            System.out.println("Stage is closing");
            game.getTimer().cancel();
            System.exit(0);
        });
        game.getbReturn().setOnAction(e -> {
            game.getTimer().purge();
            primaryStage.setScene(menuScene);
        });
    }

//  Construct the GameScene
    private void constructGame(){
        game = new VueGame();
        game.getGameBoard().addObserver(game);
        setonActionGameButtons();
        manageTimerGame();
        activeCheatCode();
    }

    /**
     * Set All Scene Action Buttons except Games buttons which
     *  is not instancied with others scenes it will be under this methods
     */
    private void setOnActionButtons() {
        menu.getBtnGameMenu().setOnAction(e -> {
            constructGame();
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
    }


    private void setonActionGameButtons() {
        ArrayList<Cell> allButtons = game.getGameBoard().getAllMyBoxes();
        for (Cell b :
                allButtons) {
            b.getButton().setOnAction(new ButtonLauchGameHandler(b));
        }
    }

    /**
     * Cheat code is implemented in Game Scene but controlled here
     */
    private void activeCheatCode(){
        game.getlName().setOnMouseClicked(e ->{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Cheat Code Activated ! ");
            a.showAndWait();
            for (Cell cell :
                    game.getGameBoard().getAllMyBoxes()) {

                Button b = cell.getButton();
                b.setOnMouseEntered(ev -> {
                    b.setBackground(FactoryLayout.firstBack);
                });
                b.setOnMouseExited(ev -> {
                    b.setBackground(FactoryLayout.secondBack);
                });
            }
            player.setCheatActivated(true);
        });

        game.getlName().setOnMouseEntered(e ->{
            game.getlName().setText("Click for CheatCode ! ");
            for (Cell cell :
                    game.getGameBoard().getAllMyBoxes()) {

                Button b = cell.getButton();
                b.setTextFill(FactoryLayout.firstBackGroundColor);
            }
        });

        game.getlName().setOnMouseExited(e ->{
            game.getlName().setText("Hello : "+player.getName());
            for (Cell cell :
                    game.getGameBoard().getAllMyBoxes()) {

                Button b = cell.getButton();
                b.setTextFill(FactoryLayout.secondBackGroundColor);
            }
        });
    }

    /**
     * Following methods are catching Click buttons,
     * this one is for game set
     */

    //GAME
    private class ButtonLauchGameHandler implements EventHandler<ActionEvent> {

        private final Button b;
        private final Cell cell;

        public ButtonLauchGameHandler(Cell b) {
            this.cell = b;
            this.b = b.getButton();
//            firstButtonClick = GameBoardgetFirstButtonClick();
        }

        @Override
        public void handle(ActionEvent actionEvent) {

            //Already checked or confirmed
            if (cell.isOk()) {

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("You Cannot Change this Case ! \n The game has not started yet or button is already validated ! ");
                a.showAndWait();

            //click ok
            } else {

                //first click
                if (GameBoard.getFirstButtonClick() == null) {

                    GameBoard.setFirstButtonClick(cell);
                    GameBoard.getFirstButtonClick().setOk(true);
                    game.getGameBoard().changeButtonBackcolorOnClick(b);

                //second click
                } else {

                    //match
                    if (GameBoard.getFirstButtonClick().getButton().getText().equalsIgnoreCase(b.getText())) {

                        game.getGameBoard().validateAndOk(cell, player);

                    //not match
                    } else {

                        game.getGameBoard().validateNotOk(cell, player);

                    }
                    GameBoard.setFirstButtonClick(null);
                }
                //END GAME
                if (game.getGameBoard().checkEndGame()) {
                    game.getGameBoard().endGame(player,game.getTimerSeconds());
                    bestScore = new VueBestScore();
                    primaryStage.setScene(getSceneMenu());
                }
            }
        }
    }

//  Config Confirmation buttons handlers
    private class ButtonConfigOk implements EventHandler<ActionEvent> {

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

//  Config Difficulty buttons handlers
    private class ButtonConfigHandlerColor implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Background b = getPref(actionEvent);
            backGroundColor = b;
            game.getvBxGame().setBackground(b);
            configuration.getvBxConfig().setBackground(b);
            configuration.getvBoxConfigHelp().setBackground(b);
            bestScore.getvBxScore().setBackground(b);
            menu.getvBxMenu().setBackground(b);
        }

        private Background getPref(ActionEvent e) {
            Background b;

            if (e.getSource() == configuration.getbThemeDark()) {
                b = Theme.DARK;
            } else if (e.getSource() == configuration.getbThemeGreen()) {
                b = Theme.GREEN;
            } else if (e.getSource() == configuration.getbThemeRed()) {
                b = Theme.RED;
            } else {
                throw new RuntimeException("Error GET PREFERENCES");
            }
            return b;
        }
    }

//  Config Colors buttons handlers
    private class ButtonConfigHandlerDifficulty implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            player.setDifficulty(getDifficulty(actionEvent));
            configuration.getlDiff().setText("Difficulty : " + getDifficulty(actionEvent));
        }

        private Difficulty getDifficulty(ActionEvent e) {
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
