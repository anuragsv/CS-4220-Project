import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class CrimeDataServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> lat = ;
        List<Integer> lon = ;
        request.setAttribute("lat", lat);
        request.setAttribute("lon", lon);
    }
}
