/**
 * The guiding set used for the genetic algorithm composition.
 *
 * @author Daniel Krastev
 * @version 1.0
 */
public class GuidingSet {
    private final static String DEAR_MOM = "B4i C5i B4i C5i D5q D5q B4i C5i B4i C5i D5q D5q E5i D5i E5i D5i C5q C5q D5i C5i D5i C5i B4q B4q B4i C5i B4i C5i D5q D5q B4i C5i B4i C5i D5q D5q E5i D5i E5i D5i C5q C5q D5i C5i D5i C5i B4q B4q";
    private final static String THE_WHEELS_ON_THE_BUS = "D4q G4q G4i G4i G4q B4q D5q B4q G4h A4q F#4q D4h D5q B4q G4q D4q G4q G4i G4i G4q B4q D5q B4q G4h A4h D4q D4q G4h D4q G4q G4i G4i G4q B4q D5q B4q G4h A4q F#4q D4h D5q B4q G4q D4q G4q G4i G4i G4q";
    private final static String ITSY_BITSY_SPIDER = "G4i C5i C5i C5i D5i E5q E5i E5i D5i C5i D5i E5i C5h E5q E5i F5i G5q G5i G5i F5i E5i F5i G5i E5h C5q C5i D5i E5q E5q D5i C5i D5i E5i C5q G4i G4i C5i C5i C5i D5i E5q E5i E5i D5i C5i D5i E5i C5h";

    private static String[] GUIDING_SET;

    static {
        GUIDING_SET = new String[]{DEAR_MOM, THE_WHEELS_ON_THE_BUS, ITSY_BITSY_SPIDER};
    }

    /**
     * Return the set of guiding songs.
     *
     * @return The set of guiding songs.
     */
    public static String[] GET_SET() {
        return GUIDING_SET;
    }
}
