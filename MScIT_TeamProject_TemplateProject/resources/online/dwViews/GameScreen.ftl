<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

        <link rel="stylesheet" type = "text/css" href="https://raw.githack.com/r-ferrier/topTrumpsCSS/master/topTrumpsStyle.css">

	</head>

    <body onload="initalize()"> <!-- Call the initalize method when the page loads -->

    <div class="top-line">
        <input id="quit" type = "submit" value="quit"></input>
        <h2 id="players-turn">Player turn</h2>
    </div>

    <div class="all-cards-played"hidden >
        <img id="player1-card" src ="https://photos.bigoven.com/recipe/hero/roasted-vegetables-baguette-sandwic.jpg?h=100&w=200">
        <img id="player2-card" src ="https://photos.bigoven.com/recipe/hero/roasted-vegetables-baguette-sandwic.jpg?h=100&w=200">
        <img id="player3-card"src ="https://photos.bigoven.com/recipe/hero/roasted-vegetables-baguette-sandwic.jpg?h=100&w=200">
        <img id="player4-card"src ="https://photos.bigoven.com/recipe/hero/roasted-vegetables-baguette-sandwic.jpg?h=100&w=200">
        <img id="player5-card"src ="https://photos.bigoven.com/recipe/hero/roasted-vegetables-baguette-sandwic.jpg?h=100&w=200">
    </div>

    <div class="card-outline" >
        <div class="card-contents">

            <img id="sandwich" src="http://frostyqueen.org/wp-content/uploads/2016/06/Tuna_Sandwhich.jpg" width="200" height="150">
            <p id="caption">Placeholder text</p>


            <div class="radio-labels">
                <input class = "labels" type="radio" id="Deliciousness" name="choices" value="Deliciousness">
                <label class = "labels" for="Deliciousness">Deliciousness</label>
                <br>
                <div class="this-label">
                    <input class = "labels" type="radio" id="Size" name="choices" value="Size">
                    <label class = "labels" for="Deliciousness">Size</label>
                </div>
                <br>
                <input class = "labels" type="radio" id="Toastability" name="choices" value="Toastability">
                <label class = "labels" for="Deliciousness">Toastability</label>
                <br>
                <input class = "labels" type="radio" id="Satiation" name="choices" value="Satiation">
                <label class = "labels" for="Deliciousness">Satiation</label>
                <br>
                <input class = "labels" type="radio" id="Complexity" name="choices" value="Complexity">
                <label class = "labels" for="Deliciousness">Complexity</label>
            </div>
        </div>
    </div>

    <div class="winning-card" hidden>
        <img src="https://photos.bigoven.com/recipe/hero/roasted-vegetables-baguette-sandwic.jpg?h=100&w=200">
    </div>

    <input id="play-card" type="submit" value="play card" disabled></input>

    <div class="game-stats">
        <p id="number-of-cards">number of cards left Placeholder</p>
        <p id="count-of-rounds">Count of rounds Placedholder</p>
    </div>



    </body>


		</div>
		
		<script type="text/javascript">
		
			// Method that is called on page load
			function initalize() {
			
				// --------------------------------------------------------------------------
				// You can call other methods you want to run when the page first loads here
				// --------------------------------------------------------------------------
				
				// For example, lets call our sample methods
				helloJSONList();
				helloWord("Student");
				
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
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloWord(word) {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}

		</script>
		
		</body>
</html>