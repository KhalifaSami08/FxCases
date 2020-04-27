package be.sami.Vue;

import be.sami.Controller;
import be.sami.FactoryLayout;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VueMenu {

    private final Stage primaryStage = Controller.primaryStage;
    private final Scene SceneGame = Controller.SceneGame;
    private final Scene SceneConfig = Controller.SceneConfig;
    private final Scene SceneScore = Controller.SceneScore;

    private Scene SceneMenu; //export to Controller
    private VBox vBxMenu; //export to Controller

    public VueMenu(){
        initMenu();
    }

    private void initMenu() {
        int btnPaddingY = 10;
        int btnPaddingX = 120;

        Insets insetPadding = new Insets(btnPaddingY, btnPaddingX, btnPaddingY, btnPaddingX);//Right Bottom Left Top

        Button btnGameMenu = FactoryLayout.createButton("Launch Game", e -> { primaryStage.setScene(SceneGame); });
        btnGameMenu.setPadding(insetPadding);
        btnGameMenu.setOnAction(e -> {
                /*if(player == null){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error ! ");
                    a.setContentText("Game is not configured");
                    a.showAndWait();
                    primaryStage.setScene(SceneConfig);
                }
                else{
                    primaryStage.setScene(SceneGame);
                }*/
            primaryStage.setScene(SceneGame);
        });

        Button btnConfigMenu = FactoryLayout.createButton("Configurations", e ->{ primaryStage.setScene(SceneConfig);});
        btnConfigMenu.setPadding(insetPadding);

        Button btnBestScoreMenu = FactoryLayout.createButton("Best Scores", e ->{ primaryStage.setScene(SceneScore);});
        btnBestScoreMenu.setPadding(insetPadding);

        vBxMenu = FactoryLayout.createVBOX(btnGameMenu,btnConfigMenu,btnBestScoreMenu);
        SceneMenu = new Scene(vBxMenu,550,400);
    }

    public Scene getSceneMenu() {
        return SceneMenu;
    }

    public VBox getvBxMenu() {
        return vBxMenu;
    }
}
