import java.util.ArrayList;
import java.util.List;

public class Note {
    private int[] arrNote;
    private String strNote;

    public Note(final String note) {
        strNote = note;
    }

    public Note(final int key, final int length) {
        arrNote = new int[2];
        arrNote[0] = key;
        arrNote[1] = length;
    }

    public static String NotesToStringSong(final Note[] notes) {
        StringBuilder song = new StringBuilder();
        String str;
        for(Note note : notes) {
            str = note.getStringForm();
            song.append(str);
            song.append(" ");
        }
        return song.toString();
    }

    public static List<Note> StringSongToNotes(final String song) {
        List<Note> notes = new ArrayList<>();
        for(String strNote : song.split(" ")) {
            notes.add(new Note(strNote));
        }
        return notes;
    }

    public void mutate() {
        if(arrNote == null) {
            parse();
        }
        doMutate();
    }

    private void doMutate() {
        arrNote[0]++;
        if(arrNote[0] > 96) {
            arrNote[0] = 8;
        }
        arrNote[1] = (arrNote[1] + 1) % 8;
    }


    public int[] getIntForm() {
        if(arrNote != null) {
            return arrNote;
        }
        return parse();
    }

    public String getStringForm() {
        if(strNote != null) {
            return strNote;
        }
        return toStringNote();
    }

    private String toStringNote() {
        StringBuilder res = new StringBuilder();
        res.append(getStrNote(arrNote[0]));
        res.append(getStrOctave(arrNote[0]));
        res.append(getStrLength(arrNote[1]));
        return res.toString();
    }

    private String getStrNote(final int n) {
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

    private String getStrOctave(final int n) {
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

    private String getStrLength(final int l) {
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

    private int[] parse() {
        arrNote = new int[2];
        int octave;
        int noteL;
        noteL = strNote.length();
        octave = Character.getNumericValue(strNote.charAt(noteL - 2));
        arrNote[0] = getIntNote(noteL == 3 ? strNote.substring(0, 1) : strNote.substring(0, 2), octave);
        arrNote[1] = getIntLength(strNote.charAt(noteL - 1));
        return arrNote;
    }

    private int getIntLength(final char l) {
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

    private int getIntNote(final String n, final int octave) {
        return getNoteIntPosition(n) + 12 * octave;
    }

    private int getNoteIntPosition(final String n) {
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
