<?php
 
include("connection.php");

$uid = $_REQUEST['uid'];

$sql = "SELECT * FROM cart_tbl WHERE user_id='$uid'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM product_tbl WHERE id ='$row[product_id]'";
		$result2 = mysqli_query($con,$sql2);
		$roww = mysqli_fetch_assoc($result2);

		$data["data"][] = array('id'=>$row['id'],'pname'=>$roww['pname'],'image' => $roww['image'], 'price' => $roww['price']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>