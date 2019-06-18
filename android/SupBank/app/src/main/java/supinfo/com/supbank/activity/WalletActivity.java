package supinfo.com.supbank.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import supinfo.com.supbank.R;
import supinfo.com.supbank.database.LocalData;
import supinfo.com.supbank.service.WalletService;
import supinfo.com.supbank.service.WalletServiceImpl;
import supinfo.com.supbank.utils.RSAUtils;
import supinfo.com.supbank.utils.UIUtils;

/**
 * @Author long
 */
public class WalletActivity extends AppCompatActivity {

    /**
     * 查看交易记录
     */
    Button viewTransaction;
    /**
     * 我的余额
     */
    TextView myBalance;
    /**
     * 我的地址
     */
    TextView myAddress;
    /**
     * 转入地址
     */
    EditText transferredAddress;
    /**
     * 转入金额
     */
    EditText transferredAmount;
    /**
     * 转账
     */
    Button transferred;
    /**
     * 退出
     */
    Button exitBtn;
    /**
     * 挖矿
     */
    Button digMine;
    private String address = "";
    private Double amount = 0.0;
    private WalletService walletService;
    private String output = "";
    private Double sum = 0.0;
    /**
     * 挖矿对话框
     */
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        myAddress = (TextView)findViewById(R.id.my_address);
        myBalance = (TextView)findViewById(R.id.my_balance);
        transferredAddress = (EditText)findViewById(R.id.address_in);
        transferredAmount = (EditText)findViewById(R.id.amount);
        transferred = (Button)findViewById(R.id.submit);
        digMine = (Button)findViewById(R.id.dig_mine);
        walletService = WalletServiceImpl.getInstance();
        viewTransaction = (Button)findViewById(R.id.check_history);
        exitBtn = (Button)findViewById(R.id.exitBtn);
        mAlertDialog = new AlertDialog.Builder(WalletActivity.this, R.style.CustomProgressDialog).create();

        /**
         * 查询交易记录
         */
        viewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transactionDetails = new Intent();
                transactionDetails.setClass(WalletActivity.this,TransactionActivity.class);
                startActivity(transactionDetails);
                WalletActivity.this.finish();
            }
        });
        /**
         * 退出
         */
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent();
                loginPage.setClass(WalletActivity.this,LoginActivity.class);
                startActivity(loginPage);
                WalletActivity.this.finish();
            }
        });
        this.refreshData();
        /**
         * 挖矿
         */
        digMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(getApplicationContext());
            }
        });
        /**
         * 转账
         */
        transferred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output = transferredAddress.getText().toString();
                String amountString = transferredAmount.getText().toString();
                if(amountString == null || amountString.length()==0){
                    UIUtils.showShort("请输入交易金额！", WalletActivity.this);
                    return;
                }else{
                    sum =Double.valueOf(amountString);
                    if(sum>amount){
                        UIUtils.showShort("余额不足！", WalletActivity.this);
                        return;
                    }
                }
                if(!validateTransferAddress(output)){
                    //提示交易信息错误
                    UIUtils.showShort("请输入正确的交易地址！", WalletActivity.this);
                    return;
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalletActivity.this);
                    builder.setTitle("交易信息");
                    builder.setMessage("转出到:" + output + "\n" + "金额: " + String.valueOf(sum));
                    View view = LayoutInflater.from(WalletActivity.this).inflate(R.layout.pay_dialog, null);
                    builder.setView(view);
                    final EditText pay_pass = (EditText)view.findViewById(R.id.pay_pass);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String payPass = pay_pass.getText().toString();
                            if(payPass == null || payPass.length() == 0){
                                UIUtils.showShort("请输入支付密码！", WalletActivity.this);
                            }else{
                                if(!LocalData.loginNative(payPass, getApplicationContext())){
                                    UIUtils.showShort("支付密码错误！", WalletActivity.this);
                                }else{
                                    String result = getTransferMsg(getAddress(),output,sum,payPass);
                                    UIUtils.showLong(result, WalletActivity.this);
                                    transferredAddress.setText("");
                                    transferredAmount.setText("");
                                    refreshData();
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            UIUtils.showLong("您取消了本次交易！", WalletActivity.this);
                            output = "";
                            sum = 0.0;
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    /**
     * 刷新余额地址数据
     */
    private void refreshData(){
        this.address = getAddress();
        Double temp = getAmount(address);
        if(temp == -1){
            UIUtils.showLong("发生了一个错误，请联系管理员！", WalletActivity.this);
            return;
        }
        this.amount = temp;
        myAddress.setText("我的地址:     " + address);
        myBalance.setText("我的余额:     " + String.valueOf(amount));
    }

    /**
     * 获取地址
     * @return
     */
    private String getAddress(){
        return LocalData.getNativeAccount(getApplicationContext()).get("address").toString();
    }


    /**
     * 获取余额
     * @return
     */
    private double getAmount(String address){
        if(null == walletService.getBalance(address)){
            return -1;
        }
        return Double.valueOf (walletService.getBalance(address).get("balance").toString());
    }

    /**
     * 校验交易参数 至少32位地址
     * @param outputAddress 接收方地址
     * @return
     */
    private boolean validateTransferAddress(String outputAddress){
        if(null == outputAddress || outputAddress.length() == 0){
            return false;
        }
        return outputAddress.matches("^[A-Za-z0-9]{32,128}$");
    }

    /**
     * 发送交易
     * @param input 发送方地址
     * @param output 接收方地址
     * @param sum 金额
     * @return
     */
    private String getTransferMsg(String input, String output, Double sum, String payPass){
        String msg = input + output + sum;
        byte [] bytes = msg.getBytes();
        String pk = LocalData.getNativeAccount(getApplicationContext()).get("prvKey").toString();
        String signature = "";
        try {
            signature = RSAUtils.sign(bytes, pk);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> response = walletService.transfer(input, output ,sum , signature, payPass);
        Map<String,Object> status = (Map) response.get("status");
        String ack = status.get("Ack").toString();
        return ack.equals("success") ? "交易成功!" : "服务端提示: " + status.get("ErrorMessage").toString();
    }

    /**
     * 弹出耗时对话框
     * @param context
     */
    public void showProgressDialog(Context context) {
        View loadView = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        mAlertDialog.setView(loadView, 0, 0, 0, 0);
        mAlertDialog.setCanceledOnTouchOutside(false);
        TextView tvTip = loadView.findViewById(R.id.tvTip);
        tvTip.setText("挖矿中...请耐心等待！");
        walletService.digMine(getAddress());
        mAlertDialog.show();
        long time = UIUtils.getRandomMillsSeconds();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                mAlertDialog.dismiss();
                refreshData();
            }
        }, time);
    }
}
