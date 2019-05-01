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
import com.mybatis.model.DoctorInfo;
import com.mybatis.model.DoctorInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class GetAllDocotorInfoAction implements Action {

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
			List<DoctorInfo> list = getAllDoctors(session);
			JSONArray array = new JSONArray();
			for(DoctorInfo info : list){
				JSONObject object = new JSONObject();
				object.put("department", info.getDepartement());
				object.put("name", info.getName());
				object.put("gender", info.getGender());
				object.put("age", info.getAge());
				object.put("phone", info.getPhone());
				object.put("education", info.getEducation());
				object.put("id", info.getId());
				object.put("uid", info.getUid());
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
	
	private List<DoctorInfo> getAllDoctors(SqlSession session) {
		DoctorInfoExample example = new DoctorInfoExample();
		DoctorInfoExample.Criteria criteria = example.createCriteria();

		DoctorInfoMapper mapper = session.getMapper(DoctorInfoMapper.class);
		List<DoctorInfo> selectByExample = mapper.selectByExample(example);
		if (selectByExample == null)
			return new ArrayList<DoctorInfo>();
		return selectByExample;
	}

}
