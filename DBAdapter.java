import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
  	static final String KEY_ROWID = "_id";
		static final String KEY_NAME = "name"; 
		static final String KEY_SCORE = "score"; 
		static final String TAG = "DBAdapter"; 
		
		static final String DATABASE_NAME="SchoolDB1"; 
		static final String DATABASE_TABLE="API_scores";
		static final int DATABASE_VERSION = 1; 
		
		static final String DATABASE_CREATE = 
				"Create table Schools (_id integer primary key autoincrement, " + "name text not null, score text not null);";
		
		final Context context;
		
		DatabaseHelper DBHelper;
		SQLiteDatabase db;
		
		public DBAdapter(Context ctx){
			this.context = ctx;
			DBHelper = new DatabaseHelper(context); 
		}
		
		private static class DatabaseHelper extends SQLiteOpenHelper
		{
			DatabaseHelper (Context context)
			{
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
			}
		
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try 
			{
				db.execSQL(DATABASE_CREATE);
			}
			catch(SQLException e){
				e.printStackTrace(); 
			}
			
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version" + oldVersion + "to" + newVersion +",which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS schools"); 
			onCreate(db);
		}
	}
		
	//opens the databases
	public DBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase(); 
		return this; 
	}
	
	//closes the databases
	public void close()
	{
		DBHelper.close();
	}
	
	//insert school info into the database 
	public long insertSchool (String name, String score)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_SCORE, score); 
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	//deletes a particular school
	public boolean deleteSchool(long rowID)
	{
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null) > 0; 
	}
	
	//retrieves all the schools
	public Cursor getAllSchools()
	{
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_SCORE}, null, null, null, null, null); 
	}
	
	//retrieves a particular school
	public Cursor getSchool(long rowId) throws SQLException
	{
		Cursor mCursor = 
				db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_SCORE}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor; 
	}
	
	//updates a school
	public boolean updateSchool(long rowId, String name, String score)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name); 
		args.put(KEY_SCORE, score); 
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) >0;
	}

}
