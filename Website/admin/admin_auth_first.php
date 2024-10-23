<?php
    include('config.php');
    error_reporting(0);
    session_start();
    $error = '';
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
            session_start();
            $user 	= $_SESSION['admin'];
            $_SESSION['time'] = "$full_date";
            $update_sql = "update user set isauth='1' where username='".$user."'";
            $res = mysqli_query($conn, $update_sql);
            if($res){
                $_SESSION['isauth']="Auth";
            	header("Location:./admin_home.php");
                exit();
            }else{
                $error = 'Something Went Wrong';
            }
        }
        else{
            $color = '#FFBEBE';
        	$error = 'Incorrect or expired code!';
        }
    }
    else{
        if(isset($_SESSION['admin'])){
            $user = $_SESSION['admin'];
            $qrCodeUrl 	= $g->getURL($user,'www.niramayhealthcare.ml',$secret);
    
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
        
        <!--Bootstrap Js-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

		<!-- main css file -->
		<link rel="stylesheet" href="style.css">
		
	<script language="JavaScript" Type="text/javascript" >
	    document.addEventListener('contextmenu', event => event.preventDefault());
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
                            <p style="padding-left:10px;">Scan the below QR Code and Enter the verification code generated by Google Authenticator app on your phone.</p>
                            <div class="form-area auth-area bg-dark" style="display:flex;border-radius:10px;">
                                <!--<div class="text-light auth-img" style="padding-top:25px;padding-left:10px;width:50%;display:block;">-->
                                    
                                <!--</div>-->
                                <div class="form-input code-auth text-light" style="background-image: url('https://image.freepik.com/free-photo/key-lock-password-security-privacy-protection-graphic_53876-121252.jpg');padding:20px;border-radius:10px;">
                                    <h2>Scan Code</h2>
                                    <form name="reg" action="" method="POST">
    
                                        <div class="form-group">
    										<img src='<?php echo $qrCodeUrl; ?>'/>
                                        </div>
    
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
                                          Help
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
            </section>
            
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">How to generate verification code ?</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    First of all, install the Google Authenticator app from the Play/App Store on your new phone. When opening the application for the first time, tap on “Get started” to skip the introduction. Next up, Google Authenticator will ask you to set up your first account. This can be done either by scanning a setup code or entering the setup key provided on the service’s settings page. In order to set up 2FA for your Google account, head over to this account settings page. Having added your account, Google Authenticator will guide you to the app’s home screen.<br>
                        On the home screen, you’ll find a list of all the accounts added and the two-factor authentication passcode beneath. The animated circle next to it shows you how much time is left before the code expires. To add another account, tap the floating plus button on the very bottom of your screen and choose whether to scan a setup code or manually enter the 2FA setup key.
                  </div>
                  <div class="modal-footer">
                      <h3>Get Google Authenticator</h3>
                      <a href="https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2&hl=en_IN&gl=US" target="_blank"><button type="button" class="btn btn-primary">Play Store</button></a>
                      <a href="https://apps.apple.com/us/app/google-authenticator/id388497605" target="_blank"><button type="button" class="btn btn-primary">App Store</button></a>
                    <!--<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>-->
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