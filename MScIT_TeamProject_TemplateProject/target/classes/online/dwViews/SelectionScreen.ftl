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

    <link rel="stylesheet" type = "text/css" href="https://raw.githack.com/r-ferrier/topTrumpsCSS/master/topTrumpsStyle.css">

</head>

<body onload="initialize()"> <!-- Call the initalize method when the page loads -->

<div class="title">
    <h1 id="firstWord">Top Trumps:</h1>
    <h1 id="secondWord"><br>Sandwiches<br>edition</h1>
</div>
<br>

<div class="winner-message">
    <h2 id="This person won"></h2>
</div>

<div class="buttonsContainer">

    <form action="http://localhost:7777/toptrumps/stats" method="GET">
    <input id="view_stats" type = submit value="view stats" class = "buttons"><br>
    </form>

    <form action="http://localhost:7777/toptrumps/game" method="GET">
        <!--form to chose how many players are in game-->
    <div class="choose_players">
        <label for="choose_players">choose players</label>
        <select id="choose_players" name="players" required>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
        <input id="start_new_game" type = submit value="start new game" class = "buttons"">
    </div>
    </form>

</div>
<script type="text/javascript">

    // Method that is called on page load

    function initialize(){

        let n = window.location.search.lastIndexOf("=");
        let result = window.location.search.substring(n + 1);

        if(result) {

            document.getElementById("This person won").innerText = result+" won! Game over.";

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

