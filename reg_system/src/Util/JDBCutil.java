package Util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONObject;

import com.mybatis.dao.UserInfoMapper;
import com.mybatis.model.UserInfo;

public class JDBCutil
{
	
	private static SqlSessionFactory sqlSessionFactory = null; 
	
	
	public static SqlSessionFactory getSessionFactory() {
		if(sqlSessionFactory != null)
			return sqlSessionFactory;
		String resource = "M_config.xml";
		InputStream inputStream = null;
		try
		{
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	//test
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		SqlSessionFactory factory = JDBCutil.getSessionFactory();
		SqlSession session = null;
		try
		{
			session = factory.openSession();
			
		} finally
		{
			// TODO: handle finally clause
			if (session!=null)
			{
				session.close();
			}
			
		}
		
		
	}
	
	// 测试
	private static void test(){
		JSONObject resp = new JSONObject();
		JSONObject data = new JSONObject();
		resp.put("data", data);
		data.put("x", 1);
		resp.getJSONObject("data").put("y", 2);
		System.out.println(resp);
	}

}
