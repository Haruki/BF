// ==UserScript==
// @name		Hello World
// @namespace 	http://www.oreilly.com/catalog/greasemonkeyhacks/
// @description	example script to alert
// @include        http://www.imdb.com/title/tt*/
// @include        http://imdb.com/title/tt*
// @exclude         http://*.media-imdb.com/*
// @exclude			http://www.imdb.com/rt/*
// @exclude 		http://www.imdb.com/images/*
// @exclude			http://www.imdb.com/widget/*
// @exclude			http://www.imdb.com/title/tt*/_ajax/*
// @exclude 		http://www.imdb.com/title/tt*//event/*
// ==/UserScript==


//iframe detection:
if (window.top != window.self) {
	//dont run in iframes.
	return;
}

//config:
var host = "http://pimpelkram.com:7780";

console.log("starting...");
var socialDivElement = clearSocialDiv();

//this object collects all relevant data of the current movie.
var pageMovie = new Object();
//collect movie essential infos:
//imdburl:
pageMovie.imdburl = getImdbUrl();
pageMovie.score = getScore();
//releaseDate, created from basic year if not present/not released yet:
pageMovie.releasedate = getReleaseDate(getReleaseYear(),"january",1);
pageMovie.namedeutsch = getNameDeutsch();
pageMovie.nameoriginal = getNameOriginal(pageMovie.namedeutsch);
pageMovie.log = log;
pageMovie.log();

getMovie(pageMovie.imdburl);

console.log('end...');







//======================= functions ===========================//


function log() {
	with (this) {
		console.log(imdburl);
		console.log(score);
		console.log(releasedate);
		console.log(namedeutsch);
		console.log(nameoriginal);
	}
}

function clearSocialDiv() {
	var socialDivResult = document.evaluate("//div[@class='social']",document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
	var socialDivElement = socialDivResult.snapshotItem(0);
	var children = socialDivElement.childNodes;
	for (var k=children.length-1; k>=0; k--) {
		socialDivElement.removeChild(children.item(k));
	}
	return socialDivElement;
}


function getImdbUrl() {
	return window.location.href;
}


function getScore() {
	var score = 0;
	var ratingSpanSearchResult = document.evaluate("//span[@itemprop='ratingValue']",document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
	if (ratingSpanSearchResult.snapshotLength > 0) {
		var ratingSpanElement = ratingSpanSearchResult.snapshotItem(0);
		var ratingChildren = ratingSpanElement.childNodes;
		for (var k=ratingChildren.length-1; k>=0; k--) {
			score = ratingChildren.item(k).nodeValue;
		}
	}
	return score;
}

function getReleaseYear() {
	var year;
	var releaseAnchorSearchResult = document.evaluate("//span[@class='nobr']//a", document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
	if (releaseAnchorSearchResult.snapshotLength > 0) {
		//console.log("found year anchor...");
		var releaseAnchor = releaseAnchorSearchResult.snapshotItem(0);
		year = releaseAnchor.childNodes.item(0).nodeValue;
	} else {
		//console.log("no year anchor..using direct children");
		releaseAnchorSearchResult = document.evaluate("//span[@class='nobr']", document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
		var releaseAnchor = releaseAnchorSearchResult.snapshotItem(0);
		year = releaseAnchor.childNodes.item(0).nodeValue.substring(1,5);
	}
	return year;
}

//releaseDate if present:
function getReleaseDate(releaseYear, releaseMonth, releaseDay) {
	var date;
	var dateAnchorSearchResult = document.evaluate("//a[@title='See all release dates']", document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
	if (dateAnchorSearchResult.snapshotLength > 0) {
		var dateAnchor = dateAnchorSearchResult.snapshotItem(0);
		var fullString = dateAnchor.childNodes.item(0).nodeValue;
		var parts = fullString.split(/\W+/);
		releaseDay = parts[1];
		releaseMonth = parts[2];
		releaseYear = parts[3];
	}
	date = new Date(releaseMonth + " " + releaseDay + ", " + releaseYear + " 00:00:00");
	// console.log(date);
	// console.log(">" + releaseDay + "< >" + releaseMonth + "< >" + releaseYear + "<");
	return date.getTime();
}


function getNameDeutsch() {
	var name;
	var deutschH1SearchResult = document.evaluate("//h1[@itemprop='name']", document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
	var deutschH1Element = deutschH1SearchResult.snapshotItem(0);
	name = deutschH1Element.childNodes.item(0);
	name = name.nodeValue.replace(/^\s+/,"").replace(/\s+$/,"");
	return name;
}


function getNameOriginal(nameDeutsch) {
	var name;
	var originalSearchResult = document.evaluate("//span[@class='title-extra']", document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
	if (originalSearchResult.snapshotLength > 0) {
		console.log("found original title");
		var originalElement = originalSearchResult.snapshotItem(0);
		name = originalElement.childNodes.item(0).nodeValue;
		name = name.replace(/^\s+/,"").replace(/\s+$/,"");
	} else {
		name = nameDeutsch;
	}
	return name;
}


function getMovie(imdburl) {
	GM_xmlhttpRequest({
	  method:"POST",
	  url:host + "/BF/MovieServlet",
	  data:"service=getmovie"
		+ "&imdburl=" + imdburl
		,
	  headers:{
		"User-Agent":"monkeyagent",
		"Accept":"text/monkey,text/xml",
		"Content-Type":"application/x-www-form-urlencoded"
		},
	  onload:processGetMovieResult
	});
}





function addMovie() {
	document.getElementById('addButton').disabled=true;
	GM_xmlhttpRequest({
	  method:"POST",
	  url:host + "/BF/MovieServlet",
	  data:"service=addmovie"
		+ "&imdburl=" + pageMovie.imdburl
		+ "&score=" + pageMovie.score
		+ "&releasedate=" + pageMovie.releasedate
		+ "&namedeutsch=" + pageMovie.namedeutsch
		+ "&nameoriginal=" + pageMovie.nameoriginal
		,
	  headers:{
		"User-Agent":"monkeyagent",
		"Accept":"text/monkey,text/xml",
		"Content-Type":"application/x-www-form-urlencoded"
		},
	  onload:processAddMovieResponse
	});
}

function createJsonResponse(result) {
	var response = JSON.parse(result.responseText);
	console.log(response);
}

function createUpdateForm() {
	var form = document.createElement('form');
	form.setAttribute('id','updateForm');
	form.setAttribute('action','');
	form.appendChild(document.createTextNode('seen'));
	var seenCheckbox = document.createElement('input');
	seenCheckbox.setAttribute('type','checkbox');
	seenCheckbox.setAttribute('checked','true');
	seenCheckbox.setAttribute('id','seen');
	seenCheckbox.setAttribute('value','seen');
	seenCheckbox.addEventListener('click',toggleSubmitButton,false);
	form.appendChild(seenCheckbox);
	var submitButton = document.createElement('input');
	submitButton.setAttribute('type','button');
	submitButton.setAttribute('value','update');
	submitButton.setAttribute('id','submit');
	submitButton.setAttribute('disabled','true');
	submitButton.addEventListener('click',updateMovieSeen,false);
	form.appendChild(submitButton);
	form.appendChild(document.createElement('br'));
	socialDivElement.appendChild(form);

	// socialDivElement.innerHTML =
	// "<form id='updateForm' name='updateForm' action=''>"
	// +	"<input type='text' readonly name='score' value='0.0' /><br/>"
	// +	"seen<input type='checkbox' checked name='seen' value='seen' /><br/>"
	// +	"<input type='submit' value='submit' disabled/>"
	// +"</form>"
	// ;
}

function createAddForm() {
	addInfoLine(socialDivElement,'form',{'id':'addForm','action':''},false);
	var form = document.getElementById('addForm');
	addInfoLine(form,'input',{'type':'button','value':'ADD MOVIE','id':'addButton'},true);
	document.getElementById('addButton').addEventListener('click',addMovie,false);
	// socialDivElement.innerHTML =
	// "<form id='addForm' name='addForm' action=''>"
	// +	"<input type='button' name='addButton' value='ADD MOVIE' onclick='addMovie'/>"
	// +"</form>"
	// ;
}

function processGetMovieResult(result) {
	var response = JSON.parse(result.responseText);
	if (response.error) {
		if (response.error == "not found") {
			createAddForm();
		} else {
			showError(response.error);		
		}
	} else {
		createUpdateForm();
		populateUpdateForm(response);
	}
}


function roundFloat(number) {
	return Math.round(number * 10)/10;
}

function asString(floatNumber) {
	if (floatNumber % 1 == 0) {
		return floatNumber + ".0";
	} else {
		return floatNumber;
	}
}

function renderScoreDifference(movie) {
	var databaseScore = roundFloat(movie.imdbrating);
	var diff = roundFloat(databaseScore - pageMovie.score);
	var diffString = "";
	if (diff > 0) {
		diffString = "  ( - " + diff + " )";
	} else if (diff < 0) {
		diffString = "  " + Math.abs(diff);
	}
	return asString(databaseScore) + diffString;
}

function populateUpdateForm(movie) {
	var parent = document.getElementById('updateForm');
	//addInfoLine(parent,'input',{'type':'text','value':renderScoreDifference(movie)},true);
	addTextLine(parent,"Score: " + renderScoreDifference(movie),true);
	addTextLine(parent,"Title: " + movie.namedeutsch,true);
	addTextLine(parent,"Original: " + movie.nameoriginal,true);
	addTextLine(parent,"Added: " + formatDate(new Date(movie.creationtime)),true);
	addTextLine(parent,"SeenTime: " + formatDate(new Date(movie.seentime)),true);
	addTextLine(parent,"Released: " + formatDate(new Date(movie.releasedate)),true);
	var seenBox = document.getElementById('seen');	
	if (movie.seen == true) {
		seenBox.checked = true;
		seenBox.value = 'true';
	} else {
		seenBox.checked = false;
		seenBox.value = 'false';
	}
}

function showError(error) {
	socialDivElement.innerHTML = error;
	console.log(error);
}

function addInfoLine(parent,name,attributes,br) {
	var field = document.createElement(name);
	for (key in attributes) {
		field.setAttribute(key,attributes[key]);
	}
	parent.appendChild(field);
	if (br) {
		var breakElement = document.createElement('br');
		parent.appendChild(breakElement);
	}
}

function addTextLine(parent,text,br) {
	var textField = document.createTextNode(text);
	parent.appendChild(textField);
	if (br) {
		var breakElement = document.createElement('br');
		parent.appendChild(breakElement);
	}
}

function formatDate(date) {
	return date.getFullYear() + "-" +(date.getMonth()+1) + "-" + date.getDate();
}

function toggleSubmitButton() {
	console.log("toggled!!!");
	var button = document.getElementById('submit');
	//var val = (this.value == 'true');
	button.disabled = (!button.disabled);
}

function processUpdateMovieResult(response) {
	var resp = JSON.parse(response.responseText);
	if (resp.error) {
		addTextLine(document.getElementById('updateForm'),' >>> ERROR <<< ',true);
	} else {
		addTextLine(document.getElementById('updateForm'),' >>> CHANGE SUCCESS',true);
	}
}


function updateMovieSeen() {
	//disable submit button because seen status swapped:
	document.getElementById('submit').disabled=true;
	//get status of seen checkbox:
	console.log("updateMovieSeen!");
	var checkBox = document.getElementById('seen');
	var status = checkBox.checked;
	console.log(status);
	GM_xmlhttpRequest({
	  method:"POST",
	  url:host + "/BF/MovieServlet",
	  data:"service=updatemovieseen"
		+ "&imdburl=" + pageMovie.imdburl
		+ "&seen=" + status
		,
	  headers:{
		"User-Agent":"monkeyagent",
		"Accept":"text/monkey,text/xml",
		"Content-Type":"application/x-www-form-urlencoded"
		},
	  onload:processUpdateMovieResult
	});
}

function processAddMovieResponse(response) {
	var jsonResponse = JSON.parse(response.responseText);
	if (jsonResponse.success) {
		clearSocialDiv();
		getMovie(pageMovie.imdburl);
	}
}
