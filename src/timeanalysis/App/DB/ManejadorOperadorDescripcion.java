package timeanalysis.App.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import timeanalysis.App.DB.Modelos.OperacionDescripcionModelo;
import timeanalysis.App.Interfaces.IManejador;

public class ManejadorOperadorDescripcion implements IManejador {
	
	private String ID_Des = "id_descripcion";
	private String ID_Ope = "id_operacion";
	private String Nombre = "nombre";
	private String Tabla = "operacionDescripcion";
	private static ManejadorOperadorDescripcion mod;
	private OperacionDescripcionModelo opeDesMod;
	private String[] allColumns = {ID_Des,ID_Ope, Nombre};
	
	private ManejadorOperadorDescripcion() {}
	
	public ManejadorOperadorDescripcion getInstancia() {
		if(mod == null) {
			mod = new ManejadorOperadorDescripcion();
		}
		return mod;
	}
	
	public void Preparar(OperacionDescripcionModelo dat) {
		opeDesMod = dat;
	}
	
	private void Terminar() {
		opeDesMod = null;
	}
	
	@Override
	public void Insertar() {
		ContentValues values = new ContentValues();
		values.put(ID_Ope, opeDesMod.getIdOperacion());
		values.put(Nombre, opeDesMod.getNombre());
		Manejador.getInstancia().getDB().insert(Tabla, null,values);
		Terminar();
	}
	
	@Override
	public void Eliminar() {
		Manejador.getInstancia().getDB().delete(Tabla, ID_Des + " = " + opeDesMod.getId(),null);
		Terminar();
	}
	
	@Override
	public void Actualizar() {
		//Esto se queda para despues.
		Terminar();
	}
	
	public OperacionDescripcionModelo[] Buscar() {
		//Esto se queda para despues.
		Terminar();
		return null;
	}
	
	public List<OperacionDescripcionModelo> Listado() {
		List<OperacionDescripcionModelo> dats = new ArrayList<OperacionDescripcionModelo>();
		Cursor cursor = Manejador.getInstancia().getDB().query(Tabla, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			OperacionDescripcionModelo ope = cursorToOperacionDescripcionModelo(cursor);
			dats.add(ope);
			cursor.moveToNext();
		}
		cursor.close();
		return dats;
	}
	
	private OperacionDescripcionModelo cursorToOperacionDescripcionModelo(Cursor cursor) {
		OperacionDescripcionModelo ope = new OperacionDescripcionModelo();
		ope.setId(cursor.getLong(0));
		ope.setIdOperacion(cursor.getLong(1));
		ope.setNombre(cursor.getString(2));
		return ope;
	}
	
}