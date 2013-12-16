package com.example.buscaminas;

import android.os.Bundle;
import android.app.Activity;

import java.util.List;
import android.widget.TextView;

/**
 * Actividad que muestra la lista de Ganadores del juego
 * @author gabo
 *
 */
public class ListaUsuarios extends Activity {
	
	/**
	 * Asigna el layout ranking.xml a la actividad
	 * Genera la lista de ganadores recuperandolos de la base de datos
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
		DatabaseHandler db = new DatabaseHandler(this);
		
		TextView text = (TextView) findViewById(R.id.lista);
		List<Ranking> rankings = db.getAllUsers("principiante");       
        String log = "\tPrincipiante\n\n";
        for (Ranking cn : rankings) {
            log += "\tNombre: " + cn.getNombre() + " ,Tiempo: " + cn.getTiempo()+"\n";
        }
        rankings = db.getAllUsers("intermedio");       
        log += "\n\tIntermedio\n\n";
        for (Ranking cn : rankings) {
            log += "\tNombre: " + cn.getNombre() + " ,Tiempo: " + cn.getTiempo()+"\n";
        }
        rankings = db.getAllUsers("experto");       
        log += "\n\tExperto\n\n";
        for (Ranking cn : rankings) {
            log += "\tNombre: " + cn.getNombre() + " ,Tiempo: " + cn.getTiempo()+"\n";
        }
        rankings = db.getAllUsers("personalizado");       
        log += "\n\tPersonalizado\n\n";
        for (Ranking cn : rankings) {
            log += "\tNombre: " + cn.getNombre() + " ,Tiempo: " + cn.getTiempo()+"\n";
        }
        text.setText(log);
        
    }

}
