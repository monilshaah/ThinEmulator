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
	$('#signupbutton').click(function() {
		$.ajax({
			url : $( '#signup' ).attr( 'action' ),
			data :  JSON.stringify($('#signup').serializeObject()),
			type : "POST",
			contentType : "application/json",
			success: function(result) {
				console.log(result);
			},
			failure : function(result) {
				console.log(result);
			}
		});
	});

	$('#loginbutton').click(function() {
		$.ajax({
			url : $('#login').attr('action'),
			data : JSON.stringify($('#login').serializeObject()),
			type : "POST",
			contentType : "application/json",
			success: function(result) {
				console.log(result);
			},
			failure : function(result) {
				console.log(result);
			}
		});
	});
});