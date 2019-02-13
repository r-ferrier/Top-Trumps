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

    // Method that is called on page load
    function initalize() {

        // --------------------------------------------------------------------------
        // You can call other methods you want to run when the page first loads here
        // --------------------------------------------------------------------------

        // For example, lets call our sample methods
        // helloJSONList();
        // helloWord("Student");
        //
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

    // This calls the helloJSONList REST method from TopTrumpsRESTAPI
    function helloJSONList() {

        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function (e) {
            var responseText = xhr.response; // the text of the response
            alert(responseText); // lets produce an alert
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }

    // This calls the helloJSONList REST method from TopTrumpsRESTAPI
    function helloWord(word) {

        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word=" + word); // Request type and URL+parameters

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function (e) {
            var responseText = xhr.response; // the text of the response
            alert(responseText); // lets produce an alert
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }

</script>

</body>
</html>