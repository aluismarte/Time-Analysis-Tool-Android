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
	public Workbook wb;
	public FileOutputStream fileOut;
	public CreationHelper createHelper;
	private List<String> Datos;
	private String HojaDatos = "Datos";
	private String HojaCalculos = "Calculos";
	public Sheet HojaDat;
	public Sheet HojaCal;
	
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
		CrearHojaExcel(HojaDatos);
		CrearHojaExcel(HojaCalculos);
	}
	
	private void EscribirDatosRecolectadosExcel() {
		Datos = CapturaActivity.getDatos();
		int x,y,dat = 0;
		
		x = OperacionActivity.getSeleccion().size();
		y = MainActivity.getRepeticiones();
		
		// Preparo las cabezeras.
		for(int k = 1; k <= x; k++) {
			CrearCabeza(k, HojaDat);
			EscribirCelda(1, k, "Ope#"+ k, HojaDat);
		}
		
		// Inserto los datos.
		for(int i = 1; i <= y; i++) {
			for(int j = 1; j <= x; j++,dat++) {
				EscribirCelda(i, j,Datos.get(dat), HojaDat);
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
	protected void EscribirCelda(int columnPos, int rowPos, String contenido, Sheet hojas) {
		Row row = hojas.getRow(rowPos);
		Cell cell = row.createCell(columnPos);
		cell.setCellValue(contenido);
	}
	
	protected void EscribirCelda(int columnPos, int rowPos, double contenido, Sheet hojas) {
		Row row = hojas.getRow(rowPos);
		Cell cell = row.createCell(columnPos);
		cell.setCellValue(contenido);
	}
	
	protected void EscribirCelda(int columnPos, int rowPos, Date contenido, Sheet hojas) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		Row row = hojas.getRow(rowPos);
		Cell cell = row.createCell(columnPos);
		cell.setCellValue(contenido);
		cell.setCellStyle(cellStyle);
	}
	
	protected void EscribirCelda(int columnPos, int rowPos, boolean contenido, Sheet hojas) {
		Row row = hojas.getRow(rowPos);
		Cell cell = row.createCell(columnPos);
		cell.setCellValue(contenido);
	}
	
	// Falta Celdas con formulas
	// Necesarias para la hoja de calculo
	
}