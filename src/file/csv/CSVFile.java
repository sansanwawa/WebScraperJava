package file.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class CSVFile {

	
	
	public static void write(String file, List<List> datas) throws IOException {
		FileWriter writer = new FileWriter(new File(String.format("%s",file)));;
		for(List data : datas)
			CSVUtils.writeLine(writer, data);	
		writer.flush();
		writer.close();
		
	}
}
