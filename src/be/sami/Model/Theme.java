package be.sami.Model;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public enum Theme {
    ///
    ;
    ///
    public static final Background Dark = new Background(new BackgroundFill(Color.rgb(30, 31,33),null,null));
    public static final Background Green = new Background(new BackgroundFill(Color.rgb(0, 51, 0),null,null));
    public static final Background Red = new Background(new BackgroundFill(Color.rgb(51, 0, 0),null,null));

    /*public static Background CastTheme(String theme) {
        Background t = null;

        if(theme.equalsIgnoreCase("DARK")){
            t = Dark;
        }
        else if(theme.equalsIgnoreCase("GREEN")){
            t = Green;
        }
        else if(theme.equalsIgnoreCase("RED")){
            t = Red;
        }
        else{
            throw new RuntimeException("CAST THEME IMPOSSIBLE");
        }
        return t;
    }*/



}
