package supinfo.com.supbank;


import com.alibaba.fastjson.JSON;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import supinfo.com.supbank.bean.WalletImpl;
import supinfo.com.supbank.service.WalletServiceImpl;
import supinfo.com.supbank.utils.RSAUtils;
import supinfo.com.supbank.utils.UIUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        WalletImpl wallet = new WalletImpl();
        wallet.createWallet();
    }

    @Test
    public void walletServiceTest(){
        WalletServiceImpl walletService = WalletServiceImpl.getInstance();
        System.out.println(JSON.toJSONString(walletService.getRecentTransaction("56aa8b2fd3c2109f598ae37ccc3dd19173d60497")));
    }

    @Test
    public void testReg(){
       String passWord = "asdf1";
       System.out.print(passWord.matches("^(?!\\d+$)(?![a-zA-Z]+$)[a-zA-Z\\d]+$") && passWord.length() >= 6);
    }

    @Test
    public void testCreateWallet(){
        WalletServiceImpl walletService = WalletServiceImpl.getInstance();
        System.out.print(JSON.toJSONString(walletService.createWallet("3wFofUnJL11FX9Lej3MnLKF2VVg", "213asdfqwsadfffasqwere", "12sadfsad")));
    }

    @Test
    public void testNativeWrite(){
        Map<String,Object> pa = new HashMap<>();
        pa.put("asdfqwerasdfqw", "asdfqqwer");
        pa.put("password", "asdfqqr");
//        LocalData.writeNative(pa, InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testNativeRead(){
//        System.out.print(LocalData.getNativeAccount());
    }

    @Test
    public void testReadAsString(){
//        System.out.print(LocalData.getStr());
    }

    @Test
    public void testRandomNum(){
        System.out.print(UIUtils.getRandomMillsSeconds());
    }

    @Test
    public void testSign(){
        byte [] bytes = "asdrqwerqsdf".getBytes();
        String pk = "adwerddsfadsgga";
        try {
            System.out.print(RSAUtils.sign(bytes, pk));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}