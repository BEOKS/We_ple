package com.example.beoks.gameis.weple.Activity.LoginActivity.version01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.beoks.gameis.weple.R;

public class UserSelectPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_typeselect);

        Button ownerButton=(Button)findViewById(R.id.ownerButton);
        Button customerButton=(Button)findViewById(R.id.customerButton);

        ownerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("type",MainLoginActivity.owner);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("type",MainLoginActivity.customer);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
