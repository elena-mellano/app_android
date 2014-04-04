package com.yumba.app;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "StatusActivity";
    SharedPreferences pref;
    EditText editText;
    Button updateButton;
    String  stringa;
    String orig;
    TextView textCount;
    TextView title;
    String username;
    int update =0;
    MyDb db;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    int i=0;
    int modify;
  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        // Find views


        editText = (EditText) findViewById(R.id.editText); //
        updateButton = (Button) findViewById(R.id.buttonUpdate);
        title = (TextView) findViewById(R.id.textView1);
        modify=0;
        updateButton.setOnClickListener(this); //
        /*textCount.setText("140");
        textCount.setTextColor(Color.GREEN);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                int count = 140 - editText.length(); //
                textCount.setText(Integer.toString(count));
                textCount.setTextColor(Color.GREEN); //
                if (count < 10)
                    textCount.setTextColor(Color.YELLOW);
                if (count < 0)
                    textCount.setTextColor(Color.RED);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        */
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        listener=new SharedPreferences.OnSharedPreferenceChangeListener(){
            public void onSharedPreferenceChanged(SharedPreferences pref, String key)
            {
                username = pref.getString("username","");
                title.setText(username);
            }
        };
        pref.registerOnSharedPreferenceChangeListener(listener);
        username = pref.getString("username", "");
        if (username !=null){
            title.setText(username);
        }

        SharedPreferences pref;

        ListView status=(ListView)findViewById(R.id.peopleLv);
        MyDb db=new MyDb(getApplicationContext());
        db.open();  //apriamo il db
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Cursor c=db.fetchProducts(pref.getString("username","")); // Creiamo un cursore con tutti i dati del db
        startManagingCursor(c);

        SimpleCursorAdapter adapter=new SimpleCursorAdapter( //semplice adapter per i cursor
                this,
                R.layout.stato, //il layout di ogni riga/prodotto
                c,
                new String[]{MyDb.StatusMetaData.STATUS_STATUS_KEY},//queste colonne
                new int[]{R.id.ageTv});//in queste textView
        status.setAdapter(adapter); //L'adapter per la lista*/

        status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                TextView item =(TextView)((LinearLayout) arg1).getChildAt(0);
                editText.setText(item.getText());
                modify=1;
                orig=item.getText().toString();

            }

        });

        db.close();
    }





    // Called when button is clicked //
    public void onClick(View v) {
        stringa=(editText.getText().toString()); //
        if (stringa.compareTo("")==0 && modify==0){
            return;
        }
        db=new MyDb(getApplicationContext());
        db.open();

        if (modify==1){
            if (stringa.compareTo("")!=0)
                db.updateStatus(orig,stringa);
            else
                db.deleteStatus(orig);
            modify=0;
            orig="";
        }else{
        db.insertStatus(pref.getString("username", ""), stringa);
        }
        ListView status=(ListView)findViewById(R.id.peopleLv);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Cursor c=db.fetchProducts(pref.getString("username","")); // Creiamo un cursore con tutti i dati del db
        startManagingCursor(c);
        SimpleCursorAdapter adapter=new SimpleCursorAdapter( //semplice adapter per i cursor
                this,
                R.layout.stato, //il layout di ogni riga/prodotto
                c,
                new String[]{MyDb.StatusMetaData.STATUS_STATUS_KEY},//queste colonne
                new int[]{R.id.ageTv});//in queste textView
        status.setAdapter(adapter); //L'adapter per la lista*/
        db.close();
        editText.setText("");



    }

    public void onClickTodos(View v) {
        Intent start=new Intent(MainActivity.this, ListaActivity.class);
        startActivity(start); //
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //
        inflater.inflate(R.menu.menu, menu); //
        return true; //
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { //
            case R.id.itemPrefs:
                startActivity(new Intent(this, PrefActivity.class)); //
                break;
        }
        return true; //
    }

}

