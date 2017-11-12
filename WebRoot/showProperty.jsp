<%@ page import="java.util.List" %>
<html>
  <head>
    <title>Paradisum - Real Estate Crime Mapper</title>
  </head>
  <body>
    <h1>Hello From HTML</h1>
    <br />
    <%
        List<Integer> lat = (List<String>)request.getAttribute("lat");
        List<Integer> lon = (List<String>)request.getAttribute("lon");
    %>
  </body>
</html>
