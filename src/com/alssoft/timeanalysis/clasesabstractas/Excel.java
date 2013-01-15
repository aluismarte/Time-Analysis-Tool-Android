package com.alssoft.timeanalysis.clasesabstractas;

import java.io.File;
import java.util.List;

import com.alssoft.timeanalysis.CapturaActivity;
import com.alssoft.timeanalysis.MainActivity;
import com.alssoft.timeanalysis.OperacionActivity;

public abstract class Excel {
	
	protected String Carpeta = MainActivity.BuscarTexto(1);
	private List<String> Datos;
	
	public void EscribirArchivo(File Archivo) {
	}
	
	public void EscribirDatosRecolectados() {
		Datos = CapturaActivity.getDatos();
		int x,y, movX, movY,dat = 0;
		
		movX = 4;
		movY = 4;
		x = MainActivity.getRepeticiones();
		y = OperacionActivity.getSeleccion().size();
		
		//Preparo las cabezeras.
		for(int k = 0; k < y; k++) {
			//Es dijo en 3 el x
			//Insert Ope[K] en cada columna como un label
		}
		
		//Inserto los datos.
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++,dat++) {
				
			}
		}
	}
	
	public void EscribirDatosAnalisis() {
		//Aqui supone que pongo los calculos estadisticos.
		//En otra hoja y usando formulas.
	}
	
	
	
	//Falta lo de formula y demas.
	
}