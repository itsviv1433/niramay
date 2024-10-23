<?php
//error_reporting(0);
require('config.php');
session_start();
$admin_user = $_SESSION['admin'];

$disease_name = '';
$disease_symptoms = '';
$disease_mot = '';
$disease_detail = '';
$disease_precaution = '';
$test_name = '';
$test_price = '';
$test_details = '';
$vaccine_name = '';
$vaccine_price = '';
$id = ''; 
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
    $getdataquery = "select * from `disease` where `disease_id`='$id'";
    $res = mysqli_query($conn, $getdataquery);
    $data = mysqli_fetch_object($res);
    
    $getcitiesquery = "SELECT `cities` FROM `facilities`";
    $getcitiesresult = mysqli_query($conn, $getcitiesquery);


    $disease_name = $data->disease_name;
    $disease_symptoms = $data->disease_symptoms;
    $disease_mot = $data->modes_of_transmission;
    $disease_detail = $data->disease_detail;
    $disease_precaution = $data->disease_precaution;
    $test_name = $data->test_name;
    $test_price = $data->test_price;
    $test_details = $data->test_details;
    $vaccine_name = $data->vaccine_name;
    $vaccine_price = $data->vaccine_price;
    
}
if(isset($_POST['submit'])) {
    $disease_name = $_POST['disease_name'];
    $disease_symptoms = $_POST['disease_symptoms'];
    $disease_mot =  $_POST['modes_of_transmission'];
    $disease_detail = $_POST['disease_detail'];
    $disease_precaution = $_POST['disease_precaution'];
    $test_name = $_POST['test_name'];
    $test_price = $_POST['test_price'];
    $test_details = $_POST['test_details'];
    $vaccine_name = $_POST['vaccine_name'];
    $vaccine_price = $_POST['vaccine_price'];
    

    $update_sql = "update `disease` set disease_name='$disease_name',disease_symptoms='$disease_symptoms',modes_of_transmission='$disease_mot',disease_detail='$disease_detail',disease_precaution='$disease_precaution',test_name='$test_name',test_price='$test_price',test_details='$test_details',vaccine_name='$vaccine_name',vaccine_price='$vaccine_price' where disease_id=$id";
    $res = mysqli_query($conn,$update_sql);
    if($res){   
    	header("Location:./admin_disease_list.php");
        exit();
    }else{
        $msg = "Something went wrong";
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
                                <div class="card-header"><strong>Edit Disease :- <?php echo $disease_name ?></strong><small> </small></div>
                                <form method="POST" enctype="multipart/form-data" action="">
                                    <div class="card-body card-block">
            
                                        <div class="form-group">
                                            <label for="name" class=" form-control-label">Disease Name</label>
                                            <input type="text" name="disease_name" placeholder="Enter Disease Name" class="form-control" required value="<?php echo $disease_name ?>">
                                        </div>
                                        <div class="form-group">
                                            <label for="username" class=" form-control-label">Symptoms</label>
                                            <textarea type="text" name="disease_symptoms" placeholder="Enter Symptoms" class="form-control" required rows="8"><?php echo $disease_symptoms ?></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="email" class=" form-control-label">Modes of Transmission</label>
                                            <textarea type="text" name="modes_of_transmission" placeholder="Enter Modes of Transmission" class="form-control" required rows="8"><?php echo $disease_mot ?></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Disease Details</label>
                                            <textarea type="text" name="disease_detail" placeholder="Enter Details" class="form-control" required rows="8"><?php echo $disease_detail ?></textarea>
                                        </div>
                                       <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Precautions</label>
                                            <textarea type="text" name="disease_precaution" placeholder="Enter Precautions" class="form-control" required rows="8"><?php echo $disease_precaution ?></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Test Name</label>
                                            <textarea type="text" name="test_name" placeholder="Enter Test Name" class="form-control" required rows="1"><?php echo $test_name ?></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Test Price</label>
                                            <textarea type="number" name="test_price" placeholder="Enter Test Price" class="form-control" required rows="1"><?php echo $test_price ?></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Test Details</label>
                                            <textarea type="text" name="test_details" placeholder="Enter Details" class="form-control" required rows="4"><?php echo $test_details ?></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Vaccine Name</label>
                                            <textarea type="text" name="vaccine_name" placeholder="Enter Vaccine Name" class="form-control" required rows="2"><?php echo $vaccine_name ?></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class=" form-control-label">Vaccine Price</label>
                                            <textarea type="text" name="vaccine_price" placeholder="Enter Vaccine Price" class="form-control" required rows="1"><?php echo $vaccine_price ?></textarea>
                                        </div>
                                        
                                        <div class="field_error mt-2 text-danger" ><?php echo $msg ?></div>
            
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
