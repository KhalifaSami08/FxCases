package be.sami.Vue;

import be.sami.Controller;
import be.sami.FactoryLayout;
import be.sami.Model.Player;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import be.sami.Model.Difficulty;
import be.sami.Model.Theme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class VueConfiguration {

    private static final Stage primaryStage = Controller.primaryStage;
    private static final Player player = Controller.player;

    public static VBox vBxConfig,vBoxConfigHelp;
    public static Button bThemeDark,bThemeGreen,bThemeRed;
    public static Button bEasy,bMedium,bHard;
    public static TextField tXfEditName;

    private Scene sceneConfig; //export to Controller

    public VueConfiguration(){
        initConfig();
    }

    private void initConfig() {

        Insets insetsHbox = new Insets(15);

        Label lEditName = FactoryLayout.createLabel("Edit name : ");

        tXfEditName = new TextField("New Player");
        tXfEditName.setAlignment(Pos.CENTER);
        tXfEditName.setPadding(insetsHbox);

        Label lBackground = FactoryLayout.createLabel("Chose Theme : ");

        bThemeDark = FactoryLayout.createButton("Default",new Controller.ButtonConfigHandlerColor());
        FactoryLayout.ChangeBackground(bThemeDark, Theme.Dark);

        bThemeGreen = FactoryLayout.createButton("Green",new Controller.ButtonConfigHandlerColor());
        FactoryLayout.ChangeBackground(bThemeGreen,Theme.Green);

        bThemeRed = FactoryLayout.createButton("Red",new Controller.ButtonConfigHandlerColor());
        FactoryLayout.ChangeBackground(bThemeRed,Theme.Red);

        HBox h1 = FactoryLayout.createHBOX(bThemeDark,bThemeGreen,bThemeRed);
            h1.setBackground(FactoryLayout.firstBack);
            h1.setPadding(insetsHbox);

        Label lDiff = FactoryLayout.createLabel("Chose Difficulty (Default is Medium): ");

        bEasy = FactoryLayout.createButton("Easy",new Controller.ButtonConfigHandlerDifficulty());
        FactoryLayout.ChangeTextFillAndLabel(bEasy,lDiff, Color.DARKGREEN);

        bMedium = FactoryLayout.createButton("Medium",new Controller.ButtonConfigHandlerDifficulty());
        FactoryLayout.ChangeTextFillAndLabel(bMedium,lDiff,Color.DARKBLUE);

        bHard = FactoryLayout.createButton("Hard",new Controller.ButtonConfigHandlerDifficulty());
        FactoryLayout.ChangeTextFillAndLabel(bHard,lDiff,Color.DARKRED);

        HBox h2 = FactoryLayout.createHBOX(bEasy,bMedium,bHard);
            h2.setBackground(Theme.Dark);
            h2.setPadding(insetsHbox);

        Button bOk = FactoryLayout.createButton("OK", new Controller.ButtonConfigOk());

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



}
