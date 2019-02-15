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
    <div id="rounds-played">
        <div id="count-of-rounds">
            Round <strong>23</strong>
        </div>
    </div>
    <div id="quit">
        <form action="http://localhost:7777/toptrumps" method="GET">
            <input type="submit" value="quit">
        </form>
    </div>
</div>


<div class="middle-line">

    <div class="players-turn">
        <h2 id="players-turn-text"></h2>
    </div>

    <div class="all-cards-played" style="display: none">
        <div class="row">
            <div class="card-1" style="margin-left: 15px" style="margin-right: 15px">
                <img id="player-1-card" width="170">
                <p id="card-1-caption"></p>
            </div>
        </div>
        <div class="row">
            <div class="card-2" style="margin-left: 15px" style="margin-right: 15px">
                <img id="player-2-card" width="170">
                <p id="card-2-caption"></p>
            </div>
        </div>
        <div class="row">
            <div class="card-3" style="margin-left: 15px" style="margin-right: 15px">
                <img id="player-3-card" width="170">
                <p id="card-3-caption"></p>
            </div>
        </div>
        <div class="row">
            <div class="card-4" style="margin-left: 15px" style="margin-right: 15px">
                <img id="player-4-card" width="170">
                <p id="card-4-caption"></p>
            </div>
        </div>
        <div class="row">
            <div class="card-5" style="margin-left: 15px" style="margin-right: 15px">
                <img id="player-5-card" width="170">
                <p id="card-5-caption"></p>
            </div>
        </div>
    </div>

    <div class="card-outline" style="display: none">
        <div class="card-contents">
            <img id="sandwich" src="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/jam_sandwich.png"
                 width="200" height="150">
            <p id="caption"></p>
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
        <img id="human-card" width="250px">
    </div>

    <div class="winning-card" style="display: none">
        <img id="winners-card" width="200px">
        <p id="winners-card-caption"></p>
    </div>

    <div class="play-card">
        <input id="play-card" type="submit" value="play your card" name="choices" onclick="buttons()">
        <form action="http://localhost:7777/toptrumps" method="GET">
            <button id="end-game" type="submit" name="winner" style="display: none">end game</button>
        </form>
    </div>

</div>

<div class="bottom-line">

    <div class="game-stats">
        <div id="players-in-game">
            <div id="not-number">
                <p></p>
            </div>
            <p>Players: </p>
        </div>
        <div id="cards-left">
            <div id="cards-left-0">
                <div id="number">
                    <p id="number-0"></p>
                </div>
                <p id="name-0">name</p>
            </div>
            <div id="cards-left-1">
                <div id="number">
                    <p id="number-1"></p>
                </div>
                <p id="name-1">name</p>
            </div>
            <div id="cards-left-2">
                <div id="number">
                    <p id="number-2"></p>
                </div>
                <p id="name-2">name</p>
            </div>
            <div id="cards-left-3">
                <div id="number">
                    <p id="number-3"></p>
                </div>
                <p id="name-3">name</p>
            </div>
            <div id="cards-left-4">
                <div id="number">
                    <p id="number-4"></p>
                </div>
                <p id="name-4">name</p>
            </div>
        </div>
        <div id="cards-left-communal">
            <div id="number-communal">
                <p id="communal-6" style="text-align: center">23</p>
            </div>
            <p>On the table</p>
        </div>
    </div>


</div>
</body>

<script type="text/javascript">

    /*
    global variables for use in functions
     */

    const cardAddressStart = "https://raw.githack.com/r-ferrier/images-and-css-for-top-trumps/master/imagesOfCards/"
    const sandwichAddressStart = "https://raw.githack.com/r-ferrier/images-and-css-for-top-trumps/master/maincards/";
    const addressEnd = ".png";

    let deck;
    let listOfPlayers = [];
    let communalPile = [];
    let countOfRounds = 1;

    let indexOfCurrentPlayer = 0;
    let indexOfHumanPlayer;
    let indexOfRoundWinner = 0;
    let categoryIndexOfCardChoice = 0;
    let cardNumberOfRoundWinner = 0;
    let winningCardName;
    let winningCardCategory;
    let winningCardDescription;
    let categoryChoice;
    let draw;
    let humanIsGone = false;

    let numberOfDraws = 0;

    /*
    creating one class to create player objects from. The player will hold a hand of cards, a name, and other information
    required to run the game.
     */
    class Player {

        constructor(name, number, human) {

            let hand = [];
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

    /*
    Method that is called on page load
    */
    function initalize() {

        setPlayers(); //creates as many Player objects as required for game, stores them in an array (players) and prints out two lines at top of game announcing turns
        getDeckAndBeginGame(); //calls on the rest api to upload a deck, which is then shuffled and dealt to players. changes display depending on whose turn it is and shows human's top card.

    }

    function setPlayers() {

        let aiName = ['Clive', 'Brenda', 'Janet', 'Philip'];

        listOfPlayers.push(new Player('You', 0, true));


        for (let i = 1; i < ${players}; i++) {

            listOfPlayers.push(new Player(aiName[i-1], i, false))

        }

        shuffle(listOfPlayers);
    }

    function setDeckAndBeginGame(deckFromRestApi) {

        deck = JSON.parse(deckFromRestApi); //converts string into an object

        let playerIndex = 0;

        for (let i = 0; i < deck.length; i++) {  //iterates over the deck, dealing cards to players in order
            console.log(deck[i]);
            listOfPlayers[playerIndex]._hand.push(deck[i]);

            console.log(listOfPlayers[playerIndex].name);
            playerIndex++;

            console.log(i);

            if (playerIndex >=${players}) {
                playerIndex = 0;
            }
        }

        for (let i = 0; i < listOfPlayers[0]._hand.length; i++) {

            console.log(listOfPlayers[0]._hand[i] + " card in " + listOfPlayers[0].name + "'s hand");
        }

        findHuman();
        beginRound();
        displayNumberOfCardsLeft();
    }

    /*
    returns position of human in the array, returns null if human knocked out
     */
    function findHuman() {
        for (let i = 0; i < listOfPlayers.length; i++) {
            if (listOfPlayers[i]._human) {
                indexOfHumanPlayer = i;
                return i;
            }
        }
        indexOfHumanPlayer = null;
        return null;
    }

    /*
    decides whether to show player a card they can make choices from if it's their turn, or a picture of card if not.
    Displays correct values.
     */
    function beginRound() {

        let categories = listOfPlayers[indexOfCurrentPlayer]._hand[0].categoryValues;
        let name = listOfPlayers[indexOfCurrentPlayer]._hand[0].description.replace('_', ' ');
        let number = (listOfPlayers[indexOfHumanPlayer]._hand[0].cardNumber) + 1;

        document.getElementsByClassName("winning-card")[0].style.display = "none";

        document.getElementById("count-of-rounds").innerHTML = "Round <strong>" + countOfRounds + "</strong>";

        if (indexOfCurrentPlayer === indexOfHumanPlayer) {
            setCardWithChoices(categories, name, number);
        } else {
            setCardWithoutChoices(number);
        }

        document.getElementById("players-turn-text").innerHTML = getWhoseTurnItIs();
    }


    function endGame() {

        document.getElementsByClassName("winning-card")[0].style.display = "none";
        document.getElementById("play-card").style.display = "none";
        document.getElementById("end-game").style.display = "block";

        if (humanIsGone) {
            findAIWinner();
            document.getElementById("players-turn-text").innerHTML = "You've been knocked out! Click the button to let the ai players finish the game and find out who wins.";
        } else {
            document.getElementById("players-turn-text").innerHTML = "You've won! Click the button to return to the homescreen.";

            document.getElementById("end-game").value = [countOfRounds, listOfPlayers[0].name];
        }

        setDatabase(numberOfDraws + "," + listOfPlayers[0]._number + "," + (countOfRounds - 1));

    }

    function findAIWinner() {

        while (listOfPlayers > 1) {
            let maxNumber = 0;
            for (let i = 0; i < 5; i++) {
                let value = listOfPlayers[indexOfCurrentPlayer]._hand[0].categoryValues[i];
                if (value > maxNumber) {
                    maxNumber = value;
                    categoryIndexOfCardChoice = i;
                }
            }

            maxNumber = 0;
            draw = false;

            for (let i = 0; i < listOfPlayers.length; i++) {
                winningCardCategory = listOfPlayers[i]._hand[0].categoryValues[categoryIndexOfCardChoice];

                if (winningCardCategory > maxNumber) {
                    draw = false;
                    maxNumber = winningCardCategory;
                    indexOfRoundWinner = i;

                } else if (winningCardCategory === maxNumber) {
                    draw = true;
                    winningCardName = null;
                    indexOfRoundWinner = null;
                    cardNumberOfRoundWinner = null;
                    winningCardDescription = null;
                }
            }

            if (!draw) {
                winningCardCategory = maxNumber;
            }

            for (let i = 0; i < listOfPlayers.length; i++) {
                communalPile.push(listOfPlayers[i].hand[0]);
                listOfPlayers[i].hand.shift();
            }

            if (!draw) {
                indexOfCurrentPlayer = indexOfRoundWinner;
                let length = communalPile.length;
                for (let i = 0; i < length; i++) {
                    listOfPlayers[indexOfRoundWinner].hand.push(communalPile[0]);
                    communalPile.shift();
                }
            }

            for (let i = 0; i < listOfPlayers.length; i++) {
                if (listOfPlayers[i].hand === 0) {
                    console.log(listOfPlayers[i].name + " was knocked out");
                    listOfPlayers.splice(i, 1);
                    console.log(listOfPlayers.length);
                    if (indexOfCurrentPlayer >= i) {
                        indexOfCurrentPlayer -= 1;
                    }

                }
            }
        }

        document.getElementById("end-game").value = [countOfRounds, listOfPlayers[0].name];

    }

    //helper method to set categories correctly and choose correct image for a card with choices
    function setCardWithChoices(categories, name, number) {

        document.getElementsByClassName("card-outline")[0].style.display = "block";
        document.getElementsByClassName("ai-card-outline")[0].style.display = "none";

        document.getElementById("sandwich").src = sandwichAddressStart + number + addressEnd;

        document.getElementById("category1").innerText = categories[0];
        document.getElementById("category2").innerText = categories[1];
        document.getElementById("category3").innerText = categories[2];
        document.getElementById("category4").innerText = categories[3];
        document.getElementById("category5").innerText = categories[4];

        document.getElementById("caption").innerText = name;
    }

    //helper method to choose correct image for a card without choices
    function setCardWithoutChoices(number) {

        document.getElementById("human-card").src = cardAddressStart + number + addressEnd;
        document.getElementsByClassName("card-outline")[0].style.display = "none";
        document.getElementsByClassName("ai-card-outline")[0].style.display = "block";

    }

    function buttons() {

        const buttonClicked = document.getElementById("play-card").getAttribute("value");

        if (buttonClicked === "play your card") {

            calculateWinner();
            everybodyPlayACard();

            document.getElementsByClassName("card-outline")[0].style.display = "none";
            document.getElementsByClassName("all-cards-played")[0].style.display = "flex";
            document.getElementsByClassName("ai-card-outline")[0].style.display = "none";

            document.getElementById("play-card").setAttribute("value", "show winner");


        } else if (buttonClicked === "show winner") {


            addCardsToWinnersHand();

            document.getElementsByClassName("card-outline")[0].style.display = "none";
            document.getElementsByClassName("all-cards-played")[0].style.display = "none";
            document.getElementsByClassName("winning-card")[0].style.display = "block";
            document.getElementsByClassName("ai-card-outline")[0].style.display = "none";


            document.getElementById("play-card").setAttribute("value", "continue to next round");


        } else if (buttonClicked === "continue to next round") {

            if (!draw) {
                updateRoundCount(listOfPlayers[indexOfRoundWinner].number);
            }

            if (!humanIsGone && listOfPlayers.length > 1) {
                beginRound();
                document.getElementById("play-card").setAttribute("value", "play your card");
            } else {
                endGame();
            }

            countOfRounds++;

        }
    }


    function addCardsToWinnersHand() {

        if (!draw) {

            let length = communalPile.length;

            for (let i = 0; i < length; i++) {

                listOfPlayers[indexOfRoundWinner].hand.push(communalPile[0]);
                communalPile.shift();
            }
        }

        displayNumberOfCardsLeft();

        for (let i = 0; i < listOfPlayers.length; i++) {

            if (listOfPlayers[i].hand.length === 0) {
                if (listOfPlayers[i].human) {
                    humanIsGone = true;
                }
                console.log(listOfPlayers[i].name + " was knocked out");
                listOfPlayers.splice(i, 1);
                console.log(listOfPlayers.length);
                if (indexOfCurrentPlayer >= i) {
                    indexOfCurrentPlayer -= 1;
                }
                if (indexOfRoundWinner >= i) {
                    indexOfRoundWinner -= 1;
                }
                i -= 1;
            }
        }
        findHuman();
        getWinner();
    }

    function getWinner() {

        if (!draw) {

            let captionText;
            if (listOfPlayers[indexOfRoundWinner].human) {
                captionText = "Your card";
            } else {
                captionText = listOfPlayers[indexOfRoundWinner].name + '\'s card';
            }

            document.getElementById("winners-card").style.display = "block";
            document.getElementById("winners-card").src = cardAddressStart + cardNumberOfRoundWinner + addressEnd;
            document.getElementById("winners-card-caption").innerText = captionText;

            document.getElementById("players-turn-text").innerHTML = winningCardName + " won this round, with the card " + winningCardDescription + " which had a " +
                    categoryChoice + " value of " + winningCardCategory + ".";
        } else {
            document.getElementById("winners-card").style.display = "none";

            document.getElementById("players-turn-text").innerHTML = "This round was a draw!";
            numberOfDraws++;

        }


    }

    function everybodyPlayACard() {

        for (let i = 0; i < listOfPlayers.length; i++) {

            communalPile.push(listOfPlayers[i].hand[0]);
            listOfPlayers[i].hand.shift();
        }

        displayNumberOfCardsLeft();

        if (!draw) {
            indexOfCurrentPlayer = indexOfRoundWinner;
        }

    }

    function displayNumberOfCardsLeft() {

        for (let i = 0; i < listOfPlayers.length; i++) {

            let name = listOfPlayers[i].name;
            let cards = listOfPlayers[i].hand.length + "";

            document.getElementById("name-" + i).innerHTML = name;
            document.getElementById("number-" + i).innerText = cards;
        }

        for (let i = 4; i >= listOfPlayers.length; i--) {

            document.getElementById("cards-left-" + i).style.display = "none";
        }


        document.getElementById("communal-6").innerHTML = communalPile.length + "";
    }


    function calculateWinner() {

        let maxNumber = 0;

        if (indexOfCurrentPlayer === indexOfHumanPlayer) {
            categoryChoice = document.querySelector('input[name="choices"]:checked').value;
            switch (categoryChoice) {

                case "Deliciousness":
                    categoryIndexOfCardChoice = 0;
                    break;
                case "Size":
                    categoryIndexOfCardChoice = 1;
                    break;
                case "Toastability":
                    categoryIndexOfCardChoice = 2;
                    break;
                case "Satiation":
                    categoryIndexOfCardChoice = 3;
                    break;
                case "Complexity":
                    categoryIndexOfCardChoice = 4;
            }

        } else {
            for (let i = 0; i < 5; i++) {
                let value = listOfPlayers[indexOfCurrentPlayer]._hand[0].categoryValues[i];
                if (value > maxNumber) {
                    maxNumber = value;
                    categoryIndexOfCardChoice = i;
                }
            }
            switch (categoryIndexOfCardChoice) {

                case 0:
                    categoryChoice = "Deliciousness";
                    break;
                case 1:
                    categoryChoice = "Size";
                    break;
                case 2:
                    categoryChoice = "Toastability";
                    break;
                case 3:
                    categoryChoice = "Satiation";
                    break;
                case 4:
                    categoryChoice = "Complexity";
            }
        }

        maxNumber = 0;
        draw = false;

        for (let i = 0; i < listOfPlayers.length; i++) {

            console.log("winningCardCategory = listOfPlayers[" + i + "]._hand[0].categoryValues[" + categoryIndexOfCardChoice + "]");

            winningCardCategory = listOfPlayers[i]._hand[0].categoryValues[categoryIndexOfCardChoice];

            if (winningCardCategory > maxNumber) {
                draw = false;
                maxNumber = winningCardCategory;
                winningCardName = listOfPlayers[i].name;
                indexOfRoundWinner = i;
                cardNumberOfRoundWinner = (listOfPlayers[i]._hand[0].cardNumber) + 1;
                winningCardDescription = listOfPlayers[i]._hand[0].description.replace('_', ' ');
            } else if (winningCardCategory === maxNumber) {
                draw = true;
                winningCardName = null;
                indexOfRoundWinner = null;
                cardNumberOfRoundWinner = null;
                winningCardDescription = null;
            }
        }

        if (!draw) {
            winningCardCategory = maxNumber;
        }

        showAllCards();

    }

    function showAllCards() {

        for (let i = 0; i < listOfPlayers.length; i++) {

            let number = (listOfPlayers[i]._hand[0].cardNumber) + 1;
            document.getElementById("player-" + (i + 1) + "-card").src = cardAddressStart + number + addressEnd;
            document.getElementById("card-" + (i + 1) + "-caption").innerText = listOfPlayers[i].name;
            if (listOfPlayers[i].name === "You") {
                document.getElementById("card-" + (i + 1) + "-caption").style.color = "#000000";
            } else {
                document.getElementById("card-" + (i + 1) + "-caption").style.color = "#ffffff";
            }
        }

        for (let i = 5; i > listOfPlayers.length; i--) {
            document.getElementsByClassName("card-" + i)[0].style.display = "none";
        }

        let name = listOfPlayers[indexOfCurrentPlayer].name;
        document.getElementById("players-turn-text").innerHTML = name + " chose " + categoryChoice;
    }


    // This is a reusable method for creating a CORS request. Do not edit this.
    function createCORSRequest(method, url) {
        const xhr = new XMLHttpRequest();
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

    //helper method to allow easy shuffling of arrays
    function shuffle(array) {
        let currentIndex = array.length, temporaryValue, randomIndex;

        while (0 !== currentIndex) {

            randomIndex = Math.floor(Math.random() * currentIndex);
            currentIndex -= 1;

            temporaryValue = array[currentIndex];
            array[currentIndex] = array[randomIndex];
            array[randomIndex] = temporaryValue;
        }
        return array;
    }

    function getWhoseTurnItIs() {

        findHuman();

        if (indexOfCurrentPlayer === indexOfHumanPlayer) {
            return "\nIt's your turn! Your top card is " + listOfPlayers[indexOfHumanPlayer].hand[0].description.replace('_', ' ') + ".";
        } else {
            return "\nIt's " + listOfPlayers[indexOfCurrentPlayer]._name + "'s turn. Your top card is " + listOfPlayers[indexOfHumanPlayer].hand[0].description.replace('_', ' ') + ".";
        }
    }

    function confirmDatabaseUpdated(responseText) {
        console.log(responseText);
    }

</script>

<!-- this is how we get java into the website -->

<script type="text/javascript">

    function getDeckAndBeginGame() {

        const xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/deck"); //first create cors request to my new restapi method

        if (!xhr) {
            alert("CORS not supported");
        }


        xhr.onload = function (e) {

            let responseText = xhr.response; // the text of the response
            setDeckAndBeginGame(responseText);
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }


    function updateRoundCount(playerNumber) {
        const xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateRoundCountsForPlayer/" + playerNumber); //first create cors request to my new restapi method

        if (!xhr) {
            alert("CORS not supported");
        }
        xhr.send();
    }

    function setDatabase(databaseArray) {

        const xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/writeDatabase/" + databaseArray); //first create cors request to my new restapi method

        if (!xhr) {
            alert("CORS not supported");
        }

        xhr.onload = function (e) {

            let responseText = xhr.response; // the text of the response
            confirmDatabaseUpdated(responseText);
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }

</script>


</html>