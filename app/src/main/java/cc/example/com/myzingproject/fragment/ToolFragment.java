package cc.example.com.myzingproject.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.example.com.myzingproject.R;
import cc.example.com.myzingproject.activity.BackUpActivity;
import cc.example.com.myzingproject.activity.BlackMenuActivity;
import cc.example.com.myzingproject.activity.CollectionActivity;
import cc.example.com.myzingproject.activity.CommonMessageActivity;
import cc.example.com.myzingproject.activity.DraftActivity;


/**
 * 工具箱页面
 * Created by CC on 2017/4/6.
 */

public class ToolFragment extends Fragment {

    private View mCommonMessageView;
    private View mDraftView;
    private View mCollectionView;
    private View mBlackMenuView;
    private View mBackupView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tool,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        mCommonMessageView = (View)view.findViewById(R.id.view_common_message);
        mDraftView = view.findViewById(R.id.view_draft);
        mCollectionView = view.findViewById(R.id.view_collection);
        mBlackMenuView = view.findViewById(R.id.view_black_menu);
        mBackupView = view.findViewById(R.id.view_back_up);


        mCommonMessageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommonMessageActivity.class);
                startActivity(intent);
            }
        });
        mBackupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BackUpActivity.class);
                startActivity(intent);
            }
        });
        mBlackMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BlackMenuActivity.class);
                startActivity(intent);
            }
        });
        mCollectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
            }
        });
        mDraftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DraftActivity.class);
                startActivity(intent);
            }
        });
    }
}
