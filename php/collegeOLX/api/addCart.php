<?php

include("connection.php");

$uid = $_POST['uid'];
$productId = $_POST['pid'];

$sql ="INSERT INTO cart_tbl (user_id,product_id) VALUES ('$uid','$productId')";

if(mysqli_query($con,$sql)){
	
	echo "success";
}
else{
	
	echo"failed";
}


?>