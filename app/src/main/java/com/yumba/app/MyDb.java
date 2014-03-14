package com.yumba.app;

/**
 * Created by elena on 10/03/14.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.Cursor;
import android.content.ContentValues;
import android.preference.PreferenceManager;


public class MyDb {

    SQLiteDatabase mDb;

    DbHelper mDbHelper;

    Context mContext;

    SharedPreferences pref;

    private static final String DB_NAME="statusDb";//name of database

    private static final int DB_VERSION=1; //number of versione

    public MyDb(Context ctx){

        mContext=ctx;

        mDbHelper=new DbHelper(ctx, DB_NAME, null, DB_VERSION);

    }

    public void open(){

        mDb=mDbHelper.getWritableDatabase(); //open the database, we can write and read it

    }

    public void close(){

        mDb.close();

    }

// Vediamo ora come aggiungere tabelle e campi alle tabelle del db

    public void insertStatus(String name,String status){ //metodo per inserire i dati

        ContentValues cv=new ContentValues();

        cv.put(StatusMetaData.STATUS_NAME_KEY, name);

        cv.put(StatusMetaData.STATUS_STATUS_KEY, status);

        mDb.insert(StatusMetaData.STATUS_TABLE, null, cv);

    }

    public Cursor fetchProducts(String name){ //metodo per fare la query di tutti i dati

            return(mDb.query(StatusMetaData.STATUS_TABLE,null,StatusMetaData.STATUS_NAME_KEY+" = '"+name+"'",null,null,null,null));

    }

    static class StatusMetaData {  // i metadati della tabella, accessibili ovunque

        static final String STATUS_TABLE = "status";

        static final String ID = "_id";

        static final String STATUS_NAME_KEY = "name";

        static final String STATUS_STATUS_KEY = "status";

    }

    private static final String PERSON_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "  //codice sql di creazione della tabella

            + StatusMetaData.STATUS_TABLE + " ("

            + StatusMetaData.ID+ " integer primary key autoincrement, "

            + StatusMetaData.STATUS_NAME_KEY + " text not null, "

            + StatusMetaData.STATUS_STATUS_KEY  + " text not null);";

    private class DbHelper extends SQLiteOpenHelper { //classe che ci aiuta nella creazione del db

        public DbHelper(Context context, String name, CursorFactory factory,int version) {

            super(context, name, factory, version);

        }

        @Override

        public void onCreate(SQLiteDatabase _db) { //solo quando il db viene creato, creiamo la tabella

            _db.execSQL(PERSON_TABLE_CREATE);

        }

        @Override

        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {

//qui mettiamo eventuali modifiche al db, se nella nostra nuova versione della app, il db cambia numero di versione

        }

    }

}