package com.alssoft.timeanalysis.db;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.alssoft.timeanalysis.MainActivity;

@SuppressLint("Instantiatable")
public class Manejador extends Activity{
	
	private static Manejador manejador;
	private static SQLiteDatabase database;
	private static DB db;
	
	private Manejador() {
		db = new DB(MainActivity.contexto);
		open();
	}
	
	public static Manejador getInstancia() {
		if(manejador == null) {
			manejador = new Manejador();
		}
		return manejador;
	}
	
	private static void open() throws SQLException {
	    database = db.getWritableDatabase();
	    db.prepararFK(database);
	}
	
	public void close() {
		db.close();
	}
	
	public SQLiteDatabase getDB() {
		db.prepararFK(database);
		return database;
	}
	
}