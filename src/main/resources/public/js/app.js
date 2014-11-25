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
   		if(!this.checkValidity())
    	{
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
    				if(JSON.parse(result).success == "true") {
    					location.href = "/home/"+JSON.parse(result).username;
    					console.log(result);
    					//$().redirect('home',result);
    				}else {
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
    				url : "/createnewdevice/"+$( '#createnew' ).attr( 'action' ),
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
    
    $("#placeholder").hide().load(url).slideDown( "slow" ).fadeIn(500);
}

function loadconfigureddevices(username) {
    var url = '/loadconfigureddevices/'+username;
    
    document.getElementById("emulatordetails").innerHTML = "";
    
    $("#placeholder").hide().load(url).slideDown( "slow" ).fadeIn(500);
}
function emulatordetails(id, name, status, deviceType) {
// Adding the script tag to the head as suggested before
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = "./../js/upload.js";
    head.appendChild(script);
	var htmlelements =	"<div class='customspan35 well'>"+
					"<p> <b>Emulator Name :</b> <span style='word-wrap: break-word; width: 10%'>"+name+"</span>"+
					"<br/><br/><b> Status :</b> "+status+
					"<br/><br/><b> Device Type :</b> "+deviceType+
					"</p>"+
					"<form enctype='multipart/form-data' name='fileinfo'>"+
					"Upload APK file : <input type='file' name='file'><br /> "+
					 "<br /> Name: <input type='text' name='name' maxlength='32' />"+ 
					 "<input type='submit' class='btn btn-primary alignright' value='Upload'/>"+
					"</form>"+
					"<div id='output'></div>"+
					"</div>";
					document.getElementById("emulatordetails").innerHTML = htmlelements;
}
