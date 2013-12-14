package com.example.buscaminas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class JuegoNuevo extends Activity {
	public static final String TAMANO = "com.example.Buscaminas";
	int columnas,filas, minas,blancos;
	int contadorBlancos=0;
	int tam=100;
	boolean iniciado = false, setBandera = false,gameover=false;
	int segundos =0;
	long inicio = 0;
	int pos[][];
	ImageButton[][] botones;
	ImageButton bandera;
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
	
	
	
	TextView timer,textViewMinas;
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long milis = System.currentTimeMillis();
			if(! gameover)
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
		textViewMinas = (TextView) findViewById(R.id.minas);
		textViewMinas.setText(Integer.toString(minas));
		nuevo=new Intent(this,MainActivity.class);
		nuevo2 = new Intent(this,JuegoNuevo.class);
		bandera = (ImageButton) findViewById(R.id.flag);
		crearBotones();
	}
	
	private void crearBotones(){

		
		campo = (TableLayout) findViewById(R.id.campo); 
		campo.setStretchAllColumns(true);
		//campo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		botones = new ImageButton[filas][columnas];
		pos = new int[filas][columnas];
		for(int i=0;i<filas;i++){
			TableRow tablerow = new TableRow(this);
			//tablerow.setLayoutParams(new LayoutParams((tam+2)*columnas, tam+2));
			for(int j=0;j<columnas;j++){
				pos[i][j]=0;
				botones[i][j]=new ImageButton(this);
				TableRow.LayoutParams params = new TableRow.LayoutParams() ;
				
				//params.width=tam;
				params.width=TableRow.LayoutParams.WRAP_CONTENT;
				
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
						}else{ //if(event.getAction() == MotionEvent.ACTION_UP){
							cara.setPressed(false);
						}
						return false;
					}
				});
				botones[i][j].setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(!iniciado && !setBandera){
							IniciarContador();
							generarTablaMinas(arg0.getId());
							iniciado=true;
						}
						ImageButton boton = (ImageButton) arg0;
						if(setBandera){
							if(boton.isFocusable()){
								boton.setImageResource(R.drawable.flag);
								boton.setScaleType(ScaleType.CENTER_INSIDE);
								boton.setFocusable(false);
								textViewMinas.setText(Integer.toString(--minas));
							}else{
								//boton.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
								boton.setImageResource(0);
								boton.setFocusable(true);
								textViewMinas.setText(Integer.toString(++minas));
							}
						}else{
							if(boton.isFocusable()){
								clickButton(boton);
								if(contadorBlancos==blancos){
									ImageButton cara = (ImageButton) findViewById(R.id.cara);
									cara.setBackgroundResource(R.drawable.glasses_smiley);
								
								}
							}
						}	
					}
				});
				
				tablerow.addView(botones[i][j],params);
				LayoutParams params1 = botones[i][j].getLayoutParams();
				params.height = 100;
				params.width = 100;
				botones[i][j].setLayoutParams(params1);
				//botones[i][j].setHeight(botones[i][j].getWidth());
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
	private int numImage(int n){
		switch (n) {
		case 1:
			return R.drawable.uno;
			
		case 2:
			return R.drawable.dos;
		case 3:
			return R.drawable.tres;
		case 4:
			return R.drawable.cuatro;
		case 5:
			return R.drawable.cinco;
		case 6:
			return R.drawable.seis;
		case 7:
			return R.drawable.siete;
		default:
			return R.drawable.ocho;
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
	private void clickButton(ImageButton boton){
		if(boton.isFocusable()){
			boton.setClickable(false);
			boton.setEnabled(false);
			int id= boton.getId();
			int n = generarNum(id);
			if(n == -1){
				//Bitmap nuevo = drawableToBitmap (this.getResources().getDrawable(R.drawable.mine));
				//nuevo.createScaledBitmap(nuevo, boton.getWidth(), boton.getHeight(), false);
				//boton.setBackgroundDrawable(new BitmapDrawable(boton.getContext().getResources(), nuevo));
				//boton

				//campo.setBackgroundResource(R.drawable.background2);
				
				MostrarMinas(boton);
				boton.setImageResource(0);
				
				boton.setBackgroundColor(Color.RED);
				boton.setImageResource(R.drawable.mine);
				boton.setScaleType(ScaleType.FIT_XY);
				
				gameOver("Encontraste una mina.. Has perdido!!");
			}else if(n==0){
				contadorBlancos++;
				extender(id);
				if(blancos==contadorBlancos){
					guardarUsuario();
				}
				
			}else{
				
				boton.setImageResource(numImage(n));
				boton.setScaleType(ScaleType.FIT_XY);
				
				contadorBlancos++;
				if(blancos==contadorBlancos){
					guardarUsuario();
				}
				
			}
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
	
	private void guardarUsuario(){
			gameover = true;
			final DatabaseHandler db = new DatabaseHandler(this);
			LayoutInflater inflater = this.getLayoutInflater();
			final EditText edit = new EditText(this);
			edit.setWidth(50);
			edit.setText("");
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			//builder.setView(inflater.inflate(R.layout.ingresar_usuario,null))
			builder.setTitle("Has ganado!!! Ingresa tu nombre:");
			builder.setView(edit)
					.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							gameOver("Felicitaciones!!");
						}
					})
					.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							String dificultad = getDificultad();
							String name = edit.getText().toString();
							String tiempo = Integer.toString(segundos);
							if(name != ""){
								db.addUsuario(new Ranking(name,tiempo,dificultad));
								Context context = getApplicationContext();
								CharSequence text = "puntuacion guardada!";
								int duration = Toast.LENGTH_SHORT;
								Toast toast = Toast.makeText(context, text, duration);
								toast.show();
							}
							gameOver("Felicitaciones!!");
						}
					  });
			AlertDialog dialog = builder.create();
			 
			// show it
			dialog.show();
		
	}
	private void gameOver(String mensaje){
		gameover = true;
		final Intent intent = new Intent(this,MainActivity.class);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
			// set title
			alertDialogBuilder.setTitle(mensaje);
			
			// set dialog message
			alertDialogBuilder
				.setMessage("Que deseas hacer ahora??")
				.setCancelable(false)
				.setNegativeButton("Salir",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {

						
						startActivity(intent);
						
						//dialog.cancel();
						finish();
						
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
	}
	public void clickCara(View view){
		
		DialogFragment dialog2 = new SeleccionNuevoJuego();
		dialog2.show(getFragmentManager(),"SeleccionNuevoJuego");
		
	}
	public void clickBandera(View view){
		if(setBandera){
			setBandera = false;
			bandera.setBackgroundResource(R.drawable.flag_icon);
		}else{
			setBandera = true;
			bandera.setPressed(true);
			bandera.setBackgroundResource(R.drawable.flag_icon2);
		}
	}
	private void MostrarMinas(ImageButton boton){
		for(int i=0; i<filas; i++){
			for(int j=0; j<columnas;j++){
				if(pos[i][j]==-1){
					botones[i][j].setImageResource(R.drawable.mine);
					botones[i][j].setScaleType(ScaleType.FIT_XY);
				}
			}
		}
		
	}
	public String getDificultad(){
		if(filas == 8 && columnas == 8)
			return "principiante";
		else if(filas == 12 && columnas == 12)
			return "intermedio";
		else if(filas == 16 && columnas == 16)
			return "experto";
		else
			return "personalizado";
		
	}

}