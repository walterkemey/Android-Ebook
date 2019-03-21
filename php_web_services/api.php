<?php include("includes/connection.php");
 	  include("includes/function.php"); 	
	
 	    function get_user_name($user_id)
		{	
			global $mysqli;

			$u_query="SELECT * FROM tbl_users WHERE tbl_users.id='".$user_id."'";
			$u_sql = mysqli_query($mysqli,$u_query)or die(mysqli_error());

			$u_row=mysqli_fetch_assoc($u_sql);

			return $u_row['name'];
		}

		function get_total_books($cat_id)
		{	
			global $mysqli;

			$qry_books="SELECT COUNT(*) as num FROM tbl_books WHERE cat_id='".$cat_id."'";
			$total_books = mysqli_fetch_array(mysqli_query($mysqli,$qry_books));
			$total_books = $total_books['num'];

			return $total_books;
		}

	 if(isset($_GET['home']))
	  {
	  
	    $jsonObj1= array(); 
	  
	      $query1="SELECT * FROM tbl_books
	    LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
	    LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id   
	    WHERE tbl_books.featured=1
	    ORDER BY tbl_books.id DESC";

	    $sql1 = mysqli_query($mysqli,$query1)or die(mysqli_error());

	    while($data1 = mysqli_fetch_assoc($sql1))
	    {
	      $row1['id'] = $data1['id'];
	      $row1['cat_id'] = $data1['cat_id'];
	      $row1['aid'] = $data1['aid'];
	      $row1['book_title'] = $data1['book_title'];
	      $row1['book_cover_img'] = $data1['book_cover_img'];
	      $row1['book_bg_img'] = $data1['book_bg_img'];
	      $row1['book_file_type'] = $data1['book_file_type'];
	      
	      $row1['total_rate'] = $data1['total_rate'];
	      $row1['rate_avg'] = $data1['rate_avg'];
	      $row1['book_views'] = $data1['book_views'];

	      $row1['author_id'] = $data1['author_id'];
	      $row1['author_name'] = $data1['author_name'];

	 
	      $row1['cid'] = $data1['cid'];
	      $row1['category_name'] = $data1['category_name'];
 	       
	      array_push($jsonObj1,$row1);
	    
	    }

	    $row['featured_books'] = $jsonObj1;


	    $jsonObj2= array(); 
	 
	    $query2="SELECT * FROM tbl_books
	    LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
	    LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id   
	    ORDER BY tbl_books.id DESC LIMIT 3";

	    $sql2 = mysqli_query($mysqli,$query2)or die(mysqli_error());

	    while($data2 = mysqli_fetch_assoc($sql2))
	    {
	      $row2['id'] = $data2['id'];
	      $row2['cat_id'] = $data2['cat_id'];
	      $row2['aid'] = $data2['aid'];
	      $row2['book_title'] = $data2['book_title'];
	      $row2['book_cover_img'] = $data2['book_cover_img'];
	      $row2['book_bg_img'] = $data2['book_bg_img'];
	      $row2['book_file_type'] = $data2['book_file_type'];
	      
	      $row2['total_rate'] = $data2['total_rate'];
	      $row2['rate_avg'] = $data2['rate_avg'];
	      $row2['book_views'] = $data2['book_views'];

	      $row2['author_id'] = $data2['author_id'];
	      $row2['author_name'] = $data2['author_name'];
	 
	      $row2['cid'] = $data2['cid'];
	      $row2['category_name'] = $data2['category_name'];
 	      
	      array_push($jsonObj2,$row2);
	    
	    } 
	    $row['latest_books'] = $jsonObj2;

	 	$jsonObj3= array(); 
   
      $query3="SELECT * FROM tbl_books
      LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
      LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id   
      ORDER BY tbl_books.book_views DESC,tbl_books.total_rate DESC LIMIT 3";

      $sql3 = mysqli_query($mysqli,$query3)or die(mysqli_error());

      while($data3 = mysqli_fetch_assoc($sql3))
      {
        $row3['id'] = $data3['id'];
        $row3['cat_id'] = $data3['cat_id'];
        $row3['aid'] = $data3['aid'];
        $row3['book_title'] = $data3['book_title'];
        $row3['book_description'] = stripslashes($data3['book_description']);
        $row3['book_cover_img'] = $data3['book_cover_img'];
        $row3['book_bg_img'] = $data3['book_bg_img'];
        $row3['book_file_type'] = $data3['book_file_type'];
        
        $row3['total_rate'] = $data3['total_rate'];
        $row3['rate_avg'] = $data3['rate_avg'];
        $row3['book_views'] = $data3['book_views'];

        $row3['author_id'] = $data3['author_id'];
        $row3['author_name'] = $data3['author_name'];
   
        $row3['cid'] = $data3['cid'];
        $row3['category_name'] = $data3['category_name'];
         
        array_push($jsonObj3,$row3);
      
      } 
      $row['popular_books'] = $jsonObj3;

	    $set['EBOOK_APP'] = $row;

	    header( 'Content-Type: application/json; charset=utf-8' );
	      echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
	    die();
    
    }
    else if(isset($_GET['cat_list']))
 	{
 		$jsonObj= array();
		
		$cat_order=API_CAT_ORDER_BY;


		$query="SELECT cid,category_name FROM tbl_category ORDER BY tbl_category.".$cat_order."";
		$sql = mysqli_query($mysqli,$query)or die(mysql_error());

		while($data = mysqli_fetch_assoc($sql))
		{
			
			$row['cid'] = $data['cid'];
			$row['category_name'] = $data['category_name'];
 			$row['total_books'] = get_total_books($data['cid']); 

			array_push($jsonObj,$row);
		
		}

		$set['EBOOK_APP'] = $jsonObj;
		
		header( 'Content-Type: application/json; charset=utf-8' );
	    echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();
 	}
	else if(isset($_GET['cat_id']))
	{
		$post_order_by=API_CAT_POST_ORDER_BY;

		$cat_id=$_GET['cat_id'];	

		$jsonObj= array();	
	
	    $query="SELECT * FROM tbl_books
		LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
		LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id 
		where tbl_books.cat_id='".$cat_id."' ORDER BY tbl_books.id ".$post_order_by."";

		$sql = mysqli_query($mysqli,$query)or die(mysqli_error());

		while($data = mysqli_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['cat_id'] = $data['cat_id'];
			$row['aid'] = $data['aid'];
			$row['book_title'] = $data['book_title'];
			$row['book_description'] = stripslashes($data['book_description']);
			$row['book_cover_img'] = $data['book_cover_img'];
			$row['book_file_type'] = $data['book_file_type'];
			
			$row['total_rate'] = $data['total_rate'];
			$row['rate_avg'] = $data['rate_avg'];
			$row['book_views'] = $data['book_views'];

			$row['author_id'] = $data['author_id'];
			$row['author_name'] = $data['author_name'];
 
			$row['cid'] = $data['cid'];
			$row['category_name'] = $data['category_name'];
 			 

			array_push($jsonObj,$row);
		
		}

		$set['EBOOK_APP'] = $jsonObj;
		
		header( 'Content-Type: application/json; charset=utf-8' );
	    echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();

		
	}
	if(isset($_GET['author_list']))
 	{
 		$jsonObj= array();
		
		$author_order=API_AUTHOR_ORDER_BY;


		$query="SELECT author_id,author_name,author_image FROM tbl_author ORDER BY tbl_author.".$author_order."";
		$sql = mysqli_query($mysqli,$query)or die(mysql_error());

		while($data = mysqli_fetch_assoc($sql))
		{
			
			$row['author_id'] = $data['author_id'];
			$row['author_name'] = $data['author_name'];
			$row['author_image'] = $data['author_image'];
 			  
			array_push($jsonObj,$row);
		
		}

		$set['EBOOK_APP'] = $jsonObj;
		
		header( 'Content-Type: application/json; charset=utf-8' );
	    echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();
 	}
 	else if(isset($_GET['author_id']))
	{
		$post_order_by=API_AUTHOR_POST_ORDER_BY;

		$author_id=$_GET['author_id'];	

		$jsonObj= array();	
	
	    $query="SELECT * FROM tbl_books
		LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
		LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id  
		where tbl_books.aid='".$author_id."' ORDER BY tbl_books.id ".$post_order_by."";

		$sql = mysqli_query($mysqli,$query)or die(mysqli_error());

		while($data = mysqli_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['cat_id'] = $data['cat_id'];
			$row['aid'] = $data['aid'];
			$row['book_title'] = $data['book_title'];
			$row['book_description'] = stripslashes($data['book_description']);
			$row['book_cover_img'] = $data['book_cover_img'];
			$row['book_file_type'] = $data['book_file_type'];
			
			$row['total_rate'] = $data['total_rate'];
			$row['rate_avg'] = $data['rate_avg'];
			$row['book_views'] = $data['book_views'];

			$row['author_id'] = $data['author_id'];
			$row['author_name'] = $data['author_name'];
			$row1['author_image'] = $data1['author_image'];
 
			$row['cid'] = $data['cid'];
			$row['category_name'] = $data['category_name'];
 			 

			array_push($jsonObj,$row);
		
		}

		$set['EBOOK_APP'] = $jsonObj;
		
		header( 'Content-Type: application/json; charset=utf-8' );
	    echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();

		
	}	
	else if(isset($_GET['latest']))
	{
		//$limit=$_GET['latest'];	 

		$limit=API_LATEST_LIMIT;

		$jsonObj= array();	
 
		$query="SELECT * FROM tbl_books
		LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
		LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id   
		ORDER BY tbl_books.id DESC LIMIT $limit";

		$sql = mysqli_query($mysqli,$query)or die(mysqli_error());

		while($data = mysqli_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['cat_id'] = $data['cat_id'];
			$row['aid'] = $data['aid'];
			$row['book_title'] = $data['book_title'];
			$row['book_description'] = stripslashes($data['book_description']);
			$row['book_cover_img'] = $data['book_cover_img'];
			$row['book_bg_img'] = $data['book_bg_img'];
			$row['book_file_type'] = $data['book_file_type'];
			
			$row['total_rate'] = $data['total_rate'];
			$row['rate_avg'] = $data['rate_avg'];
			$row['book_views'] = $data['book_views'];

			$row['author_id'] = $data['author_id'];
			$row['author_name'] = $data['author_name'];
 
			$row['cid'] = $data['cid'];
			$row['category_name'] = $data['category_name'];
 			 

			array_push($jsonObj,$row);
		
		}

		$set['EBOOK_APP'] = $jsonObj;
		
		header( 'Content-Type: application/json; charset=utf-8' );
	    echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();

	}
	else if(isset($_GET['search_text']))
	{
		//$limit=$_GET['latest'];	 

 
		$jsonObj= array();	
 
		$query="SELECT * FROM tbl_books
		LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
		LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id 
		WHERE  tbl_books.book_title LIKE '%".$_GET['search_text']."%'
		ORDER BY tbl_books.book_title";

		$sql = mysqli_query($mysqli,$query)or die(mysqli_error());

		while($data = mysqli_fetch_assoc($sql))
		{
			$row['id'] = $data['id'];
			$row['cat_id'] = $data['cat_id'];
			$row['aid'] = $data['aid'];
			$row['book_title'] = $data['book_title'];
			$row['book_description'] = stripslashes($data['book_description']);
			$row['book_cover_img'] = $data['book_cover_img'];
			$row['book_bg_img'] = $data['book_bg_img'];
			$row['book_file_type'] = $data['book_file_type'];
			
			$row['total_rate'] = $data['total_rate'];
			$row['rate_avg'] = $data['rate_avg'];
			$row['book_views'] = $data['book_views'];

			$row['author_id'] = $data['author_id'];
			$row['author_name'] = $data['author_name'];
 
			$row['cid'] = $data['cid'];
			$row['category_name'] = $data['category_name'];
 			 

			array_push($jsonObj,$row);
		
		}

		$set['EBOOK_APP'] = $jsonObj;
		
		header( 'Content-Type: application/json; charset=utf-8' );
	    echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();

	}	
	else if(isset($_GET['book_id']))
	{
		  
				 
		$jsonObj= array();	

		$query="SELECT * FROM tbl_books 
		LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
		LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id   
		WHERE id='".$_GET['book_id']."'";
		$sql = mysqli_query($mysqli,$query)or die(mysqli_error());

		while($data = mysqli_fetch_assoc($sql))
		{
			 
			$row['id'] = $data['id'];
			$row['cat_id'] = $data['cat_id'];
			$row['aid'] = $data['aid'];
			$row['featured'] = $data['featured'];
			$row['book_title'] = $data['book_title'];
			$row['book_description'] = stripslashes($data['book_description']);
			$row['book_cover_img'] = $data['book_cover_img'];
			$row['book_bg_img'] = $data['book_bg_img'];

			$row['book_file_type'] = $data['book_file_type'];
			$row['book_file_url'] = $data['book_file_url'];
			
			$row['total_rate'] = $data['total_rate'];
			$row['rate_avg'] = $data['rate_avg'];
			$row['book_views'] = $data['book_views'];

			$row['author_id'] = $data['author_id'];
			$row['author_name'] = $data['author_name'];
 
			$row['cid'] = $data['cid'];
			$row['category_name'] = $data['category_name'];
  			
			//Related book
		      $qry2="SELECT * FROM tbl_books 
					LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
					LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id   
					WHERE id!='".$_GET['book_id']."' AND cat_id='".$data['cat_id']."'";
		      $result2=mysqli_query($mysqli,$qry2); 

		      if($result2->num_rows > 0)
		      {
		      		while ($related_books=mysqli_fetch_array($result2)) {
 		      	
		 		      	$row2['id'] = $related_books['id'];
						$row2['cat_id'] = $related_books['cat_id'];
						$row2['aid'] = $related_books['aid'];
						$row2['book_title'] = $related_books['book_title'];
						$row2['book_description'] = $related_books['book_description'];
						$row2['book_cover_img'] = $related_books['book_cover_img'];
						$row2['book_file_type'] = $related_books['book_file_type'];
						
						$row2['total_rate'] = $related_books['total_rate'];
						$row2['rate_avg'] = $related_books['rate_avg'];
						$row2['book_views'] = $related_books['book_views'];

						$row2['author_id'] = $related_books['author_id'];
						$row2['author_name'] = $related_books['author_name'];
			 
						$row2['cid'] = $related_books['cid'];
						$row2['category_name'] = $related_books['category_name'];
 
		 		      	$row['related_books'][]= $row2;
				      }
		     
		      }
		      else
		      {	
		      		 
		      		$row['related_books']= array();
		      }

			//Comments
		      $qry3="SELECT * FROM tbl_comments where book_id='".$_GET['book_id']."' ORDER BY id";
		      $result3=mysqli_query($mysqli,$qry3); 

		      if($result3->num_rows > 0)
		      {
		      		while ($row_comments=mysqli_fetch_array($result3)) {
 		      	
		 		      	$row3['book_id'] = $row_comments['book_id'];
		 		      	$row3['user_name'] = $row_comments['user_name'];
 		 		      	//$row3['user_name'] = get_user_name($row_comments['user_id']);
 		 		      	$row3['comment_text'] = $row_comments['comment_text'];
		 		      	$row3['dt_rate'] = date('d M Y',strtotime($row_comments['dt_rate']));

		 		      	$row['user_comments'][]= $row3;
				      }
		     
		      }
		      else
		      {	
		      		 
		      		$row['user_comments']=array();
		      }


			array_push($jsonObj,$row);
		
		}

		$view_qry=mysqli_query($mysqli,"UPDATE tbl_books SET book_views = book_views + 1 WHERE id = '".$_GET['book_id']."'");

		$set['EBOOK_APP'] = $jsonObj;
		
		header( 'Content-Type: application/json; charset=utf-8' );
	    echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();	
 

	}
	else 
	{
		$jsonObj= array();	

		$query="SELECT * FROM tbl_settings WHERE id='1'";
		$sql = mysqli_query($mysqli,$query)or die(mysqli_error());

		while($data = mysqli_fetch_assoc($sql))
		{
			 
			$row['app_name'] = $data['app_name'];
			$row['app_logo'] = $data['app_logo'];
			$row['app_version'] = $data['app_version'];
			$row['app_author'] = $data['app_author'];
			$row['app_contact'] = $data['app_contact'];
			$row['app_email'] = $data['app_email'];
			$row['app_website'] = $data['app_website'];
			$row['app_description'] = stripslashes($data['app_description']);
 			
 			$row['app_privacy_policy'] = stripslashes($data['app_privacy_policy']);

			array_push($jsonObj,$row);
		
		}

		$set['EBOOK_APP'] = $jsonObj;
		
		header( 'Content-Type: application/json; charset=utf-8' );
	    echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();	
	}		
	 
	 
?>