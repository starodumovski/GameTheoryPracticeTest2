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
    int TIME = 4;
    boolean mixStrategy = false; 
    
    int max3To4 = 0;
    int max4To3 = 1;
    int strategy4To3 = max4To3; // max 1
    int strategy3To4 = max3To4; // max 2

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
        this.howMuchCount = howMuchCount;
        if (howMuchCount == 0) {
            waitAndBeat = true;
            wait = true;
            this.howMuchCount = this.TIME;
        } else if (howMuchCount == -1) {
            mixStrategy = true;
        }
        countMinutes = 0;
        maxRoundMinutes = 60;
    }


    int makeShot(int minutesPassedAfterYourShot, int snowballNumber) {
        if ((countMinutes % howMuchCount == 0) || (countMinutes == maxRoundMinutes)) {
            return (Math.min(maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber));
        }
        return (0);
    }
    int makeMixShot(int minutesPassedAfterYourShot, int snowballNumber) {
        if ((minutesPassedAfterYourShot == howMuchCount) || (countMinutes == maxRoundMinutes)) {
            return (Math.min(maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber));
        }
        return (0);
    }
    /**
     * 
     */
    @Override
    public void reset() {
        this.countMinutes = 0;
        if (wait) {this.waitAndBeat = true; this.howMuchCount = this.TIME;}
        else {this.waitAndBeat = false;}
        strategy4To3 = max4To3; // max 1
        strategy3To4 = max3To4; // max 2 
    }

    /**
     * 
     */
    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber,
            int minutesPassedAfterYourShot) {
        countMinutes += 1;
        if (waitAndBeat) {
            if (opponentLastShotToYourField == 0) {
                return 0;
            } else {
                this.waitAndBeat = false;
                this.howMuchCount = 4;
            }
        } else if (mixStrategy) {
            while (true) {
                if (strategy4To3 != 0) {
                    howMuchCount = 4;
                    if ((minutesPassedAfterYourShot == howMuchCount) || (countMinutes == maxRoundMinutes)) {
                        strategy4To3 -= 1;
                        return (Math.min(maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber));
                    }
                    return (0);
                } else if (strategy3To4 != 0) {
                    howMuchCount = 3;
                    if ((minutesPassedAfterYourShot == howMuchCount) || (countMinutes == maxRoundMinutes)) {
                        strategy3To4 -= 1;
                        return (Math.min(maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber));
                    }
                    return (0);
                } else {
                    strategy4To3 = max4To3; // max 1
                    strategy3To4 = max3To4; // max 2 
                }
            }
            
        }
        return makeShot(minutesPassedAfterYourShot, snowballNumber);
    }

    /**
     * 
     */
    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        if (waitAndBeat) {
            return makeShot(minutesPassedAfterYourShot, snowballNumber);
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
