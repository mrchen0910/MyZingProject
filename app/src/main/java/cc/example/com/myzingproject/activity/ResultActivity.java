package cc.example.com.myzingproject.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cc.example.com.myzingproject.R;
import cc.example.com.myzingproject.db.PhonesDB;
import cc.example.com.myzingproject.decode.DecodeThread;

public class ResultActivity extends Activity {

	private ImageView mResultImage,backImage;
	private TextView mResultText;
	private String result;
	private String content="测试短信";
	private TextView mcontent;
	private Button sendBt,callBt,continueBt;

	private MyApplication app;
	private List<String> numList;
	private Intent intent;
	private static final int  REQUEST_CODE = 0;
	private static final int RESULT_OK = 1;

	private PhonesDB mPhoneDB;
	private Cursor mCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Bundle extras = getIntent().getExtras();

		initView();

		initVariables();

		if (null != extras) {
			int width = extras.getInt("width");
			int height = extras.getInt("height");

//			LayoutParams lps = new LayoutParams(width, height);
//			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//
//			//mResultImage.setLayoutParams(lps);

			result = extras.getString("result");
			mResultText.setText(result);


			/**
			 * //将数据存入数据库
			 */
			mPhoneDB = new PhonesDB(this);
			//mCursor = mPhoneDB.select();
			if("".equals(result)){
				return;
			}
			mPhoneDB.insert(result);


			//将扫描结果加入到list当中
			app.addNumberList(result);
			//设置监听事件
			setListener();
			//设置图片
			Bitmap barcode = null;
			byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
			if (compressedBitmap != null) {
				barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
				// Mutable copy:
				barcode = barcode.copy(Bitmap.Config.RGB_565, true);
			}
			mResultImage.setImageBitmap(barcode);
		}
	}

	private void initVariables() {
		app=(MyApplication)getApplication();
		numList=app.getNumberList();
	}

	private void initView() {

		mResultImage = (ImageView) findViewById(R.id.result_image);
		backImage=(ImageView)findViewById(R.id.id_back_arrow);
		mResultText = (TextView) findViewById(R.id.result_text);
		mcontent=(TextView)findViewById(R.id.id_content);
		sendBt = (Button)findViewById(R.id.id_send);
		callBt = (Button)findViewById(R.id.id_call);
		continueBt=(Button)findViewById(R.id.id_continue);

	}
	private void setListener() {

		sendBt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				send();
				Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_SHORT).show();
			}
		});

		callBt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				call();
			}
		});

		continueBt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		backImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mcontent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(ResultActivity.this, EditContentActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});

	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case REQUEST_CODE:
				if (resultCode == RESULT_OK){
					Bundle bundle = data.getExtras();
					String content = bundle.getString("content");

					mcontent.setText(content);
				}
		}
	}

	private void send(){
		String SENT = "sms_sent";
		String DELIVERED = "sms_delivered";

		PendingIntent sentPI = PendingIntent.getActivity(this, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getActivity(this, 0, new Intent(DELIVERED), 0);

		registerReceiver(new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				switch(getResultCode())
				{
					case Activity.RESULT_OK:
						Log.i("====>", "Activity.RESULT_OK");
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						Log.i("====>", "RESULT_ERROR_GENERIC_FAILURE");
						break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						Log.i("====>", "RESULT_ERROR_NO_SERVICE");
						break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						Log.i("====>", "RESULT_ERROR_NULL_PDU");
						break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						Log.i("====>", "RESULT_ERROR_RADIO_OFF");
						break;
				}
			}
		}, new IntentFilter(SENT));

		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent){
				switch(getResultCode())
				{
					case Activity.RESULT_OK:
						Log.i("====>", "RESULT_OK");
						break;
					case Activity.RESULT_CANCELED:
						Log.i("=====>", "RESULT_CANCELED");
						break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager smsm = SmsManager.getDefault();
		for(int i=0;i<numList.size();i++) {
			smsm.sendTextMessage(numList.get(i).toString(), null, mcontent.getText().toString(), sentPI, deliveredPI);
		}
	}

	private void call(){
		Intent intent = new Intent(Intent.ACTION_DIAL);
		Uri data = Uri.parse("tel:" + result);
		intent.setData(data);
		startActivity(intent);
	}

}
