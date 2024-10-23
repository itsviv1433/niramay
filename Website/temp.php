<?php

	include("config.php");
	include("emailData.php");

    // $name = "Vivek Mahajan";
    // $email = "itsviv1433@gmail.com";
    // $username = "itsviv09";
    // $password = "itsviv1433";
    
    use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\Exception;
    
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/Exception.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/PHPMailer.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/SMTP.php';
    
    $mail = new PHPMailer;
    $mail->isSMTP(); 
    $mail->SMTPDebug = false; // 0 = off (for production use) - 1 = client messages - 2 = client and server messages
    //$mail->Host = gethostbyname('smtp.gmail.com');
    $mail->do_debug = 0;
    $mail->Host = "smtp.gmail.com";  // use $mail->Host = gethostbyname('smtp.gmail.com'); // if your network does not                                                                      support SMTP over IPv6
    $mail->Port = 587; // TLS only
    $mail->SMTPSecure = 'tls';                                               // ssl is deprecate
    $mail->SMTPAuth = true;
    $mail->Username = 'healthcare.niramay@gmail.com'; // email
    $mail->Password = 'healthcare.niramay@01042021'; // password
    $mail->setFrom('healthcare.niramay@gmail.com','Niramay HealthCare Team'); // From email and name
    $mail->addReplyTo('healthcare.niramay@gmail.com','Niramay HealthCare Team'); 
    $mail->addAddress($email,$name);                                        // to email and name
    $mail->Subject ="Next Step of $name to Us...";
    $mail->Body = file_get_contents('https://niramayhealthcare.000webhostapp.com/emailTemplate.php');
        // $mail->Body = "test";
    $mail->AltBody = 'HTML messaging not supported';
    $mail->SMTPOptions = array(
                        'ssl' => array(
                            'verify_peer' => false,
                            'verify_peer_name' => false,
                            'allow_self_signed' => true
                        )
                    );
    $mail->send();
    if(!$mail->send()){
        echo json_encode(array('status' => "error",'Error' => $mail->ErrorInfo));
    }else{
        echo json_encode(array('status' => "sent"));
    }
?>