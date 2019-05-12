package com.supbank.controller;

import com.alibaba.fastjson.JSON;
import com.supbank.base.DataRow;

import com.supbank.util.ResponseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/failed")
public class VisitFailedController {

    @RequestMapping("/expired")
    @CrossOrigin
    @ResponseBody
    public String requestFiled(){
        DataRow result = new DataRow();
        result.put("status", ResponseUtils.returnErrorMessage("session expired, login again"));
        return JSON.toJSONString(result);
    }


}
