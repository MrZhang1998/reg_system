package my.patient;

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
import com.mybatis.dao.RegInfoMapper;
import com.mybatis.model.DoctorInfo;
import com.mybatis.model.DoctorInfoExample;
import com.mybatis.model.PatientInfo;
import com.mybatis.model.PatientInfoExample;
import com.mybatis.model.RegInfo;
import com.mybatis.model.RegInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class GetAllRegInfo implements Action {

	@Override
	public String execute() throws Exception {
		System.out.println("get all reg info is running");
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
			String p_id = getPIdByUid(uid, session);
			// 从pid 获得所有挂号记录
			List<RegInfo> list = getRegInfosByPid(p_id, session);
			// 联合查询 医生名字 ，并构造结果
			JSONArray data = new JSONArray();
			for(RegInfo info : list){
				JSONObject object = new JSONObject();
				String d_id = info.getDoctor();
				DoctorInfo doctorInfo = getDoctorInfoById(session, d_id);
				if (doctorInfo == null) {
					object.put("doctor_name", "医生已删除");
					object.put("time", "医生已删除");
					object.put("desc", "医生已删除");
				}else{
					object.put("doctor_name", doctorInfo.getName());
					object.put("time", Utility.getDateString(info.getTime()));
					object.put("desc", doctorInfo.getDescription());
				}
				data.put(object);
			}
			
			resp.put("error", 0);
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
	private List<RegInfo> getRegInfosByPid(String p_id, SqlSession session){
		RegInfoMapper mapper = session.getMapper(RegInfoMapper.class);
		RegInfoExample example = new RegInfoExample();
		RegInfoExample.Criteria criteria = example.createCriteria();
		criteria.andPatientEqualTo(p_id);
		List<RegInfo> selectByExample = mapper.selectByExample(example);
		if(selectByExample == null || selectByExample.size() == 0)
			return new ArrayList<RegInfo>();
		return selectByExample;
	}
	private DoctorInfo getDoctorInfoById(SqlSession session, String id){
		DoctorInfoMapper mapper = session.getMapper(DoctorInfoMapper.class);
		DoctorInfoExample example = new DoctorInfoExample();
		DoctorInfoExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<DoctorInfo> list = mapper.selectByExample(example);
		if(list == null || list.size() == 0)
			return null;
		return list.get(0);
	}
	
	private String getPIdByUid(String uid ,SqlSession session){
		PatientInfoMapper mapper = session.getMapper(PatientInfoMapper.class);
		PatientInfoExample example = new PatientInfoExample();
		PatientInfoExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<PatientInfo> list = mapper.selectByExample(example);
		if(list == null || list.size() == 0)
			return null;
		return list.get(0).getId();
	}
}
