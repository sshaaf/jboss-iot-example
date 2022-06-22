var xAxis = [0];
var xMax = 0;

var config = {
    type: 'line',
    data: {
        labels: xAxis,
        datasets: [{
            label: 'Temperature',
            backgroundColor: 'red',
            borderColor: 'red',
            data: [
            ],
            fill: false,
        }, {
            label: 'Humidity',
            fill: false,
            backgroundColor: 'blue',
            borderColor: 'blue',
            data: [
            ],
        }]
    },
    options: {
        responsive: true,
        plugins: {
            title: {
                display: true,
                text: 'Chart.js Line Chart'
            },
            tooltip: {
                mode: 'index',
                intersect: false,
            }
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            x: {
                display: true,
                title: {
                    display: true,
                    text: 'Month'
                }
            },
            y: {
                display: true,
                title: {
                    display: true,
                    text: 'Value'
                }
            }
        }
    }
};

window.onload = function() {
    var ctx = document.getElementById('canvas').getContext('2d');
    window.myLine = new Chart(ctx, config);
};

var loc = window.location
var port = loc.port;
var wsProtocol = window.location.protocol == "https:" ? "wss" : "ws";
var wsurl = wsProtocol + "://" + loc.hostname + ':' + port + loc.pathname + 'devicesocket'
console.log(wsurl)
var socket = new WebSocket(wsurl);

socket.onmessage
socket.onopen = function(event) {
    console.log('Connection established');
    // Display user friendly messages for the successful establishment of connection
}

/**
 *
 * {
  "id": 0,
  "devices": [
    {
      "deviceName": "ESP8266-01",
      "uuid": "5eb1dafc-767c-4323-b76c-7884d0ccbc37",
      "features": [
        {
          "name": "Humidity",
          "value": "24"
        },
        {
          "name": "Temperature",
          "value": "35"
        }
      ]
    }
  ]
}
 * */
socket.onmessage = function(event){
    var incoming = JSON.parse(event.data);
    console.log(incoming);

    var temp = incoming.devices[0].features[1].value;
    var humidity = incoming.devices[0].features[0].value;
    var deviceName = incoming.devices[0].deviceName;

    if (config.data.datasets.length > 0) {
        xMax = xMax +5;
        config.data.labels.push(xMax);

        config.data.datasets[0].data.push(temp);
        config.data.datasets[1].data.push(humidity);
        window.myLine.update();
    }

    tr_temp = "<tr role=\"row\">\n" +
        "            <td role=\"cell\" data-label=\"Device Name\">A</td>\n" +
        "            <td role=\"cell\" data-label=\"Temperature\">B</td>\n" +
        "            <td role=\"cell\" data-label=\"Humidity\">C</td>\n" +
        "            <td role=\"cell\" data-label=\"Reading\">D</td>\n" +
        "        </tr>";

    var tableBody = document.getElementById("tempBody");

    var row = document.createElement("tr");
    var cell = document.createElement("td")
    var cellText = document.createTextNode(deviceName);
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(temp+"°");
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(humidity+"%");
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(new Date());
    cell.appendChild(cellText);
    row.appendChild(cell);

    tableBody.appendChild(row);

}
