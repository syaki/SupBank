package com.supbank.util;

import org.apache.commons.mail.HtmlEmail;

public class EmailUtil {

	/**
	 * 发送邮件
	 * @param emailaddress
	 * @param code
	 * @return
	 */
	public static boolean sendEmail(String emailaddress,String code){
		try {
			HtmlEmail email = new HtmlEmail();//不用更改
			email.setHostName("smtp.qq.com");//需要修改，126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com
//			email.setSmtpPort(465);
			email.setSslSmtpPort("465");
			email.setSSLOnConnect(true);
			email.setCharset("UTF-8");
			email.addTo(emailaddress);// 收件地址
 
			email.setFrom("1138500436@qq.com", "Admin");//此处填邮箱地址和用户名,用户名可以任意填写
 
			email.setAuthentication("1138500436@qq.com", "rryyyhwwgyqigbeg");//此处填写邮箱地址和客户端授权码
 
			email.setSubject("SupBank");//此处填写邮件名，邮件名可任意填写
			email.setMsg("尊敬的用户您好,您本次注册的验证码是:<a>" + code+"</a>,有效期5分钟");//此处填写邮件内容
 
			email.send();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 产生6位随机验证码
	 * @return
	 */
	public static String generateCode() {
		String code = "";
		for(int i=0;i<6;i++) {
			code += (int)(Math.random()*10);
		}
		return code;
	}
}
