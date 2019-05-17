package com.supbank.controller;

import com.supbank.base.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * controller增强器
 */
@ControllerAdvice
public class MyControllerAdvice {

    /**
     * 全局异常捕捉
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "500");
        map.put("msg", e.getMessage());
        return map;

    }


    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Map notFoundHandler(NoHandlerFoundException ne){
        Map<String,String> map = new HashMap<>();
        map.put("code", "404");
        map.put("msg", ne.getMessage());
        return map;
    }


    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public Map myErrorHandler(MyException me) {
        Map<String, String> map = new HashMap<>();
        map.put("code", me.getCode());
        map.put("msg", me.getMsg());
        return map;
    }

}
