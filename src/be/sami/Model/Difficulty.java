package be.sami.Model;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    //Cast String into Difficulty While Reading a File
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
            throw new RuntimeException("CAST DIFFICULTY : READING FILE IMPOSSIBLE");
        }
        return d;
    }
    //Set number of rows_col
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
            throw new RuntimeException("CAST DIFFICULTY : SET NUMBER OF ROWS IMPOSSIBLE ");
        }
        return i;
    }
}