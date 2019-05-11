package com.supbank.dao.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.supbank.util.ResponseUtils;
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
		List<DataRow> heightestBlockList = dbService.queryForList("select blockid,height,hash,prehash,merkleroothash,nonce,blockreward,transactionnumber,miner from td_block where flag=1 and islegal=1 and height =(select max(height) from td_block where flag=1 and islegal=1)");

		List<DataRow> longestLegalChain = new ArrayList<DataRow>();
		int pageNumber = params.getInt("pageNumber");


		int pageSize = 5;


		for (DataRow dataRow : heightestBlockList) {

			do {
				longestLegalChain.add(dataRow);

				String prehash = dataRow.getString("prehash");
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
		}
		if(longestLegalChain.size()<pageSize){
			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("longestLegalChain", longestLegalChain);
			return result;
		}
		if(longestLegalChain.size()>=pageNumber*pageSize){
			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("longestLegalChain", longestLegalChain.subList((pageNumber - 1)*pageSize, pageNumber*pageSize));
			return result;
		}
		if(longestLegalChain.size()<pageNumber*pageSize){
			result.put("status", ResponseUtils.returnSuccessMessage());
			result.put("longestLegalChain", longestLegalChain.subList((pageNumber - 1)*pageSize, longestLegalChain.size()));
			return result;
		}


		
		return result;
		
	}
	
	
	
	
	
	
	
}
