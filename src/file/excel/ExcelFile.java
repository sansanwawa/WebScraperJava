package file.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import info.university.Fields;

public class ExcelFile {
	private List<Map<String, Object>> url;
	private String file;
	
	public ExcelFile(String file) {
		url = new ArrayList<Map<String, Object>>();
		this.file = file;
		try {
			this.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void readFile() throws IOException {
		readFile(this.file);
	}
	
	private void readFile(String file) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		Workbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(0);
		for (Row row : sheet ) {
			  Map<String, Object> map = new HashMap<String, Object>(); 
		      Cell c1 = row.getCell(0);
			  Cell c2 = row.getCell(1);
			  Cell c3 = row.getCell(2);
			  Cell c4 = row.getCell(3);
			  Cell c5 = row.getCell(4);
			  Cell c6 = row.getCell(5);

			  if(c1 == null) continue;
			  
			  map.put(Fields.NUMBER, 		Double.valueOf(c1.getNumericCellValue()).intValue() );
			  map.put(Fields.CAMPUS_NAME, 	c2.getStringCellValue());
		      map.put(Fields.MAJOR_LINK,  	c3.getStringCellValue());
		      map.put(Fields.MINOR_LINK,  	c4.getStringCellValue());
		      map.put(Fields.MAJOR_TAG,   	c5.getStringCellValue());
		      map.put(Fields.MINOR_TAG,   	c6.getStringCellValue());
				
		      url.add(map);
		       
		}
	}
	
	
	public List getData() {
		return url;
	}
	
	
	
	
}
