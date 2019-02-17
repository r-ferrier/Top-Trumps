<html>

<head>
    <!-- Web page title -->
    <title>Top Trumps</title>

    <!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    <script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    <!--<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">-->

    <!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
    <!--<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">-->
    <!--<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>-->
    <!--<script>vex.defaultOptions.className = 'vex-theme-os';</script>-->
    <!--&lt;#&ndash;<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>&ndash;&gt;-->
    <!--<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>-->
    <!--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
    <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">-->

    <link rel="stylesheet" type="text/css"
          href="https://raw.githack.com/r-ferrier/topTrumpsCSS/master/topTrumpsStyle.css">

</head>

<body onload="initialize()"> <!-- Call the initalize method when the page loads -->
<img id="title" src="https://github.com/r-ferrier/images-and-css-for-top-trumps/blob/master/title-image.png?raw=true" alt="Top trumps: Sandwiches edition">
<br>


<div class="winner-message">
    <img id="winner-image" src="https://github.com/r-ferrier/images-and-css-for-top-trumps/blob/master/winner-image.png?raw=true" style="display:none">
    <h2 id="winner"></h2>
</div>

<div class="buttonsContainer">

    <!--form to chose how many players are in game-->
    <div class="choose_players">
        <form action="http://localhost:7777/toptrumps/game" method="GET">
            <label for="choose_players">Number of players for this game:</label>
            <select id="choose_players" name="players" required>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
            <input type=submit value="start new game">
        </form>
    </div>

    <div class="view-stats">
        <form action="http://localhost:7777/toptrumps/stats" method="GET">
            <input type=submit value="view stats"><br>
        </form>
    </div>

</div>
<script type="text/javascript">

    // Method that is called on page load

    function initialize() {

        let n = window.location.search.lastIndexOf("=");
        let result = window.location.search.substring(n + 1);
        let results = result.split(".");
        let rounds = "rounds.";

        if (results[0] === "1") {
            rounds = "round.";
        }

        if (result) {

            document.getElementById("winner").innerHTML = results[1] + " won! <br>Game over in " + results[0] + " " + rounds;
            document.getElementById("winner-image").style.display = "block";

        }
    }

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
</body>


</html>

