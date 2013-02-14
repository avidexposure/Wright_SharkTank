//
//  AvidSchoolFinder
//
//  Created by Laura Wright Teclemariam on 12/21/12.
//  Copyright (c) 2012 Laura Wright. All rights reserved.
//

package com.avidexposure.schoolfinder;

import android.os.Bundle;
import android.app.Activity;
//import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener; 
import android.widget.Button;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class SchoolActivity extends Activity implements OnClickListener{

  Button btn; 
	
//	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btn = (Button) findViewById(R.id.btn_school);
		btn.setOnClickListener(this);
	
}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent myintent = new Intent (this, FindSchoolView.class); 
		startActivity(myintent);

	/*	Intent myintent = new Intent(v.getContext(),FindSchoolView.class);
		startActivity(myintent); */
		
		/*Intent myintent = new Intent();
		myintent.setClass(getBaseContext(), FindSchoolView.class);
		((Activity)getBaseContext()).startActivity(myintent); //Errors Out
		*/
	}
	
	
	public void CopyDB (InputStream inputStream, OutputStream outputStream) throws IOException {
		 //copy 1kB at a time 
		 byte[] buffer = new byte[1024];
		 int length;
		 while ((length = inputStream.read(buffer)) > 0){
			 outputStream.write(buffer, 0, length); 
		 }
		 inputStream.close();
		 outputStream.close();
}
		
	public void DisplaySchool(Cursor c){  
	//Test DB by display content in dialog box. 
		Toast.makeText(this,
				"id:  " + c.getString(0) + "\n" +
				"County:  " + c.getString(1) + "\n" + 
				"District:  " + c.getString(2) +"\n" +
				"School:  " + c.getString(3) + "\n" +
				"Street:  " + c.getString(4) + "\n" + 
				"ABR:  " + c.getString(5) + "\n"+
				"City:  " + c.getString(6) + "\n" + 
				"Zip:  " + c.getString(7) + "\n" +
				"State:  " + c.getString(8) + "\n" + 
				"API Score:  " + c.getString(9), Toast.LENGTH_LONG).show();
}
	
	public void onHomeClick(View v){
		//To Find by Home Address
	}

	public void onSchoolClick(View v){
		//To find by School Name		
		
		
	}
	public void onFindClick(View v){
		//To find via GeoLocator 
		
		DBAdapter db = new DBAdapter(this); 
		
		//Test Public School Database of API Scores
				
			try {
				String destPath = "data/data/" + getPackageName() + "/databases"; 
				File f = new File (destPath);
	//Feb 13:  For some reason (!f.exists) doesn't work. 
				if(f.exists()){
					f.mkdirs();
					f.createNewFile();
					
					//Copy the database from the assets folder into the databases folder
					CopyDB(getBaseContext().getAssets().open("SchoolDB_test"), new FileOutputStream(destPath + "/Android_SchoolDB"));
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace(); 
			}
			
			
		// Get A single Contact
			db.open();
		 
	 		Cursor c = db.getSchool(1);
			if (c.moveToFirst())		
				DisplaySchool(c);
			else 
				Toast.makeText(this, "No School Record Found", Toast.LENGTH_LONG).show();
			
			//Get All Contacts from -- 
	/*	 	db.open();
	 		Cursor c = db.getAllSchools(); 
			if(c.moveToFirst())
			{
				do {
					DisplaySchool(c);
				}while (c.moveToNext());
			}
	*/	
			db.close();
	}		
		

	
/*	private void DisplayToast(String msg)
	{
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}  */

	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}




} //END OF CLASS
