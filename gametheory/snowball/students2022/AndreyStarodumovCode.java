/*
 * %W% %E% Andrey Starodumov
 * 
 * 2022, Innopolis University
 * 
 * Game Theory
 * Second Practice Test
 * 
 * java.specification.version=17
 */
package gametheory.snowball.students2022;

import gametheory.snowball.Player; // interface import

/**
 * @author Andrey Starodumov
 */
public class AndreyStarodumovCode implements Player {
    int countMinutes;        // counter of imaginary minutes
    int maxRoundMinutes;    // maximum number of minutes of a game
    public int howMuchCount;     // how many minutes should it take before agent will shoot
    boolean waitAndBeat = false; //
    boolean wait = false;        // 

    /**
     * 
     */
    public AndreyStarodumovCode() {
        this(4);
        countMinutes = 0;
        maxRoundMinutes = 60;
    }

    /**
     * 
     * @param howMuchCount  how many minutes should it take
     *                      before agent will shoot 
     */
    public AndreyStarodumovCode(int howMuchCount) {
        if (howMuchCount == 0) {
            waitAndBeat = true;
            wait = true;
        }
        this.howMuchCount = howMuchCount;
        countMinutes = 0;
        maxRoundMinutes = 60;
    }

    /**
     * 
     */
    @Override
    public void reset() {
        this.countMinutes = 0;
        // temporary
        if (wait) {this.waitAndBeat = true; this.howMuchCount = 0;}
        else {this.waitAndBeat = false;}
    }

    /**
     * 
     */
    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber,
            int minutesPassedAfterYourShot) {
        countMinutes += 1;
        if (waitAndBeat == true) {
            if (opponentLastShotToYourField == 0) {
                return 0;
            } else {
                this.waitAndBeat = false;
                this.howMuchCount = 4;
            }
        }
        if (countMinutes % howMuchCount == 0) {
            return (Math.min(maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber));
        }
        if (countMinutes == maxRoundMinutes) {
            return (Math.min(maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber));
        }
        return (0);
    }

    /**
     * 
     */
    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        if (waitAndBeat == true) {
            return (Math.min(maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber));
        }
        return 0;
    }

    /**
     * 
     */
    @Override
    public String getEmail() {
        return ("a.starodumov@innopolis.university");
    }
}
