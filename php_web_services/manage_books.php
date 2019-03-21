<?php include('includes/header.php'); 

    include('includes/function.php');
	include('language/language.php');  


	if(isset($_POST['books_search']))
	 {
		 
		
		 $books_qry="SELECT * FROM tbl_books
							LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
							LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id
							WHERE tbl_books.book_title like '%".addslashes($_POST['book_title'])."%' ORDER BY tbl_books.id DESC";  
		 				 
		$books_result=mysqli_query($mysqli,$books_qry);
		
		 
	 }
	 else
	 {
	 
							$tableName="tbl_books";		
							$targetpage = "manage_books.php"; 	
							$limit = 10; 
							
							$query ="SELECT COUNT(*) as num FROM $tableName
							LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
							LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id";
							$total_pages = mysqli_fetch_array(mysqli_query($mysqli,$query));
							$total_pages = $total_pages['num'];
							 
							
							$stages = 3;
							$page=0;
							if(isset($_GET['page'])){
							$page = mysqli_real_escape_string($mysqli,$_GET['page']);
							}
							if($page){
								$start = ($page - 1) * $limit; 
							}else{
								$start = 0;	
								}	
							
							
						 $books_qry="SELECT * FROM tbl_books
							LEFT JOIN tbl_category ON tbl_books.cat_id= tbl_category.cid
							LEFT JOIN tbl_author ON tbl_books.aid= tbl_author.author_id
						 ORDER BY tbl_books.id DESC LIMIT $start, $limit";
							 
							$books_result=mysqli_query($mysqli,$books_qry);
							
	 }
	if(isset($_GET['book_id']))
	{
	  
		$img_res=mysqli_query($mysqli,'SELECT * FROM tbl_books WHERE id=\''.$_GET['book_id'].'\'');
		$img_row=mysqli_fetch_assoc($img_res);
			
		if($img_row['book_cover_img']!="")
		{
			unlink('images/'.$img_row['book_cover_img']);
			unlink('images/thumb/'.$img_row['book_cover_img']);
			 
		}	

		Delete('tbl_books','id='.$_GET['book_id'].'');
		
		$_SESSION['msg']="12";
		header( "Location:manage_books.php");
		exit;
	}
	
	//Active and Deactive status
	if(isset($_GET['status_deactive_id']))
	{
		$data = array('status'  =>  '0');
		
		$edit_status=Update('tbl_books', $data, "WHERE id = '".$_GET['status_deactive_id']."'");
		
		 $_SESSION['msg']="14";
		 header( "Location:manage_books.php");
		 exit;
	}
	if(isset($_GET['status_active_id']))
	{
		$data = array('status'  =>  '1');
		
		$edit_status=Update('tbl_books', $data, "WHERE id = '".$_GET['status_active_id']."'");
		
		$_SESSION['msg']="13";
		 header( "Location:manage_books.php");
		 exit;
	}
	
	//Active and Deactive featured
	if(isset($_GET['featured_deactive_id']))
	{
		$data = array('featured'  =>  '0');
		
		$edit_status=Update('tbl_books', $data, "WHERE id = '".$_GET['featured_deactive_id']."'");
		
		 $_SESSION['msg']="14";
		 header( "Location:manage_books.php");
		 exit;
	}
	if(isset($_GET['featured_active_id']))
	{
		$data = array('featured'  =>  '1');
		
		$edit_status=Update('tbl_books', $data, "WHERE id = '".$_GET['featured_active_id']."'");
		
		$_SESSION['msg']="13";
		 header( "Location:manage_books.php");
		 exit;
	}

	 

?>


 <div class="row">
      <div class="col-xs-12">
        <div class="card mrg_bottom">
          <div class="page_title_block">
            <div class="col-md-5 col-xs-12">
              <div class="page_title">Manage Books</div>
            </div>
            <div class="col-md-7 col-xs-12">              
                  <div class="search_list">
                    <div class="search_block">
                      <form  method="post" action="">
                        <input class="form-control input-sm" placeholder="Search book..." aria-controls="DataTables_Table_0" type="search" name="book_title" required>
                        <button type="submit" name="books_search" class="btn-search"><i class="fa fa-search"></i></button>
                      </form>  
                    </div>
                    <div class="add_btn_primary"> <a href="add_book.php">Add Book</a> </div>
                  </div>
                  
            </div>
          </div>
          <div class="clearfix"></div>
          <div class="row mrg-top">
            <div class="col-md-12">
               
              <div class="col-md-12 col-sm-12">
                <?php if(isset($_SESSION['msg'])){?> 
               	 <div class="alert alert-success alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                	<?php echo $client_lang[$_SESSION['msg']] ; ?></a> </div>
                <?php unset($_SESSION['msg']);}?>	
              </div>
            </div>
          </div>
          <div class="col-md-12 mrg-top">
            <table class="table table-striped table-bordered table-hover">
              <thead>
                <tr>
                  
 		          <th>Category</th>
 		          <th>Author</th>
		          <th>Title</th>
          		  <th>Image</th>				  
				  <th>Featured</th>
				  <th>Status</th>	 
                  <th class="cat_action_list">Action</th>
                </tr>
              </thead>
              <tbody>
              	<?php
						$i=0;
						while($books_row=mysqli_fetch_array($books_result))
						{
						 
				?>
                <tr> 
                   <td><?php echo $books_row['category_name'];?></td>
                   <td><?php echo $books_row['author_name'];?></td>
		           <td><?php echo stripslashes($books_row['book_title']);?></td>
		           <td><img src="images/thumbs/<?php echo $books_row['book_cover_img'];?>" width="100" height="150"/></td>
		           <td>
		          		<?php if($books_row['featured']!="0"){?>
		              <a href="manage_books.php?featured_deactive_id=<?php echo $books_row['id'];?>" title="Change Status"><span class="badge badge-success badge-icon"><i class="fa fa-check" aria-hidden="true"></i><span>Enable</span></span></a>

		              <?php }else{?>
		              <a href="manage_books.php?featured_active_id=<?php echo $books_row['id'];?>" title="Change Status"><span class="badge badge-danger badge-icon"><i class="fa fa-check" aria-hidden="true"></i><span>Disable </span></span></a>
		              <?php }?>
              		</td>  
 		           <td>
		          		<?php if($books_row['status']!="0"){?>
		              <a href="manage_books.php?status_deactive_id=<?php echo $books_row['id'];?>" title="Change Status"><span class="badge badge-success badge-icon"><i class="fa fa-check" aria-hidden="true"></i><span>Enable</span></span></a>

		              <?php }else{?>
		              <a href="manage_books.php?status_active_id=<?php echo $books_row['id'];?>" title="Change Status"><span class="badge badge-danger badge-icon"><i class="fa fa-check" aria-hidden="true"></i><span>Disable </span></span></a>
		              <?php }?>
              		</td>
                   <td><a href="edit_book.php?book_id=<?php echo $books_row['id'];?>" class="btn btn-primary">Edit</a>
                    <a href="manage_books.php?book_id=<?php echo $books_row['id'];?>" onclick="return confirm('Are you sure you want to delete this book?');" class="btn btn-default">Delete</a></td>
                </tr>
               <?php
						
						$i++;
						}
			   ?>
              </tbody>
            </table>
          </div>
          <div class="col-md-12 col-xs-12">
            <div class="pagination_item_block">
              <nav>

              	<?php if(!isset($_POST["books_search"])){ include("pagination.php");}?>                 
              </nav>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
      </div>
    </div>     



<?php include('includes/footer.php');?>                  