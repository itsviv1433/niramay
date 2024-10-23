<?php
    include('config.php');
	   $name = trim(stripslashes($_POST['user_name']));
	   $email = trim(stripslashes($_POST['user_email']));
	   $phone = trim(stripslashes($_POST['user_phone']));
	   $city = trim(stripslashes($_POST['user_city']));
	   $weight = trim(stripslashes($_POST['user_weight']));
	   $height = trim(stripslashes($_POST['user_height']));
	   $age = trim(stripslashes($_POST['user_age']));
	   $gender = trim(stripslashes($_POST['user_gender']));
	   $disease = trim(stripslashes($_POST['user_disease']));

	    $query1 = "UPDATE `user` SET `name`='$name',`email`='$email',`phone`='$phone',`city`='$city',`weight`='$weight',`height`='$height',`age`='$age',`gender`='$gender',`disease`='$disease' WHERE `username` = 'itsviv'";
	   	$result1 = mysqli_query($conn, $query1);
	   	if($result1){
	  
	   	    header("Location:../user.php");
	   	}else{
	   	   echo "error";
	   	}
?>