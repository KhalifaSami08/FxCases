package be.sami.Vue;

import be.sami.Controller;
import be.sami.FactoryLayout;
import be.sami.Model.Difficulty;
import be.sami.Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.swing.text.Position;

public class VueGame {

    private VBox vBxGame;
    private Scene SceneGame;

    private static final Player player = Controller.player;

    Button[][] btn;
    boolean[][] isOK; // if dual set is ok, not check anymore
    Button firstButtonClick;
    Background backgroundOnClick;

    int gridNb_Rows;
    int gridNb_Col;

    Label lName, lScore;
    GridPane gridPane;
    Button bReturn;

     Position pos;

    public VueGame(){
        setGame();
    }

    public void setGame() {

        gridNb_Rows = Difficulty.CastDifficultyString(player.getDifficulty());
        gridNb_Col = Difficulty.CastDifficultyString(player.getDifficulty());
        backgroundOnClick = new Background(new BackgroundFill(Color.rgb(180, 200, 245), null, null));
        btn = new Button[gridNb_Rows][gridNb_Col];
        isOK = new boolean[gridNb_Rows][gridNb_Col];
        firstButtonClick = null;

        initGame();
        initButtonsValues();
        randomiseButtons();
        dispaygame();

    }

    private void dispaygame() {
        lScore = FactoryLayout.createLabel("Score : ");
        bReturn = FactoryLayout.generateButtonReturnHome();

        vBxGame = FactoryLayout.createVBOX(lName, gridPane, lScore, bReturn);
        SceneGame = new Scene(vBxGame, 500, 650);
    }


    private void randomiseButtons() {

        int nb_sort = (int) (Math.random() * (1000 - 500) + 500);
        for (int i = 0; i < nb_sort; i++) {

            int a = (int) (Math.random() * gridNb_Rows);
            int b = (int) (Math.random() * gridNb_Col);
            String temp;

            temp = btn[a][b].getText();
            btn[a][b].setText(btn[b][a].getText());
            btn[b][a].setText(temp);
        }
    }

    private void initButtonsValues() {
        int cpt = 0;
        for (int i = 0; i < gridNb_Rows; i++) {
            for (int j = 0; j < gridNb_Col; j++) {
                int nbr = ((cpt++) % ((gridNb_Rows * gridNb_Col / 2)));
                btn[i][j].setText("b" + nbr);
                isOK[i][j] = false;
            }
        }
    }

    private void initGame() {
        lName = FactoryLayout.createLabel("Hello " + player.getName() + " ! ");

        gridPane = new GridPane();
        gridPane.setMinSize(400, 300);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(1);
        gridPane.setVgap(1);


        for (int i = 0; i < gridNb_Rows; i++) {

            for (int j = 0; j < gridNb_Col; j++) {

                Button b = new Button("");
                b.setPadding(new Insets(15));
                b.setBackground(FactoryLayout.secondBack);
                b.setTextFill(FactoryLayout.secondBackGroundColor);
                b.setOnMouseEntered(e -> {
                    b.setBackground(FactoryLayout.firstBack);
                });
                b.setOnMouseExited(e -> {
                    b.setBackground(FactoryLayout.secondBack);
                });

                btn[i][j] = b;
                b.setOnAction(new ButtonLauchGameHandler(b, i, j));
                gridPane.add(b, j, i);
            }
        }
    }

    public VBox getvBxGame() {
        return vBxGame;
    }

    public Scene getSceneGame() {
        return SceneGame;
    }

    private class ButtonLauchGameHandler implements EventHandler<ActionEvent> {

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

                if (firstButtonClick == null) { //first click

                    firstButtonClick = b;
                    b.setBackground(backgroundOnClick);
                    b.setTextFill(FactoryLayout.firstBackGroundColor);
                    b.setOnMouseExited(e -> {
                        b.setBackground(backgroundOnClick);
                    });

                } else { //second click

                    if (firstButtonClick.getText().equalsIgnoreCase(b.getText())) { //match

                        b.setBackground(backgroundOnClick);
                        b.setTextFill(FactoryLayout.firstBackGroundColor);
                        b.setOnMouseExited(e -> {
                            b.setBackground(backgroundOnClick);
                        });
                        isOK[i][j] = true;

                        //isOK[][j] = true;
                        player.setScore(player.getScore() + 1);

                    } else { //not match

                        firstButtonClick.setBackground(FactoryLayout.secondBack);
                        b.setBackground(FactoryLayout.secondBack);

                        firstButtonClick.setTextFill(FactoryLayout.secondBackGroundColor);
                        player.setScore(player.getScore() - 1);
                    }
                    firstButtonClick = null;
                    lScore.setText("Score : " + player.getScore());
                }
            }


        }
    }
}

