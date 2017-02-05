<?php

$db_name = "codeutsava";
$mysql_user = "root";
$mysql_pass = "june1106";
$server_name = "localhost";

$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);

if(!$con)
{
	//echo "Connection Error.....".mysqli_connect_error();
}
else
{
	//echo "<h3>Database Connection Sucessful!!</h3>";
}
?>
