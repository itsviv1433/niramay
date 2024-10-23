<?php

    use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\Exception;
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/Exception.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/PHPMailer.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/SMTP.php';
    
    $to      = '';
    $subject = 'the subject';
    $message = 'hello';
    $headers = array(
    'From' => 'team@niramayhealthcare.ml',
    'Reply-To' => 'team@niramayhealthcare.ml',
    'X-Mailer' => 'PHP/' . phpversion() );

    if(mail($to, $subject, $message, $headers)){
        echo "mail sent";
    }else{
        echo "not sent";
    }
  
?>