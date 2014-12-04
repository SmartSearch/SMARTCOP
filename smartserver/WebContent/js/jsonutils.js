/**
 * This library contains helper functions used to work on generic JSON objects
 * @author Wladimir Totino
 */

/**
 * Traverse a json object to find the value which has a given 
 * WARNING: if there are more items with the same name (such as an array of items all with a field in common) 
 * it will return only the last one.
 */
function findInJson(search, jsonObj){
	return traverse("", jsonObj, search, new TraverseValue());
}

function traverse(key, jsonObj, search, value) {
	if(key!="" && key==search){
		//If it found the key then sets the value inside the value holding object
		value.setValue(jsonObj);
    } else if( typeof jsonObj == "object" ) {
    	//Else if jsonObj is another JSON object and not a value it will iterate through its keys
        $.each(jsonObj, function(k, v) {
            traverse(k, v, search, value);
        });
    }
    return value.getValue();
}

/**
 * Object used for json objects traversing
 * 
 */
function TraverseValue() {
    this.tempvalue;
}

TraverseValue.prototype.getValue= function() {
    return this.tempvalue;
};

TraverseValue.prototype.setValue= function(value) {
    this.tempvalue=value;
};