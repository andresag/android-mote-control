package org.mindlapse.mobile;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DeviceView extends Activity {

	private Long mRowId;
	private String name;
	private String ipaddr;
	private String bcaddr;
	private String mac;
	private String cat;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_view);
		//setTitle(R.string.edit_device);
		Button wakeButton = (Button) findViewById(R.id.wake);
		Button sleepButton = (Button) findViewById(R.id.sleep);
		Button editButton = (Button) findViewById(R.id.edit);
		TextView textView = (TextView) findViewById(R.id.details);

		mRowId = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			name = extras.getString(MoteDbAdapter.KEY_NAME);
			ipaddr= extras.getString(MoteDbAdapter.KEY_IPADDR);
			bcaddr= extras.getString(MoteDbAdapter.KEY_BCADDR);
			mac= extras.getString(MoteDbAdapter.KEY_MAC);
			cat= extras.getString(MoteDbAdapter.KEY_CAT);
		    mRowId = extras.getLong(MoteDbAdapter.KEY_ROWID);
		    String details = "";
		    details += "id:"+mRowId+"\n";
		    details += "Name:"+name+"\n";
		    details += "IP:"+ipaddr+"\n";
		    details += "bcaddr:"+bcaddr+"\n";
		    details += "MAC:"+mac+"\n";
		    details += "Category:"+cat+"\n";
		    textView.setText(details);

		}
		
		
		wakeButton.setOnClickListener(new View.OnClickListener() {

		    public void onClick(View view) {
		        System.out.println("Waky Waky...............................................................................");
		        Bundle bundle = new Bundle();
	    	   RunWake run = new RunWake();
		   		run.wake(bcaddr,mac);
		    	Intent mIntent = new Intent();
		    	mIntent.putExtras(bundle);
		    	setResult(RESULT_OK, mIntent);
		    	finish();

		    	
		    }
		});
		
		sleepButton.setOnClickListener(new View.OnClickListener() {

		    public void onClick(View view) {
		        System.out.println("Sleep time...............................................................................");
		        Bundle bundle = new Bundle();
		        RunCommand run = new RunCommand();
		        run.start(ipaddr, "SLEEP");
		    	Intent mIntent = new Intent();
		    	mIntent.putExtras(bundle);
		    	setResult(RESULT_OK, mIntent);
		    	finish();

		    	
		    }
		});
		
		editButton.setOnClickListener(new View.OnClickListener() {

		    public void onClick(View view) {
		        System.out.println("nothing time...............................................................................");
		        Bundle bundle = new Bundle();
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
