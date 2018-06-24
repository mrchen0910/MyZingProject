package cc.example.com.myzingproject.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cc.example.com.myzingproject.R;
import cc.example.com.myzingproject.db.PhonesDB;
import cc.example.com.myzingproject.entity.PersonInfo;
import cc.example.com.myzingproject.utils.CommonAdapter;
import cc.example.com.myzingproject.utils.ViewHolder;


/**
 * 短信页面
 * Created by CC on 2017/4/6.
 */

public class MessageFragment extends Fragment{

    private TextView mMessageText;
    private EditText mPhoneEdit;
    private ListView mPhoneListView;

    private PhonesDB mPhoneDB;
    private Cursor mCursor;

    private Button mButton;

    private List<PersonInfo> data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDb(view);
    }

    private void initDb(View view) {

        mPhoneDB = new PhonesDB(getActivity());
        mCursor = mPhoneDB.select();
        //mPhoneEdit = (EditText)view.findViewById(R.id.text_message);
        mPhoneListView = (ListView)view.findViewById(R.id.menssage_phone_list);

 /*       mPhoneListView.setAdapter(new CommonAdapter<PersonInfo>(getActivity(),data,R.layout.item_message_with_time) {
            @Override
            public void convert(ViewHolder helper, PersonInfo item) {
                helper.setText(R.id.item_phone_text,item.phone);
                helper.setText(R.id.item_time_text,item.time);
            }
        });*/
        //mListview.setAdapter(mAdapter);
        mPhoneListView.setAdapter(new BooksListAdapter(getActivity(),mCursor));

    }

    public void add(){
        String phonenumber = mPhoneEdit.getText().toString();

        if (phonenumber.equals("")){
            return;
        }
        mPhoneDB.insert(phonenumber);
        mCursor.requery();
        mPhoneListView.invalidateViews();
        mPhoneEdit.setText("");
        Toast.makeText(getActivity(), "Add Successed!", Toast.LENGTH_SHORT).show();
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
 /*           TextView mTextView = new TextView(mContext);
            mCursor.moveToPosition(position);
            mTextView.setText(mCursor.getString(1));
            return mTextView;*/

            ViewHolder holoder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_message_with_time, null);
                holoder = new ViewHolder();
                holoder.tvPhone = (TextView) convertView.findViewById(R.id.item_phone_text);
                holoder.tvTime = (TextView) convertView.findViewById(R.id.item_time_text);
                convertView.setTag(holoder);
            } else {
                holoder = (ViewHolder) convertView.getTag();
            }

            mCursor.moveToPosition(position);
            holoder.tvPhone.setText(mCursor.getString(1));
            //holoder.tvTime.setText("最近签收时间："+mCursor.getString(2));
            //BitmapHelp.display(context, holoder.ivIcon, item.avatar, R.drawable.icon_userphoto_loading, R.drawable.icon_userphoto_loading);
            return convertView;
        }

        public class ViewHolder {
            TextView tvPhone;
            TextView tvTime;
        }

    }


    //获取短信
    public String getSmsInPhone()
    {
        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";

        StringBuilder smsBuilder = new StringBuilder();

        try{
            ContentResolver cr = getContext().getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, projection, null, null, "date desc");

            if (cur.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                String type;

                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");

                do{
                    name = cur.getString(nameColumn);
                    phoneNumber = cur.getString(phoneNumberColumn);
                    smsbody = cur.getString(smsbodyColumn);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                    date = dateFormat.format(d);

                    int typeId = cur.getInt(typeColumn);
                    if(typeId == 1){
                        type = "接收";
                    } else if(typeId == 2){
                        type = "发送";
                    } else {
                        type = "";
                    }

                    smsBuilder.append("[");
                    smsBuilder.append(name+",");
                    smsBuilder.append(phoneNumber+",");
                    smsBuilder.append(smsbody+",");
                    smsBuilder.append(date+",");
                    smsBuilder.append(type);
                    smsBuilder.append("] ");

                    if(smsbody == null) smsbody = "";
                }while(cur.moveToNext());
            } else {
                smsBuilder.append("no result!");
            }

            smsBuilder.append("getSmsInPhone has executed!");
        } catch(SQLiteException ex) {
            //Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
        }
        return smsBuilder.toString();
    }
}
