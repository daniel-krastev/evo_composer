import org.jfugue.player.Player;

/**
 * The main class where we start the application.
 *
 * @author Daniel Krastev
 * @version 1.0
 */
public class App {

    /**
     * The main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        //Create a JFugue player.
        Player player = new Player();

        //Run the genetic algorithm to compose the new song.
        int[][] res = new EvoComposer().compose();

        //Play the song with the "player".
        player.play(Note.SongArrayToString(res));
    }
}
