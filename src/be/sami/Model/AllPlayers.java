package be.sami.Model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AllPlayers {
    private static final ArrayList<Player> allPlayers = new ArrayList<>();

    public AllPlayers(){
        ReadFile();
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public boolean isPlayerExist(String playerName){
        for (Player p :
                allPlayers) {
            if(p.getName().equals(playerName)){
                return true;
            }
        }
        return false;
    }

    private void ReadFile() {
        try {
            FileReader read = new FileReader("src\\be\\sami\\Model\\Players.csv");
            Scanner scan = new Scanner(read);
            scan.useDelimiter(";");

            while (scan.hasNext()){
                String name = scan.next();
                Difficulty diff = Difficulty.CastDifficulty(scan.next());
                int score = Integer.parseInt(scan.next());
                allPlayers.add(new Player(name,diff,score));
            }
            scan.close();
            read.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
