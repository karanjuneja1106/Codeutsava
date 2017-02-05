<?php
require "init.php";



$sql_query="SELECT Name, Contact,User_id FROM DOCTOR_DETAILS;";


$result = mysqli_query($con,$sql_query);

$doctors = array();

while($row = mysqli_fetch_array($result))
{
	array_push($doctors,array("Name"=>$row[0],"Contact"=>$row[1],"User_id"=>$row[2]));
}

echo json_encode(array("doctors_From_Server"=>$doctors));
mysqli_close($con);

?>
