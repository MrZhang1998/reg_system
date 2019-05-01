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
import com.mybatis.model.PatientInfo;
import com.mybatis.model.PatientInfoExample;


import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class GetPatientInfoAction implements Action {

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
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		try
		{
			session = factory.openSession();
			PatientInfoMapper mapper = session.getMapper(PatientInfoMapper.class);
			PatientInfoExample example = new PatientInfoExample();
			PatientInfoExample.Criteria  criteria = example.createCriteria();
			criteria.andUidEqualTo(uid);
			
			List<PatientInfo> list = mapper.selectByExample(example);
			if(list == null || list.size() == 0)
			{
				resp.put("error", -1);
				resp.put("reason", "查询不到记录");
				Utility.putDataToResponse(response, resp);
				return null;
			}
			PatientInfo patientInfo = list.get(0);
			resp.put("error", 0);
			JSONObject data = new JSONObject();
			data.put("id", patientInfo.getId());
			data.put("name", patientInfo.getName());
			data.put("gender", patientInfo.getGender());
			data.put("age", patientInfo.getAge());
			data.put("phone", patientInfo.getPhone());
			data.put("IDcard", patientInfo.getIdcard());
			data.put("desc", patientInfo.getDescription());
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

}
