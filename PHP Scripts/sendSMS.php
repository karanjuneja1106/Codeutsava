<?php
require "init.php";
$STRING_NAME = $_POST["STRING_NAME"];
$STRING_CONTACT = $_POST["STRING_CONTACT"];
$STRING_TIME = $_POST["STRING_TIME"];
$STRING_DATE = $_POST["STRING_DATE"];

$post_data = array(
    // 'From' doesn't matter; For transactional, this will be replaced with your SenderId;
    // For promotional, this will be ignored by the SMS gateway
    'From'   => 'LM-SAMPLE',
    'To'    => $STRING_CONTACT,
    'Body'  => 'Dear '.$STRING_NAME.' , Your appointment has been scheduled on '. $STRING_DATE.' at '. $STRING_TIME
);

$exotel_sid = "self586"; // Your Exotel SID
$exotel_token = "5e6883e7c1aaea0552a65f71e4d7c8a56604a0d6"; // Your exotel token

$url = "https://$exotel_sid:$exotel_token@twilix.exotel.in/v1/Accounts/$exotel_sid/Sms/send";

$ch = curl_init();
curl_setopt($ch, CURLOPT_VERBOSE, 1);
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_FAILONERROR, 0);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($post_data));

$http_result = curl_exec($ch);
echo $http_result + ' ' + gettype($url);
$error = curl_error($ch);
$http_code = curl_getinfo($ch ,CURLINFO_HTTP_CODE);

curl_close($ch);

?>
