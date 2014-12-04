<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>SMART</title>
  <link href="css/main.css" rel="stylesheet" type="text/css" />
  <link href="css/console.css" rel="stylesheet" type="text/css" />
  <link href="css/priorities.css" rel="stylesheet" type="text/css" />
  <link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
  
  
  <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyCv869_troFE7YFo52ULGOrxjMC4I9NZwI&sensor=false"></script>
  <script type="text/javascript" src="js/jquery.js"></script>
  <script type="text/javascript" src="js/jquery-ui.js"></script>
  <script type="text/javascript" src="js/window-opener.js"></script>
  <script type="text/javascript" src="js/labels.js"></script>
  <script type="text/javascript" src="js/utils.js"></script>
  <script type="text/javascript" src="js/alerts.js"></script>
  <script type="text/javascript" src="js/alert-events.js"></script>
  <script type="text/javascript" src="js/refresh.js"></script>
  <script type="text/javascript" src="js/initfunctions.js"></script>
  <script type="text/javascript" src="js/jsonutils.js"></script>
  <script type="text/javascript" src="js/jquery.dialogextend.js"></script>
  
  <script type="text/javascript" src="js/map/jquery.ui.map.js"></script>
  <script type="text/javascript" src="js/map/maputils.js"></script>
  <script type="text/javascript" src="js/map/polygonGenerator.js"></script>
  <script type="text/javascript" src="js/map/geo.js"></script>
  <script type="text/javascript" src="js/map/latlon.js"></script>
  <script type="text/javascript" src="js/map/markerclusterer.js"></script>
  <!--[if gte IE 9]>
  <style type="text/css">
    .gradient {
       filter: none;
    }
  </style>
  <![endif]-->
  </head>
  <body style="background: url('img/smartbg.png') repeat; color: #FFFFFF">
  <div style="position: absolute; top: 0; left: 0; width: 100%">
  <div style="background: url('img/home-top.png') no-repeat; height: 138px; width: 398px;"></div>
  <div class="gradient gradient-bar" style="width: 100%; height: 65px; margin-top: 5px;"></div>
  </div>