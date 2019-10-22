package com.example.json_webserviceparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    TextView lblMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblMensaje=(TextView)findViewById(R.id.lblMensaje);
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("https://api.androidhive.info/contacts/", datos,
                MainActivity.this,MainActivity.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("ProcessFinish",result);
        parsearJSON(result);
    }

    public void parsearJSON (String JSON) throws JSONException {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(JSON);
        JSONArray contacts = jsonObj.getJSONArray("contacts");

        for(int i = 0; i< contacts.length(); i++)
        {
            JSONObject c = contacts.getJSONObject(i);
            String id = c.getString("id");
            String name = c.getString("name");
            String email = c.getString("email");
            String address = c.getString("address");
            String gender = c.getString("gender");

            //El nodo Phone es un objeto JSON
            JSONObject phone = c.getJSONObject("phone");
            String mobile = phone.getString("mobile");
            String home = phone.getString("home");
            String office = phone.getString("office");

            String cadena = "Id:" + id + "\nName: " + name + "\nEmail: " + email + "\nMobile: " + mobile + "\n" +
                    "--------------------------------------------\n";

            lblMensaje.setText(lblMensaje.getText() + cadena);
            lblMensaje.setVisibility(View.VISIBLE);
        }
    }
}
