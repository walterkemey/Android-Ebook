<?php include('includes/header.php'); 

    include('includes/function.php');
	include('language/language.php');  


	 function get_book_info($book_id)
	 {
	 	global $mysqli;

	 	 
	 		$query="SELECT * FROM tbl_books		 
					WHERE tbl_books.id='".$book_id."'";
	 	 
		$sql = mysqli_query($mysqli,$query)or die(mysqli_error());
		$row=mysqli_fetch_assoc($sql);

		return stripslashes($row['book_title']);
	 }
	 
							$tableName="tbl_comments";		
							$targetpage = "manage_comments.php"; 	
							$limit = 15; 
							
							$query = "SELECT COUNT(*) as num FROM $tableName";
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
							
							
						 $users_qry="SELECT * FROM tbl_comments
						 ORDER BY tbl_comments.id DESC LIMIT $start, $limit";  
							 
							$users_result=mysqli_query($mysqli,$users_qry);
							
	 
	if(isset($_GET['comment_id']))
	{
		  
		 
		Delete('tbl_comments','id='.$_GET['comment_id'].'');
		
		$_SESSION['msg']="12";
		header( "Location:manage_comments.php");
		exit;
	}
	
	 
	
	
?>


 <div class="row">
      <div class="col-xs-12">
        <div class="card mrg_bottom">
          <div class="page_title_block">
            <div class="col-md-5 col-xs-12">
              <div class="page_title">Manage Comments</div>
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
  				  <th>Book Title</th>
 				  <th>Name</th>		
				  <th>Comment Text</th>	 
                  <th class="cat_action_list">Action</th>
                </tr>
              </thead>
              <tbody>
              	<?php
						$i=0;
						while($users_row=mysqli_fetch_array($users_result))
						{
						 
				?>
                <tr>
  		           <td><?php echo get_book_info($users_row['book_id']);?></td>
 		           <td><?php echo $users_row['user_name'];?></td>
     		       <td><?php echo $users_row['comment_text'];?></td>
 
                   <td> 
                    <a href="manage_comments.php?comment_id=<?php echo $users_row['id'];?>" onclick="return confirm('Are you sure you want to delete this comment?');" class="btn btn-default">Delete</a></td>
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
              	<?php if(!isset($_POST["search"])){ include("pagination.php");}?>                 
              </nav>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
      </div>
    </div>     



<?php include('includes/footer.php');?>          