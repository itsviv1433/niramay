<?php

	include("config.php");
	session_start();
    $admin_username = $_SESSION['admin'];
    
    $getadminquery = "SELECT * FROM `user` WHERE `username`='$admin_username'";
    $getadminresult = mysqli_query($conn,$getadminquery);
    $admindata = mysqli_fetch_object($getadminresult);
    $name = $admindata->name;
    $email = $admindata->email;
    $subject = "Reset Verification Code";
    
    use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\Exception;
    
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/Exception.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/PHPMailer.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/SMTP.php';
    
    $mail = new PHPMailer;
    $mail->isSMTP(); 
    $mail->SMTPDebug = false;
    $mail->do_debug = 0;
    $mail->Host = "smtp.gmail.com"; 
    $mail->Port = 587;
    $mail->SMTPSecure = 'tls'; 
    $mail->SMTPAuth = true;
    $mail->Username = 'email'; 
    $mail->Password = 'password';
    $mail->setFrom('team@gmail.com','Niramay Healthcare Team');
    $mail->addAddress($email,$name);
    $mail->Subject = $subject;
    $mail->Body = file_get_contents('reset_code_template.php');
    $mail->AltBody = 'HTML messaging not supported';
    $mail->SMTPOptions = array(
                        'ssl' => array(
                            'verify_peer' => false,
                            'verify_peer_name' => false,
                            'allow_self_signed' => true
                        )
                    );
    if(!$mail->send()){
        function_alert1("Something Went Wrong.Please Try Again Later.");
        echo "<script>window.location.replace('../admin_login.php');</script>";
    }else{
        function_alert1("Code Sent Successful to the Registered Email Address.Kindly Check Your Email.");
        echo "<script>window.location.replace('../admin_login.php');</script>";
    }
    function function_alert1($message) {
    // Display the alert box 
    	echo "<script>alert('$message');</script>";
	}
?>