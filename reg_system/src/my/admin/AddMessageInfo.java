package my.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.mybatis.dao.MessageInfoMapper;
import com.mybatis.model.MessageInfo;
import com.mybatis.model.MessageInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class AddMessageInfo implements Action {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 返回给前端的数据 json
		// 拿到当前登录用户的account
		HttpSession httpSession = request.getSession();
		String uid = (String) httpSession.getAttribute("uid");
		String account = (String)httpSession.getAttribute("account");
		JSONObject resp = new JSONObject();
		if(uid == null || uid.equals("")){
			resp.put("error", -1);
			resp.put("reason", "您还没有登录");
			Utility.putDataToResponse(response, resp);
			return null;
		}
		JSONObject resq_data = Utility.getDataFromRequest(request);
		String content = resq_data.getString("content");
		if(content == null || content.trim().equals("")){
			resp.put("error", -1);
			resp.put("reason", "您还没有输入");
			Utility.putDataToResponse(response, resp);
			return null;
		}
		MessageInfo record = new MessageInfo();
		record.setId(Utility.generateUUID());
		record.setContent(content);
		record.setTimeCreated(new Date());
		record.setSender(account);
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		try
		{
			session = factory.openSession();
			insertMessageInfo(session, record);
			resp.put("error", 0);
			JSONObject data = new JSONObject();
			data.put("message", "成功发布公告");
			resp.put("data", data);
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
	
	private void insertMessageInfo(SqlSession session , MessageInfo record){
		MessageInfoMapper mapper = session.getMapper(MessageInfoMapper.class);
		mapper.insertSelective(record);
		session.commit();
	}

}
