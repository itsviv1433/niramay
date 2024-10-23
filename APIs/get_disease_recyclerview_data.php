<?php

  include_once("config.php");
       
    $query = "SELECT disease_name FROM `disease`";
    $result= mysqli_query($conn, $query);
    if (mysqli_num_rows($result) > 0) 
    {  
		$diseasearray = array();
        while ($row = mysqli_fetch_object($result)) 
        {
            array_push($diseasearray,$row);
        }
        echo json_encode($diseasearray);
    }
    else
    {
        echo json_encode(array( "status" => "false","message" => "Disease") );
    }
	mysqli_close($conn);
?>