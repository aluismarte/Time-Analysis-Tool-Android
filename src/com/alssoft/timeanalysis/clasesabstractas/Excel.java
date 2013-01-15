package com.alssoft.timeanalysis.clasesabstractas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alssoft.timeanalysis.CapturaActivity;
import com.alssoft.timeanalysis.MainActivity;
import com.alssoft.timeanalysis.OperacionActivity;

public abstract class Excel {
	
	protected String Carpeta = MainActivity.BuscarTexto(1);
	private Workbook wb;
	private FileOutputStream fileOut;
	private CreationHelper createHelper;
	private CellStyle cellStyle;
	private List<String> Datos;
	private String NombreHojaDatos = "Datos";
	private String NombreHojaCalculos = "Calculos";
	private Sheet HojaDat;
	//private Sheet HojaCal; No la uso todavia y evito wl warning
	
	public void EscribirArchivoExcel2007(FileOutputStream Archivo) {
		wb = new XSSFWorkbook();
		createHelper = wb.getCreationHelper();
		fileOut = Archivo;
		CrearHojas();
		try {
			wb.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		EscribirDatosRecolectadosExcel();
		EscribirDatosAnalisisExcel();
		Cerrar();
	}
	
	private void Cerrar() {
		// Esto se llama para grabar los datos, sino se pierden.
		try {
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void CrearHojas() {
		CrearHojaExcel(NombreHojaDatos);
		CrearHojaExcel(NombreHojaCalculos);
	}
	
	private void SetEstilo(int valor) {
		cellStyle = wb.createCellStyle();
		if(valor == 0) {
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		}else if(valor == 1) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		}else if(valor == 2) {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		}else if(valor == 3) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		}
	}
	
	private void EscribirDatosRecolectadosExcel() {
		Datos = CapturaActivity.getDatos();
		int x,y,dat = 0;
		
		x = OperacionActivity.getSeleccion().size();
		y = MainActivity.getRepeticiones();
		
		// Preparo las cabezeras.
		for(int k = 1; k <= x; k++) {
			CrearCabeza(k, HojaDat);
			EscribirCelda(1, k, "Ope#"+ k, HojaDat, 1);
		}
		
		// Inserto los datos.
		for(int i = 1; i <= y; i++) {
			for(int j = 1; j <= x; j++,dat++) {
				EscribirCelda(i, j,Datos.get(dat), HojaDat, 1);
			}
		}
	}
	
	private void EscribirDatosAnalisisExcel() {
		// Aqui supone que pongo los calculos estadisticos.
		// En otra hoja y usando formulas.
	}
	
	private void CrearHojaExcel(String sheetName) {
		wb.createSheet(sheetName);
	}
	
	// Esto se llama primero para crear las cabezas
	private void CrearCabeza(int rowPos, Sheet hojas) {
		hojas.createRow(rowPos);
	}
	
	// Uso polimorfismo y evito nombres diferentes con los diversos tipos de datos.
	protected void EscribirCelda(int columnPos, int rowPos, String contenido, Sheet hojas, int estilo) {
		SetEstilo(estilo);
		Row row = hojas.getRow(rowPos);
		Cell cell = row.createCell(columnPos);
		cell.setCellValue(contenido);
	}
	
	protected void EscribirCelda(int columnPos, int rowPos, double contenido, Sheet hojas, int estilo) {
		SetEstilo(estilo);
		Row row = hojas.getRow(rowPos);
		Cell cell = row.createCell(columnPos);
		cell.setCellValue(contenido);
	}
	
	protected void EscribirCelda(int columnPos, int rowPos, Date contenido, Sheet hojas, int estilo) {
		SetEstilo(estilo);
		Row row = hojas.getRow(rowPos);
		Cell cell = row.createCell(columnPos);
		cell.setCellValue(contenido);
		cell.setCellStyle(cellStyle);
	}
	
	protected void EscribirCelda(int columnPos, int rowPos, boolean contenido, Sheet hojas, int estilo) {
		SetEstilo(estilo);
		Row row = hojas.getRow(rowPos);
		Cell cell = row.createCell(columnPos);
		cell.setCellValue(contenido);
	}
	
	// Falta Celdas con formulas
	// Necesarias para la hoja de calculo
	
}