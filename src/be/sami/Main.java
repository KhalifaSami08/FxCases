package be.sami;

import be.sami.Model.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("JAVA PROJECT MEMORY - SAMI KHALIFA");

        Controller c = new Controller(Theme.Dark,new Player("New_Player",Difficulty.EASY));
        c.ShowAll();

    }

    public static void main(String[] args) {
        launch(args);
    }
}