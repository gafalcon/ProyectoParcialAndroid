package com.example.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Clase que muestra el dialogo para escoger las opciones del boton personalizado
 * @author gabo
 *
 */
public class Personalizado extends DialogFragment{
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setTitle("Seleccione tamaño y numero de minas");
		View view = inflater.inflate(R.layout.personalizado, null);
		builder.setView(view);
		final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1);
		np1.setMaxValue(50);
		np1.setMinValue(10);
		np1.setValue(25);
		np1.setWrapSelectorWheel(false);
		//np1.setOnValueChangedListener(this);
		final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2);
		np2.setMaxValue(50);
		np2.setMinValue(10);
		np2.setValue(25);
		np2.setWrapSelectorWheel(false);
		//np2.setOnValueChangedListener(this);
		final NumberPicker np3 = (NumberPicker) view.findViewById(R.id.numberPicker3);
		//Escoger num de minas de acuerdo a valores de  numFilas y numColumnas
		np3.setMaxValue((np1.getValue()*np2.getValue())-10);
		np3.setMinValue(10);
		np3.setValue(25);
		np3.setWrapSelectorWheel(false);
		
		builder.setPositiveButton("Crear Juego", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				int config[] = new int[3];
				config[0]= np1.getValue();
				config[1]= np2.getValue();
				config[2]= np3.getValue();
				Intent intent = new Intent(getActivity(),JuegoNuevo.class);
				intent.putExtra(JuegoNuevo.TAMANO, config);
				startActivity(intent);
				getActivity().finish();
			}
		});
		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		return builder.create();
	}

	
	
}
