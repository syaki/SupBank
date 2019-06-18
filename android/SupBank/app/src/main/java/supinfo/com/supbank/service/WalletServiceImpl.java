package supinfo.com.supbank.service;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
/**
 * @Author long
 */
import supinfo.com.supbank.manager.WalletServerManager;
public class WalletServiceImpl implements WalletService {

    private static WalletServiceImpl walletService = new WalletServiceImpl();

    private WalletServiceImpl(){
    }

    public static WalletServiceImpl getInstance(){
        if(walletService==null){
            return new WalletServiceImpl();
        }
        return walletService;
    }

    @Override
    public Map<String, Object> digMine(String address) {
        Map<String,Object> params = new HashMap<>();
        params.put("address", address);
        return JSON.parseObject(WalletServerManager.httpPost("/block/create", JSON.toJSONString(params)));
    }

    @Override
    public Map<String, Object> transfer(String input, String output, double sum, String signature ,String password) {
        Map<String,Object> params = new HashMap<>();
        params.put("input", input);
        params.put("output", output);
        params.put("sum", sum);
        params.put("signature", signature);
        params.put("password", password);
        return JSON.parseObject(WalletServerManager.httpPost("/wallet/transfer", JSON.toJSONString(params)));
    }

    @Override
    public Map<String, Object> createWallet(String address, String publicKey, String password) {
        Map<String,Object> params = new HashMap<>();
        params.put("address", address);
        params.put("publicKey", publicKey);
        params.put("password", password);
        return JSON.parseObject(WalletServerManager.httpPost("/wallet/create", JSON.toJSONString(params)));
    }

    @Override
    public Map<String, Object> getRecentTransaction(String address) {
        Map<String,Object> params = new HashMap<>();
        params.put("address", address);
        return JSON.parseObject(WalletServerManager.httpPost("/wallet/recentTx", JSON.toJSONString(params)));

    }

    @Override
    public Map<String, Object> getBalance(String address) {
        Map<String,Object> params = new HashMap<>();
        params.put("address", address);
        return JSON.parseObject(WalletServerManager.httpPost("/wallet/getBalance", JSON.toJSONString(params)));
    }
}
