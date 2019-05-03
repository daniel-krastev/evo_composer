import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;
import org.apache.commons.compress.compressors.lz77support.Parameters;
import org.jfugue.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;


public class App {
    final static String DEAR_MOM = "B4i C5i B4i C5i D5q D5q B4i C5i B4i C5i D5q D5q E5i D5i E5i D5i C5q C5q D5i C5i D5i C5i B4q B4q";
    final static String THE_WHEELS_ON_THE_BUS = "D4q G4q G4i G4i G4q B4q D5q B4q G4h A4q F#4q D4h D5q B4q G4q D4q G4q G4i G4i G4q B4q D5q B4q G4h A4h D4q D4q G4h";
    final static String ITSY_BITSY_SPIDER = "G4i C5i C5i C5i D5i E5q E5i E5i D5i C5i D5i E5i C5h E5q E5i F5i G5q G5i G5i F5i E5i F5i G5i E5h C5q C5i D5i E5q E5q D5i C5i D5i E5i C5q G4i G4i C5i C5i C5i D5i E5q E5i E5i D5i C5i D5i E5i C5h";

    public static void main(String[] args) {

        Player player = new Player();
        List<Note> songS = new ArrayList<>();
        List<Note> songA = new ArrayList<>();
        for(String s : ITSY_BITSY_SPIDER.split(" ")) {
            songS.add(new Note(s));
        }
        songS.forEach(note -> songA.add(new Note(note.getIntForm())));
        StringBuilder songBuilder = new StringBuilder();
        for(Note n : songA) {
            songBuilder.append(n.getStringForm());
            songBuilder.append(" ");
        }
        player.play(songBuilder.toString());
    }


}
