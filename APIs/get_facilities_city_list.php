<?php

  include_once("config.php");
       
    $query = "SELECT cities FROM `facilities`";
    $result= mysqli_query($conn, $query);
    if (mysqli_num_rows($result) > 0) 
    {  
		$cityarray = array();
		$citiesavailable = array();
        while ($row = mysqli_fetch_object($result)) 
        {
            array_push($cityarray,$row);
        }
        foreach ($cityarray as $city){
         if($city->cities !=  null)
            array_push($citiesavailable,$city);   
        }
        echo json_encode($citiesavailable);
    }
    else
    {
        echo json_encode(array( "status" => "false","message" => "City not Found") );
    }
	mysqli_close($conn);
?>