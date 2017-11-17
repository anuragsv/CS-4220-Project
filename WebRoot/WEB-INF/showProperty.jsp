<%@ page import="java.util.List" %>
<html>
  <head>
    <title>Paradisum - Real Estate Crime Mapper</title>
  </head>
  <body>
    <h1>Hello From HTML, Show Property PAGE2</h1>
    <br />
    <%
        List<Double> lat = (List<Double>)request.getAttribute("lat");
        List<Double> lon = (List<Double>)request.getAttribute("lon");
        for (int i = 0; i < lat.size(); i++) {
            out.println("(" + lat.get(i) + ", " + lon.get(i) + ")<br />");
        }
    %>
  </body>
</html>
