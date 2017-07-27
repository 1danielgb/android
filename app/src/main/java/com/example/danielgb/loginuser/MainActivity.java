package com.example.danielgb.loginuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView f,l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent i= getIntent();
        //Bundle extras=i.getExtras();
        //String F = extras.getString("firt");
        //String H = extras.getString("last");
        //TextView f=(TextView)findViewById(R.id.txtfirts);
       // TextView h = (TextView) findViewById(R.id.txtlast);
       // f.setText(String.valueOf(F));
       // h.setText(String.valueOf(H));
    }
    public void close (View v){
        finishAffinity();
        //close();
    }
}
