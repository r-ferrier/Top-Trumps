<html>

<head>
    <!-- Web page title -->
    <title>Top Trumps</title>
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
                    <img id="player-1-card" width="200">
                    <p id="card-1-caption"></p>
                </div>
            </div>
            <div class="row">
                <div class="card-2" style="margin-left: 15px" style="margin-right: 15px">
                    <img id="player-2-card" width="200">
                    <p id="card-2-caption"></p>
                </div>
            </div>
            <div class="row">
                <div class="card-3" style="margin-left: 15px" style="margin-right: 15px">
                    <img id="player-3-card" width="200">
                    <p id="card-3-caption"></p>
                </div>
            </div>
            <div class="row">
                <div class="card-4" style="margin-left: 15px" style="margin-right: 15px">
                    <img id="player-4-card" width="200">
                    <p id="card-4-caption"></p>
                </div>
            </div>
            <div class="row">
                <div class="card-5" style="margin-left: 15px" style="margin-right: 15px">
                    <img id="player-5-card" width="200">
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
                        <input type="radio" id="Deliciousness" name="choices" value="0">
                        <label for="Deliciousness" id="category1"></label>
                    </div>
                    <br>
                    <div class="this-label">
                        <label for="Size">Size</label>
                        <input type="radio" id="Size" name="choices" value="1">
                        <label for="Size" id="category2"></label>
                    </div>
                    <br>
                    <div class="this-label">
                        <label for="Toastability">Toastability</label>
                        <input type="radio" id="Toastability" name="choices" value="2">
                        <label for="Toastability" id="category3"></label>
                    </div>
                    <br>
                    <div class="this-label">
                        <label for="Satiation">Satiation</label>
                        <input type="radio" id="Satiation" name="choices" value="3">
                        <label for="Satiation" id="category4"></label>
                    </div>
                    <br>
                    <div class="this-label">
                        <label for="Complexity">Complexity</label>
                        <input type="radio" id="Complexity" name="choices" value="4">
                        <label for="Complexity" id="category5"></label>
                    </div>
                </div>
            </div>
        </div>

        <div class="ai-card-outline" style="display: none">
            <img id="human-card" width="250px">
        </div>

        <div class="winning-card" style="display: none">
            <img id="winners-card" width="250px">
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
                <p><strong>On the table</strong></p>
            </div>
        </div>

    </div>
</body>

<script type="text/javascript">

    /*
    global variables for use in functions
     */
    const cardAddressStart = "https://raw.githack.com/r-ferrier/images-and-css-for-top-trumps/master/imagesOfCards/";
    const sandwichAddressStart = "https://raw.githack.com/r-ferrier/images-and-css-for-top-trumps/master/maincards/";
    const addressEnd = ".png";

    let listOfPlayers = [];
    let communalPile = [];
    let playerWinsArray = [0,0,0,0,0];

    let countOfRounds = 0;
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

    //runs on page load and sets up the game
    function initalize() {

        getPlayers();

        //creates as many Player objects as required for game, stores them in an array (players) and prints out two
        // lines at top of game announcing turns. Also calls on the rest api to upload a deck, which is then shuffled
        // and dealt to players. changes display depending on whose turn it is and shows human's top card.
    }

    /**
     * called by the getPlayers method once an array of shuffled player objects containing shuffled cards has been set up.
     **/
    function setPlayers(playersFromRestApi) {
        listOfPlayers = JSON.parse(playersFromRestApi); //unpacks the list of player objects from the restapi
        findHuman();
        beginRound(); //starts up a round
        displayNumberOfCardsLeft(); //displays the game data in the bottom of the screen
        console.log(playersFromRestApi);
    }

    /**
     * returns position of human in the array, returns null if human knocked out
     **/
    function findHuman() {
        //iterates over array of players until it finds human and sets the index to that position
        for (let i = 0; i < listOfPlayers.length; i++) {
            if (listOfPlayers[i].human) {
                indexOfHumanPlayer = i;
                return i;
            }
        }
        indexOfHumanPlayer = null; //if no human, set index to null and return null
        return null;
    }

    /**
     decides whether to show player a card they can make choices from if it's their turn, or a picture of card if not.
     Displays correct values.
     **/
    function beginRound() {

        countOfRounds++; //advance the count of rounds by one
        playerWinsArray[indexOfRoundWinner]++;

        //set variables
        let categories = listOfPlayers[indexOfCurrentPlayer].hand[0].categoryValues;
        let name = listOfPlayers[indexOfCurrentPlayer].hand[0].description.replace(/_/g, ' ');
        let number = (listOfPlayers[indexOfHumanPlayer].hand[0].cardNumber) + 1;

        //set correct count of rounds, text, and clear the display of previous images
        document.getElementsByClassName("winning-card")[0].style.display = "none";
        document.getElementById("count-of-rounds").innerHTML = "Round <strong>" + countOfRounds + "</strong>";
        document.getElementById("players-turn-text").innerHTML = getWhoseTurnItIs();

        //choose which type of display to set
        if (indexOfCurrentPlayer === indexOfHumanPlayer) {
            setCardWithChoices(categories, name, number);
        } else {
            setCardWithoutChoices(number);
        }
    }


    /**
     * called when no more human input is required or possible to finish the game.
     */
    function endGame() {

        //clear the display of images and play-card button, and add in the end-game button to return user to homescreen
        document.getElementsByClassName("winning-card")[0].style.display = "none";
        document.getElementById("play-card").style.display = "none";
        document.getElementById("end-game").style.display = "block";

        if (humanIsGone) {
            if (listOfPlayers.length < 2) { //once human is gone, if only one other player end game + print message
                document.getElementById("players-turn-text").innerHTML = "<br><br>You've been knocked out by " + listOfPlayers[0].name + "! <br><br>Click the button to return to the homescreen.";
                document.getElementById("end-game").value = countOfRounds + "." + listOfPlayers[0].name;
            } else {
                findAIWinner(); //if more than one other player, find the ai winner, and print a message;
            }
        } else { //if human is not gone, this means they have won! Print a message and end the game;
            document.getElementById("players-turn-text").innerHTML = "<br><br>You've won! " + "<br><br>Click the button to return to the homescreen.";
            document.getElementById("end-game").value = countOfRounds + "." + listOfPlayers[0].name;
        }
        setDatabase(numberOfDraws + "," +
                listOfPlayers[0].number + "," + countOfRounds);
        console.log(playerWinsArray.toString());
    }

    /**
     * this method rattles through the rest of the game to return the AI winner and the number of rounds it takes to win
     */
    function findAIWinner() {

        document.getElementById("players-turn-text").innerHTML = "<br><br>You've been knocked out! " + "<br><br>Click the button to let the ai players finish the game and find out who wins.";

        while (listOfPlayers.length > 1) { //run loop for as long as more than one player is left in the game
            countOfRounds++; //add one to count of rounds
            playerWinsArray[listOfPlayers[indexOfRoundWinner].number]++;
            let maxNumber = 0;

            //find the highest category
            for (let i = 0; i < 5; i++) {
                let value = listOfPlayers[indexOfCurrentPlayer].hand[0].categoryValues[i];
                if (value > maxNumber) {
                    maxNumber = value;
                    categoryIndexOfCardChoice = i;
                }
            }

            maxNumber = 0;
            draw = false;

            //find the winner or draw
            for (let i = 0; i < listOfPlayers.length; i++) {
                winningCardCategory = listOfPlayers[i].hand[0].categoryValues[categoryIndexOfCardChoice];

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
            }else{
                numberOfDraws++;
            }

            //update the number of cards in each players hand

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
                if (listOfPlayers[i].hand.length === 0) {
                    listOfPlayers.splice(i, 1);
                    if (indexOfCurrentPlayer >= i) {
                        indexOfCurrentPlayer -= 1;
                    }

                }
            }

            console.log(countOfRounds+" rounds and name "+listOfPlayers[0].name);
            console.log(listOfPlayers.length);

        }

        document.getElementById("end-game").value = countOfRounds + "." + listOfPlayers[0].name;
        //update the submit form with the correct winner name to print out on the homescreen

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

    /**
     * This is the function from which all other functions are called once the game has been initialised. It checks
     * which button has been clicked to determine at which point in the round the game is at, and then calls the
     * relevant methods to update the display and advance the game in time for receiving the next button click.
     */
    function buttons() {

        // set a variable to represent the value of the button
        const buttonClicked = document.getElementById("play-card").getAttribute("value");

        //if middle of round
        if (buttonClicked === "play your card") {
            calculateWinner(); //find out who wins/ if it was a draw

            //update the display to reflect changes and show all of the top cards + change button text
            document.getElementsByClassName("card-outline")[0].style.display = "none";
            document.getElementsByClassName("all-cards-played")[0].style.display = "flex";
            document.getElementsByClassName("ai-card-outline")[0].style.display = "none";
            document.getElementById("play-card").setAttribute("value", "show winner");

            everybodyPlayACard(); //take everyone's top card off them

            //if end of round
        } else if (buttonClicked === "show winner") {

            if (!draw) {
                indexOfCurrentPlayer = indexOfRoundWinner; //if no draw, update the new current player to have winner
            }

            // if anybody won, give all of the cards to them
            addCardsToWinnersHand();

            //update the display to remove all cards played + change button text


            //if new round is beginning
        } else if (buttonClicked === "continue to next round") {
            displayNumberOfCardsLeft();
            findHuman();

            //if we still have at least two players and one is human, begin a round
            if (!humanIsGone && listOfPlayers.length > 1) {
                beginRound();
                document.getElementById("play-card").setAttribute("value", "play your card");
            } else {
                endGame(); //otherwise, end the game
            }
        }
    }

    /**
     * if there has been a winner, adds all the communal cards to their hand + updates the display of number of cards
     * left. Then checks to see if any players have 0 cards left, removes them from the deck before the next round
     * begins, and calls the getwinner method to display the winners information.
     */
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
                listOfPlayers.splice(i, 1);
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

        document.getElementsByClassName("all-cards-played")[0].style.display = "none";
        document.getElementById("play-card").setAttribute("value", "continue to next round");

        getWinner();
    }

    /**
     * update display to reflect the outcome of the round
     */
    function getWinner() {

        //if somebody won the round . . .
        if (!draw) {

            //set correct grammar for the beginning of the caption
            let captionText;
            if (listOfPlayers[indexOfRoundWinner].human) {
                captionText = "Your card";
            } else {
                captionText = listOfPlayers[indexOfRoundWinner].name + '\'s card';
            }

            //display the correct elements and update the message

            document.getElementsByClassName("winning-card")[0].style.display = "block";
            document.getElementById("winners-card").src = cardAddressStart + cardNumberOfRoundWinner + addressEnd;
            document.getElementById("winners-card-caption").innerText = captionText;

            document.getElementById("players-turn-text").innerHTML = winningCardName + " won this round, with the card "
                 + winningCardDescription + " which had a " + categoryChoice + " value of " + winningCardCategory + ".";

        }//if it was a draw . . .
        else {
            //display the draw message and remove card images
            document.getElementById("players-turn-text").innerHTML = "This round was a draw!";
            numberOfDraws++; //update the count of numbers of draws
        }
    }

    /**
     * take a card off every player + call on method to display new amounts of cards all players now have
     */
    function everybodyPlayACard() {

        for (let i = 0; i < listOfPlayers.length; i++) {
            communalPile.push(listOfPlayers[i].hand[0]);
            listOfPlayers[i].hand.shift();
        }
        displayNumberOfCardsLeft();
    }

    /**
     * update information at the bottom of screen to reflect number of cards in each players pile
     */
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


    /**
     * function to find the winner + store all of the related data to this (their category choice, its value, their
     * position in the array, and whether or not it was a draw)
     */
    function calculateWinner() {

        //find category choice for human or find highest category for computer choice
        if (indexOfCurrentPlayer === indexOfHumanPlayer) {
            categoryIndexOfCardChoice = document.querySelector('input[name="choices"]:checked').value;
        } else {
            let maxNumber = 0;
            for (let i = 0; i < 5; i++) {
                let value = listOfPlayers[indexOfCurrentPlayer].hand[0].categoryValues[i];
                if (value > maxNumber) {
                    maxNumber = value;
                    categoryIndexOfCardChoice = i;
                }
            }
        }

        //find correct word for category choice
        categoryChoice = listOfPlayers[indexOfCurrentPlayer].hand[0].categories[categoryIndexOfCardChoice];

        //set up variables to use to check if its a draw and find the winner
        let maxNumber = 0;
        draw = false;

        //iterate over every players hand to find the highest value and check for a draw
        for (let i = 0; i < listOfPlayers.length; i++) {

            winningCardCategory = listOfPlayers[i].hand[0].categoryValues[categoryIndexOfCardChoice];

            if (winningCardCategory > maxNumber) {
                //every time a higher number is found, draw is false and winner details are updated
                draw = false;
                maxNumber = winningCardCategory;
                winningCardName = listOfPlayers[i].name;
                indexOfRoundWinner = i;
                cardNumberOfRoundWinner = (listOfPlayers[i].hand[0].cardNumber) + 1;
                winningCardDescription = listOfPlayers[i].hand[0].description.replace(/_/g, ' ');
            } else if (winningCardCategory === maxNumber) {
                //every time a match with the current highest value is found, draw is true and winner details are erased
                draw = true;
                winningCardName = null;
                indexOfRoundWinner = null;
                cardNumberOfRoundWinner = null;
                winningCardDescription = null;
            }
        }
        if (!draw) {
            winningCardCategory = maxNumber; //value of the winners' card for that category
        }
        showAllCards(); //display every players card
    }

    /**
     * displays every players top card + message to display which category the current player chose
     */
    function showAllCards() {
        for (let i = 0; i < listOfPlayers.length; i++) {
            let number = (listOfPlayers[i].hand[0].cardNumber) + 1;
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

    //helper function creates message to display whose turn it is
    function getWhoseTurnItIs() {

        if (indexOfCurrentPlayer === indexOfHumanPlayer) {
            return "\nIt's your turn! Your top card is "
                    + listOfPlayers[indexOfHumanPlayer].hand[0].description.replace(/_/g, ' ') + ".";
        } else {
            return "\nIt's " + listOfPlayers[indexOfCurrentPlayer].name + "'s turn. Your top card is "
                    + listOfPlayers[indexOfHumanPlayer].hand[0].description.replace(/_/g, ' ') + ".";
        }
    }

    //helper function to confirm correct in the console that data was sent to database without error
    function confirmDatabaseUpdated(responseText) {
        console.log(responseText);
    }

</script>

<#--methods to create  cors requests to pull back or send information to the rest api -->

<script type="text/javascript">

    //function for creating cors requests
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
            xhr = null;
        }
        return xhr;
    }

    function getPlayers() {
        const xhr = createCORSRequest('GET',
                "http://localhost:7777/toptrumps/get-players/" +${players});
        if (!xhr) {
            alert("CORS not supported");
        }
        xhr.onload = function () {
            let responseText = xhr.response;
            setPlayers(responseText);
        };
        xhr.send();
    }

    function setDatabase(databaseArray) {

        let p = playerWinsArray.toString();

        const xhr = createCORSRequest('GET',
                "http://localhost:7777/toptrumps/writeDatabase/" + databaseArray +"/"+p);
        if (!xhr) {
            alert("CORS not supported");
        }
        xhr.onload = function () {
            let responseText = xhr.response;
            confirmDatabaseUpdated(responseText);
        };
        xhr.send();
    }

</script>
</html>