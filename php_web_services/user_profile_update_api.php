<?php include("includes/connection.php");
 
	include('includes/function.php');

		$qry = "SELECT * FROM tbl_users WHERE email = '".$_GET['email']."'"; 
		$result = mysqli_query($mysqli,$qry);
		$row = mysqli_fetch_assoc($result);
 	 
 	 	if (!filter_var($_GET['email'], FILTER_VALIDATE_EMAIL)) 
		{
			$set['EBOOK_APP'][]=array('msg' => "Invalid email format!",'success'=>'0');

			header( 'Content-Type: application/json; charset=utf-8' );
			$json = json_encode($set);
			echo $json;
			 exit;
		}
		else if($row['email']==$_GET['email'] AND $row['id']!=$_GET['user_id'])
		{
			$set['EBOOK_APP'][]=array('msg' => "Email address already used!",'success'=>'0');

			header( 'Content-Type: application/json; charset=utf-8' );
			$json = json_encode($set);
			echo $json;
			 exit;
		}
 	 	else if($_GET['password']!="")
		{
			$data = array(
			'name'  =>  $_GET['name'],
			'email'  =>  $_GET['email'],
			'password'  =>  $_GET['password'],
			'phone'  =>  $_GET['phone'] 
			);
		}
		else
		{
			$data = array(
			'name'  =>  $_GET['name'],
			'email'  =>  $_GET['email'],			 
			'phone'  =>  $_GET['phone'] 
			);
		}
 
		
	$user_edit=Update('tbl_users', $data, "WHERE id = '".$_GET['user_id']."'");
 		 
	  				 
	$set['EBOOK_APP'][]=array('msg'=>'Updated','success'=>'1');		 
		 

	header( 'Content-Type: application/json; charset=utf-8' );
	$json = json_encode($set);
	echo $json;
	 exit;
	 
?>