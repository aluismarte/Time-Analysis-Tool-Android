package timeanalysis.App;

import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.app.Activity;
import android.os.Bundle;
//import java.io.IOException;
//import jxl.Workbook;

public class ReporteActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public WritableWorkbook createWorkbook(String fileName){
	    //exports must use a temp file while writing to avoid memory hogging
	    WorkbookSettings wbSettings = new WorkbookSettings();
	    wbSettings.setUseTemporaryFileDuringWrite(true);
	    
	    //get the sdcard's directory
	    //File sdCard = Environment.getExternalStorageDirectory();
	    //add on the your app's path
	    //File dir = new File(sdCard.getAbsolutePath() + "/JExcelApiTest");
	    //make them in case they're not there
	    //dir.mkdirs();
	    //create a standard java.io.File object for the Workbook to use
	    //File wbfile = new File(dir,fileName);
		
		WritableWorkbook wb = null;
		
		// try{
		// create a new WritableWorkbook using the java.io.File and
		// WorkbookSettings from above
		// wb = Workbook.createWorkbook(wbfile,wbSettings);
		// }catch(IOException ex){
		// ex.getMessage();
		// }
		return wb;
	}

	public WritableSheet createSheet(WritableWorkbook wb, String sheetName,int sheetIndex) {
		return wb.createSheet(sheetName, sheetIndex);
	}
	
	public void writeCell(int columnPosition, int rowPosition, String contents,boolean headerCell, WritableSheet sheet) throws RowsExceededException, WriteException {
		// create a new cell with contents at position
		Label newCell = new Label(columnPosition, rowPosition, contents);
		
		if (headerCell) {
			// give header cells size 10 Arial bolded
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			// center align the cells' contents
			headerFormat.setAlignment(Alignment.CENTRE);
			newCell.setCellFormat(headerFormat);
		}
		sheet.addCell(newCell);
	}
	
}