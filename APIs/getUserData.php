<?php

 if($_SERVER['REQUEST_METHOD']=='POST')
  {
    require "config.php";

 	$username = $_POST['username'];
    $password = $_POST['password'];
 
   	  /*$username ="ItsViv";
      $password = "12345678";*/


        $query = "SELECT * FROM user WHERE username = '$username' AND password = '$password' ";
        $profile_pic_query = "SELECT profile_pic FROM user WHERE username = '$username' ";
        $result = mysqli_query($conn, $query);
        $userData = mysqli_fetch_object($result);

        if (mysqli_num_rows($result)>0)
        {
            $status = "Successful";
            echo json_encode(array("status"=>$status,"username"=>$username,"password"=>$password,"name"=>$userData->name,"email"=>$userData->email,"phone"=>$userData->phone,"city"=>$userData->city,"profile_pic"=>$userData->profile_pic,"weight"=>$userData->weight,"height"=>$userData->height,"age"=>$userData->age,"gender"=>$userData->gender,"disease"=>$userData->disease));
        }
        else
        { 
             $status = "Failed";
             echo json_encode(array("status"=>$status));
        }
        
    mysqli_close($conn);
}
?>