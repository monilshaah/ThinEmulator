$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


$(function() {
    $('#signup').submit(function(event) {
    	if(!this.checkValidity())
    		{
  				event.preventDefault();
				return false;
			} else {
				event.preventDefault();
    			$.ajax({
    				url : $( '#signup' ).attr( 'action' ),
    				data :  JSON.stringify($('#signup').serializeObject()),
    				type : "POST",
    				contentType : "application/json",
    				success: function(result) {
    					location.href = "/welcome";
    				},
    				failure : function(result) {
    					console.log(result);
    					alert("Some unexpected error occurred, Please try later");
    				}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
    		}  
    	});
});


$(function() {
	$('#signin').submit(function(event){
		if(!this.checkValidity()) {
			event.preventDefault();
			return false;
		} else {
			event.preventDefault();
			$.ajax({
				url :$( '#signin' ).attr( 'action' ),
				data :  JSON.stringify($('#signin').serializeObject()),
				type : "POST",
				contentType : "application/json",
				success: function(result) {
					var resultObj = JSON.parse(result);
					if(resultObj.success == "true") {
						storeUserInfo(resultObj.username, resultObj.email);
						location.href = "/home/" + resultObj.username;
					} else {
						alert("Invalid login details");
					}
				},
				failure : function(result) {
					console.log(result);
				}
			});
		}
	});
});

function validate_login(email, username) {
	var local_stored_username =getStoredUserInfo().username;
	var local_stored_email = getStoredUserInfo().email;
	if(username != local_stored_username || email != local_stored_email) {
	location.href = "/";	
	}
}

$(function() {
	$('#signout').click(function(event){
		clearStoredUserInfo();
		location.href = "/";
	});
});


function supportsLocalStorage()
{
	try {
		return 'localStorage' in window && window['localStorage'] !== null;
	} 
	catch (e)  {
		return false;
	}
}

function storeUserInfo(username, email)
{
	if(supportsLocalStorage()) {
		localStorage.setItem("username", username);
		localStorage.setItem("email", email);
	}
	else {
		console.log("Browser doesn't support local storage");
	}
}

function getStoredUserInfo()
{
	if(supportsLocalStorage()) {
		return {
			"username": localStorage.getItem("username"),
			"email": localStorage.getItem("email")}
	} else {
		console.log("Browser doesn't support local storage");
	}
}

function clearStoredUserInfo()
{
	if(supportsLocalStorage()) {
		localStorage.removeItem("username");
		localStorage.removeItem("email");
	} else {
		console.log("Browser doesn't support local storage");
	}
}

$(function() {
    $('#createnew').submit(function(event) {
    	if(!this.checkValidity())
    		{
    			console.log("failed in validation");
  				event.preventDefault();
				return false;
			} else {
			console.log("validation successful");
				event.preventDefault();
    			$.ajax({
    				url : "/createnewdevice/"+$( '#createnew' ).attr( 'action' ),
    				data :  JSON.stringify($('#createnew').serializeObject()),
    				type : "POST",
    				contentType : "application/json",
    				success: function(result) {
    					console.log(result);
    				},
    				failure : function(result) {
    					console.log(result);
    					alert("Some unexpected error occurred, Please try later");
    				}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
    		}  
    	});
});


function createnewdevice() {
$.ajax({
    				url : "/users/"+$( '#createnew' ).attr( 'action' )+"/emulators",
    				data :  JSON.stringify($('#createnew').serializeObject()),
    				type : "POST",
    				contentType : "application/json",
    				success: function(result) {
    					loadconfigureddevices($( '#createnew' ).attr( 'action' ));
    				},
    				failure : function(result) {
    					console.log(result);
    					alert("Some unexpected error occurred, Please try later");
    				}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
}
function loadcreatenew(username) {
 	var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = "./../js/createnewconfig.js";
    head.appendChild(script);
    var url = '/loadnewdeviceform/'+username;
    
    document.getElementById("emulatordetails").innerHTML = "";
    console.log(url);
    
    $.ajax({
    			url : url,
    			data :  JSON.stringify($('#createnew').serializeObject()),
    			type : "GET",
    			contentType : "application/json",
    			success: function(result) {
    				$("#placeholder").html(
    				$("#createnewemulator").render({result:result,username:username})
    			)},
    			failure : function(result) {
    				console.log(result);
    				alert("Some unexpected error occurred, Please try later");
    			}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
    
    //$("#placeholder").hide().load(url).slideDown( "slow" ).fadeIn(500);
}

function loadconfigureddevices(username) {
    var url = "/users/"+username+"/emulators";
    
    document.getElementById("emulatordetails").innerHTML = "";
    
     $.ajax({
    			url : url,
    			data :  "",
    			type : "GET",
    			contentType : "application/json",
    			success: function(result) {
    				$("#placeholder").html(
    				$("#listemulators").render({result:result,username:username})
    			)},
    			failure : function(result) {
    				console.log(result);
    				alert("Some unexpected error occurred, Please try later");
    			}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
    //$("#placeholder").hide().load(url).slideDown( "slow" ).fadeIn(500);
}
function emulatordetails(id, name, deviceType, username) {
	console.log(id);
	console.log(name);
	console.log(deviceType);
// Adding the script tag to the head as suggested before
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = "./../js/upload.js";
    head.appendChild(script);
	var htmlelements =	"<div class='customspan35 well'>"+
					"<p> <b>Emulator Name :</b> <span style='word-wrap: break-word; width: 10%'>"+name+"</span>"+
					"<br/><br/><b> Device Type :</b> "+deviceType+
					"</p>"+
					"<form enctype='multipart/form-data' name='fileinfo'>"+
					"Upload APK file : <input type='file' name='file'><br /> "+
					 "<br /> Name: <input type='text' name='name' maxlength='32' />"+ 
					 "<input type='submit' class='btn btn-primary alignright' value='Upload'/>"+
					  "<input type='button' class='btn btn-primary alignright' value='Start' onclick='access_emulator("+name+","+username+")'/>"+
					  "<input type='button' class='btn btn-primary alignright' value='Delete' onclick='delete_emulator("+name+","+username+")'/>"+
					"</form>"+
					"<div id='output'></div>"+
					"</div>";
					document.getElementById("emulatordetails").style.visibilit = "hidden";
					document.getElementById("emulatordetails").innerHTML = htmlelements;
					
	$("#emulatordetails").html(
    $("#emulator").render({name:id,username:username,deviceType:deviceType, id:id}))
    document.getElementById("emulatordetails").style.visibilit = "visible";
}



function access_emulator(name, username) {
	
	//"/users/"+username+"/emulators/"+name);
	
	$.ajax({
    				url : "/users/"+username+"/emulators/"+name,
    				data : "",
    				type : "GET",
    				contentType : "application/json",
    				success: function(result) {
    					window.open("/accessnow/"+result.processId);
    				},
    				failure : function(result) {
    					console.log(result);
    					alert("Some unexpected error occurred, Please try later");
    				}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
}



function stop_emulator(name, username) {
$.ajax({
    				url : "/users/"+username+"/emulators/"+name,
    				data : "",
    				type : "PUT",
    				contentType : "application/json",
    				success: function(result) {
    					loadconfigureddevices(username);
    				},
    				failure : function(result) {
    					console.log(result);
    					alert("Some unexpected error occurred, Please try later");
    				}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
}
function delete_emulator(name, username) {
$.ajax({
    				url : "/users/"+username+"/emulators/"+name,
    				data : "",
    				type : "DELETE",
    				contentType : "application/json",
    				success: function(result) {
    					loadconfigureddevices(username);
    				},
    				failure : function(result) {
    					console.log(result);
    					alert("Some unexpected error occurred, Please try later");
    				}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
}

function retry_telnet() {
$.ajax({
    				url : "/retrytelnet",
    				data : "",
    				type : "GET",
    				contentType : "application/json",
    				success: function(result) {
    					location.reload();
    				},
    				failure : function(result) {
    					console.log(result);
    					alert("Some unexpected error occurred, Please try later");
    				}
    			}).error(function(status, result, xhr){
    				alert(JSON.parse(status.responseText).message);
    			});
}