package com.supbank.dao.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supbank.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;
import org.springframework.util.ResourceUtils;

/**
 * 主页Service
 * @author kwj19
 *
 */
@Service
public class HomePageService {

private static final Logger logger = LoggerFactory.getLogger(HomePageService.class);
	
	@Autowired
	private DBService dbService;
	
	/**
	 * 主页搜索框搜索
	 * @param request
	 * @param param
	 * @return
	 */
	public DataRow search(HttpServletRequest request, DataRow<String,String> param) {
		DataRow result = new DataRow();
		String keyword = param.getString("keyword");
		String sql1 = "select transactionid,input,output,sum,blockid,status from td_transaction where flag=1 and transactionid='"+keyword+"'";
		String sql3 = "select blockid,height,hash,prehash,merkleroothash,nonce,blockreward,transactionnumber,miner from td_block where flag=1 and hash='"+keyword+"'";
		List<DataRow> transactionList = dbService.queryForList(sql1);
		List<DataRow> blockList = dbService.queryForList(sql3);
		if(transactionList.isEmpty()&&blockList.isEmpty()) {
			result.put("status", ResponseUtils.returnErrorMessage("0 result"));

		}else {
			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("transactionList", transactionList);
			result.put("blockList", blockList);
		}
		return result;
	}
	
	
	/**
	 * 最新交易查询(大于10条返回10条，不足返回全部)
	 * @param request
	 * @return
	 */
	public DataRow getLastTransaction(HttpServletRequest request) {
		DataRow result = new DataRow();
//		/*这里需要在session中查询当前用户的address*/
//		String address = "asdf562dsaf";
		String sql = "select transactionid,input,output,sum,timestamp,blockid,status from td_transaction where flag=1 order by timestamp desc limit 10";
		List<DataRow> transactionList = dbService.queryForList(sql);
		result.put("status", ResponseUtils.returnSuccessMessage());
		result.put("transactionList", transactionList);
		return result;
	}
	
	



	public DataRow downloadApp(HttpServletRequest request, HttpServletResponse response) {
		DataRow result = new DataRow();
//		String baseUrl = request.getContextPath();
//		String s = request.getServletPath();
//		String baseUrl = request.getRealPath("/");
//		File file = new File(baseUrl + "supbank_project_log.log");
		ClassPathResource cpr = new ClassPathResource("application.yml");
		//设置contentType 为 “application/octet-stream”或“application/x-msdownload”
		response.setContentType("application/x-msdownload");
		/*通过HttpServletResponse.setHeader方法
		设置Content-Disposition头的值为“attachment；filename=文件名”*/
		response.setHeader("Content-Disposition", "attachment;filename="+"application.yml");
		InputStream is = null;
		ServletOutputStream sos = null;
		try {
			is = cpr.getInputStream();
			sos = response.getOutputStream();
			byte[] buf = new byte[8*1028];
			int n;
			while ((n = is.read(buf)) != -1) {
				sos.write(buf, 0, n);
				sos.flush();
			}
		} catch (IOException e) {
			result.put("status", ResponseUtils.returnErrorMessage("IO exception"));
			e.printStackTrace();
			return result;
		} finally {
			try {
				sos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}


		return result;
	}




	
}
