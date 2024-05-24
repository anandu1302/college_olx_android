<?php

include("connection.php");

$uid = $_REQUEST['uid'];

$sel = "SELECT * FROM cart_tbl WHERE user_id='$uid'";
$result = mysqli_query($con,$sel);

$count = mysqli_num_rows($result);

echo $count;

?>