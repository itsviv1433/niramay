<?php
error_reporting(0);
require('config.php');
session_start();
$total_users = 15;

if($_GET){
    $logout = $_GET['logout'];
    if($logout == 1){
        unset($_SESSION['admin']); 
        session_destroy();
        header("Location:../admin_login.php");
        exit();
    }
}
if(isset($_SESSION['admin']) && isset($_SESSION['isauth'])){
    $admin = $_SESSION['admin'];
    $time = $_SESSION['time'];
    
    $user_count_query="SELECT * FROM user";
    $user_count_res=mysqli_query($conn,$user_count_query);
    $user_count = mysqli_num_rows($user_count_res);
    
    $facility_count_query="SELECT * FROM facilities";
    $facility_count_res=mysqli_query($conn,$facility_count_query);
    $facility_count = mysqli_num_rows($facility_count_res);
    
    $disease_count_query="SELECT * FROM disease";
    $disease_count_res=mysqli_query($conn,$disease_count_query);
    $disease_count = mysqli_num_rows($disease_count_res);
    
    $feedback_count_query="SELECT * FROM contactform";
    $feedback_count_res=mysqli_query($conn,$feedback_count_query);
    $feedback_count = mysqli_num_rows($feedback_count_res);

}else{
    unset($_SESSION['admin']);
    session_destroy();
    header("Location:../admin_login.php");
    exit();
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css" integrity="sha256-mmgLkCYLUQbXn0B1SRqzHar6dCnv9oZFPEC1g1cwlkk=" crossorigin="anonymous" />

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
                        <div class="container">
            		        <div class="col-12 col-md-12 col-sm-12 py-1" style="display:inline-block;">
                                <button type="button" 
                                   onclick="window.location.replace('./admin_home.php?logout=1');"
                                    style="float:right;" class="btn btn-outline-light">Logout
                                </button>
                        	</div>
            	        </div>
                      </div>
                    </nav>
                </div>
                <div class="col-12" style="padding:15px;">
        			<div class="collapse" id="navbarToggleExternalContent">
                      <div class="bg-dark p-1" style="display:flex;width:96vw;">
                        <a href="../admin_login.php" style="display:flex;"><p class="text-light text-left mt-2" style="font-size:25px;width:25vw">Hello Dear <?php echo $admin; ?></p>
                        <p class="text-right text-light mt-2" style="width:71vw;float:right;font-size:25px;padding-right:10px;" >Login Time :- <?php echo $time ?></p></a>
                      </div>
                    </div>
                </div>
			</header> <!-- .site-header -->
			
			
            <div class="content pb-0">
            	<div class="col-md-12">
                    <div class="row ">
                        <div class="col-xl-3 col-lg-6 col-md-12">
                            <div class="card l-bg-cherry" onclick="window.location.replace('./admin_user_list.php');">
                                <div class="card-statistic-3 p-4">
                                    <div class="card-icon card-icon-large"><i class="fas fa-users"></i></div>
                                    <div class="mb-4">
                                        <h5 class="card-title mb-0">Total Users</h5>
                                    </div>
                                    <div class="row align-items-center mb-2 d-flex">
                                        <div class="col-8">
                                            <h2 class="d-flex align-items-center mb-0">
                                                <?php echo $user_count; ?>
                                            </h2>
                                        </div>
                                        <div class="col-4 text-right">
                                            <span>1.2% <i class="fa fa-arrow-up"></i></span>
                                        </div>
                                    </div>
                                    <div class="progress mt-1 " data-height="8" style="height: 8px;">
                                        <div class="progress-bar l-bg-cyan" role="progressbar" data-width="100%" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo $user_count; ?>%;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-lg-6">
                            <div class="card l-bg-blue-dark" onclick="window.location.replace('./admin_facility_list.php');">
                                <div class="card-statistic-3 p-4"> 
                                    <div class="card-icon card-icon-large"><i class="fas fa-building"></i></div>
                                    <div class="mb-4">
                                        <h5 class="card-title mb-0">Total Facilities</h5>
                                    </div>
                                    <div class="row align-items-center mb-2 d-flex">
                                        <div class="col-8">
                                            <h2 class="d-flex align-items-center mb-0">
                                                <?php echo $facility_count; ?>
                                            </h2>
                                        </div>
                                        <div class="col-4 text-right">
                                            <span>9.23% <i class="fa fa-arrow-up"></i></span>
                                        </div>
                                    </div>
                                    <div class="progress mt-1 " data-height="8" style="height: 8px;">
                                        <div class="progress-bar l-bg-green" role="progressbar" data-width="100%" aria-valuemin="0" aria-valuemax="100" style="width:<?php echo $facility_count; ?>%;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-lg-6">
                            <div class="card l-bg-green-dark" onclick="window.location.replace('./admin_disease_list.php');">
                                <div class="card-statistic-3 p-4">
                                    <div class="card-icon card-icon-large"><i class="fas fa-syringe"></i></div>
                                    <div class="mb-4">
                                        <h5 class="card-title mb-0">Total Disease</h5>
                                    </div>
                                    <div class="row align-items-center mb-2 d-flex">
                                        <div class="col-8">
                                            <h2 class="d-flex align-items-center mb-0">
                                               <?php echo $disease_count; ?>
                                            </h2>
                                        </div>
                                        <div class="col-4 text-right">
                                            <span>10% <i class="fa fa-arrow-up"></i></span>
                                        </div>
                                    </div>
                                    <div class="progress mt-1 " data-height="8" style="height: 8px;">
                                        <div class="progress-bar l-bg-orange" role="progressbar" data-width="100%" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo $disease_count; ?>%;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-lg-6">
                            <div class="card l-bg-orange-dark" onclick="window.location.replace('./admin_feedback_list.php');">
                                <div class="card-statistic-3 p-4">
                                    <div class="card-icon card-icon-large"><i class="fas fa-comments"></i></div>
                                    <div class="mb-4">
                                        <h5 class="card-title mb-0">Total Feedbacks</h5>
                                    </div>
                                    <div class="row align-items-center mb-2 d-flex">
                                        <div class="col-8">
                                            <h2 class="d-flex align-items-center mb-0">
                                                <?php echo $feedback_count; ?>
                                            </h2>
                                        </div>
                                        <div class="col-4 text-right">
                                            <span>2.5% <i class="fa fa-arrow-up"></i></span>
                                        </div>
                                    </div>
                                    <div class="progress mt-1 " data-height="8" style="height: 8px;">
                                        <div class="progress-bar l-bg-cyan" role="progressbar" data-width="100%" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo $feedback_count; ?>%;"></div>
                                    </div>
                                </div>
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
    							<a href="about.html">About us</a>
    							<a href="contact.php">Contact</a>
    						</nav>
    						<div class="colophon">Copyright © 2021 Niramay Healthcare. Designed by Niramay HealthCare Team. All Rights Reserved.</div>
    					</div>
    			    </div>
			    </div>
		    </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
        
        <style>
            .card {
                background-color: #fff;
                border-radius: 10px;
                border: none;
                position: relative;
                margin-bottom: 30px;
                box-shadow: 0 0.46875rem 2.1875rem rgba(90,97,105,0.1), 0 0.9375rem 1.40625rem rgba(90,97,105,0.1), 0 0.25rem 0.53125rem rgba(90,97,105,0.12), 0 0.125rem 0.1875rem rgba(90,97,105,0.1);
            }
            .l-bg-cherry {
                background: linear-gradient(to right, #493240, #f09) !important;
                color: #fff;
            }
            
            .l-bg-blue-dark {
                background: linear-gradient(to right, #373b44, #4286f4) !important;
                color: #fff;
            }
            
            .l-bg-green-dark {
                background: linear-gradient(to right, #0a504a, #38ef7d) !important;
                color: #fff;
            }
            
            .l-bg-orange-dark {
                background: linear-gradient(to right, #a86008, #ffba56) !important;
                color: #fff;
            }
            
            .card .card-statistic-3 .card-icon-large .fas, .card .card-statistic-3 .card-icon-large .far, .card .card-statistic-3 .card-icon-large .fab, .card .card-statistic-3 .card-icon-large .fal {
                font-size: 110px;
            }
            
            .card .card-statistic-3 .card-icon {
                text-align: center;
                line-height: 50px;
                margin-left: 15px;
                color: #000;
                position: absolute;
                right: -5px;
                top: 20px;
                opacity: 0.1;
            }
            
            .l-bg-cyan {
                background: linear-gradient(135deg, #289cf5, #84c0ec) !important;
                color: #fff;
            }
            
            .l-bg-green {
                background: linear-gradient(135deg, #23bdb8 0%, #43e794 100%) !important;
                color: #fff;
            }
            
            .l-bg-orange {
                background: linear-gradient(to right, #f9900e, #ffba56) !important;
                color: #fff;
            }
            
            .l-bg-cyan {
                background: linear-gradient(135deg, #289cf5, #84c0ec) !important;
                color: #fff;
            }
        </style>
    </body>
</html>
