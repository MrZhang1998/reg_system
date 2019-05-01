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
				<div id="" class="item_option" onclick="add_message()">
					添加公告
				</div>
				<hr />
				<div id="" class="item_option" onclick="addDoctorInfo()">
					添加医生信息
				</div>
				<hr />
				<div id="" class="item_option" onclick="deleteDoctorInfo()">
					删除医生信息
				</div>
				
				<hr />
				<div id="" class="item_option" onclick="manage_p()">
					查看所有患者信息
				</div>
			</div>
			<div data-options="region:'center'" style="padding:5px;">
				<div id="main_panel">
				
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			function add_message () {
				Af.loadPage("#main_panel", "resource/A/add_message.html");
			}
			function addDoctorInfo () {
				Af.loadPage("#main_panel", "resource/A/add_doctor_info.html");
			}
			function deleteDoctorInfo () {
				Af.loadPage("#main_panel", "resource/A/delete_doctor_info.html");
			}
			function manage_p () {
				Af.loadPage("#main_panel", "resource/A/manage_p.html");
			}
		</script>
	</body>

</html>