import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class CrimeDataServlet extends HttpServlet {

    public static String CRIME_TABLE_NAME = "CrimeReports";

    public CrimeDataServlet() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double maxLat = 35.0;
        double minLat = 30.0;
        double maxLon = 0.0;
        double minLon = -100.0;

        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":maxLat", new AttributeValue().withN(Double.toString(maxLat)));
        expressionAttributeValues.put(":minLat", new AttributeValue().withN(Double.toString(minLat)));
        expressionAttributeValues.put(":maxLon", new AttributeValue().withN(Double.toString(maxLon)));
        expressionAttributeValues.put(":minLon", new AttributeValue().withN(Double.toString(minLon)));

        ScanRequest scanRequest = new ScanRequest()
            .withTableName(CRIME_TABLE_NAME)
            .withFilterExpression("Latitude < :maxLat")
            .withFilterExpression("Latitude > :minLat")
            .withFilterExpression("Longitude < :maxLon")
            .withFilterExpression("Longitude > :minLon")
            .withProjectionExpression("Latitude")
            .withProjectionExpression("Longitude")
            .withExpressionAttributeValues(expressionAttributeValues);

        List<Double> lat = new ArrayList<Double>();
        List<Double> lon = new ArrayList<Double>();

        ScanResult result = ddb.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            lat.add(Double.parseDouble(item.get("Latitude").getN()));
            lon.add(Double.parseDouble(item.get("Longitude").getN()));
        }

        System.out.println(lat);
        System.out.println(lon);
        return;

       /* request.setAttribute("lat", lat);
        request.setAttribute("lon", lon);
        String nextJSP = "/showProperty.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request,response);*/
    }

    public static void main(String args[]) {
        try {
            new CrimeDataServlet().doGet(null, null);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
