<?php include("includes/header.php");

	require("includes/function.php");
	require("language/language.php");

	require_once("thumbnail_images.class.php");

  //All Category
	$cat_qry="SELECT * FROM tbl_category ORDER BY category_name";
  $cat_result=mysqli_query($mysqli,$cat_qry);

  $author_qry="SELECT * FROM tbl_author ORDER BY author_name";
  $author_result=mysqli_query($mysqli,$author_qry);
	
	if(isset($_POST['submit']))
	{
	    
     $file_name= str_replace(" ","-",$_FILES['book_cover_img']['name']);

	   $book_cover_img=rand(0,99999)."_".$file_name;
		 	 
     //Main Image
	   $tpath1='images/'.$book_cover_img; 			 
     $pic1=compress_image($_FILES["book_cover_img"]["tmp_name"], $tpath1, 80);
	 
		 //Thumb Image 
	   $thumbpath='images/thumbs/'.$book_cover_img;		
     $thumb_pic1=create_thumb_image($tpath1,$thumbpath,'250','350');

      if($_FILES['book_bg_img']['name']!="")
      {
         $file_name2= str_replace(" ","-",$_FILES['book_bg_img']['name']);

         $book_bg_img=rand(0,99999)."_".$file_name2;
           
         //Main Image
         $tpath2='images/'.$book_bg_img;        
         $pic2=compress_image($_FILES["book_bg_img"]["tmp_name"], $tpath2, 80);
        
      }
      else
      {
        $book_bg_img=null;
      }


        if ($_POST['book_file_type']=='server_url')
        {
              $book_file_url=$_POST['book_file_server_url'];
 
        } 

        if ($_POST['book_file_type']=='local')
        {
              $path = "uploads/"; //set your folder path
              $book_file_local=rand(0,99999)."_".str_replace(" ", "-", $_FILES['book_file_local']['name']);

              $tmp = $_FILES['book_file_local']['tmp_name'];              
              move_uploaded_file($tmp, $path.$book_file_local);


              $file_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/uploads/';
              
              $book_file_url=$file_path.$book_file_local;
 
        }

          
       $data = array( 
			   'cat_id'  =>  $_POST['cat_id'],
         'aid'  =>  $_POST['aid'],
         'book_title'  =>  addslashes($_POST['book_title']),
         'book_description'  =>  addslashes($_POST['book_description']),         
         'book_cover_img'  =>  $book_cover_img,
         'book_bg_img'  =>  $book_bg_img,
         'book_file_type'  =>  $_POST['book_file_type'],
         'book_file_url'  =>  $book_file_url
			    );		

 		$qry = Insert('tbl_books',$data);	

     
		$_SESSION['msg']="10";
 
		header( "Location:add_book.php");
		exit;	

		
	}
	 

?>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function(e) {
           $("#book_file_type").change(function(){
          
           var type=$("#book_file_type").val();
              
             if(type=="server_url")
              {
                 
                 $("#book_url_display").show();
                  $("#book_local_display").hide();
              }
              else
              {   
                     
                $("#book_url_display").hide();               
                $("#book_local_display").show();
 
              }    
              
         });
        });
</script>
 
<div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="page_title_block">
            <div class="col-md-5 col-xs-12">
              <div class="page_title">Add Book</div>
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
 
              <div class="section">
                <div class="section-body">                 
                    <div class="form-group">
                    <label class="col-md-3 control-label">Category :-</label>
                    <div class="col-md-6">
                      <select name="cat_id" id="cat_id" class="select2" required>
                        <option value="">--Select Category--</option>
                        <?php
                            while($cat_row=mysqli_fetch_array($cat_result))
                            {
                        ?>                       
                        <option value="<?php echo $cat_row['cid'];?>"><?php echo $cat_row['category_name'];?></option>                           
                        <?php
                          }
                        ?>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Author :-</label>
                    <div class="col-md-6">
                      <select name="aid" id="aid" class="select2" required>
                        <option value="">--Select Author--</option>
                        <?php
                            while($author_row=mysqli_fetch_array($author_result))
                            {
                        ?>                       
                        <option value="<?php echo $author_row['author_id'];?>"><?php echo $author_row['author_name'];?></option>                           
                        <?php
                          }
                        ?>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Book Title :-</label>
                    <div class="col-md-6">
                      <input type="text" name="book_title" id="book_title" value="" class="form-control" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Book Description :-</label>
                    <div class="col-md-6">
                 
                      <textarea name="book_description" id="book_description" class="form-control"></textarea>

                      <script>CKEDITOR.replace( 'book_description' );</script>
                    </div>
                  </div>                  
                  <div class="form-group">&nbsp;</div>
                                  
                   
                  <div class="form-group">&nbsp;</div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Book Featured Image :-</label>
                    <div class="col-md-6">
                      <div class="fileupload_block">
                        <input type="file" name="book_cover_img" value="" id="fileupload" required>
                            
                        	  <div class="fileupload_img"><img type="image" src="assets/images/add-image.png" alt="Featured image" /></div>
                        	 
                      </div>
                    </div>
                  </div>
                    <div class="form-group">
                    <label class="col-md-3 control-label">Book Cover Image :-</label>
                    <div class="col-md-6">
                      <div class="fileupload_block">
                        <input type="file" name="book_bg_img" value="" id="fileupload" required>
                            
                            <div class="fileupload_img"><img type="image" src="assets/images/add-image.png" alt="Featured image" /></div>
                           
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">File Type :-</label>
                    <div class="col-md-6">                       
                      <select name="book_file_type" id="book_file_type" style="width:280px; height:25px;" class="select2" required>
                            <option value="">--Select Category--</option>
                             <option value="server_url">From Server</option>
                            <option value="local">From Local</option>
                      </select>
                    </div>
                  </div>
                     <div id="book_url_display" class="form-group">
                    <label class="col-md-3 control-label">File URL :-</label>
                    <div class="col-md-6">
                      <input type="text" name="book_file_server_url" id="book_file_server_url" value="" class="form-control">
                    </div>
                  </div>
                  
                  <div class="form-group" id="book_local_display" class="form-group" style="display:none;">
                    <label class="col-md-3 control-label">Book File :-</label>
                    <div class="col-md-6">
                      <div class="fileupload_block">
                        <input type="file" name="book_file_local" value="" id="fileupload">
                            
                            <div class="fileupload_img"><img type="image" src="assets/images/add-image.png" alt="Featured image" /></div>
                           
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
