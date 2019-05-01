package my.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import Util.Action;

public class LogoutAction implements Action {

	@Override
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		session.setAttribute("account", null);
		session.setAttribute("type", null);
		session.setAttribute("uid", null);
		String reDirectUrl = request.getScheme() //当前链接使用的协议
			    +"://" + request.getServerName()//服务器地址 
			    + ":" + request.getServerPort() //端口号 
			    + request.getContextPath() //应用名称，如果应用名称为
			    + "/login.jsp";
		
		response.sendRedirect(reDirectUrl);
		return null;
	}

}
