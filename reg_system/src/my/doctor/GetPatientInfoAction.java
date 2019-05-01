package my.doctor;

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
			// 拿到当前医生id 通过 uid
			String id = getDoctorIdbyUid(uid, session);
			// 查询所有该医生的 挂号信息
			List<RegInfo> list = getAllRegInfoByDoctor(id, session);
			// 联合查询 该医生下所有的患者 ，并构造返回数值
			JSONArray array = new JSONArray();
			for(RegInfo info : list){
				JSONObject object = new JSONObject();
				object.put("time", Utility.getDateString(info.getTime()));
				PatientInfo patientInfo = getPatientInfoById(info.getPatient(), session);
				if(patientInfo == null){
					// 未找到用户
					object.put("name","用户失效" );
					object.put("gender","用户失效" );
					object.put("age", "用户失效");
					object.put("phone", "用户失效");
					object.put("IDcard","用户失效");
					object.put("desc","用户失效");
				}else {
					object.put("name", patientInfo.getName());
					object.put("gender", patientInfo.getGender());
					object.put("age",patientInfo.getAge() );
					object.put("phone", patientInfo.getPhone());
					object.put("IDcard",patientInfo.getIdcard() );
					object.put("desc",patientInfo.getDescription() );
				}
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
	private PatientInfo getPatientInfoById(String id, SqlSession session){
		PatientInfoMapper mapper = session.getMapper(PatientInfoMapper.class);
		
		PatientInfoExample example = new PatientInfoExample();
		PatientInfoExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<PatientInfo> selectByExample = mapper.selectByExample(example);
		
		if(selectByExample == null || selectByExample.size() == 0)
			return null;
		return selectByExample.get(0);
 	}
	
	private List<RegInfo> getAllRegInfoByDoctor(String id,SqlSession session){
		RegInfoMapper mapper = session.getMapper(RegInfoMapper.class);
		
		RegInfoExample example = new RegInfoExample();
		RegInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDoctorEqualTo(id);
		List<RegInfo> list = mapper.selectByExample(example);
		if(list == null || list.size() == 0)
			return new ArrayList<RegInfo>();
		return list;
	}
	
	private String  getDoctorIdbyUid(String uid ,SqlSession session){
		DoctorInfoMapper mapper = session.getMapper(DoctorInfoMapper.class);
		DoctorInfoExample example = new DoctorInfoExample();
		DoctorInfoExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<DoctorInfo> selectByExample = mapper.selectByExample(example);
		if(selectByExample == null || selectByExample.size() ==0)
			return "";
		return selectByExample.get(0).getId();
	}

}
