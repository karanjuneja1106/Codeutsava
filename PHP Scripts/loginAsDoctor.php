<?php
require "init.php";
$DOCTOR_USER_ID = $_POST["DOCTOR_USER_ID"];
$DOCTOR_PASSWORD = $_POST["DOCTOR_PASSWORD"];

$sql_query="SELECT Name FROM `DOCTOR_DETAILS` WHERE User_id LIKE '$DOCTOR_USER_ID' AND Password LIKE '$DOCTOR_PASSWORD';";


$result = mysqli_query($con,$sql_query);

if(mysqli_num_rows($result)>0)
{
	$row=mysqli_fetch_assoc($result);
	$Name = $row["Name"];
	echo $Name;
}
else
{
	echo "Invalid DOCTOR ID or Password";
}


?>
