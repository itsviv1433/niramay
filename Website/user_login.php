<?php
    
error_reporting(0);
include('config.php');
session_start();
$error_username = "";
$error_password = ""; 
$username = "";
$password = "";
$msg = '';

    if(isset($_SESSION['username'])){
        header("Location:./user.php");
        exit();
    }else{
        
        if($_GET['logout']){
            unset($_SESSION['username']);
            session_destroy();
            header("Location:./user_login.php");
            exit();
        }
    	if($_POST) {
        
            $username = mysqli_real_escape_string($conn,$_POST['user_username']);
        	$password = mysqli_real_escape_string($conn,$_POST['user_password']);
        	
        	//Check UserName
        	if (strlen($username) < 2) {
        		$error_username = "Please enter valid username";
        	}
        	//check password
        	if(strlen($password) < 7){
        	    $error_password = "Password is too Short."; 
        	}
        
        	// Set Message
        	if ($error_username == "" && $error_password == "") {
        
        		$query = "SELECT * FROM `user` WHERE username = '$username' AND password = '$password'";
        		$result = mysqli_query($conn, $query);
        		$userData = mysqli_fetch_object($result);
        		if (mysqli_num_rows($result)>0){
        			if($userData->status){
                        $_SESSION['username']="$username";      
                        $_SESSION['password']="$password";  
                        header("Location:./user.php");
            // 			header("Location:https://niramayhealthcare.000webhostapp.com/website/user.php?username=$username&password=$password");
            			exit();
        	   		}else{
        	   		    $msg = "Access Denied.Please contact to Admin.";
                    }
        	   	}else{
        			$msg = "Enter Valid Credentials.";
           		}
        	} # end if - no validation error
        	else {
        		  $msg = $error_username.'<br>'.$error_password;
        	} # end if - there was a validation error
        }
    }
	
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>User Login | Niramay Healthcare</title>
		<link rel = "icon" href ="https://cdn-icons.flaticon.com/png/512/1979/premium/1979774.png?token=exp=1637321250~hmac=85e8787b24e4ca44db4d13eb2d2cb33e" type = "image/x-icon">

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400,700|" rel="stylesheet" type="text/css">
		<link href="admin/fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="admin/fonts/lineo-icon/style.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
		<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>

		<!-- Loading main css file -->
		<link rel="stylesheet" href="style.css">
		<script type="text/javascript">
        	window.history.forward();
        	function noback() { window.history.forward(); }
        </script>
		<!--[if lt IE 9]>
		<script src="js/ie-support/html5.js"></script>
		<script src="js/ie-support/respond.js"></script>
		<![endif]-->
	<script language="JavaScript" Type="text/javascript" >
	    document.addEventListener('contextmenu', event => event.preventDefault());
	    window.addEventListener('contextmenu', function (e) { 
             e.preventDefault(); 
        }, false);
	</script>


	</head>


	<body onload="noback();">
		
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
							<a href="#" class="phone"><img src="admin/images/icon-phone.png" class="icon">+91 1234567890</a>
					
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
							<a href="https://www.facebook.com/vivek.mahajan.75286100" target="_blank"><i class="fab fa-facebook"></i></a>
							<a href="https://twitter.com/ItsViv1433?s=03" target="_blank"><i class="fab fa-twitter"></i></a>
							<a href="https://instagram.com/itz_viv_1433?utm_medium=copy_link" target="_blank"><i class="fab fa-instagram"></i></a>
							<a href="https://www.linkedin.com/in/vivek-mahajan-aa2244216" target="_blank"><i class="fab fa-linkedin"></i></a>
						</div>
						
						<div class="mobile-navigation"></div>
					</div>
				</div>
				
			</header> <!-- .site-header -->
			<!-- Login Form --> 
         	<div class="container" align="center">
            <!-- <div class="login-content"> -->
               <!-- <div class="login-form"> -->
                  <form method="post" action="">
                     <div class="" style="margin-top: 25px;">
                        <!--<label style="margin-right:20px;"><b>USERNAME</b></label>-->
                        <input type="text" name="user_username" class="form-control" placeholder="Username..." required value="<?php echo $username; ?>">
                     </div>
                    <div style="margin-top:10px;">
                        <input type="password" id="password" name="user_password" placeholder="Enter Password" class="form-control" required maxlength="16" value="<?php echo $password ?>">
                        <i style="margin-left:-25px;cursor:pointer;z-index:1;" class="far fa-eye" id="togglePassword"></i>
                    </div>
                     <div style="color:red;margin-top:5px;" ><?php echo $msg ?></div>
                     <button type="submit" name="submit" class="btn" style="margin-top: 25px;margin-bottom: 25px;">SIGN IN</button>
                     
                     <p>Dont have an account.Please <b><a href="signup.php" style="text-decoration: underline;">Sign Up</a></b>.</p>
					</form>
               <!-- </div> -->
            <!-- </div> -->
         	</div>
         	<script>
                const togglePassword = document.querySelector('#togglePassword');
                const password = document.querySelector('#password');
            
                togglePassword.addEventListener('click', function(e) {
                    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
                    password.setAttribute('type', type);
                    this.classList.toggle('fa-eye-slash');
                });
            </script>


			<div class="site-footer">
				<div class="widget-area" align="center">
					<div class="container" align="center">
						<div class="row" align="center">
							<div class="col-sm-4">
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
		
	</body>

</html>
