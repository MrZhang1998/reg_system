package my.patient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mybatis.dao.DepartmentInfoMapper;
import com.mybatis.dao.DoctorInfoMapper;
import com.mybatis.dao.RegInfoMapper;
import com.mybatis.model.DepartmentInfo;
import com.mybatis.model.DepartmentInfoExample;
import com.mybatis.model.DoctorInfo;
import com.mybatis.model.DoctorInfoExample;
import com.mybatis.model.RegInfo;
import com.mybatis.model.RegInfoExample;

import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class GetTreeDataAction implements Action {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletResponse response = ServletActionContext.getResponse();
		// 返回给前端的数据 json
		JSONObject resp = new JSONObject();

		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		int node_id = 1;
		
		JSONArray data = new JSONArray();

		try {
			session = factory.openSession();
			// get all depart
			List<DepartmentInfo> list = getAllDepartment(session);
			Map<String, JSONObject> cache = new HashMap<String, JSONObject>();
			for (DepartmentInfo info : list) {
				JSONObject department_node = new JSONObject();
				department_node.put("id", node_id++);
				department_node.put("text", info.getdName());
				department_node.put("state", "closed");
				department_node.put("children", new JSONArray());
				// 当前节点的 自定义属性 为 部门的id
				department_node.put("attributes", info.getId());
				department_node.put("iconCls", "icon-add");
				cache.put(info.getId(), department_node);
				data.put(department_node);
			}
			// 新的缓存 parents key 为 医生id , value 为 josnobject_node;
			Map<String, JSONObject> parents = new HashMap<String, JSONObject>();
			List<DoctorInfo> allDoctors = getAllDoctors(session);
			if (allDoctors != null && allDoctors.size() != 0) {
				for (DoctorInfo doctorInfo : allDoctors) {
					JSONObject doctor_node = new JSONObject();
					doctor_node.put("id", node_id++);
					doctor_node.put("text", doctorInfo.getName());
					doctor_node.put("state", "closed");
					doctor_node.put("children", new JSONArray());
					doctor_node.put("attributes", doctorInfo.getId());
					doctor_node.put("iconCls", "icon-man");// 图标
					JSONObject parent = cache.get(doctorInfo.getDepartement());
					parent.getJSONArray("children").put(doctor_node);
					parents.put(doctorInfo.getId(), doctor_node);
				}
			}
			// 获得每个医生可以预约的时间段

			for (DoctorInfo doctorInfo : allDoctors) {
				JSONObject parent = parents.get(doctorInfo.getId());
				JSONArray children = parent.getJSONArray("children");
				// 查询该医生的挂号信息，出去已经挂号的时间段，就是可以挂号的时间
				List<RegInfo> regs = getAllRegInfo(doctorInfo.getId(), session);
				System.out.println("该医生有"+regs.size()+"个病人");
				System.out.println("医生id 为"+doctorInfo.getId());
				// 除去已经挂号的时间段
				List<Date> dates = getDates();
				for (RegInfo regInfo : regs) {
					long temp = regInfo.getTime().getTime();
					System.out.println("已经挂号的时间为 "+ temp);
					Iterator<Date> iterator = dates.iterator();
					while(iterator.hasNext()){
						Date it = iterator.next();
						if(it.getTime() == temp)
							iterator.remove();
					}
				}
				// 构建json字符串

				for (Date date : dates) {
					JSONObject node = new JSONObject();
					node.put("id", node_id++);
					String text = Utility.getDateString(date);
					node.put("text", text+" 可预约");
					node.put("attributes", doctorInfo.getId());
					node.put("date", date.getTime());
					node.put("state", "closed");
					node.put("iconCls", "icon-tip");
					children.put(node);
				}

			}
			System.out.println(data);
			resp.put("error", 0);
			resp.put("data", data);
			Utility.putDataToResponse(response, resp);
		} finally {
			// TODO: handle finally clause
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

	private List<RegInfo> getAllRegInfo(String doctorID, SqlSession session) {
		RegInfoMapper mapper = session.getMapper(RegInfoMapper.class);
		RegInfoExample example = new RegInfoExample();
		RegInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDoctorEqualTo(doctorID);
		List<RegInfo> selectByExample = mapper.selectByExample(example);
		if (selectByExample == null)
			return new ArrayList<RegInfo>();
		return selectByExample;
	}

	private List<DepartmentInfo> getAllDepartment(SqlSession session) {
		DepartmentInfoExample example = new DepartmentInfoExample();
		DepartmentInfoExample.Criteria criteria = example.createCriteria();

		DepartmentInfoMapper mapper = session.getMapper(DepartmentInfoMapper.class);
		List<DepartmentInfo> selectByExample = mapper.selectByExample(example);
		if (selectByExample == null)
			return new ArrayList<DepartmentInfo>();
		return selectByExample;
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

	private List<Date> getDates() {
		// 预约两天 每天八点到 16点 每次一个小时
		List<Date> set = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		for (int i = 1; i <= 2; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, +1);
			for (int j = 8; j < 17; j++) {
				calendar.set(Calendar.HOUR_OF_DAY, j);
				set.add(calendar.getTime());
				//System.out.println(calendar.getTimeInMillis());
			}
		}
		return set;

	}

}
