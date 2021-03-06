package com.yumba.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;

/**
 * Created by elena on 14/03/14.
 */
public class ListaActivity extends Activity {
    SharedPreferences pref;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.lista);


        ListView status=(ListView)findViewById(R.id.peopleLv);

        MyDb db=new MyDb(getApplicationContext());

        db.open();  //apriamo il db


        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Cursor c=db.allProducts(); // Creiamo un cursore con tutti i dati del db

        SimpleCursorAdapter adapter=new SimpleCursorAdapter( //semplice adapter per i cursor

                this,

                R.layout.all, //il layout di ogni riga/prodotto

                c,

                new String[]{MyDb.StatusMetaData.STATUS_STATUS_KEY, MyDb.StatusMetaData.STATUS_NAME_KEY},//queste colonne

                new int[]{R.id.pref, R.id.cat});//in queste textView

       status.setAdapter(adapter); //L'adapter per la lista*/



        db.close();



}

    public void onBackPressed() {
        Intent start=new Intent(ListaActivity.this , MainActivity.class);
        startActivity(start); //
        return;
    }

}
