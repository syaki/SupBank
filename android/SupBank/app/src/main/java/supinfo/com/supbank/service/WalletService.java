package supinfo.com.supbank.service;

import java.util.Map;
/**
 * @Author long
 */
public interface WalletService {

    /**
     * 挖矿
     * @param address 本机账号地址
     * @return
     */
    public Map<String,Object> digMine(String address);

    /**
     * 交易d
     * @param input 发送方地址
     * @param output 接收方地址
     * @param sum 金额
     * @param signature 签名
     * @return
     */
    public Map<String,Object> transfer(String input, String output, double sum, String signature, String password);

    /**
     * 创建钱包 （私钥存在本地）
     * @param address 地址
     * @param publicKey 公钥
     * @return
     */
    public Map<String,Object> createWallet(String address, String publicKey ,String password);

    /**
     * 获取最近交易信息
     * @param address 账号地址
     * @return
     */
    public Map<String,Object> getRecentTransaction(String  address);

    /**
     * 获取余额
     * @param address 账号地址
     * @return
     */
    public Map<String,Object> getBalance(String address);


}
