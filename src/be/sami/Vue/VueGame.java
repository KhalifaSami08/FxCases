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

    private static final Player player = Controller.player;

    public static Button[][] btn;
    public static boolean[][] isOK; // if dual set is ok, not check anymore
    public static Button firstButtonClick;
    public static Background backgroundOnClick;
    public static Label lScore;

    int gridNb_Rows;
    int gridNb_Col;

    Label lName;
    GridPane gridPane;
    Button bReturn;

    Position pos;

    private VBox vBxGame;
    private Scene SceneGame;

    public VueGame(){

        gridNb_Rows = Difficulty.CastDifficultyString(player.getDifficulty());
        gridNb_Col = Difficulty.CastDifficultyString(player.getDifficulty());
        backgroundOnClick = new Background(new BackgroundFill(Color.rgb(180, 200, 245), null, null));
        btn = new Button[gridNb_Rows][gridNb_Col];
        isOK = new boolean[gridNb_Rows][gridNb_Col];
        firstButtonClick = null;

        initGame();
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
                b.setOnAction(new Controller.ButtonLauchGameHandler(b, i, j));
                gridPane.add(b, j, i);
            }
        }

        int cpt = 0;
        for (int i = 0; i < gridNb_Rows; i++) {
            for (int j = 0; j < gridNb_Col; j++) {
                int nbr = ((cpt++) % ((gridNb_Rows * gridNb_Col / 2)));
                btn[i][j].setText("b" + nbr);
                isOK[i][j] = false;
            }
        }

        int nb_sort = (int) (Math.random() * (1000 - 500) + 500);
        for (int i = 0; i < nb_sort; i++) {

            int a = (int) (Math.random() * gridNb_Rows);
            int b = (int) (Math.random() * gridNb_Col);
            String temp;

            temp = btn[a][b].getText();
            btn[a][b].setText(btn[b][a].getText());
            btn[b][a].setText(temp);
        }

        lScore = FactoryLayout.createLabel("Score : ");
        bReturn = FactoryLayout.generateButtonReturnHome();

        vBxGame = FactoryLayout.createVBOX(lName, gridPane, lScore, bReturn);
        SceneGame = new Scene(vBxGame, 500, 650);

    }

    public VBox getvBxGame() {
        return vBxGame;
    }

    public Scene getSceneGame() {
        return SceneGame;
    }


}

