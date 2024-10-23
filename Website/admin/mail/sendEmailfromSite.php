<?php
	use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\Exception;
    
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/Exception.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/PHPMailer.php';
    require $_SERVER['DOCUMENT_ROOT'] . '/mail/SMTP.php';
    


if($_POST) {

  $contact_name = trim(stripslashes($_POST['Name']));
  $contact_email = trim(stripslashes($_POST['Email']));
  $contact_subject = trim(stripslashes($_POST['Subject']));
  $contact_message = trim(stripslashes($_POST['Message']));
  $error['name'] = "";
  $error['email'] = "";
  $error['message'] = "";    
//   $name = "Sample";
//   $email = "itsviv1433@gmail.com";
//   $subject = "test";
//   $contact_message = "test sts sjhjsdhjj nsdjsbjdbjshdasn dsahdghjasbjh";

   //Check Name
	if (strlen($contact_name) < 2) {
		$error['name'] = "Please enter your name.";
	}
	// Check Email
	if (!preg_match('/^[a-z0-9&\'\.\-_\+]+@[a-z0-9\-]+\.([a-z0-9\-]+\.)*+[a-z]{2}/is', $contact_email)) {
		$error['email'] = "Please enter a valid email address.";
	}
	// Check Message
	if (strlen($contact_message) < 15) {
		$error['message'] = "Please enter your message. It should have at least 15 characters.";
	}
   // Subject
	if ($contact_subject == '') { $contact_subject = "Contact Form Submission From Niramay Healthcare Website"; }

   // Set Message
   $message = "Email from: $contact_name <br> Email address: $contact_email <br>Message: <br>$contact_message<br> --------------------- <br> This email was sent from niramay healthcare website contact form. <br>";

   error_reporting(E_ERROR | E_PARSE);
   if ($error['name'] == "" && $error['email'] == "" && $error['message'] == "") {
   
    $mail = new PHPMailer;
    $mail->isSMTP(); 
    $mail->SMTPDebug = false; // 0 = off (for production use) - 1 = client messages - 2 = client and server messages
    //$mail->Host = gethostbyname('smtp.gmail.com');
    $mail->do_debug = 0;
    $mail->Host = "smtp.gmail.com";  // use $mail->Host = gethostbyname('smtp.gmail.com'); // if your network does not                                                                      
    //support SMTP over IPv6
    $mail->Port = 587; // TLS only
    $mail->SMTPSecure = 'tls';                                               // ssl is deprecate
    $mail->SMTPAuth = true;
    $mail->Username = 'healthcare.niramay@gmail.com'; // email
    $mail->Password = 'healthcare.niramay@01042021'; // password
    $mail->setFrom($contact_email,$contact_name); // From email and name
    $mail->addAddress("niramayhealthcarefeedback@gmail.com","Niramay HealthCare Team");                                        
    // to email and name
    $mail->Subject ="$contact_subject";
    $mail->msgHTML($message); 
    //$mail->msgHTML(file_get_contents('contents.html'), __DIR__); //Read an HTML message body from an external file, convert referenced images to embedded,
    $mail->AltBody = 'HTML messaging not supported'; // If html emails is not supported by the receiver, show this body
    // $mail->addAttachment('images/phpmailer_mini.png'); //Attach an image file
    $mail->SMTPOptions = array(
                        'ssl' => array(
                            'verify_peer' => false,
                            'verify_peer_name' => false,
                            'allow_self_signed' => true
                        )
                    );
    
    

		if ($mail->send()) { 
		    // include_once("addEmailData.php");
		    echo "<script>alert('Message Sent');</script>";
		    https://niramayhealthcare.000webhostapp.com/website/contact.html
		}else { 
		    echo "<script>alert('Something Went Wrong.Please Tryy Again Later...');</script>";
		}
		
	} # end if - no validation error

	else {
	    $url = "https://niramayhealthcare.000webhostapp.com/website/contact.html";
	    $error_var = $error['name'].'\n'.$error['email'].'\n'.$error['message'];
		echo "<script>alert('$error_var');</script>";
// 		include('../contact.html');
        include($url);

	} # end if - there was a validation error
}
?>