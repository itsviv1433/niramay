<?php
error_reporting(0);
require('config.php');
session_start();
$admin_user = $_SESSION['admin'];

$name = '';
$city = '';
$address = '';
$phone =  '';
$beds_occupacy = '';
$msg = '';
if(isset($_SESSION['admin']) && isset($_SESSION['isauth'])){
}else{
    session_unset();
    session_destroy();
    header("Location:../admin_login.php");
    exit();
}

if(isset($_POST['submit'])) {
    $name = $_POST['name'];
    $city = $_POST['city'];
    $address = $_POST['address'];
    $phone =  $_POST['phone'];
    $beds_occupacy = $_POST['beds_occupacy'];
    $cities = $_POST['cities'];
    if ($beds_occupacy < 2500) {
        $update_sql = "INSERT INTO `facilities`(`fac_name`, `fac_city`, `fac_address`, `fac_contact`,`beds_occupacy`) VALUES ('$name','$city','$address','$phone','$beds_occupacy')";
        $res = mysqli_query($conn, $update_sql);
        if($res){   
        	header("Location:./admin_facility_list.php");
        	exit();
        }else{
            $msg = "Something went wrong";
        }
    } else {
        $msg = "Enter Valid Beds Occupacy Value";
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
                        <a href="./admin_home.php"><p class="text-light text-left mt-2" style="font-size:25px;width:100%;">Hello Dear <?php echo $admin_user; ?></p></a>
                      </div>
                    </div>
                </div>
			</header> <!-- .site-header -->
			
			
            <div class="content pb-0">
                <div class="animated fadeIn">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-header"><strong>Add Facility</strong><small> </small></div>
                                <form method="POST" enctype="multipart/form-data" action="">
                                    <div class="card-body card-block">
            
                                        <div class="form-group">
                                            <label for="name" class=" form-control-label">Name</label>
                                            <input type="text" name="name" placeholder="Enter Name" class="form-control" required value="<?php echo $name ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="text" class=" form-control-label">City</label>
                                            <input type="text" name="city" placeholder="Enter City" class="form-control" required value="<?php echo $city ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="text" class=" form-control-label">Address</label>
                                            <input type="text" name="address" placeholder="Enter Address" class="form-control" required maxlength="30" value="<?php echo $address ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Mobile</label>
                                            <input type="number" name="phone" placeholder="Enter Mobile" class="form-control" required maxlength="10" value="<?php echo $phone ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Beds Occupacy</label>
                                            <input type="number" name="beds_occupacy" placeholder="Enter Beds Occupacy" class="form-control" required maxlength="10" value="<?php echo $beds_occupacy ?>">
                                        </div>
                                        
                                        <div class="field_error mt-2 text-danger" ><?php echo $msg ?></div>
            
                                        <button id="payment-button" name="submit" type="submit" class="btn btn-lg btn-info btn-block mt-2">
                                            <span id="payment-button-amount">ADD</span>
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
