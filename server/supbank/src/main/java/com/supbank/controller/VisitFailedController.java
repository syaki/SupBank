package com.supbank.controller;

import com.supbank.base.DataRow;
import com.supbank.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class VisitFailedController {

    @RequestMapping("/failed")
    @CrossOrigin
    @ResponseBody
    public String requestFiled(){
        DataRow result = new DataRow();
        result.put("status",1);
        result.put("errorMessage","session expired, login again");

        return JsonUtil.resultJsonString(result);
    }
}
