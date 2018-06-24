package cc.example.com.myzingproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cc.example.com.myzingproject.R;

/**
 * 登录页面
 * Created by CC on 2017/4/17.
 */


    public class LoginActivity extends Activity {

        private EditText mPhoneEdit,mPassworEdit;
        private Button mLoginButton;
        private Intent mIntent;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            initView();
            setListener();
        }

        private void initView() {
            mPhoneEdit = (EditText)findViewById(R.id.email);
            mPassworEdit = (EditText)findViewById(R.id.password);
            mLoginButton = (Button)findViewById(R.id.email_sign_in_button);

            mIntent = getIntent();

        }

    private void setListener(){
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPhoneEdit.getText().toString().equals("18862111336")
                        &&mPassworEdit.getText().toString().equals("123456")){
                    Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();

                    setResult(101,mIntent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
