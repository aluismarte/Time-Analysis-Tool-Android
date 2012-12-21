package timeanalysis.App.ClasesAbstractas;

import java.io.File;
import java.io.IOException;

import timeanalysis.App.MainActivity;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public abstract class Excel {
	
	protected String Carpeta = MainActivity.BuscarTexto(1);
	public WritableWorkbook wb;
	public WorkbookSettings wbSettings;
	
	public void EscribirArchivo(File Archivo) throws IOException {
		wbSettings.setUseTemporaryFileDuringWrite(false);
		wb = Workbook.createWorkbook(Archivo,wbSettings);
	}
	
	public WritableSheet CrearHoja(WritableWorkbook wb, String sheetName,int sheetIndex) {
		return wb.createSheet(sheetName, sheetIndex);
	}
	
	public void EscribirCelda(int columnPosition, int rowPosition, String contents,boolean headerCell, WritableSheet sheet) throws RowsExceededException, WriteException {
		Label newCell = new Label(columnPosition, rowPosition, contents);
		if (headerCell) {
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			headerFormat.setAlignment(Alignment.CENTRE);
			newCell.setCellFormat(headerFormat);
		}
		sheet.addCell(newCell);
	}
	
}