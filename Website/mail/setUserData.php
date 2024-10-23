<?php
	include("config.php");

 	$user_id = $_GET['user_id'];
 	$order_id = $_GET['order_id'];
 	$req_amount = $_GET['req_amount'];
 	$coins_used = $_GET['coins_used'];
 	$getway_name = $_GET['getway_name'];
 	$remark = $_GET['remark'];
 	$type = $_GET['type'];
 	$request_name = $_GET['request_name'];
 	$account_holder_id = $_GET['account_holder_id'];
 	$req_from = $_GET['req_from'];
 	$account_holder_name = $_GET['account_holder_name'];
    

    $query = "INSERT INTO `table_name`(`user_id`, `order_id`, `req_amount`, `coins_used`, `getway_name`, 'remark','type','request_name','id','req_from','account_holder_name') VALUES ('$user_id','$order_id','$req_amount','$coins_used','$getway_name','$remark','$type','$request_name','$account_holder_id','$req_from','$account_holder_name')";
    $result= mysqli_query($conn, $query);
    
    if($result){
        echo json_encode(array( "status" => "Inserted"));
    } else{
        echo json_encode(array( "status" => "Error"));
    }   

mysqli_close($conn);
?>