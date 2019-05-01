package my.patient;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.mybatis.dao.PatientInfoMapper;
import com.mybatis.dao.RegInfoMapper;
import com.mybatis.model.PatientInfo;
import com.mybatis.model.PatientInfoExample;
import com.mybatis.model.RegInfo;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class DoRegisterAction implements Action {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject request_data = Utility.getDataFromRequest(request);
		String doctor_id = request_data.getString("doctor_id");
		Long time = (Long)request_data.get("time");
		Date date = new Date(time);
		// 返回给前端的数据 json
		// 拿到当前登录用户的account
		HttpSession httpSession = request.getSession();
		String account = (String) httpSession.getAttribute("account");
		String uid = (String) httpSession.getAttribute("uid");
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
			session = factory.openSession();
			// 获得病人id
			
			String id = getIdByUid(uid, session);
			if(id == null){
				resp.put("error", -1);
				resp.put("reason", "请先补充信息再来挂号");
				Utility.putDataToResponse(response, resp);
				return null;
			}
			// 添加挂号记录
			insertToRegInfo(id, doctor_id, date, session);
			
			resp.put("error", 0);
			JSONObject data = new JSONObject();
			data.put("message", "挂号成功");
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
	private void insertToRegInfo(String p_id,String d_id,Date time,SqlSession session){
		RegInfo info = new RegInfo();
		info.setDoctor(d_id);
		info.setPatient(p_id);
		info.setId(Utility.generateUUID());
		info.setTime(time);
		
		RegInfoMapper mapper = session.getMapper(RegInfoMapper.class);
		mapper.insert(info);
		session.commit();
	}
	
	private String getIdByUid(String uid, SqlSession session){
		PatientInfoExample example = new PatientInfoExample();
		PatientInfoExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		PatientInfoMapper mapper = session.getMapper(PatientInfoMapper.class);
		List<PatientInfo> list = mapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0).getId();
	}
}
