<?php
require "init.php";

$DOC_ID=$_POST["DOC_ID"];
$CLIENT_MAIL=$_POST["CLIENT_MAIL"];
$PREFER_DATE=$_POST["PREFER_DATE"];

$sql = "INSERT INTO APPOINTMENT (DOC_ID, MAIL_ID, Prefer_date) VALUES ('$DOC_ID', '$CLIENT_MAIL', '$PREFER_DATE')";

if( mysqli_query($con,$sql)){
  echo "New record created successfully";
}
else{
  echo "Error";
}
mysqli_close($con);

?>
