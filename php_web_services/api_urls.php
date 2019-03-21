<?php include("includes/header.php");

$file_path = 'http://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'/';
?>
<div class="row">
      <div class="col-sm-12 col-xs-12">
     	 	<div class="card">
		        <div class="card-header">
		          Example API urls
		        </div>
       			    <div class="card-body no-padding">
         		
         			 <pre><code class="html"><b>Home</b><br><?php echo $file_path."api.php?home"?><br><br><b>Latest Books</b><br><?php echo $file_path."api.php?latest"?><br><br><b>Search Books</b><br><?php echo $file_path."api.php?search_text=Wi"?><br><br><b>Category List</b><br><?php echo $file_path."api.php?cat_list"?><br><br><b>Author List</b><br><?php echo $file_path."api.php?author_list"?><br><br><b>Books list by Cat ID</b><br><?php echo $file_path."api.php?cat_id=3"?><br><br><b>Books list by Author ID</b><br><?php echo $file_path."api.php?author_id=1"?><br><br><b>Single Book</b><br><?php echo $file_path."api.php?book_id=20"?><br><br><b>Rating</b><br><?php echo $file_path."api_rating.php?book_id=20&device_id=123&user_id=2&rate=4"?><br><br><b>User Comment</b><br><?php echo $file_path."api_comment.php?book_id=20&user_name=kuldip&comment_text=test"?><br><br><b>App Details</b><br><?php echo $file_path."api.php?app_details"?><br><br><b>User Register</b><br><?php echo $file_path."user_register_api.php?name=john&email=john@gmail.com&password=123456&phone=1234567891"?><br><br><b>User Login</b><br><?php echo $file_path."user_login_api.php?email=john@gmail.com&password=123456"?><br><br><b>User Profile</b><br><?php echo $file_path."user_profile_api.php?id=2"?><br><br><b>User Profile Update</b><br><?php echo $file_path."user_profile_update_api.php?user_id=2&name=john&email=john@gmail.com&password=123456&phone=1234567891"?><br><br><b>Forgot Password</b><br><?php echo $file_path."user_forgot_pass_api.php?email=john@gmail.com"?></code></pre>
       		
       				</div>
          	</div>
        </div>
</div>
    <br/>
    <div class="clearfix"></div>
        
<?php include("includes/footer.php");?>       
