<?php
	include("config.php");

 	$user_username = $_POST['user_username'];
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
    $city = $_POST['city'];
 	$photo = $_POST['photo'];
    $weight = $_POST['weight'];
    $height = $_POST['height'];
    $age = $_POST['age'];
    $gender = $_POST['gender'];
    $disease = $_POST['disease'];

    
    if(!$photo == "")
    {
    	$pname= rand(10000000, 99999999);
        $profile_pic = "./images/$pname.jpg";
    	$return = file_put_contents($profile_pic,base64_decode($photo));
        $profile_pic="./images/$pname.jpg";
    }
    $updated_profile;$profile_pic_query;$profile_pic_object;$profile_pic_string;
    

   		$query = "SELECT * FROM `user` WHERE `username` = '$user_username' ";
  
        $result= mysqli_query($conn, $query);
        if (mysqli_num_rows($result) > 0) 
        {  
          
           // $query1 =  "UPDATE `user` SET `profile_pic`='$profile_pic' WHERE `username` = '$user_username'";
            if($photo == "")
            {
                $querywithoutphoto = "UPDATE `user` SET `name`='$name',`email`='$email',`phone`='$phone',`city`='$city',`weight`='$weight',`height`='$height',`age`='$age',`gender`='$gender',`disease`='$disease' WHERE `username` = '$user_username'";
                $result1= mysqli_query($conn, $querywithoutphoto);
            }else
            {
                $querywithphoto = "UPDATE `user` SET `profile_pic`='$profile_pic' WHERE `username` = '$user_username'";
                $result1= mysqli_query($conn, $querywithphoto);
            }
          	if($result1 == true){
                $status = "Updated";
                $profile_pic_query = "SELECT profile_pic FROM `user` WHERE username = '$user_username' ";
                $profile_pic_object = mysqli_query($conn,$profile_pic_query);
                $profile_pic_string = mysqli_fetch_object($profile_pic_object);
                $updated_profile = $profile_pic_string->profile_pic;
            }
          	else{
              $status = "Error";
            }
        }
        else
        {
          $status = "User not Found";
        }
        
    echo json_encode(array( "status" => $status,"updated_profile" => $updated_profile) );

mysqli_close($conn);
?>