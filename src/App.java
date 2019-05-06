import org.jfugue.player.Player;

/**
 * The main class where we start the application.
 *
 * @author Daniel Krastev
 * @version 1.0
 */
public class App {
    private static String COMPOSE_COMMAND = "compose";
    private static String PLAY_GUIDING_SET_COMMAND = "guiding";
    private static String PLAY_GENERATED_SONGS = "generated";

    /**
     * The main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        //Create a JFugue player.
        Player player = new Player();

        if (args.length == 0 || args[0].equalsIgnoreCase(COMPOSE_COMMAND)) {
            composeMusic(player);
        } else if (args[0].equalsIgnoreCase(PLAY_GUIDING_SET_COMMAND)) {
            //Play the guiding set
            playGuidingSet(player);
        } else if (args[0].equalsIgnoreCase(PLAY_GENERATED_SONGS)) {
            //Play an example generated song set
            playGeneratedSet(player);
        }
    }

    public static void composeMusic(final Player player) {
        System.out.println("Genetic algorithm running...");
        //Run the genetic algorithm to compose the new song.
        int[][] res = new EvoComposer().compose();
        System.out.println("Genetic algorithm completed.");
        System.out.println("Song composed.");

        System.out.println("Playing the newly composed song...");
        //Play the song with the "player".
        player.play(Note.SongArrayToString(res));
        System.out.println("Exit.");
    }

    public static void playGuidingSet(final Player player) {
        System.out.println("Playing guiding set. Three short songs.");
        for (String song : GuidingSet.GET_SET()) {
            player.play(song);
        }
        System.out.println("Exit.");
    }

    public static void playGeneratedSet(final Player player) {
        System.out.println("Playing 6 short songs generated with the composer...");
        for (String[] song : GeneratedSongs.GET_SONGS()) {
            System.out.println("Playing song: " + song[1]);
            System.out.println("Fitness: " + song[0]);
            player.play(song[1]);
            System.out.print("\n\n");
        }
        System.out.println("Exit.");
    }
}
