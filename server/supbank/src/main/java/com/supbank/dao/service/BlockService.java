package com.supbank.dao.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.supbank.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supbank.base.DBService;
import com.supbank.base.DataRow;

/**
 * Block的Service
 * @author kwj19
 *
 */
@Service
public class BlockService {

	public static final Logger logger = LoggerFactory.getLogger(BlockService.class);
	
	@Autowired
	private DBService dbService;
	
	/**通过id查询block详细信息
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	public DataRow getBlockInfoById(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();
		String id = params.getString("id");
		String sql = "select * from td_block where flag=1 and blockid='"+id+"'";
		List<DataRow> blockList = dbService.queryForList(sql);
		if(blockList.isEmpty()) {
			result.put("status", ResponseUtils.returnErrorMessage("this block is not exist"));

		}else {
			String sql2 = "select transactionid from td_transaction where flag=1 and blockid='" + id + "'";
			List<DataRow> transactionList = dbService.queryForList(sql2);
			result.put("transactionList", transactionList);
			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("blockList", blockList);
		}
		return result;
	}
	
	/**
	 * 获取最长合法链
	 * @param request
	 * @return
	 */
	public DataRow getLongestLegalChain(HttpServletRequest request, DataRow params) {
		DataRow result = new DataRow();
		DataRow dataRow = null;
		try {
			dataRow = dbService.querySimpleRowBySql("select blockid,height,hash,prehash,merkleroothash,nonce,blockreward,transactionnumber,miner from td_block where flag=1 and islegal=1 and height =(select max(height) from td_block where flag=1 and islegal=1)");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<DataRow> longestLegalChain = new ArrayList<DataRow>();
//		int pageNumber = params.getInt("pageNumber");
//
//
//		int pageSize = 5;


//		for (DataRow dataRow : heightestBlockList) {

			do {
				longestLegalChain.add(dataRow);


				String prehash = dataRow.getString("prehash");
				if(null==prehash || prehash.equals("")) {
					result.put("status", ResponseUtils.returnSuccessMessage());
					result.put("longestLegalChain", longestLegalChain);
					return result;
				}


				String sql = "select blockid,height,hash,prehash,merkleroothash,nonce,blockreward,transactionnumber,miner from td_block where flag=1 and islegal=1 and hash='"
						+ prehash + "'";
				try {
					dataRow = dbService.querySimpleRowBySql(sql);
					if(dataRow.getString("prehash")==null) {
						longestLegalChain.add(dataRow);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} while (dataRow.getString("prehash")!=null);
//		}
//		if(longestLegalChain.size()<pageSize){
		result.put("status", ResponseUtils.returnSuccessMessage());
		result.put("longestLegalChain", longestLegalChain);
		return result;
//		}
//		if(longestLegalChain.size()>=pageNumber*pageSize){
//			result.put("status", ResponseUtils.returnSuccessMessage());
//			result.put("longestLegalChain", longestLegalChain.subList((pageNumber - 1)*pageSize, pageNumber*pageSize));
//			return result;
//		}
//		if(longestLegalChain.size()<pageNumber*pageSize){
//			result.put("status", ResponseUtils.returnSuccessMessage());
//			result.put("longestLegalChain", longestLegalChain.subList((pageNumber - 1)*pageSize, longestLegalChain.size()));
//			return result;
//		}
//
//
//
//		return result;
		
	}


	/**
	 * 产生区块
	 * @param params
	 * @return
	 */
	public DataRow createBlock(DataRow params) {
		DataRow result = new DataRow();
		String address = params.getString("address");

		try {
			DataRow addressUser = dbService.querySimpleRowBySql("select address from td_wallet where flag=1 and address='"+address+"'");
			if(addressUser.isEmpty()) {
				result.put("status", ResponseUtils.returnErrorMessage("address is not exist"));
				return result;
			}
		} catch (Exception e) {
			result.put("status", ResponseUtils.returnErrorMessage("query address user error"));
			return result;
		}


		//拿到上一个区块
		DataRow preBlock = null;
		try {
			preBlock = dbService.querySimpleRowBySql("select * from td_block order by height DESC LIMIT 1");
		} catch (Exception e) {
			e.printStackTrace();
		}

		DataRow block = DigMine.digOneBlock(address, preBlock, 1);
		String blockid = block.getString("blockid");
		//拿取未验证交易
		String sql1 = "select * from td_transaction where flag=1 and status=1";
		List<DataRow> transactionList = dbService.queryForList(sql1);

		String merkleString = "";
		int transactionnumber = 0;
		for (DataRow tx : transactionList) {
			String input = "";
			String output = "";
			double sum = 0;
			String transactionid = tx.getString("transactionid");
			input = tx.getString("input");
			output = tx.getString("output");
			sum = tx.getDouble("sum");
			String tx_infor = input+output+sum;
			String signature = tx.getString("signature");

			boolean verifyResult = true;
			String publicKey = "";
			double balance = 0;
			DataRow wallet = new DataRow();
			try {
				wallet = dbService.querySimpleRowBySql("select balance,publicKey from td_wallet where flag=1 and address='"+input+"'");
				if(wallet.isEmpty()) {
					result.put("status", ResponseUtils.returnErrorMessage("input address is not exist"));
					return result;
				}
				publicKey = wallet.getString("publicKey");
				balance = wallet.getDouble("balance");
			} catch (Exception e1) {

				e1.printStackTrace();
			}


			//验证签名，验证余额够不够
			try{
				verifyResult = RSAUtils.verify(tx_infor.getBytes(), publicKey, signature);
			} catch (Exception e) {
				verifyResult = false;
			}



			if(verifyResult) {
				//验证通过,存入区块
//				String editInputSQL = "update td_wallet set balance=balance-"+ sum +" where flag=1 and address='"+input+"'";
//				String editOutputSQL = "update td_wallet set balance=balance+"+ sum +" where flag=1 and address='"+output+"'";
				String editOutputSQL = "update td_wallet set balance=balance+"+ sum +" where flag=1 and address='"+output+"'";
				String editTxSQL = "update td_transaction set status=2,blockid='"+blockid+"' where flag=1 and transactionid='"+transactionid+"'";

				try {
//					dbService.UpdateBySql(editInputSQL);
					dbService.UpdateBySql(editOutputSQL);
					dbService.UpdateBySql(editTxSQL);
					result.put("status", ResponseUtils.returnSuccessMessage());

				} catch (Exception e) {
					result.put("status", ResponseUtils.returnErrorMessage("update balance db error"));
					e.printStackTrace();
					break;
				}
				merkleString += transactionid;
				transactionnumber++;

			}else {
				//验证未通过，取消交易,退钱
				String editTxSQL = "update td_transaction set status=3,blockid='null' where flag=1 and transactionid='"+transactionid+"'";
				String editInputSQL = "update td_wallet set balance=balance+"+ sum +" where flag=1 and address='"+input+"'";
//				String editOutputSQL = "update td_wallet set balance=balance-"+ sum +" where flag=1 and address='"+output+"'";
				try {
					dbService.UpdateBySql(editTxSQL);
					dbService.UpdateBySql(editInputSQL);
//					dbService.UpdateBySql(editOutputSQL);
				} catch (Exception e) {
					result.put("status", ResponseUtils.returnErrorMessage("update tx_status db error"));
					break;
				}
			}

		}

		String merkleroothash = SHA256Util.SHA256(merkleString);
		block.put("merkleroothash", merkleroothash);
		block.put("transactionnumber", transactionnumber);

		try {
			dbService.Insert("td_block", block);
			//区块奖励
			dbService.UpdateBySql("update td_wallet set balance=balance+5 where flag=1 and address='"+address+"'");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", ResponseUtils.returnErrorMessage("block insert error"));
			return result;
		}
		result.put("status", ResponseUtils.returnSuccessMessage());



		return result;
	}
	
	
	
}
