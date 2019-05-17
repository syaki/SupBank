package com.supbank.dao.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.supbank.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;

/**
 * 有关交易的Service
 * @author kwj19
 *
 */
@Service
public class TransactionService {

	public static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private DBService dbService;
	
	@Autowired
	private WalletService walletService;
	
	/**
	 * 通过transactionid查询交易详细信息
	 * @param request
	 * @param params
	 * @return
	 */
	public DataRow getTransactionInfoById(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();

		String sql = "select transactionid,input,output,sum,timestamp,blockid,status from td_transaction where flag=1 and transactionid='"+params.getString("transactionId")+"'";
		List<DataRow> transactionList = dbService.queryForList(sql);
		if(transactionList.isEmpty()) {

			result.put("status", ResponseUtils.returnErrorMessage("this tx is not existed"));
		}else {

			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("transactionList", transactionList);
		}
		return result;
		
	}

	
	

	
}
