package com.supbank.controller;

import com.alibaba.fastjson.JSON;
import com.supbank.base.DataRow;
import com.supbank.dao.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    /**
     * 产生交易
     * @param request
     * @param params
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/transfer")
    public String createTransaction(HttpServletRequest request, @RequestBody DataRow<String,String> params) {
        DataRow result = walletService.generateTransaction(request, params);
        return JSON.toJSONString(result);
    }




    /**
     * 查询用户自己近期交易
     * @param request
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/recentTx")
    public String queryRecentTransaction(HttpServletRequest request, @RequestBody DataRow<String, String> params) {
        DataRow result = walletService.getRecentTransactions(request, params);
        return JSON.toJSONString(result);
    }


    /**
     * 获取当前用户余额
     * @param request
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/getBalance")
    public String getBalance(HttpServletRequest request, @RequestBody DataRow<String, String> params) {
        DataRow result = null;
        result = walletService.getBalance(request, params);
        return JSON.toJSONString(result);
    }




    @CrossOrigin
    @ResponseBody
    @PostMapping("/verify")
    public String verifyTx() {
        DataRow result = walletService.txVerifyAndEditBalance("1904271708530001", "test_blockid");
        return JSON.toJSONString(result);
    }



}
