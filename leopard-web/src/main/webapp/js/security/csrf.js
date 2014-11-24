var Csrf = {
	"loadToken" : function(callback) {
		var token = this.getCookieToken();
		if (token == null || token == "") {
			this.loadHttpToken(function(token) {
				callback(token);
			});
		} else {
			callback(token);
		}
	},
	"loadHttpToken" : function(callback) {
		var hostname = document.location.hostname;
		var domain;
		if (hostname.indexOf("yy.com") != -1) {
			domain = "message.game.yy.com";
		} else if (hostname.indexOf("duowan.com") != -1) {
			domain = "message.lobby.duowan.com";
		} else {
			alert("未知域名[" + hostname + "].");
			return;
		}
		var param = {
			type : "get",
			url : "http://" + domain + "/getCsrfToken.do",
			dataType : 'jsonp',
			jsonpCallback : "callback",
			async : true,
			cache : false,
			success : function(data) {
				var token = this.getCookieToken();
				callback(token);
			},
			error : function(result) {
				var token = "errmsg:" + result.responseText;
				callback(token);
			}
		}
		return $.ajax(param);
	},
	"getCookieToken" : function() {
		var cookieName = "csrf-token";
		var arr = document.cookie.match(new RegExp("(^| )" + cookieName
				+ "=([^;]*)(;|$)"));
		if (arr != null) {
			return unescape(arr[2]);
		}
		return null;
	},
	"end" : ""
};
