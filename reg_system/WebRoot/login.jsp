<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<meta name="description" content="Login - Register Template">
		<meta name="author" content="Lorenzo Angelino aka MrLolok">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

		<jsp:include page="include.jsp"></jsp:include>
		<link rel="stylesheet" href="css/main.css">
	</head>

	<body>
		<div id="container-login">
			<div id="title">
				<i class="material-icons lock">lock</i> 挂号系统登录
			</div>

			<form>
				<div class="input">
					<div class="input-addon">
						<i class="material-icons">face</i>
					</div>
					<input id="account" placeholder="输入您的账号" type="text" required class="validate" autocomplete="off">
				</div>

				<div class="clearfix"></div>

				<div class="input">
					<div class="input-addon">
						<i class="material-icons">vpn_key</i>
					</div>
					<input id="password" placeholder="输入您的密码" type="password" required class="validate" autocomplete="off">
				</div>

				<div class="remember-me">
					<select>
						<option value="A">管理员</option>
						<option value="P">患者</option>
						<option value="D">医生</option>
					</select>
				</div>

				<input type="button" value="登录" onclick="doLogin()" />
			</form>

			<div class="privacy">
				<a href="#">Privacy Policy</a>
			</div>

			<div class="register">
				还没有账号？点击下面
				<a href="./register.jsp"><button id="register-link">  点击此处注册  </button></a>
			</div>
		</div>

		<script type="text/javascript">
			function doLogin() {
				var req = {};
				req.account = $("#account").val();
				req.password = $("#password").val();
				req.type = $("select").val();

				Af.rest('login.action', req,
					function(data) {
						var str = "http://localhost:8080/reg_system/resource/"+data.type+"/index.jsp";
						window.location = str;
					},
					function(error, reason) {
						toastr.error(reason);
					}
				);
			}
		</script>
	</body>

</html>