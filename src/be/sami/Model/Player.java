package be.sami.Model;

public class Player {

    private String name;
    private Difficulty difficulty;
    private int score;
    private int seconds;
    private boolean cheatActivated;

    //if first Launch
    public Player(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;
        this.seconds = 0;
        this.cheatActivated = false;
    }

    //Read File
    public Player(String name, Difficulty difficulty, int score, int seconds,boolean cheatActivated)  {
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
        this.seconds = seconds;
        this.cheatActivated = cheatActivated;
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

    public boolean isCheatActivated() {
        return cheatActivated;
    }

    public void setCheatActivated(boolean cheatActivated) {
        this.cheatActivated = cheatActivated;
    }
}
