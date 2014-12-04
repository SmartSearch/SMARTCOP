/**
 * Functions used to generate polygons on a gmap.
 * CURRENTLY UNUSUED
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

function createPolyMarkerInsert(latLng){
	var marker = createPolyMarker(latLng);
	polyGridMarkers.push(marker);
	if (polyGridMarkers.length != 1) {
		var vmarker = createPolyVMarker(latLng);
		polyGridVMarkers.push(vmarker);
		vmarker = null;
	}
	var path = polyGridPolyline.getPath();
	path.push(latLng);
	return marker;
}

var createPolyMarker = function(point) {
	var imageNormal = new gMap.MarkerImage(
			"img/square.png",
			new gMap.Size(11, 11),
			new gMap.Point(0, 0),
			new gMap.Point(6, 6)
	);
	var imageHover = new gMap.MarkerImage(
			"img/square_over.png",
			new gMap.Size(11, 11),
			new gMap.Point(0, 0),
			new gMap.Point(6, 6)
	);
	var marker = new gMap.Marker({
		position: point,
		map: map,
		icon: imageNormal,
		draggable: true
	});
	gMap.event.addListener(marker, "mouseover", function() {
		marker.setIcon(imageHover);
	});
	gMap.event.addListener(marker, "mouseout", function() {
		marker.setIcon(imageNormal);
	});
	gMap.event.addListener(marker, "drag", function() {
		isFirst=false;
		if(polyGridMarkers[0] == marker){
			isFirst=true;
		}
		for (var m = 0; m < polyGridMarkers.length; m++) {
			if (polyGridMarkers[m] == marker) {
				polyGridPolyline.getPath().setAt(m, marker.getPosition());
				movePolyVMarker(m);
				//Se si sta muovendo il primo
				if(isFirst){
					polyGridPolyline.getPath().setAt(polyGridMarkers.length-1, marker.getPosition());
					movePolyVMarker(polyGridMarkers.length-1);
				}
				break;
			}
		}
		m = null;
	});
	gMap.event.addListener(marker, "click", function() {
		if(polyGridMarkers.length==4){
			alert("You can't have a polygon with less than 3 vertexes");
		} else {
			for (var m = 0; m < polyGridMarkers.length; m++) {
				if (polyGridMarkers[m] == marker) {
					marker.setMap(null);
					polyGridMarkers.splice(m, 1);
					polyGridPolyline.getPath().removeAt(m);
					removePolyVMarkers(m);
					break;
				}
			}
		}
		m = null;
	});
	return marker;
};

var createPolyVMarker = function(point) {
	var prevpoint = polyGridMarkers[polyGridMarkers.length-2].getPosition();
	var imageNormal = new gMap.MarkerImage(
			"img/square_transparent.png",
			new gMap.Size(11, 11),
			new gMap.Point(0, 0),
			new gMap.Point(6, 6)
	);
	var imageHover = new gMap.MarkerImage(
			"img/square_transparent_over.png",
			new gMap.Size(11, 11),
			new gMap.Point(0, 0),
			new gMap.Point(6, 6)
	);
	var marker = new gMap.Marker({
		position: new gMap.LatLng(
				point.lat() - (0.5 * (point.lat() - prevpoint.lat())),
				point.lng() - (0.5 * (point.lng() - prevpoint.lng()))
		),
		map: map,
		icon: imageNormal,
		draggable: true
	});
	gMap.event.addListener(marker, "mouseover", function() {
		marker.setIcon(imageHover);
	});
	gMap.event.addListener(marker, "mouseout", function() {
		marker.setIcon(imageNormal);
	});
	gMap.event.addListener(marker, "dragstart", function() {
		for (var m = 0; m < polyGridVMarkers.length; m++) {
			if (polyGridVMarkers[m] == marker) {
				var tmpPath = polyGridPolylineTmp.getPath();
				tmpPath.push(polyGridMarkers[m].getPosition());
				tmpPath.push(polyGridVMarkers[m].getPosition());
				tmpPath.push(polyGridMarkers[m+1].getPosition());
				break;
			}
		}
		m = null;
	});
	gMap.event.addListener(marker, "drag", function() {
		for (var m = 0; m < polyGridVMarkers.length; m++) {
			if (polyGridVMarkers[m] == marker) {
				polyGridPolylineTmp.getPath().setAt(1, marker.getPosition());
				break;
			}
		}
		m = null;
	});
	gMap.event.addListener(marker, "dragend", function() {
		for (var m = 0; m < polyGridVMarkers.length; m++) {
			if (polyGridVMarkers[m] == marker) {
				var newpos = marker.getPosition();
				var startMarkerPos = polyGridMarkers[m].getPosition();
				var firstVPos = new gMap.LatLng(
						newpos.lat() - (0.5 * (newpos.lat() - startMarkerPos.lat())),
						newpos.lng() - (0.5 * (newpos.lng() - startMarkerPos.lng()))
				);
				var endMarkerPos = polyGridMarkers[m+1].getPosition();
				var secondVPos = new gMap.LatLng(
						newpos.lat() - (0.5 * (newpos.lat() - endMarkerPos.lat())),
						newpos.lng() - (0.5 * (newpos.lng() - endMarkerPos.lng()))
				);
				var newVMarker = createPolyVMarker(secondVPos);
				newVMarker.setPosition(secondVPos);//apply the correct position to the vmarker
				var newMarker = createPolyMarker(newpos);
				polyGridMarkers.splice(m+1, 0, newMarker);
				polyGridPolyline.getPath().insertAt(m+1, newpos);
				marker.setPosition(firstVPos);
				polyGridVMarkers.splice(m+1, 0, newVMarker);
				polyGridPolylineTmp.getPath().removeAt(2);
				polyGridPolylineTmp.getPath().removeAt(1);
				polyGridPolylineTmp.getPath().removeAt(0);
				newpos = null;
				startMarkerPos = null;
				firstVPos = null;
				endMarkerPos = null;
				secondVPos = null;
				newVMarker = null;
				newMarker = null;
				break;
			}
		}
	});
	return marker;
};

var movePolyVMarker = function(index) {
	var newpos = polyGridMarkers[index].getPosition();
	if (index != 0) {
		var prevpos = polyGridMarkers[index-1].getPosition();
		polyGridVMarkers[index-1].setPosition(new gMap.LatLng(
				newpos.lat() - (0.5 * (newpos.lat() - prevpos.lat())),
				newpos.lng() - (0.5 * (newpos.lng() - prevpos.lng()))
		));
		prevpos = null;
	}
	if (index != polyGridMarkers.length - 1) {
		var nextpos = polyGridMarkers[index+1].getPosition();
		polyGridVMarkers[index].setPosition(new gMap.LatLng(
				newpos.lat() - (0.5 * (newpos.lat() - nextpos.lat())), 
				newpos.lng() - (0.5 * (newpos.lng() - nextpos.lng()))
		));
		nextpos = null;
	}
	newpos = null;
	index = null;
};

var removePolyVMarkers = function(index) {
	if (polyGridMarkers.length > 0) {//clicked marker has already been deleted
		if (index != polyGridMarkers.length) {
			polyGridVMarkers[index].setMap(null);
			polyGridVMarkers.splice(index, 1);
		} else {
			polyGridVMarkers[index-1].setMap(null);
			polyGridVMarkers.splice(index-1, 1);
		}
	}
	if (index != 0 && index != polyGridMarkers.length) {
		var prevpos = polyGridMarkers[index-1].getPosition();
		var newpos = polyGridMarkers[index].getPosition();
		polyGridVMarkers[index-1].setPosition(new gMap.LatLng(
				newpos.lat() - (0.5 * (newpos.lat() - prevpos.lat())),
				newpos.lng() - (0.5 * (newpos.lng() - prevpos.lng()))
		));
		prevpos = null;
		newpos = null;
	}
	index = null;
};