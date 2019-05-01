package my.patient;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.mybatis.dao.PatientInfoMapper;
import com.mybatis.dao.UserInfoMapper;
import com.mybatis.model.PatientInfo;
import com.mybatis.model.PatientInfoExample;
import com.mybatis.model.UserInfo;
import com.mybatis.model.UserInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class AddPersonalInfoAction implements Action {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject request_data = Utility.getDataFromRequest(request);
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
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		try
		{
			// 从 userinfo 中拿到 account
			session = factory.openSession();
			UserInfoMapper userInfoMapper = session.getMapper(UserInfoMapper.class);
			UserInfoExample example = new UserInfoExample();
			UserInfoExample.Criteria criteria = example.createCriteria();
			criteria.andAccountEqualTo(account);
			List<UserInfo> list = userInfoMapper.selectByExample(example);
			if(list == null || list.size() == 0)
			{
				resp.put("error", -1);
				resp.put("reason", "该账号不存在");
				Utility.putDataToResponse(response, resp);
				return null;
			}
			String uid = list.get(0).getUid();
			// 根据uid 查询是否已有记录
			// 如果存在 则更新
			if(isExists(session, uid)){
				update(session, uid, request_data);
				resp.put("error", 0);
				JSONObject data = new JSONObject();
				data.put("message", "更新成功");
				resp.put("data", data);
				Utility.putDataToResponse(response, resp);
				return null;
			}
			
			// 开始储存 patient info 
			insert(session, uid, request_data);
			// 返回前端
			resp.put("error", 0);
			JSONObject data = new JSONObject();
			data.put("message", "添加成功");
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
	private void update (SqlSession session ,String uid,JSONObject request_data){
		PatientInfo patientInfo = new PatientInfo();
		patientInfo.setName(request_data.getString("name"));
		patientInfo.setGender(request_data.getString("gender"));
		patientInfo.setAge(Integer.valueOf(request_data.getString("age")));
		patientInfo.setPhone(request_data.getString("phone"));
		patientInfo.setIdcard(request_data.getString("IDcard"));
		patientInfo.setDescription(request_data.getString("desc"));
		// 存入数据库
		PatientInfoMapper mapper = session.getMapper(PatientInfoMapper.class);
		PatientInfoExample example = new PatientInfoExample();
		PatientInfoExample.Criteria  criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		mapper.updateByExampleSelective(patientInfo, example);
		session.commit();
	}
	
	private boolean isExists(SqlSession session ,String uid){
		PatientInfoMapper mapper = session.getMapper(PatientInfoMapper.class);
		PatientInfoExample example = new PatientInfoExample();
		PatientInfoExample.Criteria  criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		
		List<PatientInfo> list = mapper.selectByExample(example);
		if(list == null || list.size() == 0)
			return false;
		return true;
	}
	
	private void insert(SqlSession session ,String uid,JSONObject request_data){
		PatientInfo patientInfo = new PatientInfo();
		patientInfo.setId(Utility.generateUUID());
		patientInfo.setUid(uid);
		patientInfo.setName(request_data.getString("name"));
		patientInfo.setGender(request_data.getString("gender"));
		patientInfo.setAge(Integer.valueOf(request_data.getString("age")));
		patientInfo.setPhone(request_data.getString("phone"));
		patientInfo.setIdcard(request_data.getString("IDcard"));
		patientInfo.setDescription(request_data.getString("desc"));
		// 存入数据库
		PatientInfoMapper mapper = session.getMapper(PatientInfoMapper.class);
		mapper.insert(patientInfo);
		session.commit();
	}

}
