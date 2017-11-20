import java.util.List;
import java.io.File;

public final class CrimeSearcher {
    public static void main(String args[]) {
        // TODO this class will look very different later on
        // the point right now is just to give fake data to the database
        for (int i = 0; i <= 105; i++) {
            String filename = "Data (" + i + ").csv";
            File file = new File(filename);
            List<Crime> crimes = CSVParser.parseFile(file, "Atlanta");
            CrimeUploader.uploadCrime(crimes);
            System.out.println("Just Finished File " + filename);
        }
    }

    // Class contains static methods only and should
    // not be instantiated
    private CrimeSearcher() {}
}
