/*
 * %W% %E% Andrey Starodumov
 * 
 * 2022, Innopolis University
 * 
 * Game Theory
 * Second Practice Test
 * 
 * Testing
 * 
 * java.specification.version=17
 */
package gametheory.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gametheory.snowball.Player;


/**
 * Test class for tournaments and results
 * 
 * @author Andrey Starodumov
 */
public class AndreyStarodumovTesting {
    public static void main(String[] args) {
        startTournament();
    }

    /**
     * Function to show amount of snowballs depending on function given
     */
    static void functionOfBalls() {
        TestAgent checker = new TestAgent();
        for (int i = 0; i < 20; i++) {
            System.out.print(checker.maxSnowballsPerMinute(i) + " ");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * start the tournament
     */
    static void startTournament() {
        Map<Integer, Integer> players = definePlayers();

        tournament(players);
    }

    /**
     * This function defines the set of players
     * 
     * @return map, where we have parameter for creating kind of agent
     *         and amount of each type
     */
    static Map<Integer, Integer> definePlayers() {
        /*
         * key=-1 for CULM Agent
         * key=0 for CA Agent
         * key=1, key=2, ... for NMP Agents
         */
        return new HashMap<Integer, Integer>() {{   
            put(-1, 3);
            put(4, 20);
            put(0, 2);
            // put(5, 13);
        }};
    }

    /**
     * This function simulates one fight between player1 and player2
     * 
     * @param player1 Agent to play
     * @param player2 Agent to play
     * @return [snowballsRestForFirstPlayer, snowballsRestForSecondPlayer]
     */
    static ArrayList<Integer> oneBattle(TestAgent player1, TestAgent player2) {
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
        
        return new ArrayList<Integer>(Arrays.asList(player1balls, player2balls));
    }

    /**
     * This function is tournament function itself.
     * It computing and printing all needed statistics:
     * Table of tournament fights and sum of ol rest snowballs for each agent
     * 
     * @param eachPlayer set of players for the tournament defined by definePlayers() function
     */
    static void tournament(Map<Integer, Integer> eachPlayer) {
        int amount_of_players = 0;
        Map<String, TestAgent> playersMap = new HashMap<>();
        
        // amount counting
        for (int key : eachPlayer.keySet()) {
            amount_of_players += eachPlayer.get(key);
        }

        String keys[] = new String[amount_of_players]; 
        int tournamentTable[][] = new int[amount_of_players][amount_of_players];
        
        for (int i = 0; i < tournamentTable.length; i++) {
            for (int j = 0; j < tournamentTable.length; j++) {
                tournamentTable[i][j] = 0;
            }
        }

        int keys_idx = 0;
        System.out.println(eachPlayer.entrySet());
        for (var entry : eachPlayer.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                String name = String.format("sa%d_%d", entry.getKey(), keys_idx + i);
                playersMap.put(name, new TestAgent(entry.getKey()));
                keys[keys_idx + i] = name;
            }
            keys_idx += entry.getValue();
        }

        for (int i = 0; i < keys.length; i++) {
            for (int j = i + 1; j < keys.length; j++) {
                playersMap.get(keys[i]).reset();
                playersMap.get(keys[j]).reset();
                ArrayList<Integer> result = oneBattle(playersMap.get(keys[i]), playersMap.get(keys[j]));
                tournamentTable[i][j] = result.get(0);
                tournamentTable[j][i] = result.get(1);
            }
        }
        System.out.println(Arrays.deepToString(tournamentTable).replace("], ", "],\n"));
        System.out.println("*********************************************");
        int counter = 0;
        for (int[] is : tournamentTable) {
            int tmpSum = 0;
            for (int is2 : is) {
                tmpSum += is2;
            }
            System.out.println(keys[counter] + ": " + tmpSum);
            counter += 1;
        }
    }
}

/** 
 * Agent class
 * 
 * @author Andrey Starodumov
 */
class TestAgent implements Player {
    int countMinutes;            // counter of imaginary minutes
    int maxRoundMinutes;         // maximum number of minutes of a game
    public int howMuchCount;     // how many minutes should it take before agent will shoot
    boolean calmOrAngry = false; // variable to see if CULM Agent is calm (true) or angry (false)
    boolean CA = false;        // variable to see if it is CULM Agent (true if it is)
    boolean CULM = false;  // variable to see if it is CA Agent (true if it is)

    /**
     * Class constructor
     */
    public TestAgent() {
        this(4);
        countMinutes = 0;
        maxRoundMinutes = 60;
    }

    /**
     * Class constructor specifying the kind of Agent to create
     * 
     * @param howMuchCount  how many minutes should it take
     *                      before agent will shoot 
     */
    public TestAgent(int howMuchCount) {
        this.howMuchCount = howMuchCount;
        if (howMuchCount == 0) {
            calmOrAngry = true;
            CA = true;
            this.howMuchCount = 4;
        } else if (howMuchCount == -1) {
            this.CULM = true;
            this.howMuchCount = 4;
        }
        countMinutes = 0;
        maxRoundMinutes = 60;
    }


    /**
     * This function return exact number of snowballs to shoot
     * 
     * @param minutesPassedAfterYourShot the number of minutes passed after
     *                                   your last shot to any field
     *                                   (0 – if this is the first shot)
     * @param snowballNumber             the number of snowballs on your field
     * 
     * @return number of snowballs to shoot
     */
    int makeShot(int minutesPassedAfterYourShot, int snowballNumber) {
        if ((minutesPassedAfterYourShot % howMuchCount == 0) || (countMinutes == maxRoundMinutes)) {
            return (Math.min(maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber));
        }
        return (0);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.countMinutes = 0;
        if (CA) {
            this.calmOrAngry = true; 
        } else {
            this.calmOrAngry = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        countMinutes += 1;
        if (countMinutes == maxRoundMinutes) {
            return makeShot(minutesPassedAfterYourShot, snowballNumber);
        }
        if (CULM) {
            return (0);
        } else if (calmOrAngry) {
            if (opponentLastShotToYourField == 0) {
                return 0;
            }
            this.calmOrAngry = false;
        }
        return makeShot(minutesPassedAfterYourShot, snowballNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        if (countMinutes == maxRoundMinutes) {
            return (0);
        }
        if ((CULM) || (calmOrAngry)){  
            return makeShot(minutesPassedAfterYourShot, snowballNumber);
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return ("a.starodumov@innopolis.university");
    }
}
