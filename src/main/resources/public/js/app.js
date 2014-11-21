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
    			url :$( '#signup' ).attr( 'action' ),
    			data :  JSON.stringify($('#signin').serializeObject()),
    			type : "POST",
    			contentType : "application/json",
    			success: function(result) {
    				if(JSON.parse(result).success == "true") {
    					location.href = "/home";
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