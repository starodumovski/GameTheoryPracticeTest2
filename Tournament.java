import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gametheory.snowball.*;


public class Tournament {
    public static void main(String[] args) {
        tournament();
    }

    static int one_battle(smart_agent player1, smart_agent player2) {
        int player1balls = 100;
        int player2balls = 100;
        int minutesPassedAfter1Shot = -1;
        int minutesPassedAfter2Shot = -1;
        int p1_p2 = 0;
        int p2_p1 = 0;

        for (int i = 0; i < 60; i++) {
            minutesPassedAfter1Shot += 1;
            minutesPassedAfter2Shot += 1;

            p1_p2 = player1.shootToOpponentField(p2_p1, player1balls, minutesPassedAfter1Shot);
            int p1_tr = player1.shootToHotField(p2_p1, player1balls, minutesPassedAfter1Shot);
            if (p1_p2 != 0 || p1_tr != 0) {
                minutesPassedAfter1Shot = 0;
            }

            p2_p1 = player2.shootToOpponentField(p1_p2, player2balls, minutesPassedAfter2Shot);
            int p2_tr = player2.shootToHotField(p1_p2, player2balls, minutesPassedAfter2Shot);
            if (p2_p1 != 0 || p2_tr != 0) {
                minutesPassedAfter2Shot = 0;
            }

            // snowballs of 1st player
            player1balls -= p1_p2;
            player1balls -= p1_tr;
            player1balls += p2_p1;
            player1balls += 1;

            // snowballs of 2nd player
            player2balls -= p2_p1;
            player2balls -= p2_tr;
            player2balls += p1_p2;
            player2balls += 1;
        }
        
        String output = String.format("player(%d) has %d snowballs, player(%d) has %d snowballs", player1.howMuchCount, player1balls, player2.howMuchCount, player2balls);
        System.out.println(output);
        return player1balls - player2balls;
    }

    static void tournament() {
        Map<String, smart_agent> playersMap = new  HashMap<>();
        String keys[] = new String[10]; 
        for (int i = 1; i < 11; i++) {
            String name = String.format("sa%d", i);
            playersMap.put(name, new smart_agent(i));
            keys[i - 1] = name;
        }

        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys.length; j++) {
                if (i == j) {
                    continue;
                }
                playersMap.get(keys[i]).reset();
                playersMap.get(keys[j]).reset();
                one_battle(playersMap.get(keys[i]), playersMap.get(keys[j]));
            }
        }

    }
}
