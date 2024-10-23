<?php
    error_reporting(0);
    session_start();
    $error = '';
    $code = '';
    include_once 'googleLib/FixedBitNotation.php';
    include_once 'googleLib/GoogleAuthenticatorInterface.php';
    include_once 'googleLib/GoogleAuthenticator.php';
    include_once 'googleLib/GoogleQrUrl.php';
    $g 	= new \Google\Authenticator\GoogleAuthenticator();
    // $auth_flag = substr(str_shuffle("abcdefghijklmnopqrstvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, 16);
    $secret = 'XVQ2UIGO75XRUKJO';
    if($_POST){
        $code=$_POST['code'];	
        
        if($g->checkCode($secret, $code)){
            date_default_timezone_set("Asia/Kolkata");
            $full_date = date("l jS \of F Y h:i:s");
            $_SESSION['time'] = "$full_date";
            $_SESSION['isauth']="Auth";
        	header("Location:./admin_home.php");
            exit();
        }
        else{
            $color = '#FFBEBE';
        	$error = 'Incorrect or expired code!';
        }
    }
    else{
        if(isset($_SESSION['admin'])){

        }else{
            unset($_SESSION['admin']);
            session_destroy();
            header("Location:../admin_login.php");
            exit();
        }
    }

?>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Admin Authenticate | Niramay Healthcare</title>
		<link rel = "icon" href ="https://cdn-icons.flaticon.com/png/512/1979/premium/1979774.png?token=exp=1637321250~hmac=85e8787b24e4ca44db4d13eb2d2cb33e" type = "image/x-icon">

		<!-- fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="fonts/lineo-icon/style.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        
        <!--Bootstrap Js-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

		<!-- main css file -->
		<link rel="stylesheet" href="style.css">
		
	<script language="JavaScript" Type="text/javascript" >
	    document.addEventListener('contextmenu', event => event.preventDefault());
	    var toastobj = document.getElementById('toast');
	    window.addEventListener('contextmenu', function (e) { 
            e.preventDefault(); 
        }, false);

	</script>
    <style>
        @media only screen and (max-width: 500px) {
          .auth-area {
            flex-flow:column;
            width:100%;
          }
        }
        .auth-img{
            display:none;
        }
        .code-auth{
            width:100%;
        }
    </style>

	</head>
	<body onload="displayToast()">
		
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
							<a href="#" class="phone"><img src="images/icon-phone.png" class="icon">+91 1234567890</a>
					
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
								<!-- <li class="menu-item"><a href="#">Admin Login</a></li> -->
								<li class="menu-item"><a href="../user_login.php">User Login</a></li>
								<li class="menu-item"><a href="../index.html">Home</a></li>
								<li class="menu-item"><a href="../about.html">About Us</a></li>
								<li class="menu-item"><a href="../contact.php">Contact</a></li>
							</ul> <!-- .menu -->
						</div> <!-- .main-navigation -->

						
						<div class="mobile-navigation"></div>
					</div>
				</div>
				
			</header> <!-- .site-header -->

            <section class="section-auth">
                        <div class="text-light" style="margin:20px;background:#A4A4A4;border-radius:10px;padding-top:10px;">
                            <h2 style="padding-left:10px;">Google Two Factor Authentication</h2>
                            <p style="padding-left:10px;">Enter the verification code generated by Google Authenticator app on your phone.</p>
                            <div class="form-area auth-area bg-dark" style="display:flex;border-radius:10px;">
                                <!--Toast code-->
                                        
                                <div class="form-input code-auth text-light" style="background-image: url('https://image.freepik.com/free-photo/key-lock-password-security-privacy-protection-graphic_53876-121252.jpg');padding:20px;border-radius:10px;">
                                    <h2>Enter Code</h2>
                                    <form name="reg" action="" method="POST">

                                        <div class="form-group my-2">
    										<input type="text" name="code" id="code" autocomplete="off" maxlength="6" placeholder="Enter Verification Code" value="<?php echo $code; ?>" required autofocus style="background:<?php  echo $color; ?>;">
                                            <p class="text-light">
                                              <?php  echo $error; ?>
                                            </p>
                                            <label class="mt-2">Enter Google Authenticator Code</label>
                                        </div>
                                        <div class="d-grid gap-2">
                                            <button class="btn btn-primary" type="submit">Submit</button>
                                        </div>
                                        
                                        <button type="button" class="btn btn-primary mt-2" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                            Don't Have Code ?
                                        </button>

                                        
                                    </form>
                                </div> 
                            </div>
                        </div>
            </section>
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Reset Code</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                        In case you have deleted your google account or lost your phone try to reset your code.You will get an email with code reset link.
                  </div>
                  <div class="modal-footer">
                      <a href="admin_reset_code.php"><button type="button" class="btn btn-primary">Reset Code</button></a>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>


			<div class="site-footer">
				<div class="widget-area" align="center">
					<div class="container" align="center">
						<div class="row" align="center" style="text-align:center;">
							<div class="col-sm-12">
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
							<a href="../about.html">About us</a>
							<a href="../contact.php">Contact</a>
						</nav>

						<div class="colophon">Copyright © 2021 Niramay Healthcare. Designed by Niramay HealthCare Team. All rights reserved.</div>
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