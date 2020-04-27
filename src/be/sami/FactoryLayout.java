package be.sami;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class FactoryLayout {

    public static final Paint firstBackGroundColor = Color.rgb(50,50,50);
    public static final Paint secondBackGroundColor = Color.WHEAT; //Color.rgb(150,150,150);
    public static final Background firstBack = new Background(new BackgroundFill(firstBackGroundColor,null,null));
    public static final Background secondBack = new Background(new BackgroundFill(secondBackGroundColor,null,null));
    public static final int boxPadding = 25;

    public static Button createButton(String text, EventHandler<ActionEvent> actionEventHandler){
        Button b = new Button(text);
        b.setBackground(firstBack);
        b.setTextFill(secondBackGroundColor);
        b.setOnMouseEntered(e -> {
            b.setBackground(secondBack);
            b.setTextFill(firstBackGroundColor);
        });
        b.setOnMouseExited(e -> {
            b.setBackground(firstBack);
            b.setTextFill(secondBackGroundColor);
        });
        b.setOnAction(actionEventHandler);
        return b;
    }

    public static Button generateButtonReturnHome(){
        return FactoryLayout.createButton("Return Home", e -> Controller.primaryStage.setScene(Controller.SceneMenu));
    }

    //Only for Config - BackgroundButton
    public static void ChangeBackground(Button button,Background background){
        button.setBackground(background);
        button.setOnMouseExited(e ->{
            button.setBackground(background);
            button.setTextFill(secondBackGroundColor);
        });
    }

    //Only for Config - difficultyButton
    public static void ChangeTextFillAndLabel(Button button,Label label,Paint paint){
        button.setTextFill(paint);
        button.setOnMouseExited(e -> {
            button.setTextFill(paint);
            button.setBackground(firstBack);
        });
        button.setOnAction(e -> {label.setText("Difficulty : "+button.getText());});
    }

    public static Label createLabel(String text){
        Label l= new Label(text);
        //l.setAlignment(Pos.CENTER);
        l.setTextFill(Color.WHEAT);
        return l;
    }

    public static VBox createVBOX(Node... nodes){
        VBox vBox = new VBox(boxPadding,nodes);
            vBox.setAlignment(Pos.CENTER); //Default
            vBox.setBackground(Controller.backGroundColor);

        return vBox;
    }

    public static HBox createHBOX(Node... nodes){
        HBox hBox = new HBox(boxPadding*2,nodes);
            hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

}
