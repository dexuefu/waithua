package com.ice.config;

/**
 * 系统级参数的名称,如果不喜欢这些参数名，可以自己写配置文件修改
 * @version 1.0
 */
public class SystemParameterNames {

	/**
	 * return appKey
	 */
	public static String getAppKey(){
		return "appKey";
	}
	
   /**
    * return  method
    */
   public static String getMehod(){
    	return "method";
   }
    
   /**
    * return  v
    */
   public static String getVersion(){
    	return "v";
   }
    
   /**
    * return  sessionId
    */
   public static String getSessionId(){
	  return "sessionId"; 
   }
   
   /**
    * return  sign
    */
   public static String getSign(){
	  return "sign"; 
   }
   
   /**
    * return  format
    */
   public static String getFormat(){
	  return "format"; 
   }
   
   /**
    * return  timestamp
    */
   public static String getTimestamp(){
	   return "timestamp"; 
   }
   
}

