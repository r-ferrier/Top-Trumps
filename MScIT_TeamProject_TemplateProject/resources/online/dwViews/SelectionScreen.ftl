<html>

<head>

    <title>Top Trumps</title>

    <script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>

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

    /**
     * on page load just one thing is checked - whether a game has just been played, retuirning a winner. If it has, the winner's
     * name and number of rounds are printed to the screen.
     */
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
</script>
</body>


</html>

