<?php
error_reporting(0);
require('config.php');
session_start();
$admin_user = $_SESSION['admin'];

$name = '';
$username = '';
$email = '';
$phone = '';
$password = '';
$rpassword = '';
$admin='';
$user='';
$isadmin='';

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
    $getdataquery = "select * from `user` where `user_id`='$id'";
    $res = mysqli_query($conn, $getdataquery);
    $data = mysqli_fetch_object($res);
    
    $getcitiesquery = "SELECT `cities` FROM `facilities`";
    $getcitiesresult = mysqli_query($conn, $getcitiesquery);


    $name = $data->name;
    $username = $data->username;
    $email = $data->email;
    $phone = $data->phone;
    $password = $data->password;
    $rpassword = $data->password;
    $isadminflag = $data->isadmin;
    switch($isadminflag){
        case 0 : $user='checked';break;
        case 1 : $admin='checked';break;
    }
    
    
}
if(isset($_POST['submit'])) {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone =  $_POST['phone'];
    $password = $_POST['password'];
    $rpassword = $_POST['rpassword'];
    $isadmin = $_POST['isadmin'];
    if (strlen($phone) == 10) {
        if (strlen($password) > 6) {
            if ($password == $rpassword) {
                $update_sql = "update user set name='$name',email='$email',phone='$phone',password='$password',isadmin='$isadmin' where user_id='$id'"; 
                $res = mysqli_query($conn, $update_sql);
                if(!$isadmin){
                    $update_auth = "update user set isauth='0' where user_id='".$id."'";
                    $res_auth = mysqli_query($conn, $update_auth);
                }
                if($res){   
            		header("Location:./admin_user_list.php");
            		exit();
                }else{
                    $msg = "Something went wrong";
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
                     <!--   <div class="container">-->
            		       <!-- <div class="col-12 col-md-12 col-sm-12 py-1" style="display:inline-block;">-->
                     <!--           <button type="button" -->
                     <!--              onclick="window.location.replace('https://niramayhealthcare.000webhostapp.com/website/admin_login.php');"-->
                     <!--               style="float:right;margin-right:-7vw;" class="btn btn-outline-light">Logout-->
                     <!--           </button>-->
                     <!--   	</div>-->
            	        <!--</div>-->
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
                                        <input type="radio" id="admin" name="isadmin" value="1" <?php echo $admin ?>>
                                        <label for="male">Admin</label>
                                        <input type="radio" id="user" name="isadmin" value="0" <?php echo $user ?>>
                                        <label for="female">User</label>
                                        
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
