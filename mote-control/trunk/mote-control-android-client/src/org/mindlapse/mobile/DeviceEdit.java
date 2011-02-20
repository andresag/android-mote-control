package org.mindlapse.mobile;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeviceEdit extends Activity {

	private EditText mNameText;
	private EditText mIPAddrText;
	private EditText mBcAddrText;
	private EditText mMACText;
	private EditText mCatText;
	private Long mRowId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_edit);
		//setTitle(R.string.edit_device);
		mNameText = (EditText) findViewById(R.id.name);
		mIPAddrText = (EditText) findViewById(R.id.ipaddr);
		mBcAddrText = (EditText) findViewById(R.id.bcaddr);
		mMACText = (EditText) findViewById(R.id.mac);
		mCatText = (EditText) findViewById(R.id.cat);
		Button confirmButton = (Button) findViewById(R.id.confirm);
		mRowId = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String name = extras.getString(MoteDbAdapter.KEY_NAME);
			String ipaddr= extras.getString(MoteDbAdapter.KEY_IPADDR);
			String bcaddr= extras.getString(MoteDbAdapter.KEY_BCADDR);
			String mac= extras.getString(MoteDbAdapter.KEY_MAC);
			String cat= extras.getString(MoteDbAdapter.KEY_CAT);
		    mRowId = extras.getLong(MoteDbAdapter.KEY_ROWID);

		    if (name != null) {
		        mNameText.setText(name);
		    }
		    if (ipaddr != null) {
		    	mIPAddrText.setText(ipaddr);
		    }
		    if (mac != null) {
		    	mMACText.setText(mac);
		    }
		    if (bcaddr != null) {
		    	mBcAddrText.setText(bcaddr);
		    }
		    if (cat != null) {
		    	mCatText.setText(cat);
		    }
		}
		
		confirmButton.setOnClickListener(new View.OnClickListener() {

		    public void onClick(View view) {
		    	Bundle bundle = new Bundle();
		    	bundle.putString(MoteDbAdapter.KEY_NAME, mNameText.getText().toString());
		    	bundle.putString(MoteDbAdapter.KEY_IPADDR, mIPAddrText.getText().toString());
		    	bundle.putString(MoteDbAdapter.KEY_BCADDR, mBcAddrText.getText().toString());
		    	bundle.putString(MoteDbAdapter.KEY_MAC, mMACText.getText().toString());
		    	bundle.putString(MoteDbAdapter.KEY_CAT, mCatText.getText().toString());
		    	
		    	if (mRowId != null) {
		    	    bundle.putLong(MoteDbAdapter.KEY_ROWID, mRowId);
		    	}
		    	Intent mIntent = new Intent();
		    	mIntent.putExtras(bundle);
		    	setResult(RESULT_OK, mIntent);
		    	finish();
		    	
		    }
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	 System.out.println("nothing time...............................................................................");
		        Bundle bundle = new Bundle();
		    	Intent mIntent = new Intent();
		    	mIntent.putExtras(bundle);
		    	setResult(RESULT_OK, mIntent);
		    	finish();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
}
