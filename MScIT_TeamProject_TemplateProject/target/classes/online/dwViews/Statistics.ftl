<html>

<head>
    <!-- Web page title -->
    <title>Top Trumps</title>

    <script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>

    <link rel="stylesheet" type="text/css"
          href="https://raw.githack.com/r-ferrier/topTrumpsCSS/master/topTrumpsStyle.css">

</head>

<body onload="initialize()"> <!-- Call the initalize method when the page loads -->

<form action="http://localhost:7777/toptrumps" method="GET">
    <input id="view_stats" type=submit value="home" class="buttons"><br>
</form>

<div class="title">
    <h1 id="stats-title">Statistics</h1>
</div>

<div class="summary">
    <h4 id="overall-games-played">Number of Games Played overall</h4>
    <h4 id="computer-wins">Number of Computer wins</h4>
    <h4 id="human-wins">Number of Human wins</h4>
    <h4 id="average-draws">Average number of draws</h4>
    <h4 id="largest-round">Largest number of rounds played in a single game</h4>
</div>


<script type="text/javascript">

    let array;

    // Method that is called on page load
    function initialize() {

        pullGameStats();

    }

    function setGameStats(responseText){

        array = JSON.parse(responseText);
        document.getElementById("overall-games-played").innerText = 'Number of Games Played overall: '+array[0];
        document.getElementById("computer-wins").innerText = 'Number of Computer wins: '+array[1];
        document.getElementById("human-wins").innerText = 'Number of Human wins: '+array[2];
        document.getElementById("average-draws").innerText = 'Average number of draws: '+array[3];
        document.getElementById("largest-round").innerText = 'Largest number of rounds played in a single game: '+array[4];

    }


    // -----------------------------------------
    // Add your other Javascript methods Here
    // -----------------------------------------

    // This is a reusable method for creating a CORS request. Do not edit this.
    function createCORSRequest(method, url) {
        var xhr = new XMLHttpRequest();
        if ("withCredentials" in xhr) {

            // Check if the XMLHttpRequest object has a "withCredentials" property.
            // "withCredentials" only exists on XMLHTTPRequest2 objects.
            xhr.open(method, url, true);

        } else if (typeof XDomainRequest != "undefined") {

            // Otherwise, check if XDomainRequest.
            // XDomainRequest only exists in IE, and is IE's way of making CORS requests.
            xhr = new XDomainRequest();
            xhr.open(method, url);

        } else {

            // Otherwise, CORS is not supported by the browser.
            xhr = null;

        }
        return xhr;
    }

</script>

<!-- Here are examples of how to call REST API Methods -->
<script type="text/javascript">

    function pullGameStats(){

        let xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/pull-game-stats");

        if (!xhr) {
            alert("CORS not supported");
        }

        xhr.onload = function (e) {
            let responseText = xhr.response; // the text of the response
            setGameStats(responseText); // lets produce an alert
        };

        xhr.send();

    }


</script>

</body>
</html>