package be.sami.Vue;

import be.sami.Model.AllPlayers;
import be.sami.Model.Difficulty;
import be.sami.Model.Player;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class VueBestScore {

    private Scene SceneScore;//export to Controller
    private VBox vBxScore;//export to Controller

    public VueBestScore(){
        initBestScoreView();
    }

    private void initBestScoreView() {
        new AllPlayers();
        ArrayList<Player> myPlayers = AllPlayers.getAllPlayers();
        TableView tableView = new TableView<>();
        tableView.setPlaceholder(new Label("No rows to display"));

        TableColumn<String,Player> tableColumn1 = new TableColumn<>("Name : ");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Difficulty,Player> tableColumn2 = new TableColumn<>("Difficulty : ");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        TableColumn<Integer,Player> tableColumn3 = new TableColumn<>("Score : ");
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<Integer,Player> tableColumn4 = new TableColumn<>("Seconds : ");
        tableColumn4.setCellValueFactory(new PropertyValueFactory<>("seconds"));

        TableColumn<Boolean,Player> tableColumn5 = new TableColumn<>("Cheat Code Active : ");
        tableColumn5.setCellValueFactory(new PropertyValueFactory<>("cheatActivated"));

        tableView.getColumns().addAll(tableColumn1,tableColumn2,tableColumn3,tableColumn4,tableColumn5);

        for (Player p :
                myPlayers) {
            tableView.getItems().add(p);
        }

        VBox vBox = FactoryLayout.createVBOX(tableView);
        Button bReturn = FactoryLayout.generateButtonReturnHome();

        vBxScore = FactoryLayout.createVBOX(vBox,bReturn);
        SceneScore = new Scene(vBxScore,400,520);
    }

    public Scene getSceneScore() {
        return SceneScore;
    }

    public VBox getvBxScore() {
        return vBxScore;
    }
}
