package com.supbank;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.supbank.util.Base64Utils;
import com.supbank.util.EmailUtil;
import com.supbank.util.RSAUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupbankApplicationTests {

	@Test
	public void contextLoads() throws Exception {

//		EmailUtil.sendEmail("302589689@qq.com", "<a>562364</a> 别慌，这是一封测试邮件");
		
		String data = "1904232106460001"+"2187e984a6c38cc35830e139e9ac2ba29ab32476"+"56aa8b2fd3c2109f598ae37ccc3dd19173d60497"+50;
		String sign = "L4EpLC5QqkEAdubIe86ERpaPoEiRlWRI6hSnj1tW4a6NqBOLvi6UkJv4vf72HaYB9NwOt4V0Xp0v\n" + 
				"BGp4ZEJBvu8Fixcm4mnT0p//azM3xGFHFa+aKQnFQi5ndcWN+0VlFjfaFj0OPYxuPOt/MRqDC/8R\n" + 
				"ik+e2r/dHgvhIhHrnvU=";
		String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd0ko/baDps8o1uyd5bL0JTFmyCg/+OfkHEQBP\n" + 
				"akZKabfUYfgR2AQOm25+JLAAv27DRZrbucpCDoKH/YHeSE8vTT6hNZTHW+nN3M0/QKcK6xP8NZDs\n" + 
				"0BbTytzixBn/P/Pqy0wdG6av3xi1/i9G5v1XneoYeATUwXG0q1SnWEA1OQIDAQAB";
		
		System.out.println(RSAUtils.verify(data.getBytes(), pubKey, sign));


	}

}

