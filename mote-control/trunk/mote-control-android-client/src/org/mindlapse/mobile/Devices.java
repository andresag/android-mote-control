package org.mindlapse.mobile;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Devices extends ListActivity {
	
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private static final int ACTIVITY_VIEW=2;
    
    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;

    private MoteDbAdapter dbConn;
    private Cursor devicesCursor;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list);
        dbConn = new MoteDbAdapter(this);
        dbConn.open();
    
        System.out.println("Entering onCreate");
        fillData();
        registerForContextMenu(getListView());
	    
//	    RunWake run = new RunWake();
//		run.wake("192.168.69.255","00:0E:A6:9C:AA:A5");
	
    }
    
    private void fillData()
    {
    	devicesCursor = dbConn.fetchAllDevices();
    	 startManagingCursor(devicesCursor);
    	String[] from = new String[]{MoteDbAdapter.KEY_NAME};
    	
    	int[] to = new int[]{R.id.text1};
    	SimpleCursorAdapter devices = new SimpleCursorAdapter(this,R.layout.device_row,devicesCursor,from,to);
    	setListAdapter(devices);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID,0, R.string.menu_insert);
        return true;
    }
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, DELETE_ID, 0, R.string.menu_delete);

        // TODO: fill in rest of method
	}
    @Override
	public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case DELETE_ID:
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            dbConn.deleteDevice(info.id);
            fillData();
            return true;
        }
        return super.onContextItemSelected(item);

	}
    
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case INSERT_ID:
            createDevice();
            return true;
        }
        
        return super.onMenuItemSelected(featureId, item);
    }
    
    
    private void createDevice() {
    	Intent i = new Intent(this, DeviceEdit.class);
    	startActivityForResult(i, ACTIVITY_CREATE);

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        Cursor c = devicesCursor;
        c.moveToPosition(position);
        Intent i = new Intent(this, DeviceView.class);
        i.putExtra(MoteDbAdapter.KEY_ROWID, id);
        i.putExtra(MoteDbAdapter.KEY_NAME, c.getString(
                c.getColumnIndexOrThrow(MoteDbAdapter.KEY_NAME)));
        i.putExtra(MoteDbAdapter.KEY_IPADDR, c.getString(
                c.getColumnIndexOrThrow(MoteDbAdapter.KEY_IPADDR)));
        i.putExtra(MoteDbAdapter.KEY_BCADDR, c.getString(
                c.getColumnIndexOrThrow(MoteDbAdapter.KEY_BCADDR)));
        i.putExtra(MoteDbAdapter.KEY_MAC, c.getString(
                c.getColumnIndexOrThrow(MoteDbAdapter.KEY_MAC)));
        i.putExtra(MoteDbAdapter.KEY_CAT, c.getString(
                c.getColumnIndexOrThrow(MoteDbAdapter.KEY_CAT)));
        startActivityForResult(i, ACTIVITY_VIEW);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	super.onActivityResult(requestCode, resultCode, intent);
    	Bundle extras = intent.getExtras();

    	switch(requestCode) {
    	case ACTIVITY_CREATE:
    	    String name = extras.getString(MoteDbAdapter.KEY_NAME);
    	    String ipaddr = extras.getString(MoteDbAdapter.KEY_IPADDR);
    	    String bcaddr = extras.getString(MoteDbAdapter.KEY_BCADDR);
    	    String mac = extras.getString(MoteDbAdapter.KEY_MAC);
    	    String cat = extras.getString(MoteDbAdapter.KEY_CAT);
    	    dbConn.createDevice(name,mac,ipaddr,bcaddr,cat);
    	    fillData();
    	    break;
    	case ACTIVITY_EDIT:
    	    Long mRowId = extras.getLong(MoteDbAdapter.KEY_ROWID);
    	    if (mRowId != null) {
        	    String ename = extras.getString(MoteDbAdapter.KEY_NAME);
        	    String eipaddr = extras.getString(MoteDbAdapter.KEY_IPADDR);
        	    String ebcaddr = extras.getString(MoteDbAdapter.KEY_BCADDR);
        	    String emac = extras.getString(MoteDbAdapter.KEY_MAC);
        	    String ecat = extras.getString(MoteDbAdapter.KEY_CAT);
    	        dbConn.updateDevice(mRowId,ename,emac,eipaddr,ebcaddr,ecat);
    
    	    }
    	    fillData();
    	    break;
    	case ACTIVITY_VIEW:
    	    fillData();
    	    break;
    	}
    	
        
    }
}