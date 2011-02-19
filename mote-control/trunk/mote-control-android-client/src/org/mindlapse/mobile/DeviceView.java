package org.mindlapse.mobile;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
		Button editButton = (Button) findViewById(R.id.edit);

		mRowId = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			name = extras.getString(MoteDbAdapter.KEY_NAME);
			ipaddr= extras.getString(MoteDbAdapter.KEY_IPADDR);
			bcaddr= extras.getString(MoteDbAdapter.KEY_BCADDR);
			mac= extras.getString(MoteDbAdapter.KEY_MAC);
			cat= extras.getString(MoteDbAdapter.KEY_CAT);
		    mRowId = extras.getLong(MoteDbAdapter.KEY_ROWID);

		}
		
		wakeButton.setOnClickListener(new View.OnClickListener() {

		    public void onClick(View view) {
		        System.out.println("Waky Waky...............................................................................");
	    	   RunWake run = new RunWake();
		   		run.wake(bcaddr,mac);

		    	
		    }
		});
		
	}
	
}
