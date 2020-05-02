package be.sami.Model;

import be.sami.Controller;
import be.sami.Vue.FactoryLayout;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameBoard implements Observable {

    public static final Background backgroundOnClick = new Background(new BackgroundFill(Color.rgb(180, 200, 245), null, null));
    private static Boxes firstButtonClick;

    private final int gridNb_Rows_Col = Difficulty.CastDifficultyString(Controller.getPlayer().getDifficulty());
    private final ArrayList<Observer> myObservers;

    private final ArrayList<Boxes> allboxes;
    private GridPane gridPane;

    public GameBoard() {

        allboxes = new ArrayList<>();
        myObservers = new ArrayList<>();
        firstButtonClick = null;
        createGridPane();
        createButtons();
        initButtons();
        randomiseButtons();
    }

    private void randomiseButtons() {
        int nb_sort = (int) (Math.random() * (1000 - 500) + 500);
        for (int i = 0; i < nb_sort; i++) {

            int a = (int) (Math.random() * (gridNb_Rows_Col*gridNb_Rows_Col));
            int b = (int) (Math.random() * (gridNb_Rows_Col*gridNb_Rows_Col));
            String temp;

            temp = allboxes.get(a).getButton().getText();
            allboxes.get(a).getButton().setText(allboxes.get(b).getButton().getText());
            allboxes.get(b).getButton().setText(temp);

        }
    }

    private void initButtons() {
        int cpt = 0;
        for (Boxes b :
                allboxes) {
            int nbr = ((cpt++) % ((gridNb_Rows_Col * gridNb_Rows_Col / 2)));
            b.getButton().setText("b" + nbr);
        }
    }

    private void createButtons() {

        for (int i = 0; i < gridNb_Rows_Col; i++) {

            for (int j = 0; j < gridNb_Rows_Col; j++) {

                Boxes b = new Boxes(new Position<>(i, j));
                b.getButton().setMinSize(gridPane.getMaxHeight() / gridNb_Rows_Col,gridPane.getMaxWidth() / gridNb_Rows_Col);

                allboxes.add(b);
                gridPane.add(b.getButton(), j, i);
            }
        }
    }

    private void createGridPane() {
        gridPane = new GridPane();
        gridPane.setMaxSize(450,450);
        gridPane.setMinSize(300,300);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(1);
        gridPane.setVgap(1);
    }

    public void changeButtonBackcolorOnClick(Button button) {
        button.setBackground(backgroundOnClick);
        button.setTextFill(FactoryLayout.firstBackGroundColor);
        button.setOnMouseExited(e -> {
            button.setBackground(backgroundOnClick);
        });
    }

    public void resetButtonifClickNotMatch(Button first, Button b) {
        first.setBackground(FactoryLayout.secondBack);
        first.setTextFill(FactoryLayout.secondBackGroundColor);

        first.setOnMouseExited(e -> {
            first.setBackground(FactoryLayout.secondBack);
            first.setTextFill(FactoryLayout.secondBackGroundColor);
        });

        b.setOnMouseExited(e -> {
            b.setBackground(FactoryLayout.secondBack);
            b.setTextFill(FactoryLayout.secondBackGroundColor);
        });
        b.setBackground(FactoryLayout.secondBack);
        b.setTextFill(FactoryLayout.secondBackGroundColor);
    }

    private ArrayList<Boxes> getArraylistCoupleButtons(Boxes firstButtonClick, Boxes b){
        ArrayList<Boxes> boxesOk = new ArrayList<>();
        boxesOk.add(firstButtonClick);
        boxesOk.add(b);
        return boxesOk;
    }

    public void validateAndOk(Boxes firstButtonClick, Boxes b, Player player){

        notifyObserver(getArraylistCoupleButtons(firstButtonClick,b) , 1);
        player.setScore(player.getScore() + getGridNb_Rows_Col()/2);
        notifyObserver(player,0);
    }

    public void validateNotOk(Boxes firstButtonClick,Boxes b,Player player){

        notifyObserver(getArraylistCoupleButtons(firstButtonClick,b) , 2);
        player.setScore(player.getScore() - 1);
        notifyObserver(player,0);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ArrayList<Boxes> getAllboxes() {
        return allboxes;
    }

    public static Boxes getFirstButtonClick() {
        return firstButtonClick;
    }

    public int getGridNb_Rows_Col() {
        return gridNb_Rows_Col;
    }

    public boolean checkEndGame() {
        for (Boxes b :
                allboxes) {
            if(!b.isOk())
                return false;
        }
        return true;
    }

    @Override
    public void addObserver(Observer o) {
        myObservers.add(o);
    }

    @Override
    public void notifyObserver(Object object,int param) {
        for (Observer observer: myObservers) {
            observer.modify(object,param);
        }
    }
}
