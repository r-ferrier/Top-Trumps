<html>

<head>
    <title>Top Trumps</title>
    <link rel="stylesheet" type="text/css"
          href="https://raw.githack.com/r-ferrier/topTrumpsCSS/master/stats.css">
</head>

<body onload="initialize()"> <!-- Call the initalize method when the page loads -->

    <form action="http://localhost:7777/toptrumps" method="GET">
        <input id="home" type=submit value="home" class="buttons"><br>
    </form>
    <div class="title">
        <h1 id="stats-title">Statistics</h1>
    </div>
    <div class="summary">
        <h4 id="overall-games-played">Database unable to load. <br><br> Please check your connection and try reloading this page.</h4>
        <h4 id="computer-wins"></h4>
        <h4 id="human-wins"></h4>
        <h4 id="average-draws"></h4>
        <h4 id="largest-round"></h4>
    </div>
    <img id="stats-image" src="https://github.com/r-ferrier/images-and-css-for-top-trumps/blob/master/stats.png?raw=true">

    <script type="text/javascript">

        let array;

        /**
         * on intitialisation a method is called to pull game stats from the database using the restapi. If no results are returned,
         * the page just displays a message to alert the user to the fact that the database can't be reached.
         */
        function initialize() {

            pullGameStats();

        }

        function setGameStats(responseText){

            array = JSON.parse(responseText);

            if(array[0]!==undefined) {
                document.getElementById("overall-games-played").innerText = 'Number of Games Played overall: ' + array[0];
                document.getElementById("computer-wins").innerText = 'Number of Computer wins: ' + array[1];
                document.getElementById("human-wins").innerText = 'Number of Human wins: ' + array[2];
                document.getElementById("average-draws").innerText = 'Average number of draws: ' + array[3];
                document.getElementById("largest-round").innerText = 'Largest number of rounds played in a single game: ' + array[4];
            }

        }
    </script>

 <#--cors request methods-->

    <script type="text/javascript">
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

        /**
         * method to pull statistics out of the database
         */
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