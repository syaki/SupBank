package supinfo.com.supbank.bean;
import java.util.HashMap;
import java.util.Map;

import supinfo.com.supbank.utils.RSAUtils;

/**
 * 钱包应用程序
 */
public class WalletImpl implements Wallet {

    @Override
    public Map<String,Object> createWallet() {
        Map<String,Object> result = new HashMap<>();
        try {
            Map<String,Object> temp = RSAUtils.genKeyPair();
            String pubK = RSAUtils.getPublicKey(temp);
            String prvK = RSAUtils.getPrivateKey(temp);
            String address = temp.get("address").toString();
            if(pubK==null||pubK.equals("")||prvK==null||prvK.equals("")){
                throw new Exception("generate ECKey pair failed");
            }
            result.put("prvKey", prvK);
            result.put("pubKey", pubK);
            result.put("address", address);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
