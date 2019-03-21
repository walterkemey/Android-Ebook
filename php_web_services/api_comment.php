<?php include("includes/connection.php"); 
	include('includes/function.php');

	  
		$data = array(
  	    'book_id'  => $_GET['book_id'],
 	    'user_name'  => $_GET['user_name'],				    
		'comment_text'  =>  $_GET['comment_text']
		);		
 		
		$qry = Insert('tbl_comments',$data);									 
					 
		$set['EBOOK_APP'][]=array('msg' => "Comment post successflly...!",'success'=>'1');
		 
	 	 header( 'Content-Type: application/json; charset=utf-8');
	     $json = json_encode($set);				
		 echo $json;
		 exit;
	 
?>