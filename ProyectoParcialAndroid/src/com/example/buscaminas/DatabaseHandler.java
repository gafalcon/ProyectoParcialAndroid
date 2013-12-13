package com.example.buscaminas;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "ranking";
	private static final String TABLE_USUARIOS = "usuarios";
	private static final String KEY_ID = "id";
	private static final String KEY_NOMBRE  = "nombre";
	private static final String KEY_TIEMPO = "tiempo";
	private static final String KEY_DIF = "dificultad";
	
	public DatabaseHandler(Context context){
		super(context, DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db){
		String CREATE_RANKING_TABLE = "CREATE TABLE "+TABLE_USUARIOS +" (" 
	+ KEY_ID +" INTEGER PRIMARY KEY, " + KEY_NOMBRE+" TEXT, "
	+ KEY_TIEMPO + " TEXT, " + KEY_DIF + " TEXT);";
		db.execSQL(CREATE_RANKING_TABLE);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ){
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_USUARIOS);
		onCreate(db);
	}
	
	public void addUsuario(Ranking ranking){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NOMBRE, ranking.getNombre());
		values.put(KEY_TIEMPO, ranking._tiempo);
		values.put(KEY_DIF, ranking._dificultad);
		
		db.insert(TABLE_USUARIOS, null,values);
		db.close();
	}
	
	public Ranking getRanking(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_USUARIOS, new String[] {KEY_ID,
				KEY_NOMBRE, KEY_TIEMPO, KEY_DIF}, KEY_ID + "=?",
				new String[] {String.valueOf(id)}, null, null, null, null);
		Ranking ranking = new Ranking(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1),cursor.getString(2),cursor.getString(3));
		return ranking;
	}
	
	public List<Ranking> getAllUsers() {
	    List<Ranking> listaUsuarios = new ArrayList<Ranking>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_USUARIOS+" ORDER BY tiempo";
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Ranking ranking = new Ranking();
	            ranking.setID(Integer.parseInt(cursor.getString(0)));
	            ranking.setNombre(cursor.getString(1));
	            ranking.setTiempo(cursor.getString(2));
	            ranking.setDificultad(cursor.getString(3));
	            // Adding contact to list
	            listaUsuarios.add(ranking);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return listaUsuarios;
	}
}
