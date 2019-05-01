package my.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mybatis.dao.DoctorInfoMapper;
import com.mybatis.dao.PatientInfoMapper;
import com.mybatis.model.DoctorInfo;
import com.mybatis.model.DoctorInfoExample;
import com.mybatis.model.PatientInfo;
import com.mybatis.model.PatientInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class GetAllPatientInfoAction implements Action {

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
			List<PatientInfo> list = getAllPatient(session);
			JSONArray array = new JSONArray();
			for(PatientInfo info :list){
				JSONObject object = new JSONObject();
				object.put("name", info.getName());
				object.put("gender", info.getGender());
				object.put("age", info.getAge());
				object.put("phone", info.getPhone());
				object.put("IDcard", info.getIdcard());
				object.put("desc", info.getDescription());
				array.put(object);
			}
			resp.put("data", array);
			resp.put("error", 0);
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
	
	private List<PatientInfo> getAllPatient(SqlSession session) {
		PatientInfoExample example = new PatientInfoExample();
		PatientInfoExample.Criteria criteria = example.createCriteria();

		PatientInfoMapper mapper = session.getMapper(PatientInfoMapper.class);
		List<PatientInfo> selectByExample = mapper.selectByExample(example);
		if (selectByExample == null)
			return new ArrayList<PatientInfo>();
		return selectByExample;
	}

}
