package com.example.buscaminas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
	static JuegoNuevo single=null;
	Intent nuevo ;
	Intent nuevo2;
	TableLayout campo;
	public static JuegoNuevo instance(){
		if(single==null)
			return single=new JuegoNuevo();
		return single;
				
	}
	public JuegoNuevo(){
		
	}
	
	
	
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
		nuevo=new Intent(this,MainActivity.class);
		nuevo2 = new Intent(this,JuegoNuevo.class);
		crearBotones();
	}
	
	private void crearBotones(){

		
		campo = (TableLayout) findViewById(R.id.campo); 
		campo.setStretchAllColumns(true);
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
				
				//params.width=tam;
				params.width=TableRow.LayoutParams.WRAP_CONTENT;
				//params.height=params.width;
				params.weight=(float)1;
				params.setMargins(0,0,0,0);
				
				//botones[i][j].setPadding(1,1,1,1);
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
				botones[i][j].setHeight(botones[i][j].getWidth());
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
	public static Bitmap drawableToBitmap (Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);

	    return bitmap;
	}
	@SuppressWarnings("static-access")
	private void clickButton(Button boton){
		boton.setClickable(false);
		boton.setEnabled(false);
		int id= boton.getId();
		int n = generarNum(id);
		if(n == -1){
			//Bitmap nuevo = drawableToBitmap (this.getResources().getDrawable(R.drawable.mine));
			//nuevo.createScaledBitmap(nuevo, boton.getWidth(), boton.getHeight(), false);
			//boton.setBackgroundDrawable(new BitmapDrawable(boton.getContext().getResources(), nuevo));
			//boton.
			Drawable drawa=this.getResources().getDrawable(R.drawable.mine);
			drawa.setBounds(0,0,boton.getWidth(),boton.getHeight());
			int width=boton.getWidth();
			int height=boton.getHeight();
			campo.setBackgroundResource(R.drawable.background2);
			System.out.println("TAmano del boton :"+boton.getWidth()+" Tamano del boton alto :"+boton.getHeight());
			boton.setBackgroundResource(R.drawable.minesweeper);
			System.out.println("TAmano del boton :"+boton.getWidth()+" Tamano del boton alto :"+boton.getHeight());
			boton.setHeight(height);
			boton.setWidth(width);
			System.out.println("TAmano del boton :"+boton.getWidth()+" Tamano del boton alto :"+boton.getHeight());
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
	 
				// set title
				alertDialogBuilder.setTitle("Tu has Perdido!");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Que deseas hacer ahora Losser?")
					.setCancelable(false)
					.setNegativeButton("Salir",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {

							
							back();
							dialog.cancel();
						}
					})
					.setPositiveButton("Volver a Jugar",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							
							
							DialogFragment dialog2 = new SeleccionNuevoJuego();
							dialog2.show(getFragmentManager(),"SeleccionNuevoJuego");
							
							//JuegoNuevo.instance().startActivity(nuevo2);
							
						}
					  });
					
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
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
	public void back(){
		super.onBackPressed();
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
