<?php include("includes/header.php");

$qry_cat="SELECT COUNT(*) as num FROM tbl_category";
$total_category= mysqli_fetch_array(mysqli_query($mysqli,$qry_cat));
$total_category = $total_category['num'];

$qry_books="SELECT COUNT(*) as num FROM tbl_books";
$total_books = mysqli_fetch_array(mysqli_query($mysqli,$qry_books));
$total_books = $total_books['num'];

$qry_author="SELECT COUNT(*) as num FROM tbl_author";
$total_author = mysqli_fetch_array(mysqli_query($mysqli,$qry_author));
$total_author = $total_author['num'];

?>       


        <div class="btn-floating" id="help-actions">
      <div class="btn-bg"></div>
      <button type="button" class="btn btn-default btn-toggle" data-toggle="toggle" data-target="#help-actions"> <i class="icon fa fa-plus"></i> <span class="help-text">Shortcut</span> </button>
      <div class="toggle-content">
        <ul class="actions">
          <li><a href="http://www.viaviweb.com" target="_blank">Website</a></li>
           <li><a href="https://codecanyon.net/user/viaviwebtech?ref=viaviwebtech" target="_blank">About</a></li>
        </ul>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12"> <a href="manage_category.php" class="card card-banner card-green-light">
        <div class="card-body"> <i class="icon fa fa-sitemap fa-4x"></i>
          <div class="content">
            <div class="title">Categories</div>
            <div class="value"><span class="sign"></span><?php echo $total_category;?></div>
          </div>
        </div>
        </a> </div>
         <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12"> <a href="manage_author.php" class="card card-banner card-yellow-light">
        <div class="card-body"> <i class="icon fa fa-users fa-4x"></i>
          <div class="content">
            <div class="title">Author</div>
            <div class="value"><span class="sign"></span><?php echo $total_author;?></div>
          </div>
        </div>
        </a> </div>
      <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12"> <a href="manage_books.php" class="card card-banner card-blue-light">
        <div class="card-body"> <i class="icon fa fa-book fa-4x"></i>
          <div class="content">
            <div class="title">Books</div>
            <div class="value"><span class="sign"></span><?php echo $total_books;?></div>
          </div>
        </div>
        </a> </div>
     
    </div>

        
<?php include("includes/footer.php");?>       
