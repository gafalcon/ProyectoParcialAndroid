package com.example.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class SeleccionNuevoJuego extends DialogFragment{
	private String arreglo_opciones[] = {"Principiante", "Intermedio","Experto"};
	private int tam[] = new int[3];
	private String TAMANO = "com.example.Buscaminas";
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Empezar nuevo juego");
		builder.setItems(arreglo_opciones, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:{
					tam[0]=8;
					tam[1]=8;
					tam[2]=10;
					Intent intent = new Intent(getActivity(), JuegoNuevo.class);
					intent.putExtra(TAMANO, tam);
					startActivity(intent);
					break;
				}
				case 1:{
					tam[0]=12;
					tam[1]=12;
					tam[2]=40;
					Intent intent = new Intent(getActivity(), JuegoNuevo.class);
					intent.putExtra(TAMANO, tam);
					startActivity(intent);
					break;}
				case 2:{
					tam[0]=16;
					tam[1]=16;
					tam[2]=80;
					Intent intent = new Intent(getActivity(), JuegoNuevo.class);
					intent.putExtra(TAMANO, tam);
					startActivity(intent);
					break;}
				

				default:
					break;
				}
			}
		});
		return builder.create();
	}
	
}
