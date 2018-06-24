package cc.example.com.myzingproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cc.example.com.myzingproject.R;
import cc.example.com.myzingproject.activity.CaptureActivity;
import cc.example.com.myzingproject.activity.DriveRouteActivity;
import cc.example.com.myzingproject.activity.MapActivity;
import cc.example.com.myzingproject.activity.SearchActivity;
import cc.example.com.myzingproject.activity.SignAllActivity;
import cc.example.com.myzingproject.activity.SignAlreadyActivity;
import cc.example.com.myzingproject.activity.SignWillActivity;


/**
 * 首页
 * Created by CC on 2017/4/6.
 */

public class HomeFragment extends Fragment {
    private ImageView mScanImage;
    private ImageView mSearchImage;
    private TextView mLocationImage;

    private View mSignAll,mSignAlready,mSignWill;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {

        mScanImage = (ImageView)view.findViewById(R.id.scan_image_view);
        mSearchImage = (ImageView)view.findViewById(R.id.img_search_home);
        mLocationImage = (TextView)view.findViewById(R.id.location_home);

        mSignAll = (View)view.findViewById(R.id.view_sign_all);
        mSignAlready = view.findViewById(R.id.view_sign_already);
        mSignWill = view.findViewById(R.id.view_sign_will);

        mSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        mScanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CaptureActivity.class);
                startActivity(intent);
            }
        });

        mLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DriveRouteActivity.class);
                startActivity(intent);
            }
        });

        mSignAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignAllActivity.class);
                startActivity(intent);
            }
        });

        mSignAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignAlreadyActivity.class);
                startActivity(intent);
            }
        });

        mSignWill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignWillActivity.class);
                startActivity(intent);
            }
        });
    }
}
