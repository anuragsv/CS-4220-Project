import java.text.ParseException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public final class CSVParser {

    public static Crime parseCrimeFromLine(String line, String city) throws ParseException {
        try {
            String[] data = line.split(",");
            CrimeType crimeType = CrimeType.getCrimeTypeFromKey(data[0]);
            int reportNumber = Integer.parseInt(data[1]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("m/d/y");
            LocalDate date = LocalDate.parse(data[2], dateFormat);
            String address = data[3];
            String neighborhood = data[5];
            double latitude = Double.parseDouble(data[7]);
            double longitude = Double.parseDouble(data[8]);
            return new Crime(crimeType, reportNumber, date, address, city, neighborhood, latitude, longitude);
        } catch (Exception e) {
            throw ParseException("Application failed to parse line: " + line + " due to error " + e);
        }
    }

    public static List<Crime> parseFile(File csvFile, String city) {
        List<Crime> crimes = new ArrayList<>();
        try {
    		FileReader fileReader = new FileReader(csvFile);
    		BufferedReader bufferedReader = new BufferedReader(fileReader);
    		String line;
            bufferedReader.readLine(); // Discard first line
    		while ((line = bufferedReader.readLine()) != null) {
    			try {
                    crimes.add(parseCrimeFromLine(line, city));
                } catch (ParseException e) {
                    // Improperly formatted crime line
                    // TODO log this error once a logger is setup
                    System.out.println(e);
                }
    		}
        } catch (IOException e) {
            // Trouble or error reading file
            // TODO log this error once a logger is setup
            System.out.println(e);
        }
		return crimes;
    }

    // Class contains static methods only and should
    // not be instantiated
    private CSVParser() {}
}
