<?php
 
include("connection.php");

$pid = $_REQUEST['pid'];

$sql = "SELECT * FROM product_tbl WHERE id = $pid";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result))
		$data["data"][] = $row;
	echo json_encode($data);
}
else{
	echo "failed";
}

?>