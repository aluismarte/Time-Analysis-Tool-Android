package com.alssoft.timeanalysis.clasesabstractas;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.alssoft.timeanalysis.CapturaActivity;
import com.alssoft.timeanalysis.MainActivity;
import com.alssoft.timeanalysis.OperacionActivity;

public abstract class Excel {
	
	protected String Carpeta = MainActivity.BuscarTexto(1);
	public WritableWorkbook wb;
	public WorkbookSettings wbSettings;
	private List<String> Datos;
	
	public void EscribirArchivo(File Archivo) throws IOException {
		wbSettings.setUseTemporaryFileDuringWrite(false);
		wb = Workbook.createWorkbook(Archivo,wbSettings);
	}
	
	public void EscribirDatosRecolectados() throws RowsExceededException, WriteException {
		CrearHoja(wb, MainActivity.BuscarTexto(5), 0);
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
				EscribirCeldaLabel(i+movX, j+movY,Datos.get(dat),wb.getSheet("Datos"));
			}
		}
	}
	
	public void EscribirDatosAnalisis() {
		//Aqui supone que pongo los calculos estadisticos.
		//En otra hoja y usando formulas.
	}
	
	public WritableSheet CrearHoja(WritableWorkbook wb, String sheetName,int sheetIndex) {
		return wb.createSheet(sheetName, sheetIndex);
	}
	
	public void EscribirCeldaLabel(int columnPosition, int rowPosition, String contents, WritableSheet sheet) throws RowsExceededException, WriteException {
		Label newCell = new Label(columnPosition, rowPosition, contents);
		WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
		WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
		headerFormat.setAlignment(Alignment.CENTRE);
		newCell.setCellFormat(headerFormat);
		sheet.addCell(newCell);
	}
	
	public void EscribirCeldaNumero(int columnPosition, int rowPosition, int contents, WritableSheet sheet) throws RowsExceededException, WriteException {
		Number newCell = new Number(columnPosition, rowPosition, contents);
		WritableCellFormat integerFormat = new WritableCellFormat (NumberFormats.INTEGER); 
		integerFormat.setAlignment(Alignment.CENTRE);
		newCell.setCellFormat(integerFormat);
		sheet.addCell(newCell);
	}
	
	public void EscribirCeldaflotante(int columnPosition, int rowPosition, float contents, WritableSheet sheet) throws RowsExceededException, WriteException {
		Number newCell = new Number(columnPosition, rowPosition, contents);
		WritableCellFormat integerFormat = new WritableCellFormat (NumberFormats.FLOAT); 
		integerFormat.setAlignment(Alignment.CENTRE);
		newCell.setCellFormat(integerFormat);
		sheet.addCell(newCell);
	}
	
	//Falta lo de formula y demas.
	
}