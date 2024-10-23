<?php
     include_once("config.php");
       
 //	$fac_city = $_POST['fac_city']; 
 	$fac_city = "pune";
    $query = "SELECT * FROM `facilities` WHERE `fac_city` = '$fac_city' ";
    
    $result= mysqli_query($conn, $query);
    $status = array('fac_city'=> "notfound");
    $statusarray = array($status);
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