package cc.example.com.myzingproject.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import cc.example.com.myzingproject.R;

import static android.provider.Telephony.ThreadsColumns.ERROR;

/**
 * 备份
 * Created by CC on 2017/5/3.
 */

public class BackUpActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);


    }

}
