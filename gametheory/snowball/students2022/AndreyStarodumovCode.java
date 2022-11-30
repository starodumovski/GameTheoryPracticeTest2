/*
 * %W% %E% Andrey Starodumov
 * 
 * 2022, Innopolis University
 * 
 * Email: a.starodumov@innopolis.university
 * 
 * Subject: Game Theory
 *          Second Practice Test
 * 
 * Agent implementation
 * 
 * java.specification.version=17
 */
package gametheory.snowball.students2022;

import gametheory.snowball.Player; // interface import

/**
 * Calm-Until-Last-Minute Agent (CULM) class
 * 
 * @author Andrey Starodumov
 */
public class AndreyStarodumovCode implements Player {
    int countMinutes;        // counter of imaginary minutes
    int maxRoundMinutes;    // maximum number of minutes of a game
    int howMuchCount;     // how many minutes should it take before agent will shoot

    /**
     * Class constructor
     */
    public AndreyStarodumovCode() {
        howMuchCount = 4;
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber,
            int minutesPassedAfterYourShot) {
        countMinutes += 1;
        if (countMinutes == maxRoundMinutes) {
            return makeShot(minutesPassedAfterYourShot, snowballNumber);
        }
        return (0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        if (countMinutes == maxRoundMinutes) {
            return (0);
        }
        return makeShot(minutesPassedAfterYourShot, snowballNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return ("a.starodumov@innopolis.university");
    }
}
