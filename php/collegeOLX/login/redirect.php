<?php
session_start();
include('../db/connectionI.php');
//$db_name="music"; // Database name 
$tbl_name="employee"; // Table name 

// Connect to server and select databse.


// username and password sent from form 
$myusername=$_POST['UserName']; 
$mypassword=$_POST['Password']; 


if(isset($_POST['login']))
{
//

if($myusername=="admin" and $mypassword=="admin")
{
		$_SESSION['type']="admin";
header("location:../dashboard/dashboard.php");
}
else
{
header("location:login.php?a=1");
}




}

?>
 
 



