<?php

	include("config.php");
    
    
    // $name = "Vivek Mahajan";
    // $email = "itsviv1433@gmail.com";
    // $username = "itsviv";
    // $password = "itsviv1433";
    
    $path = "../images/niramay_logo.png";
    
    $query = "SELECT * FROM user WHERE user_id = (SELECT max(user_id) FROM user)";
    $result = mysqli_query($conn, $query);
    $userData = mysqli_fetch_object($result);

    $name = $userData->name;
    $email = $userData->email;
    $username = $userData->username;
    $password = $userData->password;  
    
    // echo json_encode($userData);
    include('send_signup_email.php');  

?>