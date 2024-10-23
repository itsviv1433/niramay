<?php
    error_reporting(0);
    
    use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\Exception;
    
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/Exception.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/PHPMailer.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/SMTP.php';
    
    
    $body = "Dear ".$name.",<br>Thanks for joining us at Niramay HealthCare.We are glad you are here.Health and Wellness is about keeping your body fit and healthy.NIRAMAY is a healthcare app with health information that help you to stay fit and updated about various diseases and their symptoms.<br>So,Lets Start.....! 
        <br><b><center>Your Username : ".$username."<br>Your Password : ".$password."</center></b>Thank You!!!";
    
    $mail = new PHPMailer;
    $mail->isSMTP(); 
    $mail->SMTPDebug = false; // 0 = off (for production use) - 1 = client messages - 2 = client and server messages
    //$mail->Host = gethostbyname('smtp.gmail.com');
    $mail->do_debug = 0;
    $mail->Host = "smtp.gmail.com";  // use $mail->Host = gethostbyname('smtp.gmail.com'); // if your network does not                                                                      support SMTP over IPv6
    $mail->Port = 587; // TLS only
    $mail->SMTPSecure = 'tls';                                               // ssl is deprecate
    $mail->SMTPAuth = true;
    $mail->Username = ''; // email
    $mail->Password = ''; // password
    $mail->setFrom('','Niramay HealthCare Team'); // From email and name
    $mail->addReplyTo('','Niramay HealthCare Team'); 
    $mail->addAddress($email,$name);                                        // to email and name
    $mail->Subject ="Next Step of $name to Us...";
    // $mail->Body = file_get_contents('./emailTemplate.php');
    $mail->Body = $body;
    $mail->AltBody = 'HTML messaging not supported';
    $mail->SMTPOptions = array(
                        'ssl' => array(
                            'verify_peer' => false,
                            'verify_peer_name' => false,
                            'allow_self_signed' => true
                        )
                    );
    $mail->send();
    // if($mail->send()){
    //     echo json_encode(array('status' => "sent"));
       
    // }else{
    //     echo json_encode(array('status' => "error",'Error' => $mail->ErrorInfo)); 
    // }
?>