<?php
    include('config.php');
    error_reporting(0);
    session_start();
    if(isset($_SESSION['admin'])){
        $admin_username = $_SESSION['admin'];
        $update_sql = "update user set isauth='0' where username='".$admin_username."'";
        $res = mysqli_query($conn,$update_sql);
        if($res){
            echo "Code Reset Successful.Kindly Login through <a href='../'>website</a>.";
            // echo $admin_username;
        }else{
            echo "Something Went Wrong.Please Try Again Later.";
        }
    
    }else{
        unset($_SESSION['admin']);
        session_destroy();
        header("Location:../admin_login.php");
        exit();
    }
?>
