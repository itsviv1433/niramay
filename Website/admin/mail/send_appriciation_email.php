<?php
    
    // $name = "Vivek";
    // $email = "itsviv1433@gmail.com";
    // $phone = "7219276033";
    // $subject = "Test Email";
    // $message = "This is testing email template.";
    
    $mail->isSMTP(); 
    $mail->SMTPDebug = false; // 0 = off (for production use) - 1 = client messages - 2 = client and server messages
    //$mail->Host = gethostbyname('smtp.gmail.com');
    $mail->do_debug = 0;
    $mail->Host = "smtp.gmail.com";  // use $mail->Host = gethostbyname('smtp.gmail.com'); // if your network does not                                                                      support SMTP over IPv6
    $mail->Port = 587; // TLS only
    $mail->SMTPSecure = 'tls';                                               // ssl is deprecate
    $mail->SMTPAuth = true;
    $mail->Username = 'itsvivsitemail@gmail.com'; // email
    $mail->Password = 'itsvivsitemail@7102001'; // password
    $mail->setFrom('itsvivsitemail@gmail.com','Its Viv'); // From email and name
    $mail->addReplyTo('itsvivsitemail@gmail.com','Its Viv'); 
    $mail->ClearAllRecipients();                                      // to email and name
    $mail->addAddress($email,$name);
    $mail->Subject ="Received Mail from $name";
    $mail->msgHTML("Dear $name,<br><br> Your Message is Received Successfully to ItsViv.<br>
                Thank you so much for your message.I really appreciate it.<br>
                If you want to contact further simply reply this mail.<br><br>
                Or You can also contact me from below sources <br>
                My Email : itsviv1433@gmail.com.<br>
                My Instagram Handle : https://instagram.com/itz_viv_1433?igshid=vqf9krhjivh6 <br>
                My Twitter Handle : https://twitter.com/ItsViv1433?s=03 <br>
                My Facebook : https://www.facebook.com/vivek.mahajan.75286100 <br><br>
                Best Regards,<br>VIVEK MAHAJAN"); //$mail->msgHTML(file_get_contents('contents.html'), __DIR__); //Read an HTML message body from an external file, convert referenced images to embedded,
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
        echo "OK";
    }
?>