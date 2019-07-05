package com.supbank.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.supbank.base.DBService;
import com.supbank.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.supbank.base.DataRow;
import com.supbank.dao.service.UserService;
import com.supbank.util.EmailUtil;
import com.supbank.util.JsonUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private DBService dbService;

	/**
	 * 发送验证码
	 * @param request
	 * @param params
	 * @return
	 */
	@CrossOrigin("*")
	@ResponseBody
	@PostMapping("/sendCode")
	public String sendCode(HttpServletRequest request, @RequestBody DataRow params) {
		DataRow result = new DataRow();

		String code = EmailUtil.generateCode();
		System.out.println(code);
		String address = params.getString("email");
		DataRow dataRow = new DataRow();
		try {
			dataRow = dbService.querySimpleRowBySql("select email from td_user where flag=1 and email='"+address+"'");
		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("query email error"));
			e.printStackTrace();
			return JSON.toJSONString(result);
		}

		if(!dataRow.isEmpty()) {
			result.put("status", ResponseUtils.returnErrorMessage("email has used"));
			return JSON.toJSONString(result);
		}

		boolean isSend = EmailUtil.sendEmail(address, code);

		if(isSend) {
			result.put("status", ResponseUtils.returnSuccessMessage());
			HttpSession session = request.getSession();
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("code", code);
			jsonobj.put("timestamp", System.currentTimeMillis());
			session.setAttribute("verifyCode", jsonobj);
		}else {
			result.put("status", ResponseUtils.returnErrorMessage("send verifyCode failed"));
		}
		return JSON.toJSONString(result);
	}
	
	
	
	/**
	 * 注册
	 * @param request
	 * @param params
	 * @return
	 */
	@CrossOrigin("*")
	@ResponseBody
	@PostMapping("/register")
	public String registerUser(HttpServletRequest request, @RequestBody DataRow<String,String> params) {
		DataRow result = null;
		result = userService.registerUser(request, params);
		return JSON.toJSONString(result);
	}
	
	
	/**
	 * 登陆操作
	 * @param request
	 * @param params
	 * @return
	 */
	@CrossOrigin("*")
	@ResponseBody
	@PostMapping("/login")
	public String userLogin(HttpServletRequest request, HttpServletResponse response ,@RequestBody DataRow<String, String> params) {
		DataRow result = null;
		result = userService.login(request, response, params);
		return JSON.toJSONString(result);
	}
	


	@CrossOrigin("*")
	@ResponseBody
	@PostMapping("/logout")
	public String userLogout(HttpServletRequest request, HttpServletResponse response) {
		return JSON.toJSONString(userService.logout(request, response));
	}






	
	
	
}
