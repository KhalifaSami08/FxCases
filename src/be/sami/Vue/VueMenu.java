package be.sami.Vue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class VueMenu {

    private Scene SceneMenu; //export to Controller
    private VBox vBxMenu; //export to Controller

    private Button btnGameMenu,btnConfigMenu,btnBestScoreMenu;

    public VueMenu(){
        initMenu();
    }

    private void initMenu() {
        int btnPaddingY = 10;
        int btnPaddingX = 120;

        Insets insetPadding = new Insets(btnPaddingY, btnPaddingX, btnPaddingY, btnPaddingX);//Right Bottom Left Top

        btnGameMenu = FactoryLayout.createButton("Launch Game");
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
        });

        btnConfigMenu = FactoryLayout.createButton("Configurations");
        btnConfigMenu.setPadding(insetPadding);

        btnBestScoreMenu = FactoryLayout.createButton("Best Scores");
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

    public Button getBtnGameMenu() {
        return btnGameMenu;
    }

    public Button getBtnConfigMenu() {
        return btnConfigMenu;
    }

    public Button getBtnBestScoreMenu() {
        return btnBestScoreMenu;
    }
}
