<?php


include("connection.php");

if($_REQUEST['uid'] != "")
{
    $query = "SELECT * FROM user_tbl WHERE id='$_REQUEST[uid]' ";
    $result = mysqli_query($con,$query);

    $data = array();

    while($row = mysqli_fetch_assoc($result))
    {
        $data['data'][] = $row;
    }

    echo json_encode($data);

}
else
 echo "";

?>