import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;

public class CrimeUploader {
    public static String CRIME_TABLE_NAME = "CrimeReports";

    public static void uploadCrime(List<Crime> crimes) {
        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        for (Crime crime : crimes) {
            HashMap<String,AttributeValue> crime_data_values = new HashMap<String,AttributeValue>();
            crime_data_values.put("CrimeType", new AttributeValue(crime.getCrimeType().getDataBaseKey()));
            crime_data_values.put("ReportNumber", new AttributeValue().withN(Integer.toString(crime.getReportNumber())));
            crime_data_values.put("ReportDate", new AttributeValue(crime.getReportDate().format(DateTimeFormatter.BASIC_ISO_DATE)));
            crime_data_values.put("Address", new AttributeValue(crime.getAddress()));
            crime_data_values.put("Neighborhood", new AttributeValue(crime.getNeighborhood()));
            crime_data_values.put("City", new AttributeValue(crime.getCity()));
            crime_data_values.put("Latitude", new AttributeValue().withN(Double.toString(crime.getLatitude())));
            crime_data_values.put("Longitude", new AttributeValue().withN(Double.toString(crime.getLongitude())));

            try {
                ddb.putItem(CRIME_TABLE_NAME, crime_data_values);
            } catch (ResourceNotFoundException e) {
                // TODO actually handle this error
                // System.err.format("Error: The table \"%s\" can't be found.\n", table_name);
                // System.err.println("Be sure that it exists and that you've typed its name correctly!");
                // System.exit(1);
                System.out.println(e);
            } catch (AmazonServiceException e) {
                // TODO actually handle this error
                // System.err.println(e.getMessage());
                // System.exit(1);
                System.out.println(e);
            }
        }
    }
}
