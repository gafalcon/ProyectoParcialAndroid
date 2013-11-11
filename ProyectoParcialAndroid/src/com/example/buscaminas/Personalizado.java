package com.example.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

public class Personalizado extends DialogFragment{
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setTitle("Seleccione tamaño y numero de minas");
		builder.setView(inflater.inflate(R.layout.personalizado, null));
		return builder.create();
	}
	
}
