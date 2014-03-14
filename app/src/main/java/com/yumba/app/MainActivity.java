package com.yumba.app;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "StatusActivity";
    SharedPreferences pref;
    EditText editText;
    Button updateButton;
    String  stringa;
    TextView textCount;
    TextView title;
    String username;
    int update =0;
    MyDb db;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    int i=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
    // Find views


        editText = (EditText) findViewById(R.id.editText); //
        updateButton = (Button) findViewById(R.id.buttonUpdate);
        textCount = (TextView) findViewById(R.id.textView); //
        title = (TextView) findViewById(R.id.textView1);

        updateButton.setOnClickListener(this); //
        textCount.setText("140");
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
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        listener=new SharedPreferences.OnSharedPreferenceChangeListener(){
            public void onSharedPreferenceChanged(SharedPreferences pref, String key)
            {
               username = pref.getString("username","");
                title.setText("status of "+ username);
            }
        };
        pref.registerOnSharedPreferenceChangeListener(listener);
        username = pref.getString("username","");
        if (username !=null){
            title.setText("status of "+ username);
        }
    }
    // Called when button is clicked //
    public void onClick(View v) {
        stringa=(editText.getText().toString()); //

        db=new MyDb(getApplicationContext());
        db.open();
        db.insertStatus(pref.getString("username", ""), stringa);
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

