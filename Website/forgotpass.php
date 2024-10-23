<?php

 if($_SERVER['REQUEST_METHOD']=='POST')
 {
    require "config.php";

 	$username = $_POST['username'];
    $password = $_POST['password'];
    
   	//  $username ="itsvi";
   	//  $password ="12345678";
    
    $query = "SELECT * FROM user WHERE username = '$username'";
    $result = mysqli_query($conn, $query);
    $resultArray = mysqli_fetch_object($result);
    
        if (mysqli_num_rows($result)>0)
        {
            $status = "ValidUser";
            if($password != ""){
                $query = "UPDATE `user` SET `password`='$password' WHERE `username` = '$username'";
                $result = mysqli_query($conn, $query);
                if ($result == true)
                {
                    $status = "PassChange";
                }
                else
                { 
                     $status = "Error";
                } 
            }
        }
        else
        { 
             $status = "InvalidUser";
        } 
        if($resultArray != null){
            echo json_encode(array("status"=>$status,"phone"=>$resultArray->phone));   
        }else{
            echo json_encode(array("status"=>$status)); 
        }
        
    mysqli_close($conn);
}
?>