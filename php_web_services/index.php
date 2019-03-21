<?php
    include("includes/connection.php");
		include("language/language.php");

	if(isset($_SESSION['admin_name']))
	{
		header("Location:home.php");
		exit;
	}
?>
<!DOCTYPE html>
<html>
<head>
<meta name="author" content="">
<meta name="description" content="">
<meta http-equiv="Content-Type"content="text/html;charset=UTF-8"/>
<meta name="viewport"content="width=device-width, initial-scale=1.0">
<title><?php echo APP_NAME;?></title>
<link rel="stylesheet" type="text/css" href="assets/css/vendor.css">
<link rel="stylesheet" type="text/css" href="assets/css/flat-admin.css">

<!-- Theme -->
<link rel="stylesheet" type="text/css" href="assets/css/theme/blue-sky.css">
<link rel="stylesheet" type="text/css" href="assets/css/theme/blue.css">
<link rel="stylesheet" type="text/css" href="assets/css/theme/red.css">
<link rel="stylesheet" type="text/css" href="assets/css/theme/yellow.css">

<script src="assets/js/login.js" type="text/javascript"></script>

</head>
<body>
<div class="app app-default">
  <div class="app-container app-login">
    <div class="flex-center">
      <div class="app-body">
        <div class="app-block">
          <div class="app-form login-form">
            <div class="form-header">
              <div class="app-brand"><?php echo APP_NAME;?></div>
            </div>
			<div class="login_title_lineitem">
				<div class="line_1"></div>
				  <div class="flipInX-1 blind icon">
					 <span class="icon">
						 <i class="fa fa-gg"></i>&nbsp;
						 <i class="fa fa-gg"></i>&nbsp;
						 <i class="fa fa-gg"></i>
				   </span>
				   </div>
				<div class="line_2"></div>
			</div>
			<div class="clearfix"></div>
            <form action="login_db.php" method="post">
			  <div class="input-group" style="border:0px;">
                <?php if(isset($_SESSION['msg'])){?>
                <div class="alert alert-danger  alert-dismissible" role="alert"> <?php echo $client_lang[$_SESSION['msg']]; ?> </div>
                <?php unset($_SESSION['msg']);}?>
              </div>
              <div class="input-group"> <span class="input-group-addon" id="basic-addon1"> <i class="fa fa-user" aria-hidden="true"></i></span>
                <input type="text" name="username" id="username" class="form-control" placeholder="Username" aria-describedby="basic-addon1">
              </div>
              <div class="input-group"> <span class="input-group-addon" id="basic-addon2"> <i class="fa fa-key" aria-hidden="true"></i></span>
                <input type="password" name="password" id="password" class="form-control" placeholder="Password" aria-describedby="basic-addon2">
              </div>
              <div class="text-center">
                <input type="submit" class="btn btn-success btn-submit" value="Login">
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>


</body>
</html>