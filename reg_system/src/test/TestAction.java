package test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import Util.Utility;

public class TestAction {
	
	public String execute() throws IOException{
		HttpServletRequest request =  ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		JSONObject re = Utility.getDataFromRequest(request);
		JSONObject resp = new JSONObject();
		resp.put("error", 0);
		JSONObject data = new JSONObject();
		data.put("name", "wenxuan");
		resp.put("data", data);
		Utility.putDataToResponse(response, resp);
		
		return null;
	}
}
