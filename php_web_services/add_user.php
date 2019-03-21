<?php include('includes/header.php');

    include('includes/function.php');
	include('language/language.php'); 

 	require_once("thumbnail_images.class.php");
	 
	 
	if(isset($_POST['submit']) and isset($_GET['add']))
	{		
			  
			$data = array(
			'user_type'=>'Normal',	
			'name'  =>  $_POST['name'],
			'email'  =>  $_POST['email'],
			'password'  =>  $_POST['password'],
			'phone'  =>  $_POST['phone']			
			);

			$qry = Insert('tbl_users',$data);

		
			$_SESSION['msg']="10";
			header("location:manage_users.php");	 
			exit;
		
	}
	
	if(isset($_GET['user_id']))
	{
			 
			$user_qry="SELECT * FROM tbl_users where id='".$_GET['user_id']."'";
			$user_result=mysqli_query($mysqli,$user_qry);
			$user_row=mysqli_fetch_assoc($user_result);
		
	}
	
	if(isset($_POST['submit']) and isset($_POST['user_id']))
	{
		  
		if($_POST['password']!="")
		{
			$data = array(
			'name'  =>  $_POST['name'],
			'email'  =>  $_POST['email'],
			'password'  =>  $_POST['password'],
			'phone'  =>  $_POST['phone'] 
			);
		}
		else
		{
			$data = array(
			'name'  =>  $_POST['name'],
			'email'  =>  $_POST['email'],			 
			'phone'  =>  $_POST['phone'] 
			);
		}
 
		
		   $user_edit=Update('tbl_users', $data, "WHERE id = '".$_POST['user_id']."'");
		 
			if ($user_edit > 0){
				
				$_SESSION['msg']="11";
				header("Location:add_user.php?user_id=".$_POST['user_id']);
				exit;
			} 	
		
	 
	}
	
	
?>
 	

 <div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="page_title_block">
            <div class="col-md-5 col-xs-12">
              <div class="page_title"><?php if(isset($_GET['cat_id'])){?>Edit<?php }else{?>Add<?php }?> User</div>
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
            <form action="" name="addedituser" method="post" class="form form-horizontal" enctype="multipart/form-data" >
            	<input  type="hidden" name="user_id" value="<?php echo $_GET['user_id'];?>" />

              <div class="section">
                <div class="section-body">
                  <div class="form-group">
                    <label class="col-md-3 control-label">Name :-</label>
                    <div class="col-md-6">
                      <input type="text" name="name" id="name" value="<?php if(isset($_GET['user_id'])){echo $user_row['name'];}?>" class="form-control" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Email :-</label>
                    <div class="col-md-6">
                      <input type="email" name="email" id="email" value="<?php if(isset($_GET['user_id'])){echo $user_row['email'];}?>" class="form-control" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Password :-</label>
                    <div class="col-md-6">
                      <input type="password" name="password" id="password" value="" class="form-control" <?php if(!isset($_GET['user_id'])){?>required<?php }?>>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label">Phone :-</label>
                    <div class="col-md-6">
                      <input type="text" name="phone" id="phone" value="<?php if(isset($_GET['user_id'])){echo $user_row['phone'];}?>" class="form-control">
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
   

<?php include('includes/footer.php');?>                  