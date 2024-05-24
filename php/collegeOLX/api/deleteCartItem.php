<?php

include("connection.php");

$itemId = $_POST['itemId'];

$res1 = mysqli_query($con,"delete from cart_tbl where id='$itemId'");
$res = mysqli_query($con,"select * from cart_tbl where id='$itemId'");

if(mysqli_num_rows($res)>0){

	echo "failed";

}
else{
	
	echo "success";
}

?>