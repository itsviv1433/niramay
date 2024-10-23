<?php

	include("config.php");
        
    // $name = "Vivek";
    // $email = "itsviv1433@gmail.com";
    // $phone = "7219276033";
    // $subject = "Test Email";
    // $message = "This is testing email template.";

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
    $mail->Username = 'email'; // email
    $mail->Password = 'password'; // password
    $mail->setFrom('team@gmail.com','Niramay HealthCare Team'); // From email and name
    $mail->addReplyTo('teammail','Niramay HealthCare Team'); 
    $mail->addAddress($email,$name);                                        // to email and name
    $mail->Subject ="About Feedback Appreciation to $name";
    $mail->msgHTML("Dear $name,<br><br> Your Feedback is Received Successfully to Niramay HealthCare.<br>
                Thank you so much for sharing your experience with us.We really appreciate it.<br>
                Let us know about your future experiences.<br><br>
                If you have any queries or concerns feel free to contact us at...<br>
                Our Email : healthcare.niramay@gmail.com.<br>
                Our Instagram Handle : https://instagram.com/itz_viv_1433?igshid=vqf9krhjivh6 <br><br>
<font size = 3> Our Developers Contact :</font><br>
                Vivek Mahajan : itsviv1433@gmail.com<br>
                Rashi Patil : rashirpatil1968@gmail.com<br><br>
                Best Regards,<br>NIRAMAY HEALTHCARE"); //$mail->msgHTML(file_get_contents('contents.html'), __DIR__); //Read an HTML message body from an external file, convert referenced images to embedded,
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
        echo json_encode(array('status' => "appriciationsent"));
    }
?>