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
    <h6 id="testing-area2"></h6>
</div>

<div class="all-cards-played" style="display: none">
    <img id="player-1-card" width="170">
    <img id="player-2-card" width="170">
    <img id="player-3-card" width="170">
    <img id="player-4-card" width="170">
    <img id="player-5-card" width="170">
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
    <img id="human-card" width="250px">
</div>


<div class="winning-card" style="display: none">
    <img id="winners-card" width="250px">
</div>

<div class="bottom-line">

    <div class="play-card">
        <input id="play-card" type="submit" value="play card" name="choices" onclick="buttons()">
    </div>

    <div class="game-stats">
        <p id="number-of-cards">number of cards left Placeholder</p>
        <p id="count-of-rounds">Count of rounds Placeholder</p>
        <div>

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

    let ifHuman;
    let deck;
    let listOfPlayers = [];

    let indexOfCurrentPlayer = 0;
    let indexOfHumanPlayer;

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

        addCard(){
            this._hand.push()
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


        listOfPlayers.push(new Player('you', 0, true));

        for (let i = 0; i < (${players}-1); i++) {
            listOfPlayers.push(new Player(aiName[i], 0, false))
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

        for (let i = 0; i<listOfPlayers[0]._hand.length;i++){

            console.log(listOfPlayers[0]._hand[i]+" card in "+listOfPlayers[0].name+"'s hand");
        }

        findHuman();
        beginRound();





    }

    /*
    returns position of human in the array, returns null if human knocked out
     */
    function findHuman(){

        for (let i = 0; i < listOfPlayers.length; i++){
            if(listOfPlayers[i]._human){
                indexOfHumanPlayer = i;
                return i;
            }
        }
        return null;
    }

    /*
    decides whether to show player a card they can make choices from if it's their turn, or a picture of card if not.
    Displays correct values.
     */
    function beginRound(){

        let categories = listOfPlayers[indexOfCurrentPlayer]._hand[0].categoryValues;
        let name = listOfPlayers[indexOfCurrentPlayer]._hand[0].description;
        let number = (listOfPlayers[indexOfCurrentPlayer]._hand[0].cardNumber)+1;

        document.getElementById("testing-area2").innerHTML = listOfPlayers[0]._name;

        if(indexOfCurrentPlayer===indexOfHumanPlayer){
            setCardWithChoices(categories,name,number);
        }else{
            setCardWithoutChoices(number);
        }

        document.getElementById("players-turn").innerHTML= getWhoIsInGame()+getWhoseTurnItIs();
    }

    //helper method to set categories correctly and choose correct image for a card with choices
    function setCardWithChoices(categories,name,number) {

        document.getElementsByClassName("card-outline")[0].style.display = "block";
        document.getElementsByClassName("ai-card-outline")[0].style.display = "none";

        document.getElementById("sandwich").src = sandwichAddressStart+number+addressEnd;

        document.getElementById("category1").innerText = categories[0];
        document.getElementById("category2").innerText = categories[1];
        document.getElementById("category3").innerText = categories[2];
        document.getElementById("category4").innerText = categories[3];
        document.getElementById("category5").innerText = categories[4];

        document.getElementById("caption").innerText = name;
    }

    //helper method to choose correct image for a card without choices
    function setCardWithoutChoices(number){

        document.getElementById("human-card").src=cardAddressStart+number+addressEnd;
        document.getElementsByClassName("card-outline")[0].style.display = "none";
        document.getElementsByClassName("ai-card-outline")[0].style.display = "block";

    }

    function buttons() {

        var button = document.getElementById("play-card").getAttribute("value");


        if (button === "play card") {

            document.getElementsByClassName("card-outline")[0].style.display = "none";
            document.getElementsByClassName("all-cards-played")[0].style.display = "block";
            document.getElementsByClassName("winning-card")[0].style.display = "none";
            document.getElementsByClassName("ai-card-outline")[0].style.display = "none";


            showAllCards();
            calculateWinner();

            document.getElementById("play-card").setAttribute("value", "show winner");


        } else if (button === "show winner") {
            document.getElementsByClassName("card-outline")[0].style.display = "none";
            document.getElementsByClassName("all-cards-played")[0].style.display = "none";
            document.getElementsByClassName("winning-card")[0].style.display = "block";
            document.getElementsByClassName("ai-card-outline")[0].style.display = "none";
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


        }
    }

    function calculateWinner(){

        console.log (JSON.stringify(listOfPlayers));

        let choice = document.querySelector('input[name="choices"]:checked').value;

        let categoryPosition;

        switch(choice) {

            case "Deliciousness":
                categoryPosition = 0;
                break;
            case "Size":
                categoryPosition = 1;
                break;
            case "Toastability":
                categoryPosition = 2;
                break;
            case "Satiation":
                categoryPosition = 3;
                break;
            case "Complexity":
                categoryPosition = 4;
        }

        alert(choice + ' was selected with a value of '+ listOfPlayers[indexOfCurrentPlayer]._hand[0].categoryValues[categoryPosition]);

        let maxNumber = 0;
        let winningPlayer;

        for (let i = 0; i<listOfPlayers.length;i++) {
            let playerScore = parseInt(listOfPlayers[i]._hand[0].categoryValues[categoryPosition]);

            if(playerScore>maxNumber){
                maxNumber = playerScore;
                winningPlayer = listOfPlayers[i].name;
            }
        }

        alert('The winner was '+winningPlayer+' with category '+choice+' which had a winning value of '+maxNumber);

    }

    function showAllCards(){

        for (let i = 0; i<listOfPlayers.length;i++) {

            let number = (listOfPlayers[i]._hand[0].cardNumber)+1;
            console.log(number+"  "+i+listOfPlayers[i]._name);
            document.getElementById("player-"+(i+1)+"-card").src = cardAddressStart + number + addressEnd;

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


    function getDeckAndBeginGame() {

        const xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/deck"); //first create cors request to my new restapi method

        if (!xhr) {
            alert("CORS not supported");
        }

        xhr.onload = function (e) {

            var responseText = xhr.response; // the text of the response
            setDeckAndBeginGame(responseText);
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }

    function getWhoIsInGame() {

        let allPlayersNames = "";

        for(let i = 0; i<${players}-2; i++){
            allPlayersNames += listOfPlayers[i]._name+", ";
        }
        allPlayersNames+= listOfPlayers[${players}-2]._name+" and "+listOfPlayers[${players}-1]._name+".<br>";

        return "There are ${players} players left in the game: "+allPlayersNames;

    }

    function getWhoseTurnItIs(){

        findHuman();

        if (indexOfCurrentPlayer === indexOfHumanPlayer) {
            return "\nIt's your turn!";
        } else {
            return "\nIt's " + listOfPlayers[indexOfCurrentPlayer]._name + "'s turn.";
        }
    }


</script>


</html>