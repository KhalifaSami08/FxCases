package be.sami;

import be.sami.Model.*;
import be.sami.Vue.VueGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("JAVA PROJECT MEMORY - SAMI KHALIFA");

        Controller c = new Controller(Theme.Dark,new Player("UNDEFINED_NAME",Difficulty.EASY));
        c.ShowAll();

    }

    public static void main(String[] args) {
        launch(args);
    }
}