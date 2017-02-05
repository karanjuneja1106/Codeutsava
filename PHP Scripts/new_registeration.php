<?php
require "init.php";
$CLIENT_NAME = $_POST["CLIENT_NAME"];
$CLIENT_MAIL_ID = $_POST["CLIENT_MAIL_ID"];
$CLIENT_PASSWORD = $_POST["CLIENT_PASSWORD"];
$CLIENT_CONTACT = $_POST["CLIENT_CONTACT"];
$CLIENT_WEEKS = $_POST["CLIENT_WEEKS"];
$CLIENT_CITY = $_POST["CLIENT_CITY"];
$CLIENT_STATE = $_POST["CLIENT_STATE"];
$sql_query = "INSERT INTO `CLIENT_DETAILS` (`Name`, `Mail_id`, `Contact`, `Weeks`, `City`, `State`, `Password`) VALUES('$CLIENT_NAME','$CLIENT_MAIL_ID','$CLIENT_CONTACT','$CLIENT_WEEKS','$CLIENT_CITY','$CLIENT_STATE','$CLIENT_PASSWORD');";
if(mysqli_query($con,$sql_query))
{
	echo "Registration Sucessful";
}
else
{
	echo "Sorry you have not been REGISTERED";
}
?>
