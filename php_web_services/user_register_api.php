<?php include("includes/connection.php");
 
	include('includes/function.php');

	 
	 	  
		$qry = "SELECT * FROM tbl_users WHERE email = '".$_GET['email']."'"; 
		$result = mysqli_query($mysqli,$qry);
		$row = mysqli_fetch_assoc($result);
		
		if (!filter_var($_GET['email'], FILTER_VALIDATE_EMAIL)) 
		{
			$set['EBOOK_APP'][]=array('msg' => "Invalid email format!",'success'=>'0');
		}
		else if($row['email']!="")
		{
			$set['EBOOK_APP'][]=array('msg' => "Email address already used!",'success'=>'0');
		}
		else
		{ 
 			
 			 
 				$data = array(
 					'user_type'=>'Normal',											 
				    'name'  => $_GET['name'],				    
					'email'  =>  $_GET['email'],
					'password'  =>  $_GET['password'],
					'phone'  =>  $_GET['phone'], 
					'status'  =>  '1'
					);		
 			 

			$qry = Insert('tbl_users',$data);									 
					 
				
			$set['EBOOK_APP'][]=array('msg' => "Register successflly...!",'success'=>'1');
					
		}

	  
 	 header( 'Content-Type: application/json; charset=utf-8');
     $json = json_encode($set);				
	 echo $json;
	 exit;
	 
?>