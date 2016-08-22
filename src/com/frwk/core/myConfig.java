package com.frwk.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class myConfig {
	
	public Map<String,Class> routes = new HashMap<String,Class>();
	
	
	private String encoding = "utf-8";
	
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public  void init(){
		setInitParam();
		setRoute();		
	}
	
	public abstract void setInitParam();
	public abstract void setRoute();
	
	
	
	public void handel(String methodName,Class controller,HttpServletRequest req 
			, HttpServletResponse resp){
		try {
						
			Object obj = controller.newInstance();
			controller.getMethod("setRequest", HttpServletRequest.class).invoke(obj, req);
			controller.getMethod("setResponse", HttpServletResponse.class).invoke(obj, resp);
			
			Method method = controller.getMethod(methodName, null);
			method.invoke(obj, null);
			
			
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
	}
}
