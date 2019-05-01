<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
		<title></title>
		
		<jsp:include page="../../header.jsp"></jsp:include>

		<style type="text/css">
			.item_option {
				margin: 15px;
				color: black;
			}
			.left_menu{
				background-color: rgba(61, 162, 191,0.6) !important; 
			}
			
		</style>
	</head>

	<body>

		<div class="easyui-layout" style="width: 100%;height: 100%;">
			
			<div class="left_menu" data-options="region:'west',split:true" style="width:140px;">
				<hr />
				<a id = "f_info" class="item_option" onclick="getFirstInfo()">公告信息</a>
				
				<hr />
				<a onclick="register()" class="item_option">挂号</a>
				<hr />
				<a class="item_option" onclick="show_register_info()">查询挂号信息</a>
				<hr />
				<a onclick="add_personal_info()" class="item_option">完善个人信息</a>
				
				<hr />
				<a onclick="display_personal_info()" class="item_option">查看个人信息</a>
			</div>
			<div data-options="region:'center',title:''" style="padding:5px;">
				<div id="main_panel">
				
				</div>
			</div>
			
		</div>
		<script type="text/javascript">
			function getFirstInfo() {
				Af.loadPage("#main_panel", "resource/P/first_page_info.html");
			}
			function add_personal_info () {
				Af.loadPage("#main_panel", "resource/P/add_personal_info.html");
			}
			
			function display_personal_info () {
				Af.loadPage("#main_panel", "resource/P/display_personal_info.html");
			}
			
			function register () {
				Af.loadPage("#main_panel", "resource/P/register.html");
			}
			function show_register_info () {
				Af.loadPage("#main_panel", "resource/P/show_register_info.html");
			}
			$(document).ready(

				function (){
					// 默认动作 显示信息
					$("#f_info").click();

				}
			);
		</script>
	</body>

</html>