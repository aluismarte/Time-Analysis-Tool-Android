package com.alssoft.timeanalysis.db;

import java.util.ArrayList;
import java.util.List;

import com.alssoft.timeanalysis.db.modelos.OperacionModelo;
import com.alssoft.timeanalysis.interfaces.IManejador;

import android.content.ContentValues;
import android.database.Cursor;

public class ManejadorOperacion implements IManejador {
	
	private String Tabla = "operacion";
	private String Nombre = "nombre";
	private String ID = "id_operacion";
	private static ManejadorOperacion mo;
	private OperacionModelo opeMod;
	private String[] allColumns = {ID, Nombre};
	
	private ManejadorOperacion() {}
	
	public static ManejadorOperacion getInstancia() {
		if(mo == null) {
			mo = new ManejadorOperacion();
		}
		return mo;
	}
	
	public void Preparar(OperacionModelo dat) {
		opeMod = dat;
	}
	
	private void Terminar() {
		opeMod = null;
	}
	
	@Override
	public void Insertar() {
		ContentValues values = new ContentValues();
		values.put(Nombre, opeMod.getNombre());
		Manejador.getInstancia().getDB().insert(Tabla, null,values);
		Terminar();
	}
	
	@Override
	public void Eliminar() {
		Manejador.getInstancia().getDB().delete(Tabla, ID + " = " + opeMod.getId(),null);
		Terminar();
	}
	
	@Override
	public void Actualizar() {
		//Esto se queda para despues.
		Terminar();
	}
	
	public List<OperacionModelo> Buscar(OperacionModelo elemento) {
		List<OperacionModelo> dats = new ArrayList<OperacionModelo>();
		Cursor cursor = Manejador.getInstancia().getDB().query(Tabla, allColumns,
				ID + " = " + elemento.getId(), null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			OperacionModelo ope = cursorToOperacionModelo(cursor);
			dats.add(ope);
			cursor.moveToNext();
		}
		cursor.close();
		return dats;
	}
	
	public List<OperacionModelo> Listado() {
		List<OperacionModelo> dats = new ArrayList<OperacionModelo>();
		Cursor cursor = Manejador.getInstancia().getDB().query(Tabla, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			OperacionModelo ope = cursorToOperacionModelo(cursor);
			dats.add(ope);
			cursor.moveToNext();
		}
		cursor.close();
		return dats;
	}
	
	private OperacionModelo cursorToOperacionModelo(Cursor cursor) {
		OperacionModelo ope = new OperacionModelo();
		ope.setId(cursor.getLong(0));
		ope.setNombre(cursor.getString(1));
		return ope;
	}
	
}