package timeanalysis.App;

import timeanalysis.App.Interfaces.ITostadas;
import timeanalysis.App.Tools.Interno;
import timeanalysis.App.Tools.SD;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SalvadoActivity extends Activity implements ITostadas {
	
	private static ListView listView;
	private ArrayAdapter<String> adapter;
	private final String[] values = new String[] {"Interno","SD","Google Drive"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_salvado);
		
		PrepararListView();
	}
	
	public void PrepararListView() {
		listView = (ListView) findViewById(R.id.ListaSalvado);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,values);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if("Interno".equals(((TextView) view).getText())) {
					MainActivity.setDispositivoSalvado(new Interno());
					MainActivity.setTabCaptura();
				}else if("SD".equals(((TextView) view).getText())) {
					MainActivity.setDispositivoSalvado(new SD());
					MainActivity.setTabCaptura();
				}else if("Google Drive".equals(((TextView) view).getText())) {
					//Todavia no implemento.
					MostrarTostada("No esta implementado todavia");
				}
			}
		});
		adapter.notifyDataSetChanged();
	}
	
	public static int ObtenerSelecion() {
		return listView.getSelectedItemPosition();
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