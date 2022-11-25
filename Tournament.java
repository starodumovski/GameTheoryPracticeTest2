import java.util.ArrayList;
import java.util.List;

import gametheory.snowball.*;


public class Tournament {
    public static void main(String[] args) {
        System.out.println(tournament()); 
    }

    static String tournament() {
        List<String> players = new ArrayList<String>();

        agent_smart_3 as3 = new agent_smart_3();
        AndreyStarodumov as1 = new AndreyStarodumov();
        agent_smart_5 as5 = new agent_smart_5();

        players.add("as3");
        players.add("as5");
        players.add("as1");

        int player1balls = 100;
        int player2balls = 100;
        int minutesPassedAfter1Shot = -1;
        int minutesPassedAfter2Shot = -1;
        int p1_p2 = 0;
        int p2_p1 = 0;

        //TODO: player1, player2 add
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

        return String.format("First player has %d snowballs, Second player has %d snowballs", player1balls, player2balls);
    }
}
