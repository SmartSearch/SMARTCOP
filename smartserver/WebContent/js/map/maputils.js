function fromLatLng(latLng, rad){
	return new LatLon(latLng.lat(), latLng.lng(), rad);
}

var createMarker = function(point) {
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
		for (var m = 0; m < markers.length; m++) {
			if (markers[m] == marker) {
				polyLine.getPath().setAt(m, marker.getPosition());
				moveVMarker(m);
				break;
			}
		}
		m = null;
	});
	gMap.event.addListener(marker, "click", function() {
		for (var m = 0; m < markers.length; m++) {
			if (markers[m] == marker) {
				marker.setMap(null);
				markers.splice(m, 1);
				polyLine.getPath().removeAt(m);
				removeVMarkers(m);
				break;
			}
		}
		m = null;
	});
	//alert(gMap.geometry.poly.containsLocation(marker.getPosition(), polyGridPolygon));
	return marker;
};

var createVMarker = function(point) {
	var prevpoint = markers[markers.length-2].getPosition();
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
		for (var m = 0; m < vmarkers.length; m++) {
			if (vmarkers[m] == marker) {
				var tmpPath = tmpPolyLine.getPath();
				tmpPath.push(markers[m].getPosition());
				tmpPath.push(vmarkers[m].getPosition());
				tmpPath.push(markers[m+1].getPosition());
				break;
			}
		}
		m = null;
	});
	gMap.event.addListener(marker, "drag", function() {
		for (var m = 0; m < vmarkers.length; m++) {
			if (vmarkers[m] == marker) {
				tmpPolyLine.getPath().setAt(1, marker.getPosition());
				break;
			}
		}
		m = null;
	});
	gMap.event.addListener(marker, "dragend", function() {
		for (var m = 0; m < vmarkers.length; m++) {
			if (vmarkers[m] == marker) {
				var newpos = marker.getPosition();
				var startMarkerPos = markers[m].getPosition();
				var firstVPos = new gMap.LatLng(
						newpos.lat() - (0.5 * (newpos.lat() - startMarkerPos.lat())),
						newpos.lng() - (0.5 * (newpos.lng() - startMarkerPos.lng()))
				);
				var endMarkerPos = markers[m+1].getPosition();
				var secondVPos = new gMap.LatLng(
						newpos.lat() - (0.5 * (newpos.lat() - endMarkerPos.lat())),
						newpos.lng() - (0.5 * (newpos.lng() - endMarkerPos.lng()))
				);
				var newVMarker = createVMarker(secondVPos);
				newVMarker.setPosition(secondVPos);//apply the correct position to the vmarker
				var newMarker = createMarker(newpos);
				markers.splice(m+1, 0, newMarker);
				polyLine.getPath().insertAt(m+1, newpos);
				marker.setPosition(firstVPos);
				vmarkers.splice(m+1, 0, newVMarker);
				tmpPolyLine.getPath().removeAt(2);
				tmpPolyLine.getPath().removeAt(1);
				tmpPolyLine.getPath().removeAt(0);
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

var moveVMarker = function(index) {
	var newpos = markers[index].getPosition();
	if (index != 0) {
		var prevpos = markers[index-1].getPosition();
		vmarkers[index-1].setPosition(new gMap.LatLng(
				newpos.lat() - (0.5 * (newpos.lat() - prevpos.lat())),
				newpos.lng() - (0.5 * (newpos.lng() - prevpos.lng()))
		));
		prevpos = null;
	}
	if (index != markers.length - 1) {
		var nextpos = markers[index+1].getPosition();
		vmarkers[index].setPosition(new gMap.LatLng(
				newpos.lat() - (0.5 * (newpos.lat() - nextpos.lat())), 
				newpos.lng() - (0.5 * (newpos.lng() - nextpos.lng()))
		));
		nextpos = null;
	}
	newpos = null;
	index = null;
};

var removeVMarkers = function(index) {
	if (markers.length > 0) {//clicked marker has already been deleted
		if (index != markers.length) {
			vmarkers[index].setMap(null);
			vmarkers.splice(index, 1);
		} else {
			vmarkers[index-1].setMap(null);
			vmarkers.splice(index-1, 1);
		}
	}
	if (index != 0 && index != markers.length) {
		var prevpos = markers[index-1].getPosition();
		var newpos = markers[index].getPosition();
		vmarkers[index-1].setPosition(new gMap.LatLng(
				newpos.lat() - (0.5 * (newpos.lat() - prevpos.lat())),
				newpos.lng() - (0.5 * (newpos.lng() - prevpos.lng()))
		));
		prevpos = null;
		newpos = null;
	}
	index = null;
};

//function drawSquareFromCenter(centerPoint, sideLen, angle){
//	var tempPath=[
//	          centerPoint.rhumbDestinationPoint(45+angle, sideLen/2*Math.sqrt(2)),
//	          centerPoint.rhumbDestinationPoint(90+45+angle, sideLen/2*Math.sqrt(2)),
//	          centerPoint.rhumbDestinationPoint(180+45+angle, sideLen/2*Math.sqrt(2)),
//	          centerPoint.rhumbDestinationPoint(270+45+angle, sideLen/2*Math.sqrt(2))
//	];
//	var path=[];
//	for(var i=0; i<tempPath.length; i++){
//		path.push(tempPath[i].toLatLng());
//	}
//	
//	//Crea il poligono
//	var poly = new gMap.Polygon({
//		strokeColor: '#ff0000',
//		strokeOpacity: 1,
//		strokeWeight: 1,
//		fillColor: '#ff763b',
//		fillOpacity: 0.25,
//		visible: true,
//		clickable: false
//	});
//	poly.setPaths(path);
//	poly.setMap(map);
//	
//	photoMarkers[photoId]=photoId;
//	altway=checkIfNumber($("#defaultalt").val(), 100, false);
//	$('#mapcontainer').gmap('addMarker', {'id': photoId, 'altway': altway, 'type': "photo", 'poly': poly, 'position': centerPoint.toLatLng(), 'bounds':false }).click(function() {
//		$('#mapcontainer').gmap('openInfoWindow', { 'content': getMarkerValues(this) }, this);
//	});
//	polygons[photoId]=poly;
//	photoId++;
//	return poly;
//}

/**
 * restituisce i punti per generare un rettangolo in formato LatLon
 */
function getRectanglePath(centerPoint, width, height, angle){
	return [
     centerPoint.rhumbDestinationPoint(0+angle, width/2).rhumbDestinationPoint(270+angle, height/2), //Punto in alto spostato a sinistra
     centerPoint.rhumbDestinationPoint(90+angle, height/2).rhumbDestinationPoint(0+angle, width/2), //Punto a destra spostato in alto
     centerPoint.rhumbDestinationPoint(180+angle, width/2).rhumbDestinationPoint(90+angle, height/2), //Punto in basso spostato a destra
     centerPoint.rhumbDestinationPoint(270+angle, height/2).rhumbDestinationPoint(180+angle, width/2) //Punto a sinistra spostato in basso
     ];
}

/**
 * Controlla se il rettangolo disegnato su quel punto intereseca l'area interessata
 */
function checkRectangleInPolygon(centerPoint, width, height, angle, polygon){
	//Ipotesi di lavoro, il rettangolo non interseca
	var inPolygon=false;
	var tempPath=getRectanglePath(centerPoint, width, height, angle);
	for(var i=0; i<tempPath.length; i++){
		if(gMap.geometry.poly.containsLocation(tempPath[i].toLatLng(), polygon)){
			inPolygon=true;
		}
	}
	return inPolygon;
}

function drawRectangleFromCenter(centerPoint, width, height, angle){
	var tempPath=getRectanglePath(centerPoint, width, height, angle);
	var path=[];
	for(var i=0; i<tempPath.length; i++){
		path.push(tempPath[i].toLatLng());
	}
	
	//Crea il poligono
	var poly = new gMap.Polygon({
		strokeColor: '#ff0000',
		strokeOpacity: 1,
		strokeWeight: 1,
		fillColor: '#ff763b',
		fillOpacity: 0.25,
		visible: true,
		clickable: false
	});
	poly.setPaths(path);
	poly.setMap(map);
	//Crea il marker al centro
	//photoMarkers[photoId]=photoId;
	photoId++;
	altway=checkIfNumber($("#defaultalt").val(), 100, false);
	mapName.gmap('addMarker', {'id': photoId, 'altway': altway, 'type': "photo", 'internalType':"photoMarker", 'poly': poly, 'angle':angle, 'position': centerPoint.toLatLng(), 'bounds':false }).click(function() {
		mapName.gmap('openInfoWindow', { 'content': getMarkerValues(this) }, this);
	});
	//polygons[photoId]=poly;
	
	return poly;
}

function getMarkerValues(marker){
	$("#wayinfo .idway").html(marker.id);
	
	//Uso entrambi i metodi per compatibilità sia con .html(); che con .dialog();
	$("#wayinfo .altway").attr("value", marker.altway);
	$("#wayinfo .altway").val(marker.altway);
	
	$("#wayinfo .typeway").html(marker.type);
	$("#wayinfo").dialog({'modal':true, 'title': 'Edit Waypoint', 'buttons': { 
		"Remove": function() {
			if(confirm("Do you want to remove this waypoint?")){
				deleteMarker(marker);
			}
			$(this).dialog( "close" );
		},
		"Save": function() {
			marker.altway=$("#wayinfo .altway").val();
			$(this).dialog( "close" );
		}
	}});
}

function clearMarkers(){
	mapName.gmap('find', 'markers', { 'property': 'internalType', 'value': 'photoMarker' }, function(marker, found) {
		deleteMarker(marker);
	});
	photoId=-1;
}

function generatePoint(lat, lng){
	var newLatLng=new gMap.LatLng(lat, lng);
	return createMarker(newLatLng);
}

/**
 * Cancella il marker ed il poligono associato
 * @param marker
 */
function deleteMarker(marker){
	marker.poly.setMap(null);
	marker.setMap(null);
}