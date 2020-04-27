package be.sami.Model;

import javafx.scene.layout.Background;

public class Player {

    private String name;
    private Difficulty difficulty;
    private Background backColor;
    private int score;

    //if first Launch
    public Player(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;
    }

    //Read File
    public Player(String name, Difficulty difficulty, int score)  {
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
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

    public Background getBackColor() {
        return backColor;
    }

    public void setBackColor(Background backColor) {
        this.backColor = backColor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
