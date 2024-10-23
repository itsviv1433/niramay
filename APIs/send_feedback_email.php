<?php

	include("config.php");

    // $name = $_POST['name'];
    // $email = $_POST['email'];
    // $phone = $_POST['phone'];
    // $usernmae = $_POST['username'];
    // $subject = $_POST['subject'];
    // $message = $_POST['message'];


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
    $mail->Host = "smtp.gmail.com"; // use $mail->Host = gethostbyname('smtp.gmail.com'); // if your network does not support SMTP over IPv6
    $mail->Port = 587; // TLS only
    $mail->SMTPSecure = 'tls'; // ssl is deprecate
    $mail->SMTPAuth = true;
    $mail->Username = ''; // email
    $mail->Password = ''; // password
    $mail->setFrom('',$name); // From email and name
    $mail->addAddress('','Niramay HealthCare Team'); // to email and name
    $mail->Subject = $subject;
    $mail->msgHTML("I am $name.<br>My Contact is : $phone.<br>My Email is : $email.<br>$message"); //$mail->msgHTML(file_get_contents('contents.html'), __DIR__); //Read an HTML message body from an external file, convert referenced images to embedded,
    $mail->AltBody = 'HTML messaging not supported'; // If html emails is not supported by the receiver, show this body
    // $mail->addAttachment('images/phpmailer_mini.png'); //Attach an image file
    $mail->SMTPOptions = array(
                        'ssl' => array(
                            'verify_peer' => false,
                            'verify_peer_name' => false,
                            'allow_self_signed' => true
                        )
                    );
    if(!$mail->send()){
        echo json_encode(array('status' => "error",'Error' => $mail->ErrorInfo));
       // echo "Mailer Error: " . $mail->ErrorInfo;
    }else{
        echo json_encode(array('status' => "sent"));
    }
?>