package supinfo.com.supbank.activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Map;

import supinfo.com.supbank.R;
import supinfo.com.supbank.bean.WalletImpl;
import supinfo.com.supbank.database.LocalData;
import supinfo.com.supbank.service.WalletServiceImpl;
/**
 * @Author long
 */
public class LoginActivity extends AppCompatActivity{

    private WalletServiceImpl walletService = WalletServiceImpl.getInstance();

    private EditText mPasswordView;

    private WalletImpl wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /**
         * android 4 版本不允许主线程http
         */
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        wallet = new WalletImpl();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        if(LocalData.isNative(getApplicationContext())){
            mEmailSignInButton.setText("登录");
        }
    }

    /**
     * 输入支付密码登录系统
     */
    private void attemptLogin() {
        mPasswordView.setError(null);
        String password = mPasswordView.getText().toString();
        if(!LocalData.isNative(getApplicationContext())){
            //非本地登录
            if(!isPasswordValid(password)){
                mPasswordView.setError(getString(R.string.error_invalid_password));
                return;
            }else{
                boolean create = this.createMyWallet(password);
                if(create){
                    this.jumpMyWallet();
                }else{
                    mPasswordView.setError("发生了一个错误");
                    return;
                }
            }
        }else{
            //本地登录
            if(LocalData.loginNative(password,getApplicationContext())){
                this.jumpMyWallet();
            }else{
                mPasswordView.setError(getString(R.string.error_incorrect_password));
            }
        }
    }

    /**
     * 密码长度大于6位，包含字母和数字
     * @param passWord
     * @return
     */
    private boolean isPasswordValid(String passWord){
        if(passWord == null){
            return false;
        }
        return passWord.matches("^(?!\\d+$)(?![a-zA-Z]+$)[a-zA-Z\\d]+$") && passWord.length() >= 6;
    }

    /**
     * 跳转我的app钱包
     */
    private void jumpMyWallet(){
        Intent loginPage = new Intent();
        loginPage.setClass(LoginActivity.this,WalletActivity.class);
        startActivity(loginPage);
        LoginActivity.this.finish();
    }

    /**
     * 远程服务器登录检测
     * @param passWord
     * @return
     */
    private boolean createMyWallet(String passWord){
        Map<String,Object> param = wallet.createWallet();
        String ack = "";
        if(null != param){
            Map<String,Object> response = walletService.createWallet(param.get("address").toString(), param.get("pubKey").toString(), passWord);
            if(null != response){
                Map<String,Object> status = (Map) response.get("status");
                ack = status.get("Ack").toString();
                if(ack.equals("success")){
                    try{
                        param.put("password", passWord);
                        LocalData.writeNative(param,getApplicationContext());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }
}

