<?php
require "init.php";

$CLIENT_MAIL_ID = $_POST["CLIENT_MAIL_ID"];
$CLIENT_SYMPTOMS = $_POST["CLIENT_SYMPTOMS"];
$CLIENT_MEDICAL_TEST = $_POST["CLIENT_MEDICAL_TEST"];
$CLIENT_MEDICINES_PRESCRIBED = $_POST["CLIENT_MEDICINES_PRESCRIBED"];
$CLIENT_DATE_TIME_SHOW=$_POST["CLIENT_DATE_TIME_SHOW"];
$CLIENT_DATE_TIME_STORE=$_POST["CLIENT_DATE_TIME_STORE"];
$sql_query = "INSERT INTO CLIENT_MEDICAL_HISTORY VALUES('$CLIENT_MAIL_ID','$CLIENT_SYMPTOMS','$CLIENT_MEDICAL_TEST','$CLIENT_MEDICINES_PRESCRIBED','$CLIENT_DATE_TIME_SHOW','$CLIENT_DATE_TIME_STORE');";
if(mysqli_query($con,$sql_query))
{
	echo "Data Inserted";
}
else
{
	echo "Insertion Failed!!".mysqli_error($con);
}


?>
