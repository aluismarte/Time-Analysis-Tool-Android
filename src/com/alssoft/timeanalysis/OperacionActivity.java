package com.alssoft.timeanalysis;

import java.util.ArrayList;
import java.util.List;

import com.alssoft.timeanalysis.R;
import com.alssoft.timeanalysis.db.ManejadorOperacion;
import com.alssoft.timeanalysis.db.ManejadorOperadorDescripcion;
import com.alssoft.timeanalysis.db.modelos.OperacionDescripcionModelo;
import com.alssoft.timeanalysis.db.modelos.OperacionModelo;
import com.alssoft.timeanalysis.interfaces.ITostadas;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OperacionActivity extends Activity implements ITostadas {
	
	private static List<OperacionModelo> operaciones = new ArrayList<OperacionModelo>();
	private static List<OperacionDescripcionModelo> elementos = new ArrayList<OperacionDescripcionModelo>();
	private static List<String> Operacionesvalues = new ArrayList<String>();
	private static List<String> Elementosvalues = new ArrayList<String>();
	private static ListView listView;
	private static ListView listViewElemento;
	private static EditText tex;
	private static EditText texElemento;
	private static ArrayAdapter<String> adapterOperaciones;
	private static ArrayAdapter<String> adapterElementos;
	private static int IDOperacionElemento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operacion);
		
		LlenarModeloOperaciones();
		
		PrepararTextEdit();
		PrepararLista();
	}
	
	public void PrepararTextEdit() {
		tex = (EditText) findViewById(R.id.editText1);
	}
	
	public void PrepararLista() {
		listView = (ListView) findViewById(R.id.ListaOperaciones);
		adapterOperaciones = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Operacionesvalues);
		listView.setAdapter(adapterOperaciones);
		listView.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				  if("No hay nada".equals(((TextView) view).getText().toString())) {
					  MostrarTostada(getString(R.string.InserteOperacion).toString());
				  }else {
					  LlenarModeloElementos(operaciones.get(position));
					  IDOperacionElemento = position;
					  Elemento();
				  }
			  }
		});
	}
	
	public void NuevaOperacion(View view) {
		OperacionModelo lol = new OperacionModelo();
		if(!"".equals(tex.getText())) {
			lol.setNombre(tex.getText().toString());
			ManejadorOperacion.getInstancia().Preparar(lol);
			ManejadorOperacion.getInstancia().Insertar();
			MostrarTostada(getString(R.string.InsertoExito).toString());
			tex.setText("");
			LlenarModeloOperaciones();
			adapterOperaciones.notifyDataSetChanged();
		}else {
			MostrarTostada(getString(R.string.CampoVacio).toString());
		}
	}
	
	public void NuevoElemento(View view) {
		OperacionDescripcionModelo lol = new OperacionDescripcionModelo();
		if(!"".equals(texElemento.getText())) {
			lol.setNombre(texElemento.getText().toString());
			lol.setIdOperacion(operaciones.get(IDOperacionElemento).getId());
			ManejadorOperadorDescripcion.getInstancia().Preparar(lol);
			ManejadorOperadorDescripcion.getInstancia().Insertar();
			MostrarTostada(getString(R.string.InsertoExito).toString());
			texElemento.setText("");
			OperacionModelo om = operaciones.get(IDOperacionElemento);
			LlenarModeloElementos(om);
			adapterElementos.notifyDataSetChanged();
		}else {
			MostrarTostada(getString(R.string.CampoVacio).toString());
		}
	}
	
	public void LlenarModeloOperaciones() {
		BorrarModeloOperacion();
		operaciones = ManejadorOperacion.getInstancia().Listado();
		if(!operaciones.isEmpty()) {
			for(int i = 0; i < operaciones.size(); i++) {
				Operacionesvalues.add(operaciones.get(i).getNombre());
			}
		}else {
			Operacionesvalues.add(getString(R.string.Nohay).toString());
		}
	}
	
	public void LlenarModeloElementos(OperacionModelo om) {
		BorrarModeloElemento();
		OperacionDescripcionModelo odm = new OperacionDescripcionModelo();
		odm.setIdOperacion(om.getId());
		elementos = ManejadorOperadorDescripcion.getInstancia().Buscar(odm);
		if(!elementos.isEmpty()) {
			for(int i = 0; i < elementos.size(); i++) {
				Elementosvalues.add(elementos.get(i).getNombre());
			}
		}else {
			Elementosvalues.add(getString(R.string.Nohay).toString());
		}
	}
	
	public void BorrarModeloOperacion() {
		Operacionesvalues.clear();
		operaciones.clear();
	}
	
	public void BorrarModeloElemento() {
		elementos.clear();
		Elementosvalues.clear();
	}
	
	public void Elemento() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.activity_elemento);
		dialog.setTitle(getString(R.string.elementos));
		
		listViewElemento = (ListView) dialog.findViewById(R.id.ListaElementos);
		adapterElementos = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Elementosvalues);
		listViewElemento.setAdapter(adapterElementos);
		adapterElementos.notifyDataSetChanged();
		
		texElemento = (EditText) dialog.findViewById(R.id.elemento);
		
		Button ButtonOK = (Button) dialog.findViewById(R.id.OK);
		Button ButtonCancel = (Button) dialog.findViewById(R.id.Cancel);
		ButtonOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NuevoElemento(v);
			}
		});
		ButtonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	public static List<OperacionDescripcionModelo> getSeleccion() {
		List<OperacionDescripcionModelo> lol = new ArrayList<OperacionDescripcionModelo>();
		OperacionDescripcionModelo odm = new OperacionDescripcionModelo();
		odm.setIdOperacion((operaciones.get(IDOperacionElemento)).getId());
		lol = ManejadorOperadorDescripcion.getInstancia().Buscar(odm);
		return lol;
	}
	
	@Override
    protected void onSaveInstanceState(Bundle save) {
		super.onSaveInstanceState(save);
	}
	
	@Override
    protected void onRestoreInstanceState(Bundle saved) {
        super.onRestoreInstanceState(saved);
	}
	
	@Override
	public void MostrarTostada(final String tostada) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), tostada, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}