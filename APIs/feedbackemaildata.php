
<?php

	include("config.php");
	$username = $_POST['username'];
    $subject = $_POST['subject'];
    $message = $_POST['message'];
    
// 	$username = "itsviv";
//     $subject = "test";
//     $message = "test";
    

    $query = "SELECT * FROM `user` WHERE `username` = '$username'";
    $result = mysqli_query($conn, $query);
    $userData = mysqli_fetch_object($result);
    $name = $userData->name;
    $email = $userData->email;
    $phone = $userData->phone;  
    
    if($subject == "" && $message == ""){
        include("send_appriciation_email.php");
    }else {
        include("send_feedback_email.php");
    }
   
    mysqli_close($conn);
?>
