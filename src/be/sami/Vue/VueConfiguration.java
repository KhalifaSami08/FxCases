package be.sami.Vue;

import be.sami.Controller;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import be.sami.Model.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class VueConfiguration {

    //export to Controller
    private Scene sceneConfig;
    private VBox vBxConfig,vBoxConfigHelp;
    private Button bThemeDark,bThemeGreen,bThemeRed;
    private Button bEasy,bMedium,bHard;
    private TextField tXfEditName;
    private Label lDiff;
    private Button bOk;

    public VueConfiguration(){
        initConfig();
    }

    private void initConfig() {

        Insets insetsHbox = new Insets(15);

        Label lEditName = FactoryLayout.createLabel("Edit name : ");

        tXfEditName = new TextField(Controller.getPlayer().getName());
        tXfEditName.setAlignment(Pos.CENTER);
        tXfEditName.setPadding(insetsHbox);

        Label lBackground = FactoryLayout.createLabel("Chose Theme : ");

        bThemeDark = FactoryLayout.createButton("Default");
        FactoryLayout.ChangeBackground(bThemeDark, Theme.Dark);

        bThemeGreen = FactoryLayout.createButton("Green");
        FactoryLayout.ChangeBackground(bThemeGreen,Theme.Green);

        bThemeRed = FactoryLayout.createButton("Red");
        FactoryLayout.ChangeBackground(bThemeRed,Theme.Red);

        HBox h1 = FactoryLayout.createHBOX(bThemeDark,bThemeGreen,bThemeRed);
            h1.setBackground(FactoryLayout.firstBack);
            h1.setPadding(insetsHbox);

        lDiff = FactoryLayout.createLabel("Chose Difficulty (Default is Easy): ");

        bEasy = FactoryLayout.createButton("Easy");
        FactoryLayout.ChangeTextFillAndLabel(bEasy, Color.DARKGREEN);

        bMedium = FactoryLayout.createButton("Medium");
        FactoryLayout.ChangeTextFillAndLabel(bMedium,Color.DARKBLUE);

        bHard = FactoryLayout.createButton("Hard");
        FactoryLayout.ChangeTextFillAndLabel(bHard,Color.DARKRED);

        HBox h2 = FactoryLayout.createHBOX(bEasy,bMedium,bHard);
            h2.setBackground(Theme.Dark);
            h2.setPadding(insetsHbox);

        bOk = FactoryLayout.createButton("OK");

        Button bReturn = FactoryLayout.generateButtonReturnHome();
        HBox h3 = FactoryLayout.createHBOX(bOk,bReturn);
            h3.setAlignment(Pos.BOTTOM_CENTER);
        vBoxConfigHelp = FactoryLayout.createVBOX(lEditName,tXfEditName,lBackground,h1,lDiff,h2);
        vBxConfig = FactoryLayout.createVBOX(vBoxConfigHelp,h3);
        sceneConfig = new Scene(vBxConfig,400,550);

    }

    public Scene getSceneConfig() {
        return sceneConfig;
    }

    public VBox getvBxConfig() {
        return vBxConfig;
    }

    public VBox getvBoxConfigHelp() {
        return vBoxConfigHelp;
    }

    public Button getbThemeDark() {
        return bThemeDark;
    }

    public Button getbThemeGreen() {
        return bThemeGreen;
    }

    public Button getbThemeRed() {
        return bThemeRed;
    }

    public Button getbEasy() {
        return bEasy;
    }

    public Button getbMedium() {
        return bMedium;
    }

    public Button getbHard() {
        return bHard;
    }

    public TextField gettXfEditName() {
        return tXfEditName;
    }

    public Label getlDiff() {
        return lDiff;
    }

    public Button getbOk() {
        return bOk;
    }
}
