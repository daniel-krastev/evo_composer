import org.jfugue.player.Player;

import java.util.ArrayList;
import java.util.List;


public class App {

    public static void main(String[] args) {
        Player player = new Player();
        EvoComposer composer = new EvoComposer();
        int[][][] res = composer.compose();
        List<String> playables = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        for (int[][] song : res) {
            for(int[] note : song) {
                notes.add(new Note(note));
            }
            playables.add(Note.NotesToStringSong(notes));
            notes.clear();
        }

        for (String song : playables) {
            player.play(song);
        }
    }


}
