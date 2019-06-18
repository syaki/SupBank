package com.supbank.util;

import com.alibaba.fastjson.JSON;
import com.supbank.base.DataRow;

public class JsonUtil {
	/**
	 * 失败返回JSON结果
	 * @param code
	 * @param msginfo
	 * @param obj
	 * @return
	 */
	public static String resultJsonString(int code,String msginfo,Object obj)
	{
		DataRow ret = new DataRow();
		ret.put("code",code);//0为没有任何错误
		ret.put("msginfo", msginfo);
		ret.put("result",obj);
		return JSON.toJSONString(ret);
	}
	/**
	 * 返回失败原因
	 * @param code
	 * @param msginfo
	 * @return
	 */
	public static String resultJsonString(int code,String msginfo)
	{
		DataRow ret = new DataRow();
		ret.put("code",code);//0为没有任何错误
		ret.put("msginfo", msginfo);
		ret.put("result","");
		return JSON.toJSONString(ret);
	}
	/**
	 * 成功返回JSON结果
	 * @param obj
	 * @return
	 */
	public static String resultJsonString(Object obj)
	{
		DataRow ret = new DataRow();
		ret.put("ack","success");//0为没有任何错误
		ret.put("errorMessage", "success");
		ret.put("timeStamp", System.currentTimeMillis());
		ret.put("result",obj);
		return JSON.toJSONString(ret);
	}
}
