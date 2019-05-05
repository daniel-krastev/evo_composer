/**
 * Helper class that accommodates some utility functions
 * used in the composition class. Includes methods that
 * change the representation of the music notes. Also
 * includes methods that compute the distances between
 * the notes.
 *
 * @author Daniel Krastev
 * @version 1.0
 */
public class Note {

    /**
     * Takes song represented as an int array
     * and returns a string that could be directly
     * passed to the JFugue's player.
     *
     * @param notes The song array.
     * @return The song as a String.
     */
    public static String SongArrayToString(final int[][] notes) {
        StringBuilder song = new StringBuilder();
        for(int[] note : notes) {
            song.append(NoteArrayToString(note));
            song.append(" ");
        }
        return song.toString();
    }

    /**
     * Takes song represented as a String
     * and returns an int array representation
     * of it. [[pitch, length], [note], [note]... ]
     *
     * @param song The song string.
     * @return The song as a 2 dimensional integer array.
     */
    public static int[][] StringSongToArray(final String song) {
        String[] notes = song.split(" ");
        int [][] res = new int[notes.length][2];
        for(int i = 0; i < notes.length; i++) {
            res[i] = StringNoteToArray(notes[i]);
        }
        return res;
    }

    /**
     * Takes a song represented as an int array
     * and return an array of the distances between
     * the consecutive notes.
     *
     * @param song The song.
     * @return Distances array.
     */
    public static int[] ComputeDistances(final int[][] song) {
        int[] res = new int[song.length - 1];
        for(int i = 0; i < res.length; i ++) {
            res[i] = song[i + 1][0] - song[i][0];
        }
        return res;
    }

    /**
     * Takes multiple arrays of the same size
     * and computes the respective averages.
     *
     * @param songs The songs represented as arrays.
     * @return The array with the averages.
     */
    public static double[] ComputeAverageDistances(final int[]... songs) {
        double[] res = new double[songs[0].length];
        double songsNum = songs.length;
        double idxSum;
        for(int i = 0; i < res.length; i++) {
            idxSum = 0;
            for (int e = 0; e < songsNum; e++) {
                idxSum += songs[e][i];
            }
            res[i] = idxSum / songsNum;
        }
        return res;
    }

    /**
     * Takes a single array note and returns a
     * string as per JFugue's library.
     *
     * @param arrNote The note array.
     * @return The string note.
     */
    private static String NoteArrayToString(final int[] arrNote) {
        StringBuilder res = new StringBuilder();
        res.append(GetStringKey(arrNote[0]));
        res.append(GetStringOctave(arrNote[0]));
        res.append(GetStringLength(arrNote[1]));
        return res.toString();
    }

    /**
     * Takes an integer and returns
     * the respective note as a String.
     *
     * @param n An int code of the note.
     * @return The String note.
     */
    private static String GetStringKey(final int n) {
        switch (n % 12) {
            case 0: return "C";
            case 1: return "C#";
            case 2: return "D";
            case 3: return "D#";
            case 4: return "E";
            case 5: return "F";
            case 6: return "F#";
            case 7: return "G";
            case 8: return "G#";
            case 9: return "A";
            case 10: return "A#";
            case 11: return "B";
            default: return "ZZ";
        }
    }

    /**
     * Takes an int note and returns
     * its octave.
     *
     * @param n Note as per JFugues codes.
     * @return The octave of the note passed.
     */
    private static String GetStringOctave(final int n) {
        switch (n / 12) {
            case 0: return "0";
            case 1: return "1";
            case 2: return "2";
            case 3: return "3";
            case 4: return "4";
            case 5: return "5";
            case 6: return "6";
            case 7: return "7";
            case 8: return "8";
            case 9: return "9";
            case 10: return "10";
            default: return "ZZ";
        }
    }

    /**
     * Takes an integer encoded length
     * of a note and returns the respective
     * JFugue's code for it.
     *
     * @param l The int code of the length of a note.
     * @return The JFugue's code of the length of a note.
     */
    private static String GetStringLength(final int l) {
        switch (l) {
            case 0: return "w";
            case 1: return "h";
            case 2: return "q";
            case 3: return "i";
            case 4: return "s";
            case 5: return "t";
            case 6: return "x";
            case 7: return "o";
            default: return "Z";
        }
    }

    /**
     * Take a String representation of a note
     * and returns an int array of it.
     *
     * @param strNote The string note.
     * @return The int array note.
     */
    private static int[] StringNoteToArray(final String strNote) {
        int[] res = new int[2];
        int octave;
        int noteL;
        noteL = strNote.length();
        octave = Character.getNumericValue(strNote.charAt(noteL - 2));
        res[0] = GetIntNote(noteL == 3 ? strNote.substring(0, 1) : strNote.substring(0, 2), octave);
        res[1] = GetIntLength(strNote.charAt(noteL - 1));
        return res;
    }

    /**
     * Takes a char representing the length of a note,
     * as per JFugue's codes and returns a respective
     * integer.
     *
     * @param l The char length.
     * @return The int coded length.
     */
    private static int GetIntLength(final char l) {
        switch (l) {
            case 'w': return 0;
            case 'h': return 1;
            case 'q': return 2;
            case 'i': return 3;
            case 's': return 4;
            case 't': return 5;
            case 'x': return 6;
            case 'o': return 7;
            default: return -1;
        }
    }

    /**
     * Takes a note and return its JFugue's code.
     *
     * @param n The String note.
     * @param octave The precalculated octave.
     * @return The int code as per the JFugue's library.
     */
    private static int GetIntNote(final String n, final int octave) {
        return GetNoteIntPosition(n) + 12 * octave;
    }

    /**
     * Takes a String note without octave
     * and returns its position in a single
     * octave.
     *
     * @param n The String note.
     * @return The integer position in the octave.
     */
    private static int GetNoteIntPosition(final String n) {
        switch (n) {
            case "C": return 0;
            case "C#": return 1;
            case "Db": return 1;
            case "D": return 2;
            case "D#": return 3;
            case "Eb": return 3;
            case "E": return 4;
            case "F": return 5;
            case "F#": return 6;
            case "Gb": return 6;
            case "G": return 7;
            case "G#": return 8;
            case "Ab": return 8;
            case "A": return 9;
            case "A#": return 10;
            case "Bb": return 10;
            case "B": return 11;
            default: return -1;
        }
    }
}
