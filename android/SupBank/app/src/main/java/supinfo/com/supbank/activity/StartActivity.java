package supinfo.com.supbank.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import supinfo.com.supbank.R;

/**
 * @Author Long
 */
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button startBtn = findViewById(R.id.startApp);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent();
                loginPage.setClass(StartActivity.this,LoginActivity.class);
                startActivity(loginPage);
                StartActivity.this.finish();
            }
        });

    }
}
