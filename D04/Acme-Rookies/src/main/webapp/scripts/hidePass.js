window.onload=function(){
	var pass = document.getElementById('pass').value;
	var confirmPass = document.getElementById('pass').value;
	
	if(pass != confirmPass || (pass === "" && confirmPass === "")){
		document.getElementById('save').style.display ='none';
	}	
}
