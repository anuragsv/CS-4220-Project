import java.text.ParseException;

public final class CSVParser {

    public Crime parseCrimeFromLine(String line) throws ParseException {
        try {
            String[] data = line.split(",");
            CrimeType crimeType = CrimeType.getCrimeTypeFromKey(data[0]);
            int reportNumber = Integer.parseInt(data[1]);
            // TODO left off here
        } catch (Exception e) {
            throw ParseException("Application failed to parse line: " + line + " due to error " + e);
        }
    }

    public static List<Crime> parseFile(File csvFile) {
        List<Crime> crimes = new ArrayList();
        try {
    		FileReader fileReader = new FileReader(csvFile);
    		BufferedReader bufferedReader = new BufferedReader(fileReader);
    		String line;
    		while ((line = bufferedReader.readLine()) != null) {
    			try {
                    crimes.add(parseCrimeFromLine(lines));
                } catch (ParseException e) {
                    // Improperly formatted crime line
                    // TODO log this error once a logger is setup
                }
    		}
        } catch (IOException e) {
            // Trouble or error reading file
            // TODO log this error once a logger is setup
        }
		return crimes;
    }

    // Class contains static methods only and should
    // not be instantiated
    private CSVParser() {}
}
