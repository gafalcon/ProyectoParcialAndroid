package com.example.buscaminas;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class JuegoNuevo extends Activity {
	public static final String TAMANO = "com.example.Buscaminas";
	int columnas,filas, minas;
	int tam=100;
	
	int segundos =0;
	long inicio = 0;
	
	TextView timer;
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long milis = System.currentTimeMillis();
			segundos++;
			timer.setText(Integer.toString(segundos));
			timerHandler.postAtTime(this,milis);
			
			timerHandler.postDelayed(this,1000);
		}
		
	};
	
	public void IniciarContador(){
		timerHandler.removeCallbacks(timerRunnable);
		timerHandler.postDelayed(timerRunnable, 1000);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juego);
		int tam[]= getIntent().getIntArrayExtra(TAMANO);
		filas=tam[0];
		columnas =tam[1];
		minas=tam[2];
		timer = (TextView) findViewById(R.id.tiempo);
		crearBotones();
	}
	
	private void crearBotones(){

		
		TableLayout campo = (TableLayout) findViewById(R.id.campo); 
		
		//campo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		Button[][] botones = new Button[filas][columnas];
		for(int i=0;i<filas;i++){
			TableRow tablerow = new TableRow(this);
			//tablerow.setLayoutParams(new LayoutParams((tam+2)*columnas, tam+2));
			for(int j=0;j<columnas;j++){
			
	
				
				
				botones[i][j]=new Button(this);
				TableRow.LayoutParams params = new TableRow.LayoutParams() ;
				params.width=tam;
				params.height=tam;
				//botones[i][j].setLayoutParams(params);
				//t.setText(botones[i][j].getWidth());
				//botones[i][j].setLayoutParams(new LayoutParams(tam+2,tam+2));
				
				botones[i][j].setPadding(1,1,1,1);
				botones[i][j].setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View arg0, MotionEvent event) {
						// TODO Auto-generated method stub
						ImageButton cara = (ImageButton) findViewById(R.id.cara);
						if(event.getAction() == MotionEvent.ACTION_DOWN){
							cara.setPressed(true);
						}else //if(event.getAction() == MotionEvent.ACTION_UP)
							cara.setPressed(false);
						return false;
					}
				});
				botones[i][j].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						IniciarContador();
					}
				});
				
				tablerow.addView(botones[i][j],params);//,new TableLayout.LayoutParams(15, 15));
			}
			campo.addView(tablerow);//,new TableLayout.LayoutParams((tam+2)*columnas,12));
		}

	}
}
