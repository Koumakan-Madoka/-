layui.config({
 	base: '../../resource/extjs/'
});

layui.use(['jquery'], function(){
	var $ = layui.jquery;
	$(function() {
		$(this).cmm_code();
		$(this).cmm_category();
	});
});