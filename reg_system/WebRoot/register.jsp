<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <jsp:include page="include.jsp"></jsp:include>
    <meta name="description" content="Login - Register Template">
    <meta name="author" content="Lorenzo Angelino aka MrLolok">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/main.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    
    
</head>

<body>
    <div id="container-register">
        <div id="title">
            <i class="material-icons lock">lock</i> 注册
        </div>

        <form>
            

            <div class="clearfix"></div>

            <div class="input">
                <div class="input-addon">
                    <i class="material-icons">face</i>
                </div>
                <input id="account" placeholder="账号" type="text" required class="validate" autocomplete="off">
            </div>

            <div class="clearfix"></div>

            <div class="input">
                <div class="input-addon">
                    <i class="material-icons">vpn_key</i>
                </div>
                <input id="password" placeholder="密码" type="password" required class="validate" autocomplete="off">
            </div>
            
			<!--
            <div class="remember-me">
                <input type="checkbox">
                <span style="color: #DDD">I accept Terms of Service</span>
            </div>
            -->
            <input type="button" value="注册" onclick="doRegister()"/>
        </form>

        <div class="privacy">
            <a href="#">Privacy Policy</a>
        </div>

        <div class="register">
            已经注册好了账号？
            <a href="./login.jsp"><button id="register-link">点击此处进行登录</button></a>
        </div>
    </div>
    
    <script type="text/javascript">
    	function doRegister() {
				var req = {};
				req.account = $("#account").val();
				req.password = $("#password").val();
				// 只有病人可以注册 账号
				req.type = 'P';
				Af.rest('register.action', req,
					function(data) {
						
						toastr.info(data.message);
					},
					function(error, reason) {
						toastr.error(reason);
					}
				);
			}

    </script>
</body>

</html>
