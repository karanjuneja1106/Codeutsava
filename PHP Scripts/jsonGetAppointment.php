<?php
require "init.php";

$DOC_USER_ID = $_POST["DOC_USER_ID"];
$sql_query="SELECT CLIENT_DETAILS.Name,CLIENT_DETAILS.Mail_id,CLIENT_DETAILS.Contact,APPOINTMENT.PREFER_DATE FROM CLIENT_DETAILS,APPOINTMENT WHERE CLIENT_DETAILS.Mail_id = APPOINTMENT.MAIL_ID and APPOINTMENT.DOC_ID='$DOC_USER_ID';";

$result = mysqli_query($con,$sql_query);

$appointment = array();

while($row = mysqli_fetch_array($result))
{
	array_push($appointment,array("NAME"=>$row[0],"MAIL_ID"=>$row[1],"CONTACT"=>$row[2],"DATE"=>$row[3]));
}

echo json_encode(array("Appointment_From_Server"=>$appointment));
mysqli_close($con);

?>
