<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Paradisum - Real Estate Crime Mapper</title>
    <style>
      #map {
        height: 100%;
      }
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #floating-panel {
        position: absolute;
        top: 10px;
        left: 25%;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
        text-align: center;
        font-family: 'Roboto','sans-serif';
        line-height: 30px;
        padding-left: 10px;
      }
      #floating-panel {
        background-color: #fff;
        border: 1px solid #999;
        left: 25%;
        padding: 5px;
        position: absolute;
        top: 10px;
        z-index: 5;
      }
    </style>
  </head>

  <body>
    <div id="floating-panel">
        <p>Address: ${address} Zestimate: ${zestimate}</p>
    </div>
    <div id="map">
    </div>
    <script>
      var map, heatmap, marker;

      function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: {lat: ${propLat}, lng: ${propLon}},
          mapTypeId: google.maps.MapTypeId.HYBRID
        });

        marker = new google.maps.Marker({
          position: {lat: ${propLat}, lng: ${propLon}},
          map: map,
          title: 'Property'
        });

        heatmap = new google.maps.visualization.HeatmapLayer({
          data: getPoints(),
          map: map
        });
      }

      function toggleHeatmap() {
        heatmap.setMap(heatmap.getMap() ? null : map);
      }

      function changeGradient() {
        var gradient = [
          'rgba(0, 255, 255, 0)',
          'rgba(0, 255, 255, 1)',
          'rgba(0, 191, 255, 1)',
          'rgba(0, 127, 255, 1)',
          'rgba(0, 63, 255, 1)',
          'rgba(0, 0, 255, 1)',
          'rgba(0, 0, 223, 1)',
          'rgba(0, 0, 191, 1)',
          'rgba(0, 0, 159, 1)',
          'rgba(0, 0, 127, 1)',
          'rgba(63, 0, 91, 1)',
          'rgba(127, 0, 63, 1)',
          'rgba(191, 0, 31, 1)',
          'rgba(255, 0, 0, 1)'
        ]
        heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);
      }

      function changeRadius() {
        heatmap.set('radius', heatmap.get('radius') ? null : 20);
      }

      function changeOpacity() {
        heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
      }

      // Heatmap data: 500 Points
      function getPoints() {
        return [
          <%
            /*List<Double> lat = (List<Double>)request.getAttribute("lat");
            List<Double> lon = (List<Double>)request.getAttribute("lon");
            for (int i = 0; i < lat.size() - 1; i++) {
                out.println("new google.maps.LatLng(" + lat.get(i) + ", " + lon.get(i) + "),");
            }
            out.println("new google.maps.LatLng(" + lat.get(lat.size() - 1) + ", " + lon.get(lat.size() - 1) + ")");*/
          %>
        ];
      }
    </script>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCE7pkcLeUxIKWxInulsN7u3I1JankwNk4&libraries=visualization&callback=initMap">
    </script>
  </body>
</html>
