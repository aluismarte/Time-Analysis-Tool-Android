package timeanalysis.App.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
	
	private static final String NombreDB = "TimeAnalysisDB.db";
	private static final int DBVersion = 36;
	private static final String FK_Ready = "PRAGMA foreign_keys = ON;";
	
	public DB(Context context) {
		super(context, NombreDB, null, DBVersion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE operacion"+ 
				"("+
				"id_operacion INTEGER PRIMARY KEY AUTOINCREMENT," +
				"nombre VARCHAR(150)" + 
				");");
		db.execSQL("CREATE TABLE operacionDescripcion"+ 
				"(" +
				"id_descripcion INTEGER PRIMARY KEY AUTOINCREMENT," +
				"id_operacion INTEGER," +
				"nombre VARCHAR(150)," +
				"FOREIGN KEY (id_operacion) REFERENCES operacion(id_operacion)" +
				");");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Datos para borrar en la siguiente version.
	}
	
	public void prepararFK(SQLiteDatabase db) {
		db.execSQL(FK_Ready);
	}
	
}