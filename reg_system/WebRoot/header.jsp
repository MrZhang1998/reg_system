<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'header.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<jsp:include page="include.jsp"></jsp:include>
		<%
		String USER = "'"+(String)request.getSession().getAttribute("account")+"'";
	%>
		<style type="text/css">
			.user {
				font-size: x-large;
				color: black;
				text-align: right;
			}
			
			.logo {
				font-size: x-large;
				color: #000000;
				font-weight: bold;
				text-align: left;
			}
			
			.main_color {
				background-color: rgba(61, 162, 191,0.7);
			}
			.quit{
				text-decoration: underline;
				color: black;
			}
		</style>
	</head>

	<body>
		<div class="container-fluid main_color" style="">

			<div class="row">
				<div class="col-lg-9 logo">

				网上挂号系统
				</div>
				<div class="col-lg-3">
					<p href="" class="user" style="font-weight: bold;">欢迎你,null用户</a>
				</div>

			</div>

			<div class="row">
				<div class="col-lg-12 text-right">
					<a href="logout.action" class="quit">退出登录</a>
				</div>

			</div>
		</div>
		<script type="text/javascript">
			var user = <%=USER %>;

			var message = "欢迎你," + user + "";
			$(".user").html(message);
		</script>
	</body>

</html>