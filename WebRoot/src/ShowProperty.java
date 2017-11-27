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
import java.net.URL;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

@WebServlet("/ShowProperty")
public class ShowProperty extends HttpServlet {

    public static String CRIME_TABLE_NAME = "CrimeReports";

    public ShowProperty() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            boolean didSetAddress = false;
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.equals("address")) {
                    String[] paramValues = request.getParameterValues(paramName);
                    String address = paramValues[0];
                    request.setAttribute("address", address);
                    didSetAddress = true;
                }
            }

            if (!didSetAddress) {
                throw new IllegalArgumentException("Must specify an address to view this page.");
            }

            double maxLat = 40.0;
            double minLat = 25.0;
            double maxLon = 0.0;
            double minLon = -100.0;

            // Now make request to zillow API
            URL url = new URL("https://www.zillow.com/webservice/GetSearchResults.htm?zws-id=X1-ZWz1fz9n2j2ya3_8i7yl&citystatezip=Atlanta%2C%20GA&" + request.getQueryString());
            InputStream stream = url.openStream();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            try {
                Document doc = dBuilder.parse(stream);
                Double latitude = Double.parseDouble(doc.getElementsByTagName("latitude").item(0).getTextContent());
                Double longitude = Double.parseDouble(doc.getElementsByTagName("longitude").item(0).getTextContent());
                int zestimate = Integer.parseInt(doc.getElementsByTagName("amount").item(0).getTextContent());

                request.setAttribute("zestimate", zestimate);
                request.setAttribute("propLat", latitude);
                request.setAttribute("propLon", longitude);
            } catch (Exception e) {
                out.println("Address provided was invalid.");
                return;
            }

            /*final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

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

            boolean hasMore = true;
            while (hasMore) {
                ScanResult result = ddb.scan(scanRequest);
                for (Map<String, AttributeValue> item : result.getItems()) {
                    lat.add(Double.parseDouble(item.get("Latitude").getN()));
                    lon.add(Double.parseDouble(item.get("Longitude").getN()));
                }
                if (result.getLastEvaluatedKey() != null) {
                    hasMore = result.getLastEvaluatedKey().isEmpty() == false;
                } else {
                    hasMore = false;
                }
                if (hasMore) {
                    scanRequest.setExclusiveStartKey(result.getLastEvaluatedKey());
                }
            }



            request.setAttribute("lat", lat);
            request.setAttribute("lon", lon);*/
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
