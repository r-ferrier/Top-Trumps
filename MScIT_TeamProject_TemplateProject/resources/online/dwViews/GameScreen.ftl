<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		<#--<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">-->
    	<#--<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>-->
    	<#--<script>vex.defaultOptions.className = 'vex-theme-os';</script>-->
    	<#--<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>-->
    	<#--<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>-->
    	<#--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
		<#--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">-->

        <link rel="stylesheet" type = "text/css" href="https://raw.githack.com/r-ferrier/topTrumpsCSS/master/topTrumpsGameScreen.css">

	</head>

    <body onload="initalize()"> <!-- Call the initalize method when the page loads -->

    <div class="top-line">
        <input id="quit" type = "submit" value="quit"></input>

        <h2 id="players-turn"></h2>
    </div>

    <div class="all-cards-played" style="display: none">
        <img id="player1-card" src ="https://github.com/r-ferrier/topTrumpsCSS/blob/master/whole_card_big_mac1.png?raw=true" width="170">
        <img id="player2-card" src ="https://github.com/r-ferrier/topTrumpsCSS/blob/master/whole_card_jam_sandwich1.png?raw=true" width="170">
        <img id="player3-card"src ="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/imageofcard.png" width="170">
        <img id="player4-card"src ="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/imageofcard.png" width="170">
        <img id="player5-card"src ="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/imageofcard.png" width="170">
    </div>

    <div class="card-outline" style="display: block">
        <div class="card-contents">

            <img id="sandwich" src="https://raw.githubusercontent.com/r-ferrier/topTrumpsCSS/master/jam_sandwich.png" width="200" height="150">
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
                    <label class = "labels" for="Toastability">Toastability</label>
                    <input class = "labels" type="radio" id="Toastability" name="choices" value="Toastability">
                    <label class = "labels" for="Toastability" id="category3"></label>
                </div>
                <br>
                <div class="this-label">
                    <label class = "labels" for="Satiation">Satiation</label>
                    <input class = "labels" type="radio" id="Satiation" name="choices" value="Satiation">
                    <label class = "labels" for="Satiation" id="category4"></label>
                </div>
                <br>
                <div class="this-label">
                    <label class = "labels" for="Complexity">Complexity</label>
                    <input class = "labels" type="radio" id="Complexity" name="choices" value="Complexity">
                    <label class = "labels" for="Complexity" id="category5"></label>
                </div>
            </div>
        </div>
    </div>

    <div class="winning-card" style="display: none" >
        <img src="https://photos.bigoven.com/recipe/hero/roasted-vegetables-baguette-sandwic.jpg?h=100&w=200">
    </div>

    <div class="bottom-line">

        <div class="play-card">
            <input id="play-card" type="submit" value="play card" onclick="buttons()">
        </div>

        <div class="game-stats">
            <p id="number-of-cards">number of cards left Placeholder</p>
            <p id="count-of-rounds">Count of rounds Placedholder</p>
            <div>

            </div>


    </body>


		</div>
		
		<script type="text/javascript">
		
			// Method that is called on page load
			function initalize() {
			
				// --------------------------------------------------------------------------
				// You can call other methods you want to run when the page first loads here
				// --------------------------------------------------------------------------

                startGame();
                getCategories();

			}
			
			// -----------------------------------------
			// Add your other Javascript methods Here
			// -----------------------------------------


            function startingGame(startMessage){

                document.getElementById("players-turn").innerHTML=startMessage;

            }

            function buttons(){

                document.getElementsByClassName("card-outline")[0].style.display="none";
                document.getElementsByClassName("all-cards-played")[0].style.display = "block";

            }

            function setCategories(categories) {

			    var categoriesArray = JSON.parse(categories);

			    document.getElementById("category1").innerText=categoriesArray[0];
                document.getElementById("category2").innerText=categoriesArray[1];
                document.getElementById("category3").innerText=categoriesArray[2];
                document.getElementById("category4").innerText=categoriesArray[3];
                document.getElementById("category5").innerText=categoriesArray[4];
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
		
		</script>

    <!-- this is how we get java into the website -->




		<!-- Here are examples of how to call REST API Methods -->
		<script type="text/javascript">

            function startGame() {

                var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/start-game"); //first create cors request to my new restapi method

                if (!xhr) {
                    alert("CORS not supported");
                }

                xhr.onload = function(e) {

                    var responseText = xhr.response; // the text of the response
                    startingGame(responseText);

                };

                // We have done everything we need to prepare the CORS request, so send it
                xhr.send();

            }

            function getCategories() {

                var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/category-values"); //first create cors request to my new restapi method

                if (!xhr) {
                    alert("CORS not supported");
                }

                xhr.onload = function(e) {

                    var responseText = xhr.response; // the text of the response
                    setCategories(responseText);

                };

                // We have done everything we need to prepare the CORS request, so send it
                xhr.send();

            }

            // function getCard() {
            //
            //     var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/card"); //first create cors request to my new restapi method
            //
            //     if (!xhr) {
            //         alert("CORS not supported");
            //     }
            //
            //     xhr.onload = function(e) {
            //
            //         var responseText = xhr.response; // the text of the response
            //         setTextAsCard(responseText);
            //
            //     };
            //
            //     // We have done everything we need to prepare the CORS request, so send it
            //     xhr.send();
            //
            // }
		
			// // This calls the helloJSONList REST method from TopTrumpsRESTAPI
			// function helloJSONList() {
			//
			// 	// First create a CORS request, this is the message we are going to send (a get request in this case)
			// 	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL
			//
			// 	// Message is not sent yet, but we can check that the browser supports CORS
			// 	if (!xhr) {
  			// 		alert("CORS not supported");
			// 	}
            //
			// 	// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
			// 	// to do when the response arrives
			// 	xhr.onload = function(e) {
 			// 		var responseText = xhr.response; // the text of the response
			// 		alert(responseText); // lets produce an alert
			// 	};
			//
			// 	// We have done everything we need to prepare the CORS request, so send it
			// 	xhr.send();
			// }
			//
			// // This calls the helloJSONList REST method from TopTrumpsRESTAPI
			// function helloWord(word) {
			//
			// 	// First create a CORS request, this is the message we are going to send (a get request in this case)
			// 	var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters
			//
			// 	// Message is not sent yet, but we can check that the browser supports CORS
			// 	if (!xhr) {
  			// 		alert("CORS not supported");
			// 	}
            //
			// 	// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
			// 	// to do when the response arrives
			// 	xhr.onload = function(e) {
 			// 		var responseText = xhr.response; // the text of the response
			// 		alert(responseText); // lets produce an alert
			// 	};
			//
			// 	// We have done everything we need to prepare the CORS request, so send it
			// 	xhr.send();
			// }

		</script>
		
		</body>
</html>