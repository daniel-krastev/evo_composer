import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;

public class EvoComposer {
    List<String> guidingSet = new ArrayList<>();

    public EvoComposer(List<List<Note>> guidingSet) {
        this.guidingSet = GuidingSet.GET_SET();
    }

    private double getFitness(final String candidate) {
        double den = 0;
        for(String guide : guidingSet) {
           den += NCD(candidate, guide);
        }
        return 1/den;
    }

    private static double NCD(final String str1, final String str2) {
        int cx = c(str1);
        int cy = c(str2);
        int cxy = c(str1 + str2);
        System.out.println("C(x+y): " + cxy);
        System.out.println("C(x): " + cx);
        System.out.println("C(y): " + cy);
        return (cxy - (double) Math.min(cx, cy)) / Math.max(cx, cy);
    }

    private static int c(String string) {
        byte[] inData = string.getBytes();
        byte[] outData = new byte[inData.length + 100];
        Deflater comp = new Deflater();
        comp.setInput(inData);
        comp.finish();
        return comp.deflate(outData);
    }
}
