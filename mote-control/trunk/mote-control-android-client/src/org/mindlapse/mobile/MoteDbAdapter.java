/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.mindlapse.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**

 */
public class MoteDbAdapter {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAC = "mac";
    public static final String KEY_IPADDR = "ipaddr";
    public static final String KEY_BCADDR = "bcaddr";
    public static final String KEY_CAT = "cat";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "MoteDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
        "create table device (_id integer primary key autoincrement, "
        + "name text not null, ipaddr text not null, mac text , bcaddr text , cat text);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "device";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public MoteDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public MoteDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create
     * 
     * 
     * @return rowId or -1 if failed
     */
    public long createDevice(String name,  String mac, String ipaddr, String bcaddr,String cat) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_IPADDR, ipaddr);
        initialValues.put(KEY_BCADDR,bcaddr);
        initialValues.put(KEY_MAC, mac);
        initialValues.put(KEY_CAT,cat);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    

    /**

     * 

     * @return true if the device was successfully updated, false otherwise
     */
    public boolean updateDevice(long rowId, String name,  String mac, String ipaddr, String bcaddr,String cat) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_IPADDR, ipaddr);
        args.put(KEY_BCADDR,bcaddr);
        args.put(KEY_MAC, mac);
        args.put(KEY_CAT, cat);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Delete the device

     * @return true if deleted, false otherwise
     */
    public boolean deleteDevice(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllDevices() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
        		 KEY_MAC, KEY_IPADDR, KEY_BCADDR, KEY_CAT}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchDevice(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
           		 KEY_MAC, KEY_IPADDR, KEY_BCADDR, KEY_CAT}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

}
