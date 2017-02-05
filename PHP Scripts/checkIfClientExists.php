<?php
require "init.php";

$CLIENT_MAIL_ID = $_POST["CLIENT_MAIL_ID"];

$sql_query="SELECT Name FROM `CLIENT_DETAILS` WHERE Mail_id LIKE '$CLIENT_MAIL_ID';";


$result = mysqli_query($con,$sql_query);

if(mysqli_num_rows($result)>0)
{
	$row=mysqli_fetch_assoc($result);
	$Name = $row["Name"];
	echo "CLIENT EXIST";
}
else
{
	echo "CLIENT DOES NOT EXISTS";
}


?>
