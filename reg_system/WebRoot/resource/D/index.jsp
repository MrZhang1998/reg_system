<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<meta charset="UTF-8">
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

		<div id="cc" class="easyui-layout" style="width:100%;height:100%;">

			<div class="left_menu" data-options="region:'west',title:'',split:true" style="width:140px;">
				<hr />
				<a id = "f_info" class="item_option" onclick="getFirstInfo()">公告信息</a>
				
				<hr />
				<a id="" class="item_option" onclick="display_patient_info()">
					查看已挂号患者
				</a>
				<hr />
				<a id="" class="item_option" onclick="add_patietn_info()" >
					添加病人信息
				</a>
			</div>
			<div data-options="region:'center'" style="padding:5px;">
				<div id="main_panel">
				
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			
			$(document).ready(
			function  () {
				$("#f_info").click();
			}
			);
			
			function getFirstInfo() {
				Af.loadPage("#main_panel", "resource/D/first_page_info.html");
			}
			function display_patient_info () {
				Af.loadPage("#main_panel", "resource/D/get_patient_info.html");
			}
			function add_patietn_info () {
				Af.loadPage("#main_panel", "resource/D/add_patient_info.html");
			}
		</script>
	</body>

</html>