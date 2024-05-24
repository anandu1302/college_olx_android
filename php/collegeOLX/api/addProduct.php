<?php

include("connection.php");

$uid = $_POST['uid'];
$productName = $_POST['productName'];
$productDesc = $_POST['productDesc'];
$productPrice = $_POST['productPrice'];
$imageFile = $_POST['image'];


$sql ="INSERT INTO product_tbl (user_id,pname,description,price,status) VALUES ('$uid','$productName','$productDesc','$productPrice','available')";

if(mysqli_query($con,$sql)){
	
	$id = mysqli_insert_id($con);
	$fileName = "product".$id.".jpg";
	file_put_contents("../products_tbl/uploads/".$fileName, base64_decode($imageFile));

	$sql = "UPDATE product_tbl SET image = '$fileName' WHERE id = '$id'";
	mysqli_query($con,$sql);
	echo "success";
}
else{
	
	echo"failed";
}


?>