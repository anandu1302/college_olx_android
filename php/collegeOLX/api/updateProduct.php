<?php

include("connection.php");

$productId = $_POST['productId'];
$productPrice = $_POST['productPrice'];

$sql ="UPDATE product_tbl SET price='$productPrice' WHERE id ='$productId'";

if(mysqli_query($con,$sql)){

       echo "success";

   }else{

      echo "Failed to Update Product";

   }

?>