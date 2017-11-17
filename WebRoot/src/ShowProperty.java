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
import javax.servlet.annotation.WebServlet;

@WebServlet("/ShowProperty")
public class ShowProperty extends HttpServlet {

    public static String CRIME_TABLE_NAME = "CrimeReports";

    public ShowProperty() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
try {
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
            .withFilterExpression("Latitude <= :maxLat AND Latitude >= :minLat AND Longitude <= :maxLon AND Longitude >= :minLon")
            .withProjectionExpression("Latitude, Longitude")
            .withExpressionAttributeValues(expressionAttributeValues);

        List<Double> lat = new ArrayList<Double>();
        List<Double> lon = new ArrayList<Double>();

        //try {
            ScanResult result = ddb.scan(scanRequest);
        //} catch (Exception e) {
            //out.println(e);
            //return;
        //}
        for (Map<String, AttributeValue> item : result.getItems()) {
            lat.add(Double.parseDouble(item.get("Latitude").getN()));
            lon.add(Double.parseDouble(item.get("Longitude").getN()));
        }


       request.setAttribute("lat", lat);
        request.setAttribute("lon", lon);
        String nextJSP = "/WEB-INF/showProperty.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request,response);
    } catch (Exception e) {
        out.println(e);
        return;
    }
    }

    public static void main(String args[]) throws ServletException, IOException {
        new ShowProperty().doGet(null, null);
    }
}
