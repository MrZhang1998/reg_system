package my.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.mybatis.dao.DepartmentInfoMapper;
import com.mybatis.dao.DoctorInfoMapper;
import com.mybatis.dao.UserInfoMapper;
import com.mybatis.model.DepartmentInfo;
import com.mybatis.model.DepartmentInfoExample;
import com.mybatis.model.DoctorInfo;
import com.mybatis.model.UserInfo;
import com.mybatis.model.UserInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class AddDoctorInfoAction implements Action {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 返回给前端的数据 json
		// 拿到当前登录用户的account
		HttpSession httpSession = request.getSession();
		String uid = (String) httpSession.getAttribute("uid");
		JSONObject resp = new JSONObject();
		if(uid == null || uid.equals("")){
			resp.put("error", -1);
			resp.put("reason", "您还没有登录");
			Utility.putDataToResponse(response, resp);
			return null;
		}
		JSONObject resq_data = Utility.getDataFromRequest(request);
		String account = resq_data.getString("account");
		String password = resq_data.getString("password");
		String departement = resq_data.getString("departement");
		String education = resq_data.getString("education");
		String name = resq_data.getString("name");
		String gender = resq_data.getString("gender");
		String age = resq_data.getString("age");
		String phone = resq_data.getString("phone");
		String desc = resq_data.getString("desc");
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		try
		{
			session = factory.openSession();
			
			if (!isDepartmentExists(departement, session)) {
				resp.put("error", -1);
				resp.put("reason", "部门不存在");
				Utility.putDataToResponse(response, resp);
				return null;
			}
			
			if(isAccountExists(account, session)){
				resp.put("error", -1);
				resp.put("reason", "账户已经存在，请重新设置账户");
				Utility.putDataToResponse(response, resp);
				return null;
			}
			// 创建账户信息
			UserInfo userInfo = new UserInfo();
			userInfo.setAccount(account);
			userInfo.setPassword(password);
			userInfo.setUid(Utility.generateUUID());
			userInfo.setType("D");
			insertUserInfo(session, userInfo);
			
			// 创建医生信息
			DoctorInfo doctorInfo = new DoctorInfo();
			doctorInfo.setId(Utility.generateUUID());
			doctorInfo.setUid(userInfo.getUid());
			doctorInfo.setDepartement(departement);
			doctorInfo.setName(name);
			doctorInfo.setGender(gender);
			doctorInfo.setPhone(phone);
			doctorInfo.setDescription(desc);
			doctorInfo.setEducation(education);
			doctorInfo.setAge(age);
			insertDoctorInfo(session, doctorInfo);
			
			resp.put("error", 0);
			JSONObject data = new JSONObject();
			data.put("message", "成功添加信息");
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
	
	
	private boolean isAccountExists(String account,SqlSession session){
		UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria criteria = example.createCriteria();
		criteria.andAccountEqualTo(account);
		List<UserInfo> selectByExample = mapper.selectByExample(example);
		if(selectByExample == null || selectByExample.size() == 0)
			return false;
		return true;
	}
	private boolean isDepartmentExists(String id,SqlSession session){
		DepartmentInfoMapper mapper = session.getMapper(DepartmentInfoMapper.class);
		DepartmentInfoExample example = new DepartmentInfoExample();
		DepartmentInfoExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<DepartmentInfo> selectByExample = mapper.selectByExample(example);
		if(selectByExample == null || selectByExample.size() == 0)
			return false;
		return true;
	}
	private void insertUserInfo(SqlSession session, UserInfo info){
		UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
		mapper.insert(info);
		session.commit();
	}
	
	private void insertDoctorInfo(SqlSession session, DoctorInfo info){
		DoctorInfoMapper mapper = session.getMapper(DoctorInfoMapper.class);
		mapper.insert(info);
		session.commit();
	}

}
