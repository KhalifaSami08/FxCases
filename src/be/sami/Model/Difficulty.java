package be.sami.Model;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    public static Difficulty CastDifficulty(String difficulty)  {
        Difficulty d;

        if(difficulty.equalsIgnoreCase("EASY")){
            d = Difficulty.EASY;
        }
        else if (difficulty.equalsIgnoreCase("MEDIUM")){
            d = Difficulty.MEDIUM;
        }
        else if (difficulty.equalsIgnoreCase("HARD")){
            d = Difficulty.HARD;
        }
        else{
            throw new RuntimeException("CAST DIFFICULTY IMPOSSIBLE");
        }
        return d;
    }
    public static int CastDifficultyString(Difficulty difficulty)  {
        int i;

        if(difficulty == Difficulty.EASY){
            i = 4;
        }
        else if (difficulty == Difficulty.MEDIUM){
            i = 6;
        }
        else if (difficulty == Difficulty.HARD){
            i = 8;
        }
        else{
            throw new RuntimeException("CAST DIFFICULTY IMPOSSIBLE");
        }
        return i;
    }
}