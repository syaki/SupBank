package com.supbank;

import java.util.HashMap;
import java.util.Map;

import com.supbank.base.DBService;
import com.supbank.dao.service.HomePageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.supbank.util.Base64Utils;
import com.supbank.util.EmailUtil;
import com.supbank.util.RSAUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupbankApplicationTests {

	@Autowired
	private HomePageService homePageService;

	@Test
	public void contextLoads() throws Exception {

//		EmailUtil.sendEmail("302589689@qq.com", "<a>562364</a> 别慌，这是一封测试邮件");
//
//		String data = "16nkaR553tqT8VvmRmCzYVxXuzTxZ9pu98"+"56aa8b2fd3c2109f598ae37ccc3dd19173d60497"+4;
//
//
//
////		System.out.println(RSAUtils.verify(data.getBytes(), "03752c4616434fe8647ed113bd0a8bfc3ce15224f96c7656bbefac2795d1c67d59", "MTZua2FSNTUzdHFUOFZ2bVJtQ3pZVnhYdXpUeFo5cHU5ODU2YWE4YjJmZDNjMjEwOWY1OThhZTM3Y2NjM2RkMTkxNzNkNjA0OTc0LjA="));
//
//
//		Map<String, Object> map = RSAUtils.genKeyPair();
//		String privKey = RSAUtils.getPrivateKey(map);
//		String pubKey = RSAUtils.getPublicKey(map);
//
//		String sign = RSAUtils.sign(data.getBytes(), privKey);
//		System.out.println(RSAUtils.verify(data.getBytes(), pubKey, sign));






	}

}

