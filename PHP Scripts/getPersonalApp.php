<?php
require "init.php";

$USER_ID = $_POST["USER_ID"];
$sql_query="SELECT DOC_ID,PREFER_DATE FROM APPOINTMENT WHERE MAIL_ID = '$USER_ID';";

$result = mysqli_query($con,$sql_query);

$appointment = array();

while($row = mysqli_fetch_array($result))
{
	array_push($appointment,array("NAME"=>$row[0],"TIME"=>$row[1]));
}

echo json_encode(array("Appointment_From_Server"=>$appointment));
mysqli_close($con);

?>
