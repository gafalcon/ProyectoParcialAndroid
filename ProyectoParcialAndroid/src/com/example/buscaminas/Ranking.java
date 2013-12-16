package com.example.buscaminas;


/**
 * Clase que crea elementos para la base de datos Ranking
 * @author gabo
 *
 */
public final class Ranking {
	int _id;
	String _nombre;
	String _tiempo;
	String _dificultad;
	
	public Ranking(){
		
	}
	public Ranking(String nombre,String tiempo,String dificultad){
		this._nombre = nombre;
		this._dificultad = dificultad;
		this._tiempo = tiempo;
	}
	public Ranking(int id,String nombre,String tiempo,String dificultad){
		this._id = id;
		this._nombre = nombre;
		this._dificultad = dificultad;
		this._tiempo = tiempo;
	}
	public int getID(){
		return this._id;
	}
	public void setID(int id){
		this._id = id;
	}
	public String getNombre(){
		return this._nombre;
	}
	public String getTiempo(){
		return this._tiempo;
	}
	public String getDificultad(){
		return this._dificultad;
	}
	public void setNombre(String nombre){
		this._nombre = nombre;
	}
	public void setTiempo(String tiempo){
		this._tiempo = tiempo;
	}
	public void setDificultad(String dif){
		this._dificultad = dif;
	}
}
