layui.define(['jquery'], function(exports) {
	var $ = layui.jquery;
	var login = {
		login: function(param) {
			$.ajax({
	            url: "../../admin/user/login.do",
	            data: {login:param.login, pwd:param.pwd, utype:param.utype},
	            type: "POST",
	            dataType: 'json',
	            success: function(data){
	            	if(data.flag) {
	            		window.location.href = param.toUrl;
	                } else {
	                	alert(data.msg);
                	}
	            }
			});
		},
		
		logout: function() {
			$.ajax({
	            url: "../../admin/user/logout.do",
	            data: {},
	            type: "POST",
	            dataType: 'json',
	            success: function(data){
	            	if(data.flag) {
	            		window.location.reload();
	                } else {
	                	alert(data.msg);
                	}
	            }
			});
		},
		
		checkLoginAdmin: function() {
			$.ajax({
	            url: "../../admin/user/permission.do",
	            data: {},
	            type: "POST",
	            dataType: 'json',
	            success: function(data){
	            	if(!data.flag) {
	            		//alert(data.msg);
	            		window.location ="../../";
	                }
	            }
			});
		},
				
		
	};
	exports('login', login);
});