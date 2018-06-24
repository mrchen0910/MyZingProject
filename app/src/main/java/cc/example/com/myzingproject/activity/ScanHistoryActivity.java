package cc.example.com.myzingproject.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import cc.example.com.myzingproject.R;
import cc.example.com.myzingproject.db.PhonesDB;
import cc.example.com.myzingproject.fragment.MessageFragment;

/**
 * 扫码历史
 * Created by CC on 2017/4/22.
 */

public class ScanHistoryActivity extends Activity{

    private ListView mPhoneListView;

    private PhonesDB mPhoneDB;
    private Cursor mCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_history);

        initDb();
    }

    private void initDb() {

        mPhoneDB = new PhonesDB(this);
        mCursor = mPhoneDB.select();
        //mPhoneEdit = (EditText)view.findViewById(R.id.text_message);
        mPhoneListView = (ListView)findViewById(R.id.scan_history_list);

        mPhoneListView.setAdapter(new BooksListAdapter(this,mCursor));

    }

    public class BooksListAdapter extends BaseAdapter {
        private Context mContext;
        private Cursor mCursor;
        public BooksListAdapter(Context context,Cursor cursor) {

            mContext = context;
            mCursor = cursor;
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
            TextView mTextView = new TextView(mContext);
            mCursor.moveToPosition(position);
            mTextView.setText(mCursor.getString(1));
            mTextView.setTextSize(16);
            mTextView.setHeight(48);
            return mTextView;
        }

    }
}
