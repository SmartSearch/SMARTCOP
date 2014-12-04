/**
 * Collection of several utility functions
 * @author Wladimir Totino
 */

/**
 * checks wheter a strings ends with a certain suffix
 * @param str
 * @param suffix
 * @returns {Boolean}
 */
function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

/**
 * Parses the JSON data received by the nodes server. 
 * The data sent by the server is as follows:
 * {results: X, "numResults":Y}
 * if numResults is 0, no results value is appended
 * if numResults is 1, then results is a single value
 * if numResults is >1 then results is an array itself
 * @param data: an already parsed JSON data which must contain a numResults field and optionally a results field 
 */
function getResultsArray(data){
	var arr=new Array(); 
	if(data.numResults==1){
		arr.push(data.results);
	} else if(data.numResults>1){
		arr=data.results;
	}
	return arr;
}

/**
 * Checks wheter a string contains a certain substring
 * @param str
 * @param haystack
 * @returns {Boolean}
 */
function contains(str, haystack){
	return str.indexOf(haystack) > -1;
}

/**
 * Writes a log message in the log window. Currently it does nothing since logs are loaded from an external source
 * @param text
 * @param severity
 */
function writeLog(text, windowId, severity){
	//$("#"+windowId+"-logscontainer").append('<div class="logs-'+severity+'">&gt; '+text+'</div>');
	scrollLog(windowId);
}

/**
 * Scrolls the log up to the last message
 * @param windowId
 */
function scrollLog(windowId){
	var el=document.getElementById(windowId+"-logscontainer");
	el.scrollTop=el.scrollHeight;
}

/**
 * Returns a label for the current value (usually JSON variables), if no label is found, returns the string
 * @param key
 * @param arr
 * @returns
 */
function getLabel(key, arr){
	if(arr[key]!=undefined){
		return arr[key];
	} else {
		return key;
	}
}

/**
 * get the current time
 * @returns
 */
function getTime(){
	var d = new Date();
	return d.getTime();
}

/**
 * get a random number between min and max
 * @param min
 * @param max
 * @returns
 */
function getRandom(min, max){
	return Math.floor((Math.random() * (max-min+1)) + min);
}

/**
 * Controlla che il valore sia un numero altrimenti restituisce un valore di default
 * @param value
 * @param defaultVal
 * @param error
 * @returns
 */
function checkIfNumber(value, defaultVal, error){
	if(value==null||value==""||isNaN(value)){
		if(error){
			//Gestione dell'errore
		} else {
			return defaultVal;
		}
	} else {
		return value;
	}
}