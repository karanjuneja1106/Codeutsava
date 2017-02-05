<?php
require "init.php";
$CLIENT_MAIL_ID = $_POST["CLIENT_MAIL_ID"];
$CLIENT_PASSWORD = $_POST["CLIENT_PASSWORD"];

$sql_query="SELECT Name FROM `CLIENT_DETAILS` WHERE Mail_id LIKE '$CLIENT_MAIL_ID' AND Password LIKE '$CLIENT_PASSWORD';";


$result = mysqli_query($con,$sql_query);

if(mysqli_num_rows($result)>0)
{
	$row=mysqli_fetch_assoc($result);
	$Name = $row["Name"];
	echo "Login Sucessful";
}
else
{
	echo "Invalid Mail or Password";
}


?>
