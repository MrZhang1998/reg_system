package Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Utility {
	
	public static final String host = "http://localhost:8080/reg_system";

	// 从 request 中 读取出 参数 
	public static JSONObject getDataFromRequest(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer();
		Map<String, String[]> map = request.getParameterMap();
		for(String temp : map.keySet())
		{
			buffer.append(temp);
		}
		System.out.println("request请求为 "+buffer.toString());
		return new JSONObject(buffer.toString());
	}
	
	// 将 处理好的数据 转换成字符串发给前端
	public static void putDataToResponse(HttpServletResponse response ,JSONObject jsonObject){
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String data = jsonObject.toString();
			writer.write(data);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(writer!=null){
				writer.close();
			}
		}
	}
	public static String getDateString(Date date){
		String string =(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date); 
		return string;
	}
	
	public static String generateUUID(){
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		System.out.println("生成的id 为 "+uuid);
		return uuid;
	}
}
