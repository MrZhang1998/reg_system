package login.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest) arg0;//获取request对象
		HttpServletResponse response=(HttpServletResponse) arg1;//获取response对象
		HttpSession session=request.getSession();//获取session对象
		String reDirectUrl = request.getScheme() //当前链接使用的协议
			    +"://" + request.getServerName()//服务器地址 
			    + ":" + request.getServerPort() //端口号 
			    + request.getContextPath() //应用名称，
			    + "/login.jsp";
		
		String path = request.getServletPath();
		
		if(session.getAttribute("account") == null)
		{
			response.sendRedirect(reDirectUrl);
			//chain.doFilter(arg0, arg1);
			return;
		}
		
		String[] paras = path.split("/");
		
		String type = (String) session.getAttribute("type");
		boolean isOk = false;
		for(String temp : paras){
			if(temp.equals(type))
				isOk = true;
		}
		// 路径不对 无权限
		if(isOk == false){
			response.sendRedirect(reDirectUrl);
			//chain.doFilter(arg0, arg1);
			return;
		}
		
		chain.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
