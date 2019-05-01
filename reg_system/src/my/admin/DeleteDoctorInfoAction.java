package my.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.mybatis.dao.DoctorInfoMapper;
import com.mybatis.dao.UserInfoMapper;
import com.mybatis.model.DoctorInfoExample;
import com.mybatis.model.UserInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class DeleteDoctorInfoAction implements Action {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 返回给前端的数据 json
		// 拿到当前登录用户的account
		HttpSession httpSession = request.getSession();
		String account = (String) httpSession.getAttribute("account");
		JSONObject resp = new JSONObject();
		if(account == null || account.equals("")){
			resp.put("error", -1);
			resp.put("reason", "您还没有登录");
			Utility.putDataToResponse(response, resp);
			return null;
		}
		JSONObject req_data = Utility.getDataFromRequest(request);
		String id = req_data.getString("id");
		String uid = req_data.getString("uid");
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		try
		{
			session = factory.openSession();
			// 删除 医生信息
			deleteDoctorInfoByid(id, session);
			// 删除对应账户
			deleteUserInfoById(uid, session);
			resp.put("error", 0);
			JSONObject data = new JSONObject();
			data.put("message", "删除成功");
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
	private void deleteUserInfoById(String uid,SqlSession session){
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
		int num = mapper.deleteByExample(example);
		System.out.println("删除"+num+"条医生信息");
		session.commit();
	}
	
	private void deleteDoctorInfoByid(String id, SqlSession session){
		DoctorInfoExample example = new DoctorInfoExample();
		DoctorInfoExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		DoctorInfoMapper mapper = session.getMapper(DoctorInfoMapper.class);
		int num = mapper.deleteByExample(example);
		System.out.println("删除"+num+"条医生信息");
		session.commit();
		
	}

}
