package be.sami.Model;

import be.sami.Controller;
import be.sami.Vue.FactoryLayout;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameBoard implements Observable {

    public static final Background backgroundOnClick = new Background(new BackgroundFill(Color.rgb(180, 200, 245), null, null));
    private static Cell firstButtonClick;

    private final int gridNb_Rows_Col = Difficulty.CastDifficultyString(Controller.getPlayer().getDifficulty());
    private final ArrayList<Observer> myObservers;
    private final ArrayList<Cell> allMyBoxes;
    private GridPane gridPane;

    public GameBoard() {

        allMyBoxes = new ArrayList<>();
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

            temp = allMyBoxes.get(a).getButton().getText();
            allMyBoxes.get(a).getButton().setText(allMyBoxes.get(b).getButton().getText());
            allMyBoxes.get(b).getButton().setText(temp);

        }
    }

    private void initButtons() {
        int cpt = 0;
        for (Cell b :
                allMyBoxes) {
            int nbr = ((cpt++) % ((gridNb_Rows_Col * gridNb_Rows_Col / 2)));
            b.getButton().setText("b" + nbr);
        }
    }

    private void createButtons() {

        for (int i = 0; i < gridNb_Rows_Col; i++) {

            for (int j = 0; j < gridNb_Rows_Col; j++) {

                Cell b = new Cell(new Position<>(i, j));
                b.getButton().setMinSize(gridPane.getMaxHeight() / gridNb_Rows_Col,gridPane.getMaxWidth() / gridNb_Rows_Col);

                allMyBoxes.add(b);
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

    private ArrayList<Cell> getArraylistCoupleButtons(Cell firstButtonClick, Cell b){
        ArrayList<Cell> cellOk = new ArrayList<>();
        cellOk.add(firstButtonClick);
        cellOk.add(b);
        return cellOk;
    }

    public void validateAndOk(Cell b, Player player){

        notifyObserver(getArraylistCoupleButtons(firstButtonClick,b) , 1);
        player.setScore(player.getScore() + getGridNb_Rows_Col()/2);
        notifyObserver(player,0);
    }

    public void validateNotOk(Cell b, Player player){

        notifyObserver(getArraylistCoupleButtons(firstButtonClick,b) , 2);
        player.setScore(player.getScore() - 1);
        notifyObserver(player,0);
    }

    public void endGame(Player player,int timerSeconds){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Win");
        a.setContentText("Congratulations ! you winned with " + player.getScore() + " points ! ");
        a.showAndWait();

        //unique player
        for (Player p :
                AllPlayers.getAllPlayers()) {
            if (p.getName().equals(player.getName())){
                player.setName(player.getName()+timerSeconds);
            }
        }

        player.setSeconds(timerSeconds);
        AllPlayers.writeFile(player);
    }

    public boolean checkEndGame() {
        for (Cell b :
                allMyBoxes) {
            if(!b.isOk())
                return false;
        }
        return true;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ArrayList<Cell> getAllMyBoxes() {
        return allMyBoxes;
    }

    public static Cell getFirstButtonClick() {
        return firstButtonClick;
    }

    public static void setFirstButtonClick(Cell firstButtonClick) {
        GameBoard.firstButtonClick = firstButtonClick;
    }

    public int getGridNb_Rows_Col() {
        return gridNb_Rows_Col;
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
