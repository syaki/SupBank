package supinfo.com.supbank.manager;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Author long
 */
public class WalletServerManager {

    /**
     * 区块链地址 http://kwjia.top:8990
     */
    private static String serverAddress = "http://kwjia.top:8990";

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * get
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(serverAddress + url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * post
     * @param url
     * @param jsonParam
     * @return
     */
    public static String httpPost(String url, String jsonParam){
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, jsonParam);
        Request request = new Request.Builder()
                .url(serverAddress + url)
                .post(requestBody)
                .build();
        String result = "";
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
