package com.example.buscaminas;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private String SELECCIONAR = "com.example.Buscaminas";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void salir(View view){
		finish();
	}
	public void seleccion(View view){
		switch (view.getId()) {
		case R.id.boton_juego_nuevo:
			DialogFragment dialog = new SeleccionNuevoJuego();
			dialog.show(getFragmentManager(),"SeleccionNuevoJuego");
			break;
		case R.id.boton_instrucciones:
			Intent intent = new Intent(this,Instrucciones.class);
			intent.putExtra(SELECCIONAR, 1);
			startActivity(intent);
			break;
		case R.id.boton_ranking:{
			Intent intent1 = new Intent(this,Instrucciones.class);
			intent1.putExtra(SELECCIONAR, 2);
			startActivity(intent1);
		}
		default:
			break;
		}
	}

}
