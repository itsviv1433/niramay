<?php
error_reporting(0);
require('config.php');
session_start();
$admin_user = $_SESSION['admin'];

$fac_name = '';
$fac_city = '';
$fac_address = '';
$fac_contact = '';
$beds_occupacy = '';

$msg = '';


if(isset($_SESSION['admin']) && isset($_SESSION['isauth'])){
}else{
    session_unset();
    session_destroy();
    header("Location:../admin_login.php");
    exit();
}
if($_GET) {
    $id = $_GET['id'];
    $getdataquery = "select * from `facilities` where `fac_id`='$id'";
    $res = mysqli_query($conn, $getdataquery);
    $data = mysqli_fetch_object($res);
    
    $getcitiesquery = "SELECT `cities` FROM `facilities`";
    $getcitiesresult = mysqli_query($conn, $getcitiesquery);


    $fac_name = $data->fac_name;
    $fac_city = $data->fac_city;
    $fac_address = $data->fac_address;
    $fac_contact = $data->fac_contact;
    $beds_occupacy = $data->beds_occupacy;
    
}
if(isset($_POST['submit'])) {
    $fac_name = $_POST['fac_name'];
    $fac_city = $_POST['fac_city'];
    $fac_address =  $_POST['fac_address'];
    $fac_contact = $_POST['fac_contact'];
    $beds_occupacy = $_POST['beds_occupacy'];

    $update_sql = "update facilities set fac_name='$fac_name',fac_city='$fac_city',fac_address='$fac_address',fac_contact='$fac_contact',beds_occupacy='$beds_occupacy' where fac_id='$id'";
    $res = mysqli_query($conn, $update_sql);
    if($res){   
    	header("Location:./admin_facility_list.php");
        exit();
    }else{
        $msg = "Something went wrong";
    }
}

?>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Admin | Niramay HealthCare</title>
		<link rel = "icon" href ="https://cdn-icons.flaticon.com/png/512/1979/premium/1979774.png?token=exp=1637321250~hmac=85e8787b24e4ca44db4d13eb2d2cb33e" type = "image/x-icon">

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="fonts/lineo-icon/style.css" rel="stylesheet" type="text/css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />

		<!-- Loading main css file -->
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="admin_style.css">
		
        <script language="JavaScript" Type="text/javascript" >
    	    document.addEventListener('contextmenu', event => event.preventDefault());
    	    window.addEventListener('contextmenu', function (e) { 
                 e.preventDefault(); 
            }, false);
            function logout() {
               window.location.replace("../admin_login.php");
            });
            
    	</script>
	</head>
	<body>
		
		<div id="site-content">
			<header class="site-header">
				<div class="top-header">
					<div class="container">
						<a href="../index.html" id="branding">
							<img src="images/logo.png" alt="Company Name" class="logo">
							<div class="logo-text">
								<h1 class="site-title">Niramay HealthCare</h1>
								<small class="description">Serve Santu Niramaya</small>
							</div>
						</a> <!-- #branding -->
					</div> <!-- .container -->
				</div> <!-- .top-header -->

				
    			<!--NAVIGATION DRAWER-->
                <div class="col-12" style="padding:15px;">
                    <nav class="navbar navbar-dark bg-dark col-12 h-50">
                        <div class="" style="display:flex;">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation" style="display:flex;">
                          <span class="navbar-toggler-icon"></span>
                        </button>
                      </div>
                    </nav>
                </div>
                <div class="col-12" style="padding:15px;">
        			<div class="collapse" id="navbarToggleExternalContent">
                      <div class="bg-dark p-2">
                        <a href="../admin_login.php"><p class="text-light text-left mt-2" style="font-size:25px;width:100%;">Hello Dear <?php echo $admin_user; ?></p></a>
                      </div>
                    </div>
                </div>
			</header> <!-- .site-header -->
			
			
            <div class="content pb-0">
                <div class="animated fadeIn">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-header"><strong>Edit Profile Facility :- <?php echo $fac_name; ?></strong><small> </small></div>
                                <form method="POST" enctype="multipart/form-data" action="">
                                    <div class="card-body card-block">
            
                                        <div class="form-group">
                                            <label for="name" class=" form-control-label">Facility Name</label>
                                            <input type="text" name="fac_name" placeholder="Enter Facility Name" class="form-control" required value="<?php echo $fac_name ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="username" class=" form-control-label">City</label>
                                            <input type="text" name="fac_city" placeholder="Enter City" class="form-control" required value="<?php echo $fac_city ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="email" class=" form-control-label">Address</label>
                                            <input type="text" name="fac_address" placeholder="Enter Address" class="form-control" required maxlength="255" value="<?php echo $fac_address ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Contact</label>
                                            <input type="number" name="fac_contact" placeholder="Enter Mobile" class="form-control" required value="<?php echo $fac_contact ?>">
                                        </div>
                                       <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Beds Occupacy</label>
                                            <input type="number" name="beds_occupacy" placeholder="Enter Beds Occupacy" class="form-control" required value="<?php echo $beds_occupacy ?>">
                                        </div>
                                        
                                        <div class="field_error mt-2 text-danger" ><?php echo $msg ?></div>
            
                                        <button id="payment-button" name="submit" type="submit" class="btn btn-lg btn-info btn-block mt-2">
                                            <span id="payment-button-amount">SUBMIT</span>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <hr class="dashed" style="border-top:3px dashed #000000;width:100%;">
            <div class="site-footer">
			    <div class="widget-area" align="center">
					<div class="container text-center" align="center">
						<div class="row text-center" align="center">
							<div class="col-sm-12">
								<div class="widget" align="text-center">
									<h3 class="widget-title">Contact</h3>
									<address> Project Done by Students of <br>Government Polytechnic<br> Jalgaon
									</address>
									<a href="#">Phone: +91 1234567890</a><br>
									<a href="mailto:healthcare.niramay@gmail.com">healthcare.niramay@gmail.com</a>
								</div>
							</div>
						</div>
					</div>
    				<div class="bottom-footer">
    					<div class="container">
    						<nav class="footer-navigation">
    							<a href="../index.html">Home</a>
    							<a href="../about.html">About us</a>
    							<a href="../contact.php">Contact</a>
    						</nav>
    						<div class="colophon">Copyright © 2021 Niramay Healthcare. Designed by Niramay HealthCare Team. All Rights Reserved.</div>
    					</div>
    			    </div>
			    </div>
		    </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
    </body>
</html>
