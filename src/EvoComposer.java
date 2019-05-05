import java.util.*;

/**
 * Music composition class, that composes music using
 * genetic algorithm.
 *
 * Create an instance of this class and invoke the compose
 * method for the composition to start. The result returned
 * from the compose method is a single song, that has
 * the highest fitness value. The song is decoded as a
 * 2 dimensional array of integers. The first value of the
 * inner array is the note key encoded as per the JFugue
 * library and the second integer is the length of the note
 * decoded as per the Note class in this package (0-7).
 *
 * One can manipulate the parameters of the genetic
 * algorithm, including the cycles, which determines
 * when the composition will finish.
 *
 * The genetic algorithm is guided by list of guiding
 * songs found in the GuidingSet class.
 *
 * @author Daniel Krastev
 * @version 1.0
 */
public class EvoComposer {
    private static final int POPULATION_SIZE = 200;
    private static final int SONG_SIZE = 48;
    private static final int CYCLES = 20000;
    private static final int CROSSOVER_RATE = 75;
    private static final int MUTATION_RATE_SONGS = 12;
    private static final double MUTATION_RATE_NOTES = 2;
    private static final int MAX_TOURNAMENT_SIZE = 6;

    private String[] gSet;
    private double[] gSetAvDist;
    private Random random;

    private int[][][] population;
    private int[][][] selected;
    private int[][][] tournBuff;
    private double[] fitness;
    private double[] tournBuffFit;
    private int[][] bestSong;
    private double bestFitness;
    private int bestCycle;

    public EvoComposer() {
        this.gSet = GuidingSet.GET_SET();
        computeAverageDistance();
    }

    public int[][] compose() {
        //Get new random generator
        random = new Random();

        population = new int[POPULATION_SIZE][][];
        selected = new int[POPULATION_SIZE][][];
        fitness = new double[POPULATION_SIZE];
        bestCycle = 0;
        bestFitness = 0.0;
        bestSong = new int[SONG_SIZE][2];

        //Generate random initial population
        for (int a = 0; a < POPULATION_SIZE; a++) {
            population[a] = new int[SONG_SIZE][2];
            //Get random song
            for (int b = 0; b < SONG_SIZE; b++) {
                //Get random note with key (9 to 96 -> 88 piano keys from JFugue)
                //And length (0-7)
                population[a][b][0] = 48 + random.nextInt(24); //We can manipulate the range from here. Currently, only two octaves.
                population[a][b][1] = 1 + random.nextInt(3); //Length of the notes. (We would use only half, quarter or eight notes.)
//                population[a][b][1] = 2; //We can use this if we want the song to have only notes of the same length.
            }

            //Calculate the fitness of the initial population
            fitness[a] = fitness(population[a]);
        }

        //Do the cycles
        for (int cycle = 0; cycle < CYCLES; cycle++) {
            //Selection and crossover
            int ptr = 0;
            while (ptr < POPULATION_SIZE) {
                //selection tournament size
                int tournSize = 2 + random.nextInt(MAX_TOURNAMENT_SIZE - 2);
                int tournIdx, crP0, crP1;
                tournBuff = new int[2][SONG_SIZE][2];
                tournBuffFit = new double[]{0.0, 0.0};
                int parsLng, par1Lng, par2Lng;
                if (random.nextInt(100) < CROSSOVER_RATE && ptr != (POPULATION_SIZE - 1)) {
                    //We do a tournament with a random size and
                    //take the best 2 for crossover
                    for (int i = 0; i < tournSize; i++) {
                        tournIdx = random.nextInt(POPULATION_SIZE);
                        if (fitness[tournIdx] > tournBuffFit[0]) {
                            tournBuffFit[1] = tournBuffFit[0];
                            tournBuffFit[0] = fitness[tournIdx];
                            tournBuff[1] = tournBuff[0];
                            tournBuff[0] = population[tournIdx];
                        } else if (fitness[tournIdx] > tournBuffFit[1]) {
                            tournBuffFit[1] = fitness[tournIdx];
                            tournBuff[1] = population[tournIdx];
                        }
                    }
                    par1Lng = tournBuff[0].length;
                    par2Lng = tournBuff[1].length;
                    parsLng = par1Lng + par2Lng;
                    int[][] ch1 = new int[parsLng / 2 + ((parsLng % 2 != 0 && par1Lng % 2 != 0) ? 1 : 0)][];
                    int[][] ch2 = new int[parsLng / 2 + ((parsLng % 2 != 0 && par2Lng % 2 != 0) ? 1 : 0)][];
                    crP0 = par1Lng / 2 + (par1Lng % 2 == 0 ? 0 : 1);
                    crP1 = par2Lng / 2 + (par2Lng % 2 == 0 ? 0 : 1);
                    for (int i = 0; i < parsLng / 2; i++) {
                        ch1[i] = i < crP0 ? tournBuff[0][i].clone() : tournBuff[1][crP1 + (i - crP0)].clone();
                        ch2[i] = i < crP1 ? tournBuff[1][i].clone() : tournBuff[0][crP0 + (i - crP1)].clone();
                    }
                    if (ch1.length > ch2.length) {
                        ch1[ch1.length - 1] = tournBuff[1][par2Lng - 1].clone();
                    } else if (ch2.length > ch1.length) {
                        ch2[ch2.length - 1] = tournBuff[0][par1Lng - 1].clone();
                    }
                    selected[ptr] = ch1.clone();
                    ptr++;
                    selected[ptr] = ch2.clone();
                    ptr++;
                } else {
                    //Do a tournament and add the best
                    for (int i = 0; i < tournSize; i++) {
                        tournIdx = random.nextInt(POPULATION_SIZE);
                        if (fitness[tournIdx] > tournBuffFit[0]) {
                            tournBuffFit[0] = fitness[tournIdx];
                            tournBuff[0] = population[tournIdx].clone();
                        }
                    }
                    selected[ptr] = tournBuff[0].clone();
                    ptr++;
                }
            }

            //Mutation
            for (int i = 0; i < POPULATION_SIZE; i++) {
                int mutateIdx;
                if (random.nextInt(100) < MUTATION_RATE_SONGS) {
                    //do mutation and add
                    for (int m = 0; m < MUTATION_RATE_NOTES; m++) {
                        mutateIdx = random.nextInt(SONG_SIZE);
                        selected[i][mutateIdx][0] = 48 + random.nextInt(24);
                    }
                }
                population[i] = selected[i].clone();
            }

            //Calculate the new fitness
            double sum = 0;
            for (int i = 0; i < POPULATION_SIZE; i++) {
                fitness[i] = fitness(population[i]);
                sum += fitness[i];
                if (fitness[i] > bestFitness) {
                    bestSong = population[i];
                    bestFitness = fitness[i];
                    bestCycle = cycle;
                }
            }

            //Print the average fitness at the end of every cycle
//            System.out.println("(" + cycle + ") Average Fitness: " + (sum / POPULATION_SIZE));
        }

        System.out.println("Best Fitness: " + bestFitness + "; Best Cycle: " + bestCycle);
        System.out.println("Best Song: " + Note.SongArrayToString(bestSong));
        System.out.print("\n\n");
        return bestSong;
    }

    /**
     * Fitness method 1.
     *
     * @param song The song represented as 2 dimensional int array
     * @return The calculated fitness
     */
    private double fitness(final int[][] song) {
        int[] currDists = Note.ComputeDistances(song);
        double[] distToGuide = new double[SONG_SIZE - 1];
        double dist;
        for (int i = 0; i < distToGuide.length; i++) {
            dist = (currDists[i] - gSetAvDist[i]);
            distToGuide[i] = dist < 0 ? -1 * dist : dist;
        }
        double sum = 0;
        for (double d : distToGuide) {
            sum += d;
        }
        return 100.0 / sum;
    }

    /**
     * Fitness method 2.
     *
     * @param song The song represented as 2 dimensional int array
     * @return The calculated fitness
     */
    private double fitness2(final int[][] song) {
        int[] currDists = Note.ComputeDistances(song);
        double currSongDists = 0.0;
        double guideSongDists = 0.0;
        for (int i = 0; i < currDists.length; i++) {
            guideSongDists += Math.abs(gSetAvDist[i]);
            currSongDists += Math.abs(currDists[i]);
        }
        return 1 / Math.abs(guideSongDists - currSongDists);
    }

    /**
     * Computes the average distance of the guiding set.
     * In other words creates a single int list with
     * the guiding distances from all the songs in the
     * guiding set.
     */
    private void computeAverageDistance() {
        int[][] dists = new int[gSet.length][];
        for (int i = 0; i < gSet.length; i++) {
            int[][] songArr = Note.StringSongToArray(gSet[i]);
            dists[i] = Note.ComputeDistances(songArr);
        }
        gSetAvDist = Note.ComputeAverageDistances(dists[0], dists[1], dists[2]);
    }
}
