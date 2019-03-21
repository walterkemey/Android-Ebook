<?php include("includes/connection.php");
 
	include('includes/function.php');

  
 	 
 		$qry = "SELECT * FROM tbl_users WHERE status='1' and email = '".$_GET['email']."' and password = '".$_GET['password']."'"; 
		$result = mysqli_query($mysqli,$qry);
		$num_rows = mysqli_num_rows($result);
		$row = mysqli_fetch_assoc($result);
		
    if ($num_rows > 0)
		{ 
					 
			     $set['EBOOK_APP'][]=array('user_id' => $row['id'],'name'=>$row['name'],'success'=>'1');
			 
		}		 
		else
		{
				 
 				$set['EBOOK_APP'][]=array('msg' =>'Login failed','success'=>'0');
		}
	 

	header( 'Content-Type: application/json; charset=utf-8' );
	$json = json_encode($set);

	echo $json;
	 exit;
	 
?>