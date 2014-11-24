Csrf.loadToken(function(token) {
	//document.getElementById("token").innerHtml = "token:"+token;
	
	var param = {
		type : "get",
		url : "http://message.game.yy.com/security/csrf/data.do?csrf-token="+token,
		dataType : 'JSONP',
		async : true,
		cache : false,
		success : function(data) {
			// Alert.show(data);
			//alert(data);
		},
		error : function(result) {
			//alert(result.responseText);
		}
	};
	return $.ajax(param);
});
