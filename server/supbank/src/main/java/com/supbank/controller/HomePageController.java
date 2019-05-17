package com.supbank.controller;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supbank.base.DataRow;
import com.supbank.dao.service.HomePageService;
import com.supbank.util.JsonUtil;

/**
 * 主页Controller
 * @author kwj19
 *
 */
@RestController
@RequestMapping("/homePage")
public class HomePageController {

	@Autowired
	private HomePageService homePageService;
	
	/**
	 * 主页搜索框搜索
	 * @param request
	 * @param params
	 * @return
	 */
	@CrossOrigin
	@ResponseBody
	@PostMapping("/search")
	public String getTestInfo(HttpServletRequest request,@RequestBody DataRow<String,String> params) {
		DataRow result = null;
		result = homePageService.search(request,params);
		return JSON.toJSONString(result);
		
	}
	
	
	/**
	 * 最新交易查询
	 * @param request
	 * @param params
	 * @return
	 */
	@CrossOrigin
	@ResponseBody
	@PostMapping("/getLastTransaction")
	public String getLastTransaction(HttpServletRequest request,@RequestBody DataRow<String,String> params) {
		DataRow result = null;
		result = homePageService.getLastTransaction(request);
		
		return JSON.toJSONString(result);
		
	}
}
