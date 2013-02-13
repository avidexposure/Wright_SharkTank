Wright_SharkTank
================

Libraries, Code, Things I need to Remember


SimpleCursorAdapter requires that the Cursor's result set must include a column named exactly "_id". 
Don't haste to change schema if you didn't define the "_id" column in your table. 
SQLite automatically added an hidden column called "rowid" for every table. All you need to do is that 
just select rowid explicitly and alias it as '_id' Ex.

SQLiteDatabase db = mHelper.getReadableDatabase();      
Cursor cur =  db.rawQuery( "select rowid _id,* from your_table", null);
