<!doctype html>
<html>
	<head>
		<meta http-equiv="refresh" content="60;URL='#menus'" />
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<title> Checks </title>
		<link rel="stylesheet" type="text/css" href="/static/checks/css/jquery.mobile-1.3.0.css" />
		</script>
		<script type="text/javascript" charset="utf-8" src="/static/checks/js/json2.js">
		</script>
		<script type="text/javascript" charset="utf-8" src="/static/checks/js/jquery-1.9.1.js">
		</script>
		<script type="text/javascript" charset="utf-8" src="/static/checks/js/jquery-migrate-1.1.1.js">
		</script>
		<script type="text/javascript" charset="utf-8" src="/static/checks/js/jquery.mobile-1.3.0.js">
		</script>
		<script type="text/javascript" charset="utf-8" src="/static/checks/js/zepto.onpress.js">
		</script>

		<script type="text/javascript" charset="utf-8" src="/static/checks/js/checks.js">
		</script>
		
		<link rel="stylesheet" type="text/css" href="/static/checks/css/mobile.css" />
		<link rel="stylesheet" type="text/css" href="/static/checks/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/static/checks/css/admin.css" />

		<link rel="stylesheet" type="text/css" href="/static/checks/css/jqm-survival-kit.css" />
		<script type="text/javascript" src="/static/checks/js/jquery.print.js"></script>
	</head>
	<body>		
	
	
		<div id="app-loader">	
				<div id="loading">
				  <img src="/static/checks/css//static/checks/images/app-loader.gif"/>
				  <p id="loadingMsg">Loading...</p>
				</div>
		</div>
		
		<!-- http://stackoverflow.com/questions/13986182/how-can-i-improve-the-page-transitions-for-my-jquery-mobile-app/13986390#13986390 -->
		<div id="allPages" style="visibility:hidden;">
			
			<!-- Home -->
			<div data-role="page" id="home">
				<a href="#menus" data-transition="slideup">
				<h1 style="color:#a00">Welcome</h1>
				<p>Please touch the screen to start</p>
				</a>
				<!-- a href="javascript:appSettings()" id="appSettings"><img src="/static/checks/images/settings-black.png"/></a -->
				<a href="#admin" id="appSettings"><img src="/static/checks/images/settings-black.png"/></a>
				
			</div>
			
			
			
			<!-- Menus -->
			<div data-role="page" id="menus">
				<div data-theme="a" data-role="header" data-position="fixed">
					
					<img id="homeBtn" style="height:50px" data-role="button" onclick='location.href="#home"' src="/static/checks/images/axis-logo.png" />
		
					<div id="update" style="display:; float:right">
					   <a href="javascript:showAppVersion()">AppVersion: Beta <script>document.write(appVersion)</script></a><br />
					</div>
					<div id="nav">
					</div>
				</div>
				<div id="topMenu">
				</div>
			
				<br clear="both"/>
				<div class="footer">
					<b>Axis Cafe and Restaurant Menu</b><br/>
					&copy; 2013 Cooked Specially. All rights reserved. 
				</div>
			</div>	
			
		</div>		
	</body>
</html>




