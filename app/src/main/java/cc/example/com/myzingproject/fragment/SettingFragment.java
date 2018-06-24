package cc.example.com.myzingproject.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cc.example.com.myzingproject.R;
import cc.example.com.myzingproject.activity.FeedBackActivity;
import cc.example.com.myzingproject.activity.LoginActivity;
import cc.example.com.myzingproject.activity.ScanHistoryActivity;
import cc.example.com.myzingproject.db.PhonesDB;

/**
 * 我的页面
 * Created by CC on 2017/4/6.
 */

public class SettingFragment extends Fragment {

    private TextView mLoginTV;
    private View mFeedView;
    private View mClearView;
    private View mScanHistroyView;
    private View mSettingView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        mLoginTV = (TextView)view.findViewById(R.id.button_login);
        mFeedView = (View)view.findViewById(R.id.view_feed_back);
        mScanHistroyView = (View)view.findViewById(R.id.view_scan_history);
        mClearView = (View)view.findViewById(R.id.view_clear_cache);
        mSettingView = (View)view.findViewById(R.id.view_setting_button);

        mFeedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(intent);
            }
        });

        mSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mScanHistroyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanHistoryActivity.class);
                startActivity(intent);
            }
        });
        mLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,100);
            }
        });
        mClearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhonesDB mPhoneDB = new PhonesDB(getActivity());
                //mCursor = mPhoneDB.select();

                mPhoneDB.deleteDatabse();
                Toast.makeText(getActivity(),"清除成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            mLoginTV.setText("已登录");
        }
    }
}
