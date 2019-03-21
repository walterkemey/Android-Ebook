function checkValidation()
{
	if(document.getElementById('username').value=="")
	{
		alert("Please Enter Your user name .!");
		 
		document.getElementById('username').focus();
		document.getElementById('username').select();
		return false;
	}	 
	
	if(document.getElementById('password').value=="")
	{
		alert("Please Enter Your password .!");
		 
		document.getElementById('password').focus();
		document.getElementById('password').select();
		return false;
	}
	  
	
	return true;
} 