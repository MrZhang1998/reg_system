<%@ page contentType="text/html;charset=UTF-8" language="java" %>

	<!--  jquery support -->
	<script src="http://localhost:8080/reg_system/js/jquery.js"></script>
	<script src="http://localhost:8080/reg_system/js/jquery.timer.js" type="text/javascript" charset="utf-8"></script>
	<!-- bootstrap support -->
	<link rel="stylesheet" href="http://localhost:8080/reg_system/css/bootstrap.css">
	<script src="http://localhost:8080/reg_system/js/bootstrap.min.js"></script>

	<!-- toastr support -->
	<link href="http://localhost:8080/reg_system/css/toastr.min.css" rel="stylesheet" />
	<script src="http://localhost:8080/reg_system/js/toastr.min.js"></script>
	<!-- 通用javascript -->
	<script src="http://localhost:8080/reg_system/js/query.js"></script>
	
	<!--
    	easyUI support
    -->
    <link rel="stylesheet" type="text/css" href="http://localhost:8080/reg_system/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="http://localhost:8080/reg_system/css/themes/icon.css">
    <script type="text/javascript" src="http://localhost:8080/reg_system/js/jquery.js"></script>
    <script type="text/javascript" src="http://localhost:8080/reg_system/js/jquery.easyui.min.js"></script>
    
	<script>
		/* 其他地方请勿使用 document.ready .. */
		$(document).ready(function () {

			/* toastr 提示 */
			toastr.options = {
				"closeButton": false, //是否显示关闭按钮
				"debug": false, //是否使用debug模式
				"positionClass": "toast-top-center", //弹出窗的位置
				"showDuration": "200", //显示的动画时间
				"hideDuration": "1000", //消失的动画时间
				"timeOut": "4000", //展现时间
				"showEasing": "swing", //显示时的动画缓冲方式
				"hideEasing": "linear", //消失时的动画缓冲方式
				"showMethod": "fadeIn", //显示时的动画方式
				"hideMethod": "fadeOut" //消失时的动画方式
			};

			/* tooltip提示 */
			$("[tooltip]").each(function (i, e) {
				$(e).tooltip({
					placement: "top",
					delay: {
						"show": 500,
						"hide": 100
					},
					title: $(e).attr("tooltip")
				})
			});
		});

		
	</script>
