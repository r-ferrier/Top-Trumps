<html>

<head>
    <!-- Web page title -->
    <title>Top Trumps</title>

<#--<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) &ndash;&gt;-->
<#--<script src="https://code.jquery.com/jquery-2.1.1.js"></script>-->
<#--<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>-->

    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

    <link rel="stylesheet" type="text/css"
          href="https://raw.githack.com/r-ferrier/topTrumpsCSS/master/topTrumpsGameScreen.css">

</head>

<body onload="initalize()"> <!-- Call the initalize method when the page loads -->

<div class="top-line">
    <input id="quit" type="submit" value="quit"></input>
    <h2 id="players-turn"></h2>
    <h6 id="testing-area"></h6>
</div>

<div class="all-cards-played" style="display: none">
    <img id="player1-card" src="https://github.com/r-ferrier/topTrumpsCSS/blob/master/whole_card_big_mac1.png?raw=true"
         width="170">
    <img id="player2-card"
         src="https://github.com/r-ferrier/topTrumpsCSS/blob/master/whole_card_jam_sandwich1.png?raw=true" width="170">
    <img id="player3-card" src="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/imageofcard.png"
         width="170">
    <img id="player4-card" src="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/imageofcard.png"
         width="170">
    <img id="player5-card" src="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/imageofcard.png"
         width="170">
</div>

<div class="card-outline" style="display: none">
    <div class="card-contents">

        <img id="sandwich" src="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/jam_sandwich.png"
             width="200" height="150">
        <p id="caption">Sandwich Name</p>


        <div class="radio-labels">
            <div class="this-label">
                <label for="Deliciousness">Deliciousness</label>
                <input type="radio" id="Deliciousness" name="choices" value="Deliciousness">
                <label for="Deliciousness" id="category1"></label>
            </div>
            <br>
            <div class="this-label">
                <label for="Size">Size</label>
                <input type="radio" id="Size" name="choices" value="Size">
                <label for="Size" id="category2"></label>
            </div>
            <br>
            <div class="this-label">
                <label for="Toastability">Toastability</label>
                <input type="radio" id="Toastability" name="choices" value="Toastability">
                <label for="Toastability" id="category3"></label>
            </div>
            <br>
            <div class="this-label">
                <label for="Satiation">Satiation</label>
                <input type="radio" id="Satiation" name="choices" value="Satiation">
                <label for="Satiation" id="category4"></label>
            </div>
            <br>
            <div class="this-label">
                <label for="Complexity">Complexity</label>
                <input type="radio" id="Complexity" name="choices" value="Complexity">
                <label for="Complexity" id="category5"></label>
            </div>
        </div>
    </div>
</div>

<div class="ai-card-outline" style="display: none">
    <img id="human-card" width="300" src ="">
</div>


<div class="winning-card" style="display: none">
    <img id="winners-card" width="300">
</div>

<div class="bottom-line">

    <div class="play-card">
        <input id="play-card" type="submit" value="play card" onclick="buttons()">
    </div>

    <div class="game-stats">
        <p id="number-of-cards">number of cards left Placeholder</p>
        <p id="count-of-rounds">Count of rounds Placeholder</p>
        <div>

        </div>


    </div>

</body>

<script type="text/javascript">

    //    let words = new URLSearchParams(window.location.search);

    let cardImagesArray = ["https://github.com/r-ferrier/topTrumpsCSS/blob/master/imageofcard.png?raw=true", "https://github.com/r-ferrier/topTrumpsCSS/blob/master/imageofcard.png?raw=true"];
    let imagesArray = ["https://github.com/r-ferrier/topTrumpsCSS/blob/master/hawaiian.png?raw=true", "https://github.com/r-ferrier/topTrumpsCSS/blob/master/smorrebrod.png?raw=true"];

    let ifHuman;
    let deck;
    let listOfPlayers = [];

    let indexOfCurrentPlayer = 0;
    let indexOfHumanPlayer;

    class Player {

        constructor(name, number, hand, human) {
            this._name = name;
            this._number = number;
            this._hand = hand;
            this._knockedOut = false;
            this._human = human;
            this._roundsWon = 0;
        }

        get name() {
            return this._name;
        }

        get hand() {
            return this._hand;
        }

        get number() {
            return this._number;
        }

        get knockedOut() {
            return this._knockedOut;
        }

        get human() {
            return this._human;
        }

        get roundsWon() {
            return this._roundsWon;
        }
    }

    // Method that is called on page load
    function initalize() {

        setPlayers();
        getWhoIsInGame();
        getDeck();

    }

    function setPlayers() {
        let aiName = ['Clive', 'Brenda', 'Janet', 'Philip'];
        let hand = [];

        listOfPlayers.push(new Player('you', 0, hand, true));

        for (let i = 0; i < (${players}-1); i++) {
            listOfPlayers.push(new Player(aiName[i], 0, hand, false))
        }

        shuffle(listOfPlayers);
    }


    function setDeck(deckFromRestApi) {

        deck = JSON.parse(deckFromRestApi); //converts string into an object

        let playerIndex = 0;

        for (let i = 0; i < deck.length; i++) {  //iterates over the deck, dealing cards to players in order

            listOfPlayers[playerIndex]._hand.push(deck[i]);
            playerIndex++;
            if (playerIndex >=${players}) {
                playerIndex = 0;
            }
        }


        findHuman();
        beginGame();


    }

    function findHuman(){

        for (let i = 0; i < listOfPlayers.length; i++){
            if(listOfPlayers[i]._human){
                indexOfHumanPlayer = i;
                return i;
            }
        }
        return 0;
    }

    function beginGame(){

        let categories = listOfPlayers[indexOfCurrentPlayer]._hand[0].categoryValues;
        let name = listOfPlayers[indexOfCurrentPlayer]._hand[0].description;
        let number = listOfPlayers[indexOfCurrentPlayer]._hand[0].cardNumber;

        document.getElementById("testing-area").innerHTML = name + " "+ number;

        if(indexOfCurrentPlayer===indexOfHumanPlayer){
            setCardWithChoices(categories,name);
        }else{
            setCardWithoutChoices(0);
        }
    }

    function setCardWithChoices(categories,name) {

        document.getElementsByClassName("card-outline")[0].style.display = "block";
        document.getElementsByClassName("ai-card-outline")[0].style.display = "none";

        document.getElementById("category1").innerText = categories[0];
        document.getElementById("category2").innerText = categories[1];
        document.getElementById("category3").innerText = categories[2];
        document.getElementById("category4").innerText = categories[3];
        document.getElementById("category5").innerText = categories[4];

        document.getElementById("caption").innerText = name;

    }

    function setCardWithoutChoices(number){

        document.getElementById("human-card").src=cardImagesArray[number];
        document.getElementsByClassName("card-outline")[0].style.display = "none";
        document.getElementsByClassName("ai-card-outline")[0].style.display = "block";




    }


    function startingGame(startMessage) {

        document.getElementById("players-turn").innerHTML = startMessage;

    }

    function buttons() {

        var button = document.getElementById("play-card").getAttribute("value");


        if (button === "play card") {
            document.getElementsByClassName("card-outline")[0].style.display = "none";
            document.getElementsByClassName("all-cards-played")[0].style.display = "block";
            document.getElementsByClassName("winning-card")[0].style.display = "none";
            document.getElementById("play-card").setAttribute("value", "show winner");
        } else if (button === "show winner") {
            document.getElementsByClassName("card-outline")[0].style.display = "none";
            document.getElementsByClassName("all-cards-played")[0].style.display = "none";
            document.getElementsByClassName("winning-card")[0].style.display = "block";
            document.getElementById("play-card").setAttribute("value", "continue to next round");

            //    getWinner();

        } else if (button === "continue to next round") {

            if (ifHuman) {
                document.getElementsByClassName("card-outline")[0].style.display = "block";
                document.getElementsByClassName("ai-card-outline")[0].style.display = "none";
            } else {
                document.getElementsByClassName("card-outline")[0].style.display = "none";
                document.getElementsByClassName("ai-card-outline")[0].style.display = "block";
            }
            document.getElementsByClassName("all-cards-played")[0].style.display = "none";
            document.getElementsByClassName("winning-card")[0].style.display = "none";
            document.getElementById("play-card").setAttribute("value", "play card");

            playersTurn();
        }
    }



    // function displayWinner(winnerInfo) {
    //
    //     var winnerArray = JSON.parse(winnerInfo);
    //     var cardnumber = parseInt(winnerArray[1]);
    //
    //     document.getElementById("players-turn").innerText = "Player " + winnerArray[0] + " won.";
    //     document.getElementById("winners-card").src = cardImagesArray[cardnumber];
    //
    // }

    // function checkIfCurrentPlayerHuman(human){
    //
    //     if(human){
    //         getCategories();
    //     }else{
    //         document.getElementsByClassName("card-outline")[0].style.display = "none";
    //         document.getElementsByClassName("ai-card-outline")[0].style.display = "block";
    //     }
    // }

    function setPlayersTurn(playerInfo) {

        var player = JSON.parse(playerInfo);

        document.getElementById("players-turn").innerText = "it is " + player[0] + " turn";

        setCategories(player);

        var card = parseInt(player[6]);

        document.getElementById("sandwich").src = imagesArray[card];
    }


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

    function shuffle(array) {
        let currentIndex = array.length, temporaryValue, randomIndex;

        // While there remain elements to shuffle...
        while (0 !== currentIndex) {

            // Pick a remaining element...
            randomIndex = Math.floor(Math.random() * currentIndex);
            currentIndex -= 1;

            // And swap it with the current element.
            temporaryValue = array[currentIndex];
            array[currentIndex] = array[randomIndex];
            array[randomIndex] = temporaryValue;
        }

        return array;
    }


</script>

<!-- this is how we get java into the website -->


<!-- Here are examples of how to call REST API Methods -->
<script type="text/javascript">

    // function startGame(numOfPlayers) {
    //
    //     var htmlStart = "http://localhost:7777/toptrumps/start-game/";
    //     var numberPathParam = numOfPlayers;
    //
    //     var xhr = createCORSRequest('GET', htmlStart + numberPathParam); //first create cors request to my new restapi method
    //
    //     if (!xhr) {
    //         alert("CORS not supported");
    //     }
    //
    //     xhr.onload = function (e) {
    //
    //         var responseText = xhr.response; // the text of the response
    //         startingGame(responseText);
    //
    //     };
    //
    //     // We have done everything we need to prepare the CORS request, so send it
    //     xhr.send();
    //
    // }

    // function getWinner() {
    //
    //     var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/get-winner"); //first create cors request to my new restapi method
    //
    //     if (!xhr) {
    //         alert("CORS not supported");
    //     }
    //
    //     xhr.onload = function (e) {
    //
    //         var responseText = xhr.response; // the text of the response
    //         displayWinner(responseText);
    //
    //     };
    //
    //     // We have done everything we need to prepare the CORS request, so send it
    //     xhr.send();
    //
    // }


    function getCategories() {

        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/category-values"); //first create cors request to my new restapi method

        if (!xhr) {
            alert("CORS not supported");
        }

        xhr.onload = function (e) {

            var responseText = xhr.response; // the text of the response
            setCategories(responseText);

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }

    function playersTurn() {

        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/players-turn"); //first create cors request to my new restapi method

        if (!xhr) {
            alert("CORS not supported");
        }

        xhr.onload = function (e) {

            var responseText = xhr.response; // the text of the response
            setPlayersTurn(responseText);

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }

    function getDeck() {

        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/deck"); //first create cors request to my new restapi method

        if (!xhr) {
            alert("CORS not supported");
        }

        xhr.onload = function (e) {

            var responseText = xhr.response; // the text of the response
            setDeck(responseText);
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }

    function getWhoIsInGame() {

        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/who-is-left/${players}"); //first create cors request to my new restapi method

        if (!xhr) {
            alert("CORS not supported");
        }

        xhr.onload = function (e) {

            var responseText = xhr.response; // the text of the response
            startingGame(responseText);
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }


</script>


</html>