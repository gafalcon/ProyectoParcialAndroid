package com.example.buscaminas;

import java.util.List;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;

public class Instrucciones extends Activity{
	public static final String SELECCIONAR = "com.example.Buscaminas";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int n= getIntent().getIntExtra(SELECCIONAR,0);
		if(n==1){
			setContentView(R.layout.intrucciones);
		}else if(n==2){
			setContentView(R.layout.ranking);
			DatabaseHandler db = new DatabaseHandler(this);
			
					TextView text = (TextView) findViewById(R.id.lista);
					List<Ranking> rankings = db.getAllUsers();       
			        String log = "";
			        for (Ranking cn : rankings) {
			            log += "Id: "+cn.getID()+" ,Nombre: " + cn.getNombre() + " ,Tiempo: " + cn.getTiempo()+"\n";
			        }
			        text.setText(log);
			
		}
		
		          
	}
}
