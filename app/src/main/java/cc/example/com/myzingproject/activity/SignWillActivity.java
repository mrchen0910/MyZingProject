package cc.example.com.myzingproject.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import cc.example.com.myzingproject.R;
import cc.example.com.myzingproject.db.PhonesDB;

/**
 * 未签收界面
 * Created by CC on 2017/4/23.
 */

public class SignWillActivity extends Activity {

    private PhonesDB mPhoneDB;
    private Cursor mCursor;
    private ListView mPhoneListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_will_sign);

        initDb();
    }

    private void initDb() {

        mPhoneDB = new PhonesDB(this);
        mCursor = mPhoneDB.select();
        mPhoneListView = (ListView)findViewById(R.id.sign_will_list);

        mPhoneListView.setAdapter(new BooksListAdapter(this,mCursor));

    }

    public class BooksListAdapter extends BaseAdapter {
        private Context mContext;
        private Cursor mCursor;
        private LayoutInflater inflater;

        public BooksListAdapter(Context context,Cursor cursor) {
            mContext = context;
            mCursor = cursor;
            inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return mCursor.getCount();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holoder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_sign_button, null);
                holoder = new BooksListAdapter.ViewHolder();
                holoder.tvPhone = (TextView) convertView.findViewById(R.id.item_phone_text);
                holoder.tvButton = (TextView) convertView.findViewById(R.id.btn_sign);
                convertView.setTag(holoder);
            } else {
                holoder = (BooksListAdapter.ViewHolder) convertView.getTag();
            }

            mCursor.moveToPosition(position);
            holoder.tvPhone.setText(mCursor.getString(1));
            holoder.tvButton.setText("未签收");
            return convertView;
        }

        public class ViewHolder {
            TextView tvPhone;
            TextView tvButton;
        }

    }
}
