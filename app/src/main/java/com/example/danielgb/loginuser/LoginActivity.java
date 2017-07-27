package com.example.danielgb.loginuser;

/**
 * Created by Daniel on 24/07/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btn;
    String ser="http://localhost:8081/loginuser/login2.php";
    EditText usuario,password;
    String us,ps;
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;
    //String json = CADENA_JSON;
    //List<Fruta> lista_frutas = new ArrayList<>();
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        btn = (Button) findViewById(R.id.btnlogin);
        usuario = (EditText) findViewById(R.id.txtuser);
        password = (EditText) findViewById(R.id.txtpass);
        //SharedPreferences prefe=getSharedPreferences("data",Context.MODE_PRIVATE);
       // usuario.setText(prefe.getString("email",""));
       // password.setText(prefe.getString("password",""));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr= new Thread(){
                    @Override
                    public void run() {
                        final String resultado=envirDatosGet(usuario.getText().toString(),password.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r=obtDatosJSON(resultado);
                                //JSONObject J = new JSONObject(r);
                                if(r>0){
                                    SharedPreferences preferencias=getSharedPreferences("datos", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=preferencias.edit();
                                    editor.putString("ema", usuario.getText().toString());
                                    editor.putString("pas", password.getText().toString());
                                    editor.commit();



                                    Intent i= new Intent(LoginActivity.this, MainActivity.class);
                                    //i.putExtra("cod",usuario.getText().toString());
                                    startActivity(i);
                                }else {
                                    Toast.makeText(getApplicationContext(),"Usuario o pas Incorrectos",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                };
                tr.start();
                //verifica();
            }

        });

    }


    public String envirDatosGet(String ema, String pas){
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try {
            //"http://192.168.56.1:8081/loginuser/login2.php?ema="+ema+"&pas="+pas+
            url= new URL("http://192.168.56.1:8081/loginuser/login2.php?ema="+ema+"&pas="+pas+"");
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            respuesta=connection.getResponseCode();
            result= new StringBuilder();
            if(respuesta==HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((linea=reader.readLine())!=null){
                    result.append(linea);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();

    }
    public int obtDatosJSON(String respose){
        int res=0;
            try{
                JSONArray json= new JSONArray(respose);
                if(json.length()>0){
                    res=1;
                }
            }catch (Exception e){}
        return res;
    }

    public void datas() {

    }
}
