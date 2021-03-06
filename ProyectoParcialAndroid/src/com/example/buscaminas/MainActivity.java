package com.example.buscaminas;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * Actividad principal del juego
 * @author gabo
 *
 */
public class MainActivity extends Activity {

	/**
	 * Asigna el layout activity_main.xml a la actividad
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
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
	/**
	 * Handler del boton salir
	 * Termina el juego
	 * @param view
	 */
	public void salir(View view){
		finish();
	}
	
	/**
	 * Handler del los botones juego nuevo, instrucciones y ranking
	 * @param view el boton presionado
	 */
	public void seleccion(View view){
		switch (view.getId()) {
		case R.id.boton_juego_nuevo:
			DialogFragment dialog = new SeleccionNuevoJuego();
			dialog.show(getFragmentManager(),"SeleccionNuevoJuego");
			break;
		case R.id.boton_instrucciones:
			Intent intent = new Intent(this,Instrucciones.class);
			startActivity(intent);
			break;
		case R.id.boton_ranking:{
			Intent intent1 = new Intent(this,ListaUsuarios.class);
			startActivity(intent1);
			break;
		}
		default:
			break;
		}
	}

}
