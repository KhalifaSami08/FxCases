package be.sami.Model;

import be.sami.Controller;
import be.sami.Vue.VueGame;
import javafx.scene.layout.Background;

public class Player {

    private String name;
    private Difficulty difficulty;
    private int score;
    private int seconds;

    //if first Launch
    public Player(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;
    }

    //Read File
    public Player(String name, Difficulty difficulty, int score, int seconds)  {
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
