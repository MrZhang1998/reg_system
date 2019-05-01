package my.common;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import com.mybatis.dao.UserInfoMapper;
import com.mybatis.model.UserInfo;
import com.mybatis.model.UserInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class LoginAction implements Action {
	public String execute() throws IOException {
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject request_data = Utility.getDataFromRequest(request);
		// 返回给前端的数据 json
		
		JSONObject resp = new JSONObject();
		
		String account = request_data.getString("account");
		String password = request_data.getString("password");
		String type = request_data.getString("type");
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;

		try
		{
			session = factory.openSession();
			UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
			UserInfoExample example = new UserInfoExample();
			UserInfoExample.Criteria criteria = example.createCriteria();
			criteria.andAccountEqualTo(account);
			List<UserInfo> info =mapper.selectByExample(example);
			if(info == null ||info.size() == 0){
				resp.put("error", -1);
				resp.put("reason", "无此账户");
				Utility.putDataToResponse(response, resp);
				return null;
			}
			UserInfo userInfo = info.get(0);
			if(userInfo.getPassword().equals(password) && userInfo.getType().equals(type))
			{
				// 登录成功
				resp.put("error", 0);
				JSONObject data = new JSONObject();
				resp.put("data", data);
				// 存到http session 里面
				data.put("message", "登录成功");
				data.put("type", type);
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("account", userInfo.getAccount());
				httpSession.setAttribute("type", userInfo.getType());
				httpSession.setAttribute("uid", userInfo.getUid());
				
			}else{
				resp.put("error", -1);
				resp.put("reason", "密码错误或登录类型选择错误");
				JSONObject data = new JSONObject();
				resp.put("data", data);
			}
			Utility.putDataToResponse(response, resp);
		} finally
		{
			// TODO: handle finally clause
			if (session!=null)
			{
				session.close();
			}
		}
		return null;
	}
}
