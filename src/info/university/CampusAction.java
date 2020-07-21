package info.university;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import connection.ssl.SSLHelper;
import file.FileSize;
import file.csv.CSVFile;
import main.WebScraper;


public class CampusAction {
	
	//which degree are you using?
	public enum DEGREE{
		MAJOR,
		MINOR
	};
	

	
	public static void getDataFromUrl(int index, String campusName, String url,String tagSelector, DEGREE degree) throws IOException {
	
		
				List dataTmp = new ArrayList();
				Document doc = SSLHelper.getConnection(url).timeout(WebScraper.TIMEOUT).get();
				Elements elements = doc.select(tagSelector);
				
				for (Element element : elements) {
					
					String data = String.format("%s", element.text());
					//String data = String.format("%s", element.nextSibling().toString());
					
					if(data.isEmpty()) continue;
					if(data.length() == 1) continue;
					
					//System.out.println(data);		
					List<String> datas = new ArrayList<>();
					datas.add(data);
					dataTmp.add(datas);
				}
				
				
				campusName = campusName.replace(" ", "_").toLowerCase();
				campusName = String.format("%02d.%s", index,campusName);
				if(WebScraper.CREATE_DIRECTORY) {
					File folder = new File(String.format("%s%02d.%s", WebScraper.OUTPUT_PATH,index,campusName));
					WebScraper.OUTPUT_PATH = String.format("%s/", folder.getAbsolutePath());
					if(!folder.isDirectory()) 
						folder.mkdir();
				}
				
				if(degree == DEGREE.MAJOR)
					campusName = String.format("%s%s_%s.csv", WebScraper.OUTPUT_PATH,campusName,WebScraper.MAJOR_FILE_NAME);
				else if(degree == DEGREE.MINOR)
					campusName = String.format("%s%s_%s.csv", WebScraper.OUTPUT_PATH,campusName,WebScraper.MINOR_FILE_NAME);
				
				CSVFile.write(campusName,dataTmp);
				System.out.println(String.format("file : %s has size : %d bytes",campusName, FileSize.inB(new File(campusName))));
			}
	
	
	
	
	public static void getDataFromFile(int index, String campusName, DEGREE degree) throws IOException {
		
				String fileTxt;
				if(degree == DEGREE.MAJOR)
					fileTxt = WebScraper.MAJOR_FILE_NAME;
				else
					fileTxt = WebScraper.MINOR_FILE_NAME;
					
				String inputFileName = String.format("%s%02d/%s.txt", WebScraper.INPUT_PATH ,index,fileTxt);
				
				FileInputStream file = new FileInputStream(inputFileName);
				DataInputStream in = new DataInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String strLine;
				List dataTmp = new ArrayList();
				int i = 0;
				while ((strLine = br.readLine()) != null) {
					if(strLine.isEmpty()) continue;
					if(strLine.length() == 1) continue;
								
					strLine = strLine.replace(",", " ");
					//System.out.println(strLine);		
					List<String> datas = new ArrayList<>();
					datas.add(strLine);
					dataTmp.add(datas);
					i++;
					
				}

				campusName = campusName.replace(" ", "_").toLowerCase();
				campusName = String.format("%02d.%s", index,campusName);
				
				if(WebScraper.CREATE_DIRECTORY) {
					File folder = new File(String.format("%s%02d.%s", WebScraper.OUTPUT_PATH,index,campusName));
					WebScraper.OUTPUT_PATH = String.format("%s/", folder.getAbsolutePath());
					if(!folder.isDirectory()) 
						folder.mkdir();
				}
				
				if(degree == DEGREE.MAJOR)
					campusName = String.format("%s%s_%s.csv", WebScraper.OUTPUT_PATH,campusName,WebScraper.MAJOR_FILE_NAME);
				else if(degree == DEGREE.MINOR)
					campusName = String.format("%s%s_%s.csv", WebScraper.OUTPUT_PATH,campusName,WebScraper.MINOR_FILE_NAME);
				
				CSVFile.write(campusName,dataTmp);
				System.out.println(String.format("file : %s has size : %d bytes",campusName, FileSize.inB(new File(campusName))));
	
		
	}
	
	
}



