package timeanalysis.App.DB;

import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Manejador extends Activity{
	
	private static Manejador manejador;
	private SQLiteDatabase database;
	private DB db;
	
	private Manejador() {
		db = new DB(getApplicationContext());
		open();
	}
	
	public static Manejador getInstancia() {
		if(manejador == null) {
			manejador = new Manejador();
		}
		return manejador;
	}
	
	public void open() throws SQLException {
	    database = db.getWritableDatabase();
	}
	
	public void close() {
		db.close();
	}
	
	public SQLiteDatabase getDB() {
		return this.database;
	}
	
}