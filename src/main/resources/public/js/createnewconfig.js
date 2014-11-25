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