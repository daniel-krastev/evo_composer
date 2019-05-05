import java.util.List;
import java.util.Random;
import java.util.zip.Deflater;

public class EvoComposer {
    private static final Note EMPTY_NOTE = new Note("");
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_SONG_SIZE = 40;
    private static final int MIN_SONG_SIZE = 6;
    private static final int CYCLES = 100;
    private static final int CROSSOVER_RATE = 75;
    private static final int MUTATION_RATE_SONGS = 10;
    private static final double MUTATION_RATE_NOTES = 80;
    private static final int MAX_TOURNAMENT_SIZE = 5;

    private List<String> guidingSet;
    private Random random;

    private Note[][] population;
    private Note[][] selected;
    private Note[][] tournBuff;
    private double[] popFitness;
    private double[] tournBuffFit;
    private String[] popString;

    public EvoComposer() {
        this.guidingSet = GuidingSet.GET_SET();
    }

    public String[] compose() {
        //Get new random generator
        random = new Random();

        population = new Note[POPULATION_SIZE][];
        popFitness = new double[POPULATION_SIZE];
        popString = new String[POPULATION_SIZE];
        selected = new Note[POPULATION_SIZE][];

        //Generate random initial population
        for(int a = 0; a < POPULATION_SIZE; a++) {
            int songSize = MIN_SONG_SIZE + random.nextInt(MAX_SONG_SIZE - MIN_SONG_SIZE + 1);
            population[a] = new Note[songSize];
            //Get random song
            for(int b = 0; b < songSize; b++) {
                //Get random note with key (9 to 96 -> 88 piano keys from JFugue)
                //And length (0-7)
                population[a][b] = new Note(9 + random.nextInt(88), random.nextInt(8));
            }
            //calculate fitness
            popString[a] = Note.NotesToStringSong(population[a]);
            popFitness[a] = getFitness(popString[a]);
        }
        for(int cycle = 0; cycle < CYCLES; cycle++) {
            //Selection and crossover
            int ptr = 0;
            while (ptr < POPULATION_SIZE) {
                //selection tournament size
                int tournSize = random.nextInt(MAX_TOURNAMENT_SIZE) + 2;
                int tournIdx, crP0, crP1;
                tournBuff = new Note[][]{{EMPTY_NOTE, EMPTY_NOTE}, {EMPTY_NOTE, EMPTY_NOTE}};
                tournBuffFit = new double[]{0,0};
                int parsLng, par1Lng, par2Lng;
                if(random.nextInt(100) < CROSSOVER_RATE && ptr != (POPULATION_SIZE - 1)) {
                    //We do a tournament with a random size and
                    //take the best 2 for crossover
                    for(int i = 0; i < tournSize; i++) {
                        tournIdx = random.nextInt(POPULATION_SIZE);
                        if(popFitness[tournIdx] > tournBuffFit[0]) {
                            tournBuffFit[1] = tournBuffFit[0];
                            tournBuffFit[0] = popFitness[tournIdx];
                            tournBuff[1] = tournBuff[0];
                            tournBuff[0] = population[tournIdx];
                        } else if(popFitness[tournIdx] > tournBuffFit[1]) {
                            tournBuffFit[1] = popFitness[tournIdx];
                            tournBuff[1] = population[tournIdx];
                        }
                    }
                    par1Lng = tournBuff[0].length;
                    par2Lng = tournBuff[1].length;
                    parsLng = par1Lng + par2Lng;
                    Note[] ch1 = new Note[parsLng / 2 + ((parsLng % 2 != 0 && par1Lng % 2 != 0) ? 1 : 0)];
                    Note[] ch2 = new Note[parsLng / 2 + ((parsLng % 2 != 0 && par2Lng % 2 != 0) ? 1 : 0)];
                    crP0 = par1Lng / 2 + (par1Lng % 2 == 0 ? 0 : 1);
                    crP1 = par2Lng / 2 + (par2Lng % 2 == 0 ? 0 : 1);
                    for(int i = 0; i < parsLng / 2; i++) {
                        ch1[i] = i < crP0 ? tournBuff[0][i] : tournBuff[1][crP1 + (i - crP0)];
                        ch2[i] = i < crP1 ? tournBuff[1][i] : tournBuff[0][crP0 + (i - crP1)];
                    }
                    if(ch1.length > ch2.length) {
                        ch1[ch1.length - 1] = tournBuff[1][par2Lng - 1];
                    } else if(ch2.length > ch1.length) {
                        ch2[ch2.length - 1] = tournBuff[0][par1Lng - 1];
                    }
                    selected[ptr] = ch1;
                    ptr++;
                    selected[ptr] = ch2;
                    ptr++;
                } else {
                    //We do a tournament and add the best
                    for(int i = 0; i < tournSize; i++) {
                        tournIdx = random.nextInt(POPULATION_SIZE);
                        if(popFitness[tournIdx] > tournBuffFit[0]) {
                            tournBuffFit[0] = popFitness[tournIdx];
                            tournBuff[0] = population[tournIdx];
                        }
                    }
                    selected[ptr] = tournBuff[0];
                    ptr++;
                }
            }

            //Mutation
            for(int i = 0; i < POPULATION_SIZE; i++) {
                if(random.nextInt(100) < MUTATION_RATE_SONGS) {
                    //do mutation and add
                    for(int m = 0; m < selected[i].length * (MUTATION_RATE_NOTES/100); m++) {
                        selected[i][random.nextInt(selected[i].length)].mutate();
                    }
                }
                population[i] = selected[i];
            }

            //Calculate fitness
            for(int i = 0; i < POPULATION_SIZE; i++) {
                popString[i] = Note.NotesToStringSong(population[i]);
                popFitness[i] = getFitness(popString[i]);
            }

        }

        return popString;
    }

    private double getFitness(final String candidate) {
        if(candidate.trim().equals("") || candidate.trim().isEmpty()) {
            return 0;
        }
        double denominator = 0;
        for(String guide : guidingSet) {
           denominator += NCD(candidate, guide);
        }
        double res = 1/denominator;
        return res;
    }

    private double NCD(final String str1, final String str2) {
        int cx = c(str1);
        int cy = c(str2);
        int cxy = c(str1 + str2);
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
