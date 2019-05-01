package my.common;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mybatis.dao.MessageInfoMapper;
import com.mybatis.model.MessageInfo;
import com.mybatis.model.MessageInfoExample;


import Util.Action;
import Util.JDBCutil;
import Util.Utility;

public class GetAllMessageAction implements Action {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletResponse response = ServletActionContext.getResponse();
		
		JSONObject resp = new JSONObject();
		
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		try
		{
			session = factory.openSession();
			MessageInfoMapper mapper = session.getMapper(MessageInfoMapper.class);
			MessageInfoExample example = new MessageInfoExample();
			MessageInfoExample.Criteria criteria = example.createCriteria();
			List<MessageInfo> list = mapper.selectByExample(example);
			if(list == null || list.size() == 0){
				resp.put("error", -1);
				resp.put("reason", "没有公告消息");
				
			}else {
				JSONArray array = new JSONArray();
				int num = 1; // 编号 
				for(MessageInfo temp : list){
					JSONObject object = new JSONObject();
					object.put("content", num+"."+" "+temp.getContent());
					object.put("timeCreated", Utility.getDateString(temp.getTimeCreated()));
					object.put("sender", temp.getSender());
					array.put(object);
					num++;
				}
				resp.put("error",0);
				resp.put("data", array);
			}
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
