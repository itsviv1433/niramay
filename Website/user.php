<?php

    error_reporting(0);
    include('config.php');
    session_start();
    
if($_GET['logout'] != 1){
    if(isset($_SESSION['username'])) {
    	    
        // $user = trim(stripslashes($_GET['username']));
        // $password = trim(stripslashes($_GET['password']));
        $user = $_SESSION['username'];
        $password = $_SESSION['password'];
        
        $getdataquery = "SELECT * FROM `user` WHERE username = '$user'";
    	$getdataresult = mysqli_query($conn, $getdataquery);
    	$userData = mysqli_fetch_object($getdataresult);
    	switch($userData->gender){
    	    case 0:$gender = "NA";break;
    	    case 1:$gender = "Male";break;
    	    case 2:$gender = "Famale";break;
    	    case 3:$gender = "Other";break;
    	}
    	$cities = array();
    // 	$city = array();
    	$getcitiesquery = "SELECT `cities` FROM `facilities`";
    	$getcitiesresult = mysqli_query($conn, $getcitiesquery);
        while ($row = mysqli_fetch_assoc($getcitiesresult)) {
            $cities[] = $row;
        }
        if($userData->city == 0){
            $city = "NA";
        }else{
            $city = $cities[$userData->city]['cities'];
        }
    }
    else{
        unset($_SESSION['username']); 
        session_destroy();
        header("Location:./user_login.php");
        exit();
    }
	function function_alert($message) {
    // Display the alert box 
    	echo "<script>alert('$message');</script>";
	}
}else{
    unset($_SESSION['username']); 
    session_destroy();
    header("Location:./user_login.php");
    exit();
}
	
	
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Profile | <?php echo $userData->name ?></title>
		<link rel = "icon" href ="https://cdn-icons.flaticon.com/png/512/1979/premium/1979774.png?token=exp=1637321250~hmac=85e8787b24e4ca44db4d13eb2d2cb33e" type = "image/x-icon">

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400,700|" rel="stylesheet" type="text/css">
		<link href="admin/fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="admin/fonts/lineo-icon/style.css" rel="stylesheet" type="text/css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="style.css">
        <script language="JavaScript" Type="text/javascript" >
    	    document.addEventListener('contextmenu', event => event.preventDefault());
    	    window.addEventListener('contextmenu', function (e) { 
                 e.preventDefault(); 
            }, false);
            
            // window.history.forward();
            // function noback() { window.history.forward(); }
            function logout(){
                window.location.replace("./user_login.php");
            }
    	</script>
    	<style>
    	    tr:hover{
    	        font-size:115%;
    	    }
    	</style>

		
	</head>


	<body onload="noback()">
		
		<div id="site-content">
			<header class="site-header">
				<div class="top-header">
					<div class="container">
						<a href="index.html" id="branding">
							<img src="admin/images/logo.png" alt="Company Name" class="logo">
							<div class="logo-text">
								<h1 class="site-title">Niramay HealthCare</h1>
								<small class="description">Serve Santu Niramaya</small>
							</div>
						</a> <!-- #branding -->
					
						<div class="right-section pull-right">
							<!--<a href="#" class="phone"><img src="images/icon-phone.png" class="icon">+91 7219276033</a>-->
					
							<form action="#" class="search-form">
								<input type="text" placeholder="Search...">
								<button type="submit"><img src="admin/images/icon-search.png" alt=""></button>
							</form>
						</div>
					</div> <!-- .container -->
				</div> <!-- .top-header -->

				
				<div class="bottom-header">
					<div class="container">
						<div class="main-navigation">
							<button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
							<ul class="menu">
								<li class="menu-item"><a href="admin_login.php">Admin Login</a></li>
								<li class="menu-item"><a href="index.html">Home</a></li>
								<li class="menu-item"><a href="about.html">About Us</a></li>
								<li class="menu-item"><a href="contact.php">Contact</a></li>
							</ul> <!-- .menu -->
						</div> <!-- .main-navigation -->
						
						<div class="social-links">
							<a href="https://www.facebook.com/vivek.mahajan.75286100" target="_blank"><i class="fa fa-facebook"></i></a>
							<a href="https://twitter.com/ItsViv1433?s=03" target="_blank"><i class="fa fa-twitter"></i></a>
							<a href="https://instagram.com/itz_viv_1433?utm_medium=copy_link" target="_blank"><i class="fa fa-instagram"></i></a>
							<a href="https://www.linkedin.com/in/vivek-mahajan-aa2244216" target="_blank"><i class="fa fa-linkedin"></i></a>
						</div>
						
						<div class="mobile-navigation"></div>
					</div>
				</div>
				
			</header> <!-- .site-header -->
			<!-- User Data -->
			<header class="user-data" style="margin:20px">
         	<div class="" align="center" style="width:98%;display:flex;flex-flow:column;">
         	  
             		<table class="table table-hover col-sm-12 col-12 col-md-12">
                        
                        <thead>
                            <tr>
                              <th scope="col">Name</th>
                              <th scope="col">Data</th>
                             </tr>
                        </thead>
                        <tbody>
                            <tr>
                              <!--<th scope="row">2</th>-->
                              <th value="">Username</th>
                              <td value=""><?php echo $userData->username ?></td>
                            </tr>
                            <tr>
                              <!--<th scope="row">2</th>-->
                              <th value="">Name</th>
                              <td value=""><?php echo $userData->name ?></td>
                            </tr>
                            <tr>
                              <!--<th scope="row">3</th>-->
                              <th value="">Email</th>
                              <td value=""><?php echo $userData->email ?></td>
                            </tr>
                            <tr>
                              <!--<th scope="row">4</th>-->
                              <th value="">Phone</th>
                              <td value=""><?php echo $userData->phone ?></td>
                            </tr>
                            <tr>
                              <!--<th scope="row">5</th>-->
                              <th value="">Weight</th>
                              <td value=""><?php echo $userData->weight ?> Kg</td>
                            </tr>
                            <tr>
                              <!--<th scope="row">6</th>-->
                              <th value="">Height</th>
                              <td value=""><?php echo $userData->height ?> cm</td>
                            </tr>
                            <tr>
                              <!--<th scope="row">7</th>-->
                              <th value="">Age</th>
                              <td value=""><?php echo $userData->age ?> Years</td>
                            </tr>
                            <tr>
                              <!--<th scope="row">7</th>-->
                              <th value="">Gender</th>
                              <td value=""><?php echo $gender ?></td>
                            </tr>
                            <tr>
                              <!--<th scope="row">7</th>-->
                              <th value="">City</th>
                              <td value=""><?php  echo $city ?></td>
                            </tr>
                            <thead>
                                <tr>
                                    <td>
                                        <a href="./user.php?logout=1">
                                            <button type="button" style="float:left;background:#0F75BD;color:white;padding:10px;border-radius:2px;border:none;">
                                                    Logout
                                            </button>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="./user_edit.php?username=<?php echo $user; ?>">
                                            <button type="button" style="float:right;background:#0F75BD;color:white;padding:10px;border-radius:2px;border:none;">
                                                Edit Profile
                                            </button>
                                        </a>
                                    </td>
                                </tr>
                            </thead>
                        </tbody>
                    </table>
                    <p style="color:black;font-family:Georgia, serif;margin-top:5px;text-align:center;">*For more details please download and login to our official<br> 
                    <a href="#">Niramay Healthcare Android Application</a>.</p>
         	</div>
         	
         	</header>
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
							<a href="index.html">Home</a>
							<a href="about.html">About us</a>
							<a href="contact.php">Contact</a>
						</nav>

						<div class="colophon">Copyright © 2021 Niramay Healthcare. Designed by Niramay HealthCare Team. All Rights Reserved.</div>
					</div>
				</div>
			</div>
		</div>

		<script src="admin/js/jquery-1.11.1.min.js"></script>
		<script src="http://maps.google.com/maps/api/js?sensor=false&amp;language=en"></script>
		<script src="admin/js/plugins.js"></script>
		<script src="admin/js/app.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
                crossorigin="anonymous">
		</script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"
                integrity="sha384-W8fXfP3gkOKtndU4JGtKDvXbO53Wy8SZCQHczT5FMiiqmQfUpWbYdTil/SxwZgAN"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.min.js"
                integrity="sha384-skAcpIdS7UcVUC05LJ9Dxay8AXcDYfBJqt1CJ85S/CFujBsIzCIv+l9liuYLaMQ/"
                crossorigin="anonymous">
        </script>
	</body>

</html>
