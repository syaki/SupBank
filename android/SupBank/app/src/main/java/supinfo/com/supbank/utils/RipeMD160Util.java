package supinfo.com.supbank.utils;

import java.security.MessageDigest;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class RipeMD160Util {

	/**
     * RipeMD160消息摘要
     * @param data 待处理的消息摘要数据
     * @return byte[] 消息摘要
     * */
    public static byte[] encodeRipeMD160(byte[] data) throws Exception{
        //加入BouncyCastleProvider的支持
        Security.addProvider(new BouncyCastleProvider());
        //初始化MessageDigest
        MessageDigest md=MessageDigest.getInstance("RipeMD160");
        //执行消息摘要
        return md.digest(data);
        
    }
    /**
     * RipeMD160Hex消息摘要
     * @param data 待处理的消息摘要数据
     * @return String 消息摘要
     * **/
    public static String encodeRipeMD160Hex(byte[] data) throws Exception{
        //执行消息摘要
        byte[] b=encodeRipeMD160(data);
        //做十六进制的编码处理
        return new String(Hex.encode(b));
    }
}
