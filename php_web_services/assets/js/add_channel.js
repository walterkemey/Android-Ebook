function checkValidation()
{
	 
	if(document.getElementById('category_id').value=="")
	{
		alert("Please Select Category.!");
		 
		document.getElementById('category_id').focus();		 
		return false;
	}
	if(document.getElementById('channel_title').value=="")
	{
		alert("Please enter channel title!");
		 
		document.getElementById('channel_title').focus();
		document.getElementById('channel_title').select();		 
		return false;
	}
	if(document.getElementById('channel_url').value=="")
	{
		alert("Please enter channel URL!");
		 
		document.getElementById('channel_url').focus();
		document.getElementById('channel_url').select();		 
		return false;
	}
		if(document.getElementById('thumb').value=="")
	{
		alert("Please enter channel image!");
		 
		document.getElementById('thumb').focus();
		document.getElementById('thumb').select();		 
		return false;
	}

	if(document.getElementById('channel_description').value=="")
	{
		alert("Please enter channel description!");
		 
		document.getElementById('channel_description').focus();	
		document.getElementById('channel_description').select();	 
		return false;
	}
	if(document.getElementById('channel_description').value.length<20)
	{
		alert("Please enter channel description at least 20 characters.!");
		 
		document.getElementById('channel_description').focus();
		document.getElementById('channel_description').select();
		return false;
	}
	
	 
	return true;
} 
function editValidation()
{
	 
	if(document.getElementById('category_id').value=="")
	{
		alert("Please Select Category.!");
		 
		document.getElementById('category_id').focus();		 
		return false;
	}
	if(document.getElementById('channel_title').value=="")
	{
		alert("Please enter channel title!");
		 
		document.getElementById('channel_title').focus();
		document.getElementById('channel_title').select();		 
		return false;
	}
	if(document.getElementById('channel_description').value=="")
	{
		alert("Please enter channel description!");
		 
		document.getElementById('channel_description').focus();	
		document.getElementById('channel_description').select();	 
		return false;
	}
	if(document.getElementById('channel_description').value.length<20)
	{
		alert("Please enter channel description at least 20 characters.!");
		 
		document.getElementById('channel_description').focus();
		document.getElementById('channel_description').select();
		return false;
	}
	
	 
	return true;
}