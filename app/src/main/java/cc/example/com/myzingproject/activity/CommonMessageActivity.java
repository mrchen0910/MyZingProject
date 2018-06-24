package cc.example.com.myzingproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cc.example.com.myzingproject.R;
import cc.example.com.myzingproject.utils.CommonAdapter;
import cc.example.com.myzingproject.utils.ViewHolder;

/**
 * 常用短语
 * Created by CC on 2017/4/22.
 */

public class CommonMessageActivity extends Activity{

    private EditText meditmessage;
    private Button sureButton,clearButton;
    private ListView mListview;
    private ImageView back_arrow;

    public static int MODE = MODE_PRIVATE;//定义访问模式为私有模式
    public static final String PREFERENCE_NAME = "SMSContent";//设置保存时的文件的名称
    private SharedPreferences record;
    private SharedPreferences.Editor editor;
    private List<String> data;
    private CommonAdapter mAdapter;
    private int totalNum;

    public static final String SMS_CONTENT="content";
    private static final int RESULT_OK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_message);

        initView();
        initVariables();
        initListView();         //初始化历史记录的listview
        setonClickListener();    //注册监听器
    }

    private void initView() {
        meditmessage=(EditText)findViewById(R.id.id_edit_message);
        sureButton=(Button)findViewById(R.id.id_sure);
        mListview=(ListView)findViewById(R.id.common_message_list);
        //clearButton=(Button)findViewById(R.id.bt_clear);
    }
    public void initVariables() {
        record = getSharedPreferences(PREFERENCE_NAME, MODE);
        editor = record.edit();

        data = new ArrayList<>();
        mAdapter = new CommonAdapter<String>(this,data,R.layout.item_content) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.id_item_textview,item);
            }
        };
        mListview.setAdapter(mAdapter);
    }

    public void initListView() {
        getData();
        if (data != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setonClickListener() {
/*        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear().commit();
                data.clear();
                mAdapter.notifyDataSetChanged();
            }
        });*/
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(meditmessage.getText().toString()) && meditmessage.getText().toString() != null) {
                    setData();
                    //passData();
                }else {
                    Toast.makeText(CommonMessageActivity.this,"请输入短信内容",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                meditmessage.setText(data.get(position));
            }
        });

    }

    private void passData(){
        Intent intent=new Intent();
        intent.putExtra(SMS_CONTENT,meditmessage.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getData() {
        int i = 0;
        String str = record.getString("item" + i, "");
        while (!str.equals("") && str != null) {
            data.add(i, str);
            i++;
            str = record.getString("item" + i, "");
        }
        totalNum = i;
        //处理显示历史记录最大个数
        if (data.size() > 10) {
            List<String> templist = new ArrayList<String>();
            String temp_str;
            for (int j = data.size() - 10; j < data.size(); j++) {
                temp_str = data.get(j);
                templist.add(temp_str);
            }
            data.clear();
            data.addAll(templist);
        }
        removeDuplicate(data);
    }

    private void setData() {
        String str = meditmessage.getText().toString();
        String temp;
        int i = 0;

        if (!str.equals("") && str != null) {
            while ((temp = record.getString("item" + i, "")) != str && temp != null) {
                if (i == totalNum) {
                    editor.putString("item" + totalNum, str);
                    editor.commit();
                    data.add(str);
                    totalNum++;
                    break;
                }
                i++;
            }
        }
        //处理显示历史记录最大个数
        if (data.size() > 10) {
            List<String> templist = new ArrayList<String>();
            String temp_str;
            for (int j = data.size() -10; j < data.size(); j++) {
                temp_str = data.get(j);
                templist.add(temp_str);
            }
            data.clear();
            data.addAll(templist);
        }

        removeDuplicate(data);
        mAdapter.notifyDataSetChanged();
    }

    public void removeDuplicate(List<String> list) {   //去重
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }
}

