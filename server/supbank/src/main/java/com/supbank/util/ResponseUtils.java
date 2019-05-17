package com.supbank.util;

import com.supbank.base.DataRow;

import java.util.Map;

public class ResponseUtils {
    //返回正确消息
    public static Map returnSuccessMessage(){
        DataRow status = new DataRow();
        status.put("Ack", "success");
        status.put("ErrorMessage", "");
        status.put("Timestamp", System.currentTimeMillis());
        return status;
    }

    //返回失败消息
    public static Map returnErrorMessage(String errorMsg){
        DataRow status = new DataRow();
        status.put("Ack", "error");
        status.put("ErrorMessage", errorMsg);
        status.put("Timestamp", System.currentTimeMillis());
        return status;
    }
}
