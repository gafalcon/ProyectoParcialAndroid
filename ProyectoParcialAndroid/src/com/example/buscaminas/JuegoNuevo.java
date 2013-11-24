package com.example.buscaminas;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
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
import java.util.Random;

public class JuegoNuevo extends Activity {
	public static final String TAMANO = "com.example.Buscaminas";
	int columnas,filas, minas,blancos;
	int contadorBlancos=0;
	int tam=100;
	boolean iniciado = false;
	int segundos =0;
	long inicio = 0;
	int pos[][];
	Button[][] botones;
	
	
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
		blancos=filas*columnas - minas;
		timer = (TextView) findViewById(R.id.tiempo);
		crearBotones();
	}
	
	private void crearBotones(){

		
		TableLayout campo = (TableLayout) findViewById(R.id.campo); 
		
		//campo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		botones = new Button[filas][columnas];
		pos = new int[filas][columnas];
		for(int i=0;i<filas;i++){
			TableRow tablerow = new TableRow(this);
			//tablerow.setLayoutParams(new LayoutParams((tam+2)*columnas, tam+2));
			for(int j=0;j<columnas;j++){
				pos[i][j]=0;
				botones[i][j]=new Button(this);
				TableRow.LayoutParams params = new TableRow.LayoutParams() ;
				params.width=tam;
				params.height=tam;
				params.setMargins(0,0,0,0);
				
				botones[i][j].setPadding(1,1,1,1);
				botones[i][j].setId(generarId(i,j));
				botones[i][j].setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View arg0, MotionEvent event) {
						// TODO Auto-generated method stub
						ImageButton cara = (ImageButton) findViewById(R.id.cara);
						if(event.getAction() == MotionEvent.ACTION_DOWN){
							cara.setPressed(true);
						}else if(event.getAction() == MotionEvent.ACTION_UP){
							cara.setPressed(false);
						}
						return false;
					}
				});
				botones[i][j].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(!iniciado){
							IniciarContador();
							generarTablaMinas(arg0.getId());
							iniciado=true;
						}
						Button boton = (Button) arg0;
						
						clickButton(boton);
						if(contadorBlancos==blancos){
							ImageButton cara = (ImageButton) findViewById(R.id.cara);
							cara.setBackgroundResource(R.drawable.glasses_smiley);
						}
							
					}
				});
				
				tablerow.addView(botones[i][j],params);
			}
			campo.addView(tablerow);
		}

	}
	private int generarId(int x,int y){
		return (x*columnas)+y;
	}
	private int generarPosX(int id){
		return id/columnas;
	}
	private int generarPosY(int id){
		return id%columnas;
	}
	private void generarTablaMinas(int id){
		int x = generarPosX(id);
		int y = generarPosY(id);
		int contador = 0;
		Random rand = new Random(System.currentTimeMillis());
		while(contador<minas){
			int x1 = rand.nextInt(filas);
			int y1 = rand.nextInt(columnas);
			if(pos[x1][y1]==0){
				if(Math.abs(x-x1)>1 || Math.abs(y-y1)>1){
					pos[x1][y1]=-1;
					contador++;
				}
			}
		}
	}
	private int generarNum(int id){
		int contador=0;
		int x1 = generarPosX(id);
		int y1 = generarPosY(id);
		if(pos[x1][y1]!= -1){
			
			if(x1!=0){
				if(y1!=0){
					if(pos[x1-1][y1-1]==-1)
						contador++;
				}
				if(pos[x1-1][y1]==-1)
					contador++;
				if(y1!=(columnas-1)){
					if(pos[x1-1][y1+1]==-1)
						contador++;
				}
			}
			if(y1 != 0){
				if(pos[x1][y1-1]==-1)
					contador++;
			}
			if(y1 != (columnas-1)){
				if(pos[x1][y1+1]==-1)
					contador++;
			}
			if(x1 != (filas-1)){
				if(y1 != 0){
					if(pos[x1+1][y1-1]==-1)
						contador++;
				}
				if(pos[x1+1][y1]==-1)
					contador++;
				if(y1 != (columnas-1)){
					if(pos[x1+1][y1+1]==-1)
						contador++;
				}
			}
			return contador;
		}else{
			return -1;
		}
	}
	private void numColor(Button boton,int n){
		switch (n) {
		case 1:
			boton.setTextColor(Color.BLUE);
			break;
		case 2:
			boton.setTextColor(Color.parseColor("#009933"));
			break;
		case 3:
			boton.setTextColor(Color.RED);
			break;
		case 4:
			boton.setTextColor(Color.parseColor("#000066"));
			break;
		case 5:
			boton.setTextColor(Color.CYAN);
			break;
		default:
			boton.setTextColor(Color.MAGENTA);
			break;
		}
	}
	private void clickButton(Button boton){
		boton.setClickable(false);
		boton.setEnabled(false);
		int id= boton.getId();
		int n = generarNum(id);
		if(n == -1){
			boton.setBackgroundResource(R.drawable.mina);
		}else if(n==0){
			contadorBlancos++;
			extender(id);
			
		}else{
			boton.setText(Integer.toString(n));
			boton.setTypeface(null, Typeface.BOLD);
			numColor(boton, n);
			contadorBlancos++;
		}
	}
	private void extender(int id){
		int x = generarPosX(id);
		int y = generarPosY(id);
		
		if(x!=0){
			if(y!=0){
				if(botones[x-1][y-1].isEnabled()){
					clickButton(botones[x-1][y-1]);
				}
			}
			if(botones[x-1][y].isEnabled()){
				clickButton(botones[x-1][y]);
			}
			if(y!=(columnas-1)){
				if(botones[x-1][y+1].isEnabled()){
					clickButton(botones[x-1][y+1]);
				}
			}
		}
		if(y!=0){
			if(botones[x][y-1].isEnabled()){
				clickButton(botones[x][y-1]);
			}
		}
		if(y!=(columnas-1)){
			if(botones[x][y+1].isEnabled()){
				clickButton(botones[x][y+1]);
			}
		}
		if(x!=(filas-1)){
			if(y!=0){
				if(botones[x+1][y-1].isEnabled()){
					clickButton(botones[x+1][y-1]);
				}
			}
			if(botones[x+1][y].isEnabled()){
				clickButton(botones[x+1][y]);
			}
			if(y!=(columnas-1)){
				if(botones[x+1][y+1].isEnabled()){
					clickButton(botones[x+1][y+1]);
				}
			}
		}
	}
}
