<?php
require "init.php";

$CLIENT_MEDICAL_HISTORY_MAIL_ID = $_POST["CLIENT_MEDICAL_HISTORY_MAIL_ID"];

$sql_query="SELECT `Symptoms`, `Date_Time_Show`, `Medical_Test`, `Medicines_Prescribed` FROM `CLIENT_MEDICAL_HISTORY` WHERE `Mail_id`='$CLIENT_MEDICAL_HISTORY_MAIL_ID' ORDER BY Date_Time_Store DESC;";


$result = mysqli_query($con,$sql_query);

$medical_History = array();

while($row = mysqli_fetch_array($result))
{
	array_push($medical_History,array("Symptoms"=>$row[0],"Date_Time_Show"=>$row[1],"Medical_Test"=>$row[2],"Medicines_Prescribed"=>$row[3]));
}

echo json_encode(array("Medical_History_From_Server"=>$medical_History));
mysqli_close($con);

?>
