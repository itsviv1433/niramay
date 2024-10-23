<?php

$servername = "localhost";
//$username = "NiramayHealthCare";
$username = "root";
$password = "";
$database = "niramay_healthcare";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

//echo "Connected successfully";
?>
