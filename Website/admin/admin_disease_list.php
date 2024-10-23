<?php
error_reporting(0);
require('config.php');
session_start();

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
    if(isset($_GET['type']) && $_GET['type']!=''){
    	$type=$_GET['type'];
    	if($type=='delete'){
    	    $id=$_GET['id'];
            $delete_sql="delete from disease where disease_id='$id'";
            mysqli_query($conn,$delete_sql);
    	}
    }
    $sql="select * from disease order by disease_id asc";
    $res=mysqli_query($conn,$sql);
    $adminquery = "select * from user WHERE username='$admin'";
    $adminres = mysqli_query($conn,$adminquery);
    $admindata = mysqli_fetch_object($adminres);
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
                    <nav class="navbar navbar-dark bg-dark col-12" style="height:50px;">
                      <div class="" style="display:flex;">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation" style="display:flex;">
                          <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="container">
            		        <div class="col-12 col-md-12 col-sm-12 py-1" style="display:inline-block;">
                                <button type="button" 
                                   onclick="window.location.replace('./admin_disease_list.php?logout=1');"
                                    style="float:right;" class="btn btn-outline-light">Logout
                                </button>
                                <button type="button" 
                                   onclick="window.location.replace('./admin_disease_add.php');"
                                    style="float:right;" class="btn btn-outline-light">Add Disease
                                </button>
                        	</div>
            	        </div>
                      </div>
                    </nav>
                </div>
                <div class="col-12" style="padding:15px;">
        			<div class="collapse" id="navbarToggleExternalContent">
                      <div class="bg-dark p-2">
                        <a href="../admin_login.php"><p class="text-light text-left mt-2" style="font-size:25px;width:100%;">Hello Dear <?php echo $admindata->username; ?></p></a>
                      </div>
                    </div>
                </div>
			</header> <!-- .site-header -->
			
			
            <div class="content pb-0">
            	<div class="orders">
            	   <div class="row">
            		  <div class="col-xl-12 col-md-12">
            			 <div class="card">
            				<div class="card-body--">
            				   <div class="table-stats order-table ov-h" style="height:60vh;overflow-y:scroll;width:95vw;overflow-x:scroll;">
            					  <table class="table">
            						 <thead>
            							<tr>
            							   <th>ID</th>
            							   <th>Name</th>
            							   <th>Symptoms</th>
            							   <th>Modes of Transmission</th>
            						       <th>Details</th>
            							   <th>Precautions</th>
            							   <th>Test Name</th>
            							   <th>Test Price</th>
            							   <th>Test Details</th>
            							   <th>Vaccine Name</th>
            							   <th>Vaccine Price</th>
            							   <th>Action</th>
            							</tr>
            						 </thead>
            						 <tbody>
            							<?php 
            							while($row=mysqli_fetch_assoc($res)){?>
            							<tr>
            							   <td><?php echo $row['disease_id']?></td>
            							   <td><?php echo $row['disease_name']?></td>
            							   <td><?php echo $row['disease_symptoms']?></td>
            							   <td><?php echo $row['modes_of_transmission']?></td>
            							   <td><?php echo $row['disease_detail']?></td>
            							   <td><?php echo $row['disease_precaution']?></td>
            							   <td><?php echo $row['test_name']?></td>
            							   <td><?php echo $row['test_price']?></td>
            							   <td><?php echo $row['test_details']?></td>
            							   <td><?php echo $row['vaccine_name']?></td>
            							   <td><?php echo $row['vaccine_price']?></td>
            							   <td style="display: flex;flex-flow:column;">
            								<?php
            								echo "<span class='badge badge-edit'><a href='admin_disease_edit.php?id=".$row['disease_id']."'>Edit</a></span>&nbsp;";
            								
            								echo "<span class='badge badge-delete'><a href='?type=delete&id=".$row['disease_id']."'>Delete</a></span>";
            								
            								?>
            							   </td>
            							</tr>
            							<?php } ?>
            						 </tbody>
            					  </table>
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
