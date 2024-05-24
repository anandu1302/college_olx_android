<?php

include("connection.php");

$uid= $_POST['uid'];
$pin= $_POST['pin'];
$acc= $_POST['accountNo'];
$am= $_POST['amount']; 

$date=date('Y-m-d');

$sel = "SELECT * FROM account_tbl WHERE accountno='$acc' and uid='$uid'";
$res = mysqli_query($con,$sel);

if(mysqli_num_rows($res)>0){

   while($row = mysqli_fetch_array($res)){

   	$p=$row["pin"];

   	if ($p == $pin) {
   	 $amount=$row["amount"];
   	 if($amount>=$am){
	   $a=$amount-$am;

	   $sql2 = "UPDATE account_tbl set amount='$a' where accountno='$acc'";
	   mysqli_query($con,$sql2);

	   $sel2 ="SELECT * FROM cart_tbl where user_id='$uid'";
	   $res2 = mysqli_query($con,$sel2);

	   if(mysqli_num_rows($res2)>0){

	   	while($row2 = mysqli_fetch_assoc($res2)){

	   	$sel3 ="SELECT * FROM product_tbl where id='$row2[product_id]'";
	    $res3 = mysqli_query($con,$sel3);

	    while($row3 = mysqli_fetch_assoc($res3)){
	    	$pid = $row2['product_id'];
            $pname = $row3['pname'];
            $price = $row3['price'];
            $image = $row3['image'];

            $six_digit_number = mt_rand(100000, 999999);


            $sql3 = "INSERT INTO history_tbl(uid,pname,price,image,date,order_id) VALUES('$uid','$pname','$price','$image','$date','$six_digit_number')";
            mysqli_query($con, $sql3);

            $sql5 ="UPDATE product_tbl set status='sold' where id='$pid'";
            mysqli_query($con,$sql5);

            $sql4 = "DELETE FROM cart_tbl where user_id='$uid'";
            mysqli_query($con, $sql4);

                  
	    }

        }

         echo "success";

	   } 

	 }
	 else {
	 	echo "failed";
	 }

   	}
   	else{
		  echo "pin";
		}

   }
}
else{

	echo "accerror";
  }

?>