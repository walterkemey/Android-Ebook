<?php include("includes/header.php");

	require("includes/function.php");
	require("language/language.php");

	require_once("thumbnail_images.class.php");

	 
	
	if(isset($_POST['submit']) and isset($_GET['add']))
	{  
      $file_name= str_replace(" ","-",$_FILES['author_image']['name']);

       $author_image=rand(0,99999)."_".$file_name;
         
       //Main Image
       $tpath1='images/'.$author_image;        
       $pic1=compress_image($_FILES["author_image"]["tmp_name"], $tpath1, 80);
     
       //Thumb Image 
       $thumbpath='images/thumbs/'.$author_image;   
       $thumb_pic1=create_thumb_image($tpath1,$thumbpath,'200','300');

	 
        $data = array( 
			    'author_name'  =>  addslashes($_POST['author_name']),
          'author_image'  =>  $author_image
			    );		

 		$qry = Insert('tbl_author',$data);			

		$_SESSION['msg']="10";
 
		header( "Location:add_author.php?add=yes");
		exit;	

		 
		
	}
	
	if(isset($_GET['author_id']))
	{
			 
			$qry="SELECT * FROM tbl_author where author_id='".$_GET['author_id']."'";
			$result=mysqli_query($mysqli,$qry);
			$row=mysqli_fetch_assoc($result);

	}
	if(isset($_POST['submit']) and isset($_POST['author_id']))
	{    

      if($_FILES['author_image']['name']!="")
     {
          $file_name= str_replace(" ","-",$_FILES['author_image']['name']);

         $author_image=rand(0,99999)."_".$file_name;
           
         //Main Image
         $tpath1='images/'.$author_image;        
         $pic1=compress_image($_FILES["author_image"]["tmp_name"], $tpath1, 80);
       
         //Thumb Image 
         $thumbpath='images/thumbs/'.$author_image;   
         $thumb_pic1=create_thumb_image($tpath1,$thumbpath,'200','300');

     
          $data = array( 
            'author_name'  =>  addslashes($_POST['author_name']),
            'author_image'  =>  $author_image
            );    

     }
     else
     {
        $data = array(
            'author_name'  =>  addslashes($_POST['author_name'])
        );
     }
		  
			 	

	         $author_edit=Update('tbl_author', $data, "WHERE author_id = '".$_POST['author_id']."'");
 
		    
 						$_SESSION['msg']="11"; 
						header( "Location:add_author.php?author_id=".$_POST['author_id']);
						exit;
 
	}


?>
<div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="page_title_block">
            <div class="col-md-5 col-xs-12">
              <div class="page_title"><?php if(isset($_GET['author_id'])){?>Edit<?php }else{?>Add<?php }?> Author</div>
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
          <div class="card-body mrg_bottom"> 
            <form action="" name="addeditcategory" method="post" class="form form-horizontal" enctype="multipart/form-data">
            	<input  type="hidden" name="author_id" value="<?php echo $_GET['author_id'];?>" />

              <div class="section">
                <div class="section-body">
                  <div class="form-group">
                    <label class="col-md-3 control-label">Author Name :-</label>
                    <div class="col-md-6">
                      <input type="text" name="author_name" id="author_name" value="<?php if(isset($_GET['author_id'])){echo $row['author_name'];}?>" class="form-control" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Author Image :-</label>
                    <div class="col-md-6">
                      <div class="fileupload_block">
                        <input type="file" name="author_image" value="fileupload" id="fileupload">
                        <?php if(isset($_GET['author_id']) and $row['author_image']!="") {?>
                            <div class="fileupload_img"><img type="image" src="images/<?php echo $row['author_image'];?>" alt="category image" /></div>
                          <?php } else {?>
                            <div class="fileupload_img"><img type="image" src="assets/images/add-image.png" alt="author image" /></div>
                          <?php }?>
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-md-9 col-md-offset-3">
                      <button type="submit" name="submit" class="btn btn-primary">Save</button>
                    </div>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
        
<?php include("includes/footer.php");?>       
