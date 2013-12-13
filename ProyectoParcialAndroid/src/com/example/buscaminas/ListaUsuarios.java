package com.example.buscaminas;

import android.os.Bundle;
import android.app.Activity;

import java.util.List;
import android.widget.TextView;


public class ListaUsuarios extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intrucciones);
		//DatabaseHandler db = new DatabaseHandler(this);
		
		//TextView text = (TextView) findViewById(R.id.lista);
		//List<Ranking> rankings = db.getAllUsers();       
        //String log = "";
        /*for (Ranking cn : rankings) {
            log += "Id: "+cn.getID()+" ,Nombre: " + cn.getNombre() + " ,Tiempo: " + cn.getTiempo()+"\n";
        }
        text.setText(log);
         */   
    }

}
