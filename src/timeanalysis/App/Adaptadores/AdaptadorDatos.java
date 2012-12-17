package timeanalysis.App.Adaptadores;

import timeanalysis.App.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdaptadorDatos extends BaseAdapter {
	
	private Context context;
	private final String[] Valores;
	
	public AdaptadorDatos(Context context, String[] valores) {
		this.context = context;
		this.Valores = valores;
	}
	
	@Override
	public int getCount() {
		return Valores.length;
	}

	@Override
	public Object getItem(int position) {
		return Valores[position];
	}

	@Override
	public long getItemId(int position) {
		//No esta implementado.
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		
		if (convertView == null) {
			gridView = new View(context);
			
			//obtengo el modelo.
			gridView = inflater.inflate(R.layout.modelo_datos, null);
			
			//Asigno valores a los campos de texto.
			TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
			textView.setText(Valores[position]);
			
			//Esto aqui es para poner más elemento,
			//Usar el patron decorador para adicionar elementos.
			//		String dato = Valores[position];
			
		}else {
			gridView = (View) convertView;
		}
		
		return gridView;
	}
	
}