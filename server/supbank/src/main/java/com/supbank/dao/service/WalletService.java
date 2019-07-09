package com.supbank.dao.service;

import java.util.List;
import java.util.Map;

import com.supbank.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class WalletService {

	@Autowired
	private DBService dbService;
	
	/**
	 * 新建钱包
	 * @return
	 */
	public DataRow createWallet(DataRow params) {

		String walletid = GeneratorIDUtil.generatorId();

		double balance = 0;
		params.put("walletid", walletid);

		params.put("balance", balance);
		params.put("password", MD5Util.makeMD5(params.getString("password")));
		DataRow result = new DataRow();
		try {
			dbService.Insert("td_wallet", params);
		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("create wallet error"));//添加失败
			e.printStackTrace();
			return result;
		}
		result.put("status", ResponseUtils.returnSuccessMessage());


		return result;
		
	}




	/**
	 * 产生交易（是否返回值）
	 * @param request
	 * @param params
	 * @return
	 */
	public DataRow generateTransaction(HttpServletRequest request, DataRow params) {

		DataRow result = new DataRow();
		String id = GeneratorIDUtil.generatorId();

		String input = params.getString("input");
		String output = params.getString("output");
		String password = MD5Util.makeMD5(params.getString("password"));
		double sum = params.getDouble("sum");
		params.put("transactionid", id);
		params.put("timestamp", DateTimeUtil.getNowDateStr());
		params.put("status", 1);

		//查询input用户余额
		double input_balance = 0;
		String queryBalanceSQL = "select balance,publicKey from td_wallet where flag=1 and address='"+input+"' and password='"+password+"'";
		try {
			DataRow wallet_infor = dbService.querySimpleRowBySql(queryBalanceSQL);
			if(wallet_infor.isEmpty()) {
				result.put("status", ResponseUtils.returnErrorMessage("password or address error"));
				return result;
			}
			input_balance = wallet_infor.getDouble("balance");
		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("query balance db error"));
			e.printStackTrace();
			return result;
		}
		if(input_balance < sum) {
			//钱不够
			result.put("status", ResponseUtils.returnErrorMessage("余额不足"));
			return result;
		}

		//查询output存不存在
		try {
			DataRow outputUser = dbService.querySimpleRowBySql("select address from td_wallet where address='"+params.getString("output")+"'");
			if (outputUser.isEmpty()) {
				result.put("status", ResponseUtils.returnErrorMessage("收款方地址不存在"));
				return result;
			}
		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("query output address error"));
			return result;
		}

		String editInputSQL = "update td_wallet set balance=balance-"+ sum +" where flag=1 and address='"+input+"'";
//		String editOutputSQL = "update td_wallet set balance=balance+"+ sum +" where flag=1 and address='"+output+"'";

		try {
			dbService.UpdateBySql(editInputSQL);
//			dbService.UpdateBySql(editOutputSQL);


		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("update balance db error"));
			e.printStackTrace();
		}



		try {
			dbService.Insert("td_transaction", params);
		} catch (Exception e) {

			result.put("status", ResponseUtils.returnErrorMessage("create tx error"));
		}

		result.put("status", ResponseUtils.returnSuccessMessage());


		/*签名操作*/
//		String input = params.getString("input");
//		String output = params.getString("output");
//		double sum = params.getDouble("sum");
//		String tx_infor = id+input+output+sum;
//
//		DataRow privKey_result = walletService.getPrivateKeyByAddress(input);
//		if(privKey_result.getInt("flag")==1) {
//			String privKey = privKey_result.getString("privateKey");
//			String signature;
//			try {
//				signature = RSAUtils.sign(tx_infor.getBytes(), privKey);
//
//				params.put("signature", signature);
//				dbService.Insert("td_transaction", params);
//
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				result.put("status", 1);
//				result.put("errorMessage", "create tx error");
//				e.printStackTrace();
//			}
//			result.put("status", 0);
//			result.put("successMessage", "create tx success");
//
//		}else {
//			result.put("status", 1);
//			result.put("errorMessage", "getPrivKeyByAddress error");
//		}

		return result;
	}













	/**
	 * 查询近期交易（App）
	 * @param request
	 * @return
	 */
	public DataRow getRecentTransactions(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();


		try {

			String address = params.getString("address");
			//查询最近交易列表
			String sql = "SELECT transactionid,input,output,sum,blockid,timestamp,status FROM td_transaction WHERE flag=1 AND (input='"+address+"' OR output='"+address+"') ORDER BY TIMESTAMP DESC";
			List<DataRow> transactionList = dbService.queryForList(sql);
			for(DataRow tx: transactionList) {
				if(tx.getString("input").equals(address)) {
					tx.put("type", "pay");
				}else {
					tx.put("type", "receive");
				}
			}
			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("transactionList", transactionList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.put("status", ResponseUtils.returnErrorMessage("query error"));

			e.printStackTrace();
		}

		return result;
	}


	/**
	 * 获取当前用户余额
	 * @param request
	 * @return
	 */
	public DataRow getBalance(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();

		String address = params.getString("address");


		String sql = "SELECT a.balance FROM td_wallet a WHERE a.flag=1 AND a.address='"+ address +"'";
		try {
			DataRow value = dbService.querySimpleRowBySql(sql);
			if(value.isEmpty()) {
				result.put("status", ResponseUtils.returnErrorMessage("该用户不存在"));
				return result;
			}
			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("balance", value.getInt("balance"));
		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("getBalance error"));
			e.printStackTrace();
		}
		return result;
	}






	
	
	
}
