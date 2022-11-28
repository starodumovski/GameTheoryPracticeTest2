package gametheory.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gametheory.snowball.students2022.*;;


public class Tournament {
    public static void main(String[] args) {
        tournament(new ArrayList<Integer>(Arrays.asList(2, 0, 10, 0, 0, 0, 0, 0, 0)));
    }

    static ArrayList<Integer> one_battle(AndreyStarodumovCode player1, AndreyStarodumovCode player2) {
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
        return new ArrayList<Integer>(Arrays.asList(player1balls, player2balls));
    }

    static void tournament(ArrayList<Integer> eachPlayer) {
        int amount_of_players = 0;
        for (int i = 0; i < eachPlayer.size(); i++) {
            amount_of_players += eachPlayer.get(i);
        }
        Map<String, AndreyStarodumovCode> playersMap = new HashMap<>();
        String keys[] = new String[amount_of_players]; 
        int tournamentTable[][] = new int[amount_of_players][amount_of_players];

        for (int i = 0; i < tournamentTable.length; i++) {
            for (int j = 0; j < tournamentTable.length; j++) {
                tournamentTable[i][j] = 0;
            }
        }

        int idx = 0;
        for (int i = 0; i < eachPlayer.size(); i++) {
            for (int j = 0; j < eachPlayer.get(i); j++) {
                String name = String.format("sa%d", i * eachPlayer.get(i) + j);
                playersMap.put(name, new AndreyStarodumovCode(i));
                keys[idx + j] = name;
            }
            idx += eachPlayer.get(i);
        }

        for (int i = 0; i < keys.length; i++) {
            for (int j = i + 1; j < keys.length; j++) {
                playersMap.get(keys[i]).reset();
                playersMap.get(keys[j]).reset();
                ArrayList<Integer> result = one_battle(playersMap.get(keys[i]), playersMap.get(keys[j]));
                tournamentTable[i][j] = result.get(0);
                tournamentTable[j][i] = result.get(1);
            }
        }
        for (int[] is : tournamentTable) {
            for (int is2 : is) {
                System.out.print(is2 + "\t");
            }
            System.out.println();
        }
        System.out.println("*********************************************");
        for (int[] is : tournamentTable) {
            int tmpSum = 0;
            for (int is2 : is) {
                tmpSum += is2;
            }
            System.out.println(tmpSum);
        }
    }
}
