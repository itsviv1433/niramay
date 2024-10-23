<?php
include('config.php');
// isAdmin();
error_reporting(0);

$name = '';
$username = '';
$email = '';
$phone = '';
$password = '';
$rpassword = '';
$checkedm = "";
$checkedf= "";
$checkedo = "";
$city = "";

$msg = '';

if($_GET) {
    $username = $_GET['username'];
    $getdataquery = "select * from `user` where `username`='$username'";
    $res = mysqli_query($conn, $getdataquery);
    $data = mysqli_fetch_object($res);
    
    $getcitiesquery = "SELECT `cities` FROM `facilities`";
    $getcitiesresult = mysqli_query($conn, $getcitiesquery);


    $name = $data->name;
    $username = $data->username;
    $email = $data->email;
    $phone = $data->phone;
    $weight = $data->weight;
    $height = $data->height;
    $gender = $data->gender;
    $age = $data->age;
    $city = $data->city;
    $password = $data->password;
    $rpassword = $data->password;
    switch($gender){
        case 1: $checkedm = "checked";break;
        case 2: $checkedf = "checked";break;
        case 3: $checkedo = "checked";break;
    }
    
}
if(isset($_POST['submit'])) {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone =  $_POST['phone'];
    $city = $_POST['city'];
    $weight =  $_POST['weight'];
    $height =  $_POST['height'];
    $age =  $_POST['age'];
    $gender = $_POST['gender'];
    $password = $_POST['password'];
    $rpassword = $_POST['rpassword'];
    // $msg = $_POST['name'];
    if (strlen($phone) == 10) {
        if (strlen($password) > 6) {
            if ($password == $rpassword) {
                if($gender > 0 && $gender < 4){
                    if(strlen($weight) < 4 && strlen($height) < 4 && strlen($age) < 4){
                        $update_sql = "update user set name='$name',email='$email',phone='$phone',weight='$weight',height='$height',age='$age',gender='$gender',city='$city',password='$password' where username='$username'";
                        $res = mysqli_query($conn, $update_sql);
                        if($res){   
                            session_start();
                            $_SESSION['status']="Active";      
            				header("Location:./user.php?username=$username");
            				exit();
                        }else{
                            $msg = "Something went wrong";
                        }
                    }else{
                        $msg = "Invalid Inputs"; 
                    }
                }else{
                    $msg = "Please select gender";
                }
            } else {
                $msg = "Password does not match";
            }
        } else {
            $msg = "Password is too small";
        }
    } else {
        $msg = "Enter Valid Contact";
    }
}
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Profile | <?php echo $name ?></title>
		<link rel = "icon" href ="https://cdn-icons.flaticon.com/png/512/1979/premium/1979774.png?token=exp=1637321250~hmac=85e8787b24e4ca44db4d13eb2d2cb33e" type = "image/x-icon">

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto+Condensed:300,400,700|" rel="stylesheet" type="text/css">
		<link href="admin/fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="admin/fonts/lineo-icon/style.css" rel="stylesheet" type="text/css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="style.css">
		<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
		
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
         	<div class="content pb-0">
                <div class="animated fadeIn">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-header"><strong>Edit Profile User :- <?php echo $username; ?></strong><small> </small></div>
                                <form method="POST" enctype="multipart/form-data" action="">
                                    <div class="card-body card-block">
            
                                        <div class="form-group">
                                            <label for="name" class=" form-control-label">Name</label>
                                            <input type="text" name="name" placeholder="Enter Name" class="form-control" required value="<?php echo $name ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="username" class=" form-control-label">Username</label>
                                            <input type="text" name="username" placeholder="Username" class="form-control" required value="<?php echo $username ?>" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="email" class=" form-control-label">Email</label>
                                            <input type="email" name="email" placeholder="Enter Email" class="form-control" required maxlength="30" value="<?php echo $email ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Mobile</label>
                                            <input type="number" name="phone" placeholder="Enter Mobile" class="form-control" required maxlength="10" value="<?php echo $phone ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="weight" class=" form-control-label">City</label>
                                            <select class="form-control" name="city">
                                                <?php 
                                                    $i=0;
                                                    foreach($getcitiesresult as $key => $value){ 
                                                        if($value['cities'] != ""){ ?>
                                                            <option value="<?php echo $key;?>" <?php if($city == $i){echo "selected";} ?>><?php echo $value['cities']; ?></option> 
                                                <?php   } $i++;
                                                    } ?>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="weight" class=" form-control-label">Weight</label>
                                            <input type="number" name="weight" placeholder="Enter Weight in Kgs" class="form-control" required maxlength="3" value="<?php echo $weight ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="height" class=" form-control-label">Height</label>
                                            <input type="number" name="height" placeholder="Enter Height in cms" class="form-control" required maxlength="3" value="<?php echo $height ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="age" class=" form-control-label">Age</label>
                                            <input type="number" name="age" placeholder="Enter Age in Years" class="form-control" required maxlength="2" value="<?php echo $age ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="gender" class=" form-control-label">Gender</label><br>
                                            <input type="radio" id="male" name="gender" value="1" <?php echo $checkedm ?>>
                                            <label for="male">Male</label>
                                            <input type="radio" id="female" name="gender" value="2" <?php echo $checkedf ?>>
                                            <label for="female">Female</label>
                                            <input type="radio" id="other" name="gender" value="3" <?php echo $checkedo ?>>
                                            <label for="other">Other</label>
                                        </div>
                                        <div class="form-group">
                                            <label for="password" class=" form-control-label">Password</label>
                                            <div style="display: flex;padding-right:5px;align-items:center">
                                                <input type="password" id="password" name="password" placeholder="Enter Password" class="form-control" required maxlength="16" value="<?php echo $password ?>">
                                                <i style="margin-left:5px;cursor:pointer;" class="far fa-eye" id="togglePassword"></i>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="password" class=" form-control-label">Re-enter Password</label>
                                            <div style="display: flex;padding-right:5px;align-items:center">
                                                <input type="password" id="rpassword" name="rpassword" placeholder="Enter Password Again" class="form-control" required maxlength="16" value="<?php echo $rpassword ?>">
                                                <i style="margin-left:5px;cursor:pointer;" class="far fa-eye" id="togglerPassword"></i>
                                            </div>
                                        </div>
                                        
                                        <div class="field_error mt-2 text-danger" ><?php echo $msg ?></div>
            
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
         	<p style="color:black;font-family:Georgia, serif;margin-top:5px;text-align:center;">*For more details please download and login to our official<br> 
            <a href="#">Niramay Healthcare Android Application</a>.</p>
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
