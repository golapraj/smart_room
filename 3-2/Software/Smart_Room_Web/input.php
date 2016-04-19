<html>
<head>
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
    <link rel="stylesheet" href="libs/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="libs/normalize/normalize.css">
    <link rel="stylesheet" href="libs/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/input.css">       
</head>
<body>

<form action="smart_room.php" method="post">
		<fieldset>
		<legend style="background:#3AA949;text-align:center;color:#fff;">Smart Room</legend>

		<div class="form-group col-md-12">
		<label class="label label-danger">Arduino IP</label>
		<input id="arduinoip" class="form-control" name="arduinoip" placeholder="Enter Arduino IP" type="text" required>
		</div>

		<div class="form-group col-md-12">
		<label class="label label-danger">Arduino Port</label>
		<input id="arduinoport" class="form-control" name="arduinoport" placeholder="Enter Arduino Port" type="number" min="0" required>
		</div>

		<div class="form-group col-md-12">
		<label class="label label-danger">Home IP Camera IP Address</label>
		<input id="homeip" class="form-control" name="homeip" placeholder="Enter IP Address" type="text" required>
		</div>

		<input style="margin-left:275px;width:150px;" class="btn btn-success btn-lg" name="submit" type="submit" value=" Start ">
		</fieldset>
		</form>
</body>
</html>