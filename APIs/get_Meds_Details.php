<?php

  include_once("config.php");
       
	$disease_name = $_POST['disease_name']; 
//  	$disease_name = "corona";
    $query = "SELECT * FROM `medicines` WHERE `disease` = '$disease_name' ";
    //$query = "SELECT * from medicines";
    $result= mysqli_query($conn, $query);
    if (mysqli_num_rows($result) > 0) 
    {  
		$emparray = array();
        while ($row = mysqli_fetch_object($result)) 
        {
            array_push($emparray,$row);
        }
        // array_push($emparray, array("status" => "successful"));
        echo json_encode($emparray);
    }
    else
    {
        $falsearray = array();
        $row = array("status" => "false");
        array_push($falsearray,$row);
        echo json_encode($falsearray);
    }
	mysqli_close($conn);
?>