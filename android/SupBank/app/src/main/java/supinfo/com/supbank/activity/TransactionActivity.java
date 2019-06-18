package supinfo.com.supbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import supinfo.com.supbank.R;
import supinfo.com.supbank.adapter.TransactionItemAdapter;
import supinfo.com.supbank.database.LocalData;
import supinfo.com.supbank.service.WalletServiceImpl;
import supinfo.com.supbank.utils.TimeUtils;

/**
 * @Author long
 */
public class TransactionActivity extends AppCompatActivity {
    EditText searchText;
    Button searchBtn;
    Button goBack;
    ListView transaction;
    TransactionItemAdapter adapter;
    private List<Map<String,Object>> data = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        searchText = (EditText)findViewById(R.id.search_msg);
        searchBtn = (Button)findViewById(R.id.search_btn);
        goBack = (Button)findViewById(R.id.go_back);
        transaction = (ListView)findViewById(R.id.transaction);
        data = this.defaultData();
        adapter = new TransactionItemAdapter(this,data);
        transaction.setAdapter(adapter);
        //收索
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Map<String,Object>> result = getSearchData(searchText.getText().toString(), data);
                adapter = new TransactionItemAdapter(getApplicationContext(),result);
                transaction.setAdapter(adapter);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //回退
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchResPage = new Intent();
                searchResPage.setClass(TransactionActivity.this,WalletActivity.class);
                startActivity(searchResPage);
                TransactionActivity.this.finish();
            }
        });

    }


    //use the search message to set the data jump to the search children page;
    private List<Map<String,Object>> getSearchData(String msg, List<Map<String,Object>> transactions){
        if(msg == null || msg.equals("")){
            return transactions;
        }
        List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> t : transactions){
            if(match(t,msg)){
                res.add(t);
            }
        }
        return res;
    }

    //use the regex to match each recipe

    private boolean match(Map<String,Object> transaction, String inputString){
        boolean match = false;
        Pattern pattern = Pattern.compile(inputString);
        String tString = transaction.get("timestamp").toString() + transaction.get("address").toString() + transaction.get("type").toString() + transaction.get("sum").toString() + transaction.get("status").toString();
        Matcher matcher = pattern.matcher(tString);
        while (matcher.find()){
            match = true;
        }
        return match;
    }




    /**
     * 获取最近交易记录
     * @return
     */
    private List<Map<String, Object>> defaultData() {
        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> temp = getRecentTransactions();
        String myAddress = LocalData.getNativeAccount(getApplicationContext()).get("address").toString();
        Map<String, Object> map;
        for (Map<String, Object> t : temp){
            map = new HashMap<>();
            if(t.get("output").toString().equals(myAddress)){
                map.put("address", t.get("input").toString());
            }else{
                map.put("address", t.get("output").toString());
            }
            map.put("sum", t.get("sum").toString());
            if(t.get("type").toString().equals("pay")){
                map.put("type", "发送交易");
            }else{
                map.put("type", "接收交易");
            }
            if(t.get("status").toString().equals("1")){
                map.put("status", "未验证");
            }else if(t.get("status").toString().equals("2")){
                map.put("status", "验证通过");
            }else {
                map.put("status", "验证未通过");
            }
            map.put("timestamp", TimeUtils.stampToDate(t.get("timestamp").toString()));
            result.add(map);
        }
        return result;
    }

    private List<Map<String,Object>> getRecentTransactions(){
        List<Map<String,Object>> result = new ArrayList<>();
        String address = LocalData.getNativeAccount(getApplicationContext()).get("address").toString();
        Map<String,Object> response = WalletServiceImpl.getInstance().getRecentTransaction(address);
        if(response == null){
            return result;
        }
        Map<String,Object> status = (Map) response.get("status");
        if(status.get("Ack").toString().equals("success")){
            result = (List)response.get("transactionList");
        }
        return result;
    }


}
