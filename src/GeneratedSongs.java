/**
 * The class stores some songs generated with the composer.
 *
 * @author Daniel Krastev
 * @version 1.0
 */
public class GeneratedSongs {
    private static String[][] GENERATED_SONGS;

    static {
        GENERATED_SONGS = new String[][]{
                {
                    "6.382978723404255",
                        "E4i G#4h G#4h G#4i A#4q C5i C5q B4h G#4i G#4i G#4q G4q A#4q A#4q A4h G4h A4h A4i A#4h A4i B4q C5i C5h A#4q A#4q G#4i A4i C5q B4q C5q B4q C5h C5q C#5q C#5h B4h C5i C5i B4h A4q C#5i B4q A#4q G4q G#4q G#4q G#4i G4q"
                },
                {
                        "5.000000000000001",
                        "F4q A4q G#4q G#4h A4q B4h B4i B4q A4i A#4q B4h A#4i C#5q C#5h C5i A#4q C5i C5q C#5h C5i D5q D#5i D5i B4h A#4h G#4i G#4h A4h G#4i A4h F4h F#4i F4i F4q F4q E4i D#4q D#4i D#4h C#4q F4h E4q E4i C#4q E4i E4i E4q D#4h"
                },
                {
                        "3.896103896103897",
                        "C5q E5q D#5h D#5i E5q F#5h F#5h F5i D5i C5h C5i B4q D#5q D#5h D5q C5i C#5i C#5q C#5h B4i C5i C5q B4i A4i G#4i F#4q G4i A#4i A4q A4h G4i F#4h F#4i F#4i F#4h F4i F#4h F#4h G4i G4q E5q D#5h D#5q C5h D5q D5q D5i C#5h"
                },
                {
                        "5.084745762711865",
                        "C4h D#4i D4q D4h D#4i F4q F4h F4h D#4h D#4i D#4q D#4i F#4i F#4h F#4q E4q F#4q F#4h G4q F#4h G#4h A4q A4i G4h F#4h E4q E4i G4q F#4h G4i F#4h G4i G#4h A#4q A#4q C5q C#5h C#5h C#5q D5i F#5q F5i E5i C#5h D5h D5i D5q C#5i"
                },
                {
                        "4.3478260869565215",
                        "F#4h A#4q A#4q A#4h B4i C5i C5h B4q A4h A#4i B4h C5h E5h E5q D#5q C#5q D5i C#5q C#5h B4h C5q C5i B4i G#4h G4h F4i F#4i A#4h A4q A#4h G#4i A4h A4i A4q A4i F#4i G4h G4h F#4q E4q G#4h G4q A4q F#4h G#4h G#4h G#4h G4q"
                },
                {
                        "3.703703703703705",
                        "G4i B4h A#4h A#4i B4h C#5q B4h A4i F#4i F#4q G4h G4i A#4q B4h C5q A#4i C#5h C#5i D5h C#5h F5h G5q G#5h F#5q F5q D#5q D#5q E5i D#5i D#5i C#5h D5q D5h D5q C#5q C5q C#5i C#5i C#5i C5q E5h D#5q C#5h A#4h B4q B4q B4q A#4h"
                },
        };
    }

    /**
     * Return the set of generated songs.
     *
     * @return The set of generated songs.
     */
    public static String[][] GET_SONGS() {
        return GENERATED_SONGS;
    }
}