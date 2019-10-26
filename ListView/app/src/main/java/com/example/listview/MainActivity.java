package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    //DataSource
    Noticias[] noticias =
            new Noticias[]{
                    new Noticias("Noticia 1", "SubNoticia Contenido Contenido Contenido Contenido 1"),
                    new Noticias("Noticia 2", "SubNoticia Contenido Contenido Contenido Contenido 2"),
                    new Noticias("Noticia 3", "SubNoticia Contenido Contenido Contenido Contenido 3"),
                    new Noticias("Noticia 4", "SubNoticia Contenido Contenido Contenido Contenido 4")
            };

    ArrayList<String> datos;
    ArrayAdapter arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        listView=(ListView)findViewById(R.id.lstVista);

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("https://api.androidhive.info/contacts/", datos,
                MainActivity.this,MainActivity.this);

        ws.execute("");

    }

    @Override
    public void processFinish(String result) throws JSONException {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(result);
        JSONArray contacts = jsonObj.getJSONArray("contacts");
        datos=new ArrayList<>();
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

            HashMap<String, String> contact = new HashMap<>();

            contact.put("id",id);
            contact.put("name",name);
            contact.put("email",email);
            contact.put("mobile",mobile);

            datos.add(name+"\n"+email+"\n"+gender+"\n"+mobile);
        }

        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos);
        listView.setAdapter(arrayAdapter);






    }
}
