package be.sami;

import be.sami.Model.AllPlayers;
import be.sami.Model.Difficulty;
import be.sami.Model.Player;
import be.sami.Model.Theme;
import be.sami.Vue.VueBestScore;
import be.sami.Vue.VueConfiguration;
import be.sami.Vue.VueGame;
import be.sami.Vue.VueMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Controller {

    public static final Stage primaryStage = Main.primaryStage; //Scenes

    public static Player player;
    public static Background backGroundColor;

    public static Scene SceneMenu,SceneGame,SceneConfig,SceneScore; //Scenes
    public static VBox vBxMenu,vBxGame,vBxScore; //For changing Background


    public Controller(Background backGroundColor, Player player) {
        Controller.backGroundColor = backGroundColor;
        Controller.player = player;

        initController();
    }

    private void initController() {

        //Game
        setGame();

        //Configuration
        setConfiguration();

        //Score
        setScore();

        //MENU
        setMenu();


    }
    private void setGame() {
        VueGame game = new VueGame();
        SceneGame = game.getSceneGame();
        vBxGame = game.getvBxGame();
    }

    private void setConfiguration() {
        VueConfiguration configuration = new VueConfiguration();
        SceneConfig = configuration.getSceneConfig();
    }

    private void setScore() {
        VueBestScore bestScore = new VueBestScore();
        SceneScore = bestScore.getSceneScore();
        vBxScore = bestScore.getvBxScore();
    }

    private void setMenu(){
        VueMenu menu = new VueMenu();
        SceneMenu = menu.getSceneMenu();
        vBxMenu = menu.getvBxMenu();
    }

    public void ShowAll() {
        primaryStage.setScene(SceneMenu);
        primaryStage.show();
    }

    //GAME
    public static class ButtonLauchGameHandler implements EventHandler<ActionEvent> {

        Button firstButtonClick = VueGame.firstButtonClick;
        Background backgroundOnClick = VueGame.backgroundOnClick;
        Button[][] btn = VueGame.btn;
        boolean[][] isOK = VueGame.isOK;
        Label lScore = VueGame.lScore;

        Button b;
        int i, j;

        public ButtonLauchGameHandler(Button b, int i, int j) {
            this.b = b;
            this.i = i;
            this.j = j;
        }

        @Override
        public void handle(ActionEvent actionEvent) {

            if (firstButtonClick == b) {

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("You Cannot Change this Case Again ! ");
                a.showAndWait();


            } else { //click ok

                System.out.println("click ok");

                if (firstButtonClick == null) { //first click

                    firstButtonClick = b;
                    b.setBackground(backgroundOnClick);
                    b.setTextFill(FactoryLayout.firstBackGroundColor);
                    b.setOnMouseExited(e -> {
                        b.setBackground(backgroundOnClick);
                    });
                    System.out.println("firstbtn null");

                } else { //second click


                    if (firstButtonClick.getText().equalsIgnoreCase(b.getText())) { //match

                        b.setBackground(backgroundOnClick);
                        b.setTextFill(FactoryLayout.firstBackGroundColor);
                        b.setOnMouseExited(e -> {
                            b.setBackground(backgroundOnClick);
                        });
                        isOK[i][j] = true;

                        //isOK[][j] = true;
                        System.out.println("first not null and egal");

                        player.setScore(player.getScore() + 1);

                    } else { //not match

                        firstButtonClick.setBackground(FactoryLayout.secondBack);
                        b.setBackground(FactoryLayout.secondBack);

                        firstButtonClick.setTextFill(FactoryLayout.secondBackGroundColor);
                        System.out.println("first not null and not egal");

                        player.setScore(player.getScore() - 1);
                    }
                    firstButtonClick = null;
                    lScore.setText("Score : " + player.getScore());
                }
            }


        }
    }

    //CONFIG

    public static class ButtonConfigOk implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            Alert a;
            if(VueConfiguration.tXfEditName.getText().equals("")){
                a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Not Complete");
                a.setContentText("Your name is empty ! ");
            }
            else{
                a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Ok");
                a.setContentText("Player Updated ! ");

                player.setName(VueConfiguration.tXfEditName.getText());
                //System.out.printf("Name : %s \n Diff : %s \n Back : %s",player.getName(),player.getDifficulty(),player.getBackColor());
                primaryStage.setScene(Controller.SceneMenu);
            }
            a.showAndWait();
        }
    }
    public static class ButtonConfigHandlerColor implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Background b = getPersonPref(actionEvent);
            Controller.backGroundColor = b;
            Controller.vBxGame.setBackground(b);
            VueConfiguration.vBxConfig.setBackground(b);
            VueConfiguration.vBoxConfigHelp.setBackground(b);
            Controller.vBxScore.setBackground(b);
            Controller.vBxMenu.setBackground(b);
        }

        private Background getPersonPref(ActionEvent e){
            Background b;


                if (e.getSource() == VueConfiguration.bThemeDark ) {
                    b = Theme.Dark;
                } else if (e.getSource() == VueConfiguration.bThemeGreen) {
                    b = Theme.Green;
                } else if (e.getSource() == VueConfiguration.bThemeRed ) {
                    b = Theme.Red;
                }
                else{
                    throw new RuntimeException("Error GET PREFERENCES");
                }

                return b;
        }
    }
    public static class ButtonConfigHandlerDifficulty implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            player.setDifficulty(getNb(actionEvent)); ;
        }
        private Difficulty getNb(ActionEvent e){
            Difficulty i;

            if(e.getSource() == VueConfiguration.bEasy){
                i = Difficulty.EASY;
            }
            else if(e.getSource() == VueConfiguration.bMedium){
                i = Difficulty.MEDIUM;
            }
            else if(e.getSource() == VueConfiguration.bHard){
                i = Difficulty.HARD;
            }
            else{
                throw new RuntimeException("Error Get NB CASES");
            }
            return i;
        }
    }
}
