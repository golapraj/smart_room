<?php
	
	if (isset($_POST['submit'])) 
	  {
	  	$arduinoip = $_POST['arduinoip'];
	  	$arduinoport = $_POST['arduinoport'];
	  	$homeip = $_POST['homeip'];
	  	echo $arduinoip."\n".$arduinoport."\n".$homeip;
	  }
?>
<html>
<head>
    <title>Smart Home</title>
    <link rel="icon" href="images/cse-logo.png" type="image/png">
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
    <link rel="stylesheet" href="libs/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="libs/normalize/normalize.css">
    <link rel="stylesheet" href="libs/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="smart_room.css">     
</head>
<body>
	<div class="main">
		<div class="video">
		    <iframe name="iframe" src="<?php echo "http://".$homeip.":8080/browserfs.html" ?>" style="margin:0px;height:550px; width:800px; border:none; border-radius:10px;"> </iframe> 
		<a href="<?php echo "http://".$homeip.":8080/photoaf.jpg" ?>" download="custom-filename.jpg" title="ImageName">
    	<br><img src="images/down.png">
		</a>
		</div><br>


		<div class="control">
			<center>
				<h1><span class="label label-success">Smart Room</span></h1>
				<button id="10" class="led"><img src="images/door2.png"></button><br><br>
				<button id="11" class="led"><img src="images/fan1.png"></button><br><br>
				<button id="12" class="led"><img src="images/light6.png"></button><br><br>
				<button id="13" class="led"><img src="images/light7.png"></button><br>
			
				<script src="jquery.min.js"></script>
				<script type="text/javascript">
				$(document).ready(function(){
				$(".led").click(function(){
					var p = $(this).attr('id');
					$.get("http://192.168.43.141:1090/",{pin:p});
					});
				});
				</script>
			</center>
		</div>
	</div>
<script src="libs/jquery/jquery.min.js"></script>
<script src="libs/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>