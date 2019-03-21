<?php
session_start();
unset($_SESSION["adminuser"]);
session_destroy();
echo "<script language=javascript>location.href='index.php';</script>";
?>