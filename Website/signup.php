<?php
    include('config.php');
    error_reporting(0);
    $error_name = "";
    $error_username = "";
    $error_email = "";
    $error_phone = "";
    $error_password = "";
 	if($_POST) {
        
        $name = trim(stripslashes($_POST['name']));        
    	$username = trim(stripslashes($_POST['username']));
    	$email = trim(stripslashes($_POST['email']));
    	$phone = trim(stripslashes($_POST['phone']));
    	$password = trim(stripslashes($_POST['password']));
    	$cpassword = trim(stripslashes($_POST['cpassword']));
    	
    	if (!preg_match("/^[a-zA-Z-' ]*$/",$name)) {
    	    $error_name = "Only letters and white space allowed in name.";
    	}
    	if (strlen($username) < 2) {
    	    $error_username = "Please enter valid username.";
    	}else{
    	    $validateusernamequery = "SELECT * FROM user WHERE username = '$username'";
            $validateusernameresult = mysqli_query($conn, $validateusernamequery);
            
            if (mysqli_num_rows($validateusernameresult)>0)
            {
                $error_username = "Username is taken.Please take another one.";
            }
    	}
    	if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    	    $error_email = "Invalid Email Format.";
    	}
    	if (strlen($phone) < 10 || strlen($phone) > 10) {
    	    $error_phone = "Please enter valid contact.";
    	}
    	if(strlen($password) < 7){
    	    $error_password = "Password is too Short."; 
    	}else{
    	    if($password != $cpassword){
    	        $error_password = "Password doesnt match.";
    	    }
    	}
    
    	if ($error_name==""&&$error_username==""&&$error_email==""&&$error_phone==""&&$error_password=="") {
            $insert_query = "INSERT INTO `user`(`name`, `username`, `email`, `phone`, `password`) VALUES ('$name','$username','$email','$phone','$password')";
            
            if (mysqli_query($conn, $insert_query)){
                include("../emailData.php");
              function_alert1("Account Created Successfully.Kindly Check Your Email to Verify Yout Credentials.");
              echo "<script>window.location.replace('http://niramayhealthcare.ml/website/user_login.php');</script>";
            } else{
                function_alert1("Something Went Wrong.Please Try Again Later.");
                echo "<script>window.location.replace('http://niramayhealthcare.ml/website/signup.php');</script>";
            } 
    	}
        else {
    		 function_alert1($error_name.'\n'.$error_username.'\n'.$error_email.'\n'.$error_phone.'\n'.$error_password);
    		  echo "<script>window.location.replace('http://niramayhealthcare.ml/website/signup.php');</script>";
    	} 
    
    }
	function function_alert1($message) {
    // Display the alert box 
    	echo "<script>alert('$message');</script>";
	}
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>User SignUp | Niramay Healthcare</title>
		<link rel = "icon" href ="https://cdn-icons.flaticon.com/png/512/1979/premium/1979774.png?token=exp=1637321250~hmac=85e8787b24e4ca44db4d13eb2d2cb33e" type = "image/x-icon">

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="fonts/lineo-icon/style.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />

		<!-- Loading main css file -->
		<link rel="stylesheet" href="style.css">
		<script type="text/javascript">
        	window.history.forward();
        	function noback() { window.history.forward(); }
        </script>
	<script language="JavaScript" Type="text/javascript" >
	    document.addEventListener('contextmenu', event => event.preventDefault());
	    window.addEventListener('contextmenu', function (e) { 
             e.preventDefault(); 
        }, false);
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
					
						<div class="right-section pull-right">
							<a href="#" class="phone"><img src="images/icon-phone.png" class="icon">+91 7219276033</a>
					
							<form action="#" class="search-form">
								<input type="text" placeholder="Search...">
								<button type="submit"><img src="images/icon-search.png" alt=""></button>
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
								<li class="menu-item"><a href="user_login.php">User Login</a></li>
								<li class="menu-item"><a href="../index.html">Home</a></li>
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
			<!-- Login Form -->
         	<div class="container" align="center">
            <!-- <div class="login-content"> -->
               <!-- <div class="login-form"> -->
                  <form method="post" action="">
                    <div class="" style="margin-top: 25px;">
                        <input type="text" name="name" class="form-control" placeholder="Name..." required>
                    </div>
                    <div class="" style="margin-top: 25px;">
                        <input type="text" name="username" class="form-control" placeholder="Username..." required>
                    </div>
                    <div class="" style="margin-top: 25px;">
                        <input type="text" name="email" class="form-control" placeholder="Email..." required>
                    </div>
                    <div class="" style="margin-top: 25px;">
                        <input type="text" name="phone" class="form-control" placeholder="Mobile Number..." required>
                    </div>
                    <div class="" style="margin-top: 25px;">
                        <input type="password" id="password" name="password" class="form-control" placeholder="Password..." required maxlength="16">
                        <i style="margin-left:-25px;cursor:pointer;z-index:1;" class="far fa-eye" id="togglePassword"></i>
                    </div>
                    <div class="" style="margin-top: 25px;">
                        <input type="password" id="rpassword" name="cpassword" class="form-control" placeholder="Confirm Password..." required maxlength="16">
                        <i style="margin-left:-25px;cursor:pointer;z-index:1;" class="far fa-eye" id="togglerPassword"></i>
                    </div>
                    <button type="submit" name="submit" class="btn" style="margin-top: 25px;margin-bottom: 25px;">SIGN UP</button>
                     
                    <p>Already have an account.Please <b><a href="user_login.php" style="text-decoration: underline;">Login</a></b>.</p>
				  </form>
               <!-- </div> -->
            <!-- </div> -->
         	</div>

             <script>
                const togglePassword = document.querySelector('#togglePassword');
                const togglerPassword = document.querySelector('#togglerPassword');
                const password = document.querySelector('#password');
                const rpassword = document.querySelector('#rpassword');

                togglePassword.addEventListener('click', function(e) {
                    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
                    password.setAttribute('type', type);
                    this.classList.toggle('fa-eye-slash');
                });
                togglerPassword.addEventListener('click', function(e) {
                    const type = rpassword.getAttribute('type') === 'password' ? 'text' : 'password';
                    rpassword.setAttribute('type', type);
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
									<a href="#">Phone: +91 7219276033</a><br>
									<a href="mailto:healthcare.niramay@gmail.com">healthcare.niramay@gmail.com</a>
								</div>
							</div>
						</div>
					</div>

				<div class="bottom-footer">
					<div class="container">
						<nav class="footer-navigation">
							<a href="../index.html">Home</a>
							<a href="about.html">About us</a>
							<a href="contact.php">Contact</a>
						</nav>

						<div class="colophon">Copyright © 2021 Niramay Healthcare. Designed by Niramay HealthCare Team. All Rights Reserved.</div>
					</div>
				</div>
			</div>
		</div>

		<script src="js/jquery-1.11.1.min.js"></script>
		<script src="http://maps.google.com/maps/api/js?sensor=false&amp;language=en"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		
	</body>

</html>
