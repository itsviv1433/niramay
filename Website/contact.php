<?php
    include("config.php");
	use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\Exception;
    
    require './mail/Exception.php';
    require './mail/PHPMailer.php';
    require './mail/SMTP.php';
    $contact_name = '';
    $contact_email = '';
    $contact_subject = '';
    $contact_message = '';

if($_POST) {

  $contact_name = trim(stripslashes($_POST['Name']));
  $contact_email = trim(stripslashes($_POST['Email']));
  $contact_subject = trim(stripslashes($_POST['Subject']));
  $contact_message = trim(stripslashes($_POST['Message']));
  $error['name'] = "";
  $error['email'] = "";
  $error['message'] = "";    

   //Check Name
	if (strlen($contact_name) < 2) {
		$error['name'] = "Please enter your name.";
	}
	// Check Email
	if (!preg_match('/^[a-z0-9&\'\.\-_\+]+@[a-z0-9\-]+\.([a-z0-9\-]+\.)*+[a-z]{2}/is', $contact_email)) {
		$error['email'] = "Please enter a valid email address.";
	}
	// Check Message
	if (strlen($contact_message) < 15) {
		$error['message'] = "Please enter your message. It should have at least 15 characters.";
	}
   // Subject
	if ($contact_subject == '') { $contact_subject = "Contact Form Submission From Niramay Healthcare Website"; }

   // Set Message
   $message = "Email from: $contact_name <br> Email address: $contact_email <br>Message: <br>$contact_message<br> --------------------- <br> This email was sent from niramay healthcare website contact form niramayhealthcare.ml";

   error_reporting(E_ERROR | E_PARSE);
   if ($error['name'] == "" && $error['email'] == "" && $error['message'] == "") {
   
        $mail = new PHPMailer;
        $mail->isSMTP(); 
        $mail->SMTPDebug = false; // 0 = off (for production use) - 1 = client messages - 2 = client and server messages
        //$mail->Host = gethostbyname('smtp.gmail.com');
        $mail->do_debug = 0;
        $mail->Host = "smtp.gmail.com";  // use $mail->Host = gethostbyname('smtp.gmail.com'); // if your network does not                                                                      
        //support SMTP over IPv6
        $mail->Port = 587; // TLS only
        $mail->SMTPSecure = 'tls';                                               // ssl is deprecate
        $mail->SMTPAuth = true;
        $mail->Username = 'email'; // email
        $mail->Password = 'password'; // password
        $mail->setFrom($contact_email,$contact_name); // From email and name
        $mail->addAddress("team@gmail.com","Niramay HealthCare Team");                                        
        // to email and name
        $mail->Subject ="$contact_subject";
        $mail->msgHTML($message); 
        //$mail->msgHTML(file_get_contents('contents.html'), __DIR__); //Read an HTML message body from an external file, convert referenced images to embedded,
        $mail->AltBody = 'HTML messaging not supported'; // If html emails is not supported by the receiver, show this body
        // $mail->addAttachment('images/phpmailer_mini.png'); //Attach an image file
        $mail->SMTPOptions = array(
                            'ssl' => array(
                                'verify_peer' => false,
                                'verify_peer_name' => false,
                                'allow_self_signed' => true
                            )
                        );
    
		if ($mail->send()) { 
		    date_default_timezone_set("Asia/Kolkata");
            $full_date = date("l jS \of F Y h:i:s");
		    $query = "INSERT INTO `contactform`(`name`, `email`, `subject`, `message`, `timestamp`) VALUES ('$contact_name','$contact_email','$contact_subject','$contact_message','$full_date')";
		    $result = mysqli_query($conn,$query);
		  //  if($result){
		        echo "<script>alert('Message Sent.');</script>"; 
		  //  }else{
		  //      echo "<script>alert('Something Went Wrong.Please Try Again Later.');</script>";
		  //  }
		}else { 
		    echo "<script>alert('Something Went Wrong.Please Try Again Later...');</script>";
		}
		
	} # end if - no validation error

	else {
	    $error_var = $error['name'].'\n'.$error['email'].'\n'.$error['message'];
		echo "<script>alert('$error_var');</script>";

	} # end if - there was a validation error
}
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Contact Us | Niramay Healthcare</title>
		<link rel = "icon" href ="https://cdn-icons.flaticon.com/png/512/1979/premium/1979774.png?token=exp=1637321250~hmac=85e8787b24e4ca44db4d13eb2d2cb33e" type = "image/x-icon">

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400,700|" rel="stylesheet" type="text/css">
		<link href="admin/fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="admin/fonts/lineo-icon/style.css" rel="stylesheet" type="text/css">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="style.css">
		
		<!--[if lt IE 9]>
		<script src="js/ie-support/html5.js"></script>
		<script src="js/ie-support/respond.js"></script>
		<![endif]-->
	<script language="JavaScript" Type="text/javascript" >
	    const form = document.getElementById("form");
	   // document.addEventListener('contextmenu', event => event.preventDefault());
	    form.addEventListener('submit', function (e) { 
             e.preventDefault(); 
        }, false);
	</script>

	</head>


	<body>
		
		<div id="site-content">
			<header class="site-header">
				<div class="top-header">
					<div class="container">
						<a href="index.html" id="branding">
							<img src="admin/images/logo.png" alt="Niramay Healthcare" class="logo">
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
								<li class="menu-item"><a href="user_login.php">User Login</a></li>
								<li class="menu-item"><a href="index.html">Home</a></li>
								<li class="menu-item"><a href="about.html">About Us</a></li>
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

			<main class="main-content">
				<div class="breadcrumbs">
					<div class="container">
						<a href="index.html">Home</a>
						<span>Contact</span>
					</div>
				</div>

				<div class="page">
					<div class="container">
						<!--<div class="map"></div>-->

						<div class="row">
							<div class="col-md-3">
								<h2 class="section-title text-left">Address</h2>

								<div class="contact-detail">
									<address>
										<p>Project Done by Students of <br>Government Polytechnic<br> Jalgaon</p>
										<br>
										<p>+91 1234567890 <br>healthcare.niramay@gmail.com</p>
									</address>
								</div>
							</div>
							<div class="col-md-9">
								<h2 class="section-title text-left">Left a Message for Us...</h2>
								<form action="" method="post" class="contact-form" id="form">
									<div class="row">
										<div class="col-md-4"><input type="text" name="Name" placeholder="Your name..." required value="<?php echo $contact_name; ?>"></div>
										<div class="col-md-4"><input type="text" name="Email" placeholder="Email..." required value="<?php echo $contact_email; ?>"></div>
										<div class="col-md-4"><input type="text" name="Subject" placeholder="Subject..." value="<?php echo $contact_subject; ?>"></div>
									</div>

									<textarea  name="Message" placeholder="Message..." required><?php echo $contact_message ?></textarea>

									<p class="text-right">
										<input type="submit" value="Send message">
									</p>
								</form>


							</div>
						</div>
					</div>
				</div> <!-- .page -->
			</main>

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