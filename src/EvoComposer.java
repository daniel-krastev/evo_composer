import java.util.List;
import java.util.Random;
import java.util.zip.Deflater;

public class EvoComposer {
    private final static int POPULATION_SIZE = 4;
    private final static int MAX_SONG_SIZE = 40;

    List<String> guidingSet;
    Random random;

    public EvoComposer() {
        this.guidingSet = GuidingSet.GET_SET();
    }

    public int[][][] compose() {
        random = new Random();
        //Generate random population

        int[][][] population = getRandomPopulation();

        //Crossover

        //Mutate

        //Calculate fitness

        return population;
    }

    private int[][][] getRandomPopulation() {
        int[][][] pop = new int[POPULATION_SIZE][][];
        for(int i = 0; i < POPULATION_SIZE; i++) {
            pop[i] = getRandomSong();
        }
        return pop;
    }

    private int[][] getRandomSong() {
        int songSize = random.nextInt(MAX_SONG_SIZE);
        int[][] song = new int[songSize][2];
        for(int i = 0; i < songSize; i++) {
            song[i][0] = 9 + random.nextInt(88);
            song[i][1] = random.nextInt(8);
        }
        return song;
    }

    private double getFitness(final String candidate) {
        double den = 0;
        for(String guide : guidingSet) {
           den += NCD(candidate, guide);
        }
        return 1/den;
    }

    private double NCD(final String str1, final String str2) {
        int cx = c(str1);
        int cy = c(str2);
        int cxy = c(str1 + str2);
        System.out.println("C(x+y): " + cxy);
        System.out.println("C(x): " + cx);
        System.out.println("C(y): " + cy);
        return (cxy - (double) Math.min(cx, cy)) / Math.max(cx, cy);
    }

    private int c(String string) {
        byte[] inData = string.getBytes();
        byte[] outData = new byte[inData.length + 100];
        Deflater comp = new Deflater();
        comp.setInput(inData);
        comp.finish();
        return comp.deflate(outData);
    }
}
