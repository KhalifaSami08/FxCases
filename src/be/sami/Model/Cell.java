package be.sami.Model;

import be.sami.Vue.FactoryLayout;
import javafx.scene.control.Button;

public class Cell {

    private final Position position;
    private Button button;
    private boolean isOk;


    public Cell(Position position) {
        this.position = position;
        this.isOk = false;
        createButtons();
    }

    private void createButtons(){
        button = new Button();
        button.setBackground(FactoryLayout.secondBack);
        button.setTextFill(FactoryLayout.secondBackGroundColor);

        button.setOnMouseEntered(e -> {
            button.setBackground(FactoryLayout.firstBack);
            button.setTextFill(FactoryLayout.firstBackGroundColor);
        });

        button.setOnMouseExited(e -> {
            button.setBackground(FactoryLayout.secondBack);
            button.setTextFill(FactoryLayout.secondBackGroundColor);
        });
    }

    public Position getPosition() {
        return position;
    }

    public Button getButton() {
        return button;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

}
