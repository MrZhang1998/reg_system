package my.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class RegisterAction implements Action {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject request_data = Utility.getDataFromRequest(request);
		JSONObject resp = new JSONObject();
		
		String account = request_data.getString("account");
		String password = request_data.getString("password");
		String type = request_data.getString("type");
		
		
		// 数据库操作
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		try
		{
			session = factory.openSession();
			UserInfoExample example = new UserInfoExample();
			UserInfoExample.Criteria criteria = example.createCriteria();
			criteria.andAccountEqualTo(account);
			UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
			List<UserInfo> list = mapper.selectByExample(example);
			if(list == null || list.size() == 0){
				UserInfo userInfo = new UserInfo();
				userInfo.setAccount(account);
				userInfo.setPassword(password);
				userInfo.setType(type);
				userInfo.setUid(Utility.generateUUID());
				int count = mapper.insert(userInfo);
				System.out.println("此次操作影响 "+count + "条记录");
				resp.put("error", 0);
				JSONObject data = new JSONObject();
				data.put("message", "注册成功");
				resp.put("data", data);
			}else {
				resp.put("error", -1);
				resp.put("reason", "账户已经存在");
				JSONObject data = new JSONObject();
				resp.put("data", data);
			}
			session.commit();
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
