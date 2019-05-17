package com.supbank.dao.service;

import java.util.List;
import java.util.Map;

import com.supbank.util.DateTimeUtil;
import com.supbank.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;
import com.supbank.util.GeneratorIDUtil;
import com.supbank.util.RSAUtils;
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
	public DataRow createWallet(HttpServletRequest request, DataRow params) {

		String walletid = GeneratorIDUtil.generatorId();




		double balance = 0;
		params.put("walletid", walletid);

		params.put("balance", balance);

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

		params.put("transactionid", id);
		params.put("timestamp", DateTimeUtil.getNowDateStr());
		params.put("status", 1);

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
	 * 验证交易并更新余额
	 * @param transactionid
	 * @param blockid
	 * @return
	 */
	@Transactional
	public DataRow txVerifyAndEditBalance(String transactionid,String blockid) {
		DataRow result = new DataRow();

		String input = "";
		String output = "";
		double sum = 0;
		//查询交易信息
		String txInforSQL = "select * from td_transaction where flag=1 and transactionid='"+transactionid+"'";
		DataRow tx = null;
		try {
			tx = dbService.querySimpleRowBySql(txInforSQL);
			if(tx.isEmpty()) {
				result.put("status", ResponseUtils.returnErrorMessage("transaction is not existed"));
				return result;
			}
		} catch (Exception e1) {
			result.put("status", ResponseUtils.returnErrorMessage("query txInfor db error"));
			e1.printStackTrace();
			return result;
		}
		input = tx.getString("input");
		output = tx.getString("output");
		sum = tx.getDouble("sum");
		String tx_infor = transactionid+input+output+sum;
		String signature = tx.getString("signature");
		//查询input用户余额
		double input_balance = 0;
		String publicKey="";
		String queryBalanceSQL = "select balance,publicKey from td_wallet where flag=1 and address='"+input+"'";
		try {
			DataRow wallet_infor = dbService.querySimpleRowBySql(queryBalanceSQL);
			input_balance = wallet_infor.getDouble("balance");
			publicKey = wallet_infor.getString("publicKey");
		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("query balance db error"));
			e.printStackTrace();
			return result;
		}
		boolean verifyResult = true;
		if(input_balance>=sum) {
			//钱够用
			try {
				verifyResult = RSAUtils.verify(tx_infor.getBytes(), publicKey, signature);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(verifyResult) {
				//验证通过
				String editInputSQL = "update td_wallet set balance="+(input_balance-sum)+" where flag=1 and address='"+input+"'";
				String editOutputSQL = "update td_wallet set balance=balance+"+sum+" where flag=1 and address='"+output+"'";
				String editTxSQL = "update td_transaction set status=2,blockid='"+blockid+"' where flag=1 and transactionid='"+transactionid+"'";
				try {
					dbService.UpdateBySql(editInputSQL);
					dbService.UpdateBySql(editOutputSQL);
					dbService.UpdateBySql(editTxSQL);
					result.put("status", ResponseUtils.returnSuccessMessage());

				} catch (Exception e) {
					result.put("status", ResponseUtils.returnErrorMessage("update balance db error"));
					e.printStackTrace();
					return result;
				}
			}else {
				result.put("status", ResponseUtils.returnErrorMessage("verify failed,data maybe changed"));
			}


		}else {
			result.put("status", ResponseUtils.returnErrorMessage("balance not enough"));
		}

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
			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("balance", value.getInt("balance"));
		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("getBalance error"));
			e.printStackTrace();
		}
		return result;
	}





	/**
	 * 根据address查询私钥
	 * @param address
	 * @return
	 */
//	public DataRow getPrivateKeyByAddress(String address) {
//		DataRow result = new DataRow();
//		String sql = "select privateKey from td_wallet where flag=1 and address='"+address+"'";
//		try {
//			DataRow value = dbService.querySimpleRowBySql(sql);
//			result.put("flag", 1);
//			result.put("privateKey", value.getString("privateKey"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			result.put("flag", 0);
//			e.printStackTrace();
//		}
//
//		return result;
//	}
	
	
	
	
}
