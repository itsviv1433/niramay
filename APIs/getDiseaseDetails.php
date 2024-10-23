<?php

  include_once("config.php");
       
 	$disease_name = $_POST['disease_name']; 
//  	$disease_name = "";
    $query = "SELECT * FROM `disease` WHERE `disease_name` = '$disease_name' ";
    //$query = "SELECT * from disease";
     $status = array('disease_symptoms'=> "notfound");
    $statusarray = array($status);
    $result= mysqli_query($conn, $query);
    if (mysqli_num_rows($result) > 0) 
    {  
		$emparray = array();
        while ($row = mysqli_fetch_object($result)) 
        {
            array_push($emparray,$row);
        }
        echo json_encode($emparray);
    }
    else
    {
        echo json_encode($statusarray);
    }
	mysqli_close($conn);
?>