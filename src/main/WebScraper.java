package main;



import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import file.excel.ExcelFile;
import info.university.CampusAction.DEGREE;
import info.university.Fields;
import info.university.runnable.RunCampusFile;
import info.university.runnable.RunCampusUrl;

public class WebScraper {

	// do you want to create directory each generated file?
	public static boolean CREATE_DIRECTORY = false;

	// set url timeout
	public static int TIMEOUT = 120000;

	// set base path
	public static String BASE_PATH = "base_path_of_your_resource";

	// set output files
	public static String OUTPUT_PATH = "output_path_to_be_define";

	// set input folder
	public static String INPUT_PATH = "input_path_to_be_define";

	// set input file
	public static String FILE_XLSX = "this_is_file_excel.xlsx";


	// set file name for major
	public static String MAJOR_FILE_NAME = "major";

	// set file name for minor
	public static String MINOR_FILE_NAME = "minor";

	public static void main(final String[] args)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		final String inputFile = String.format("%s%s", BASE_PATH,FILE_XLSX);
		final ExcelFile excelFile = new ExcelFile(inputFile);
		final List data = excelFile.getData();
		ArrayList<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < data.size(); i++) {
			final Map d = (Map) data.get(i);
			final int number = (int) d.get("number");
			final String campusName = (String) d.get(Fields.CAMPUS_NAME);
			final String majorUrl = (String) d.get(Fields.MAJOR_LINK);
			final String minorUrl = (String) d.get(Fields.MINOR_LINK);
			final String majorTag = (String) d.get(Fields.MAJOR_TAG);
			final String minorTag = (String) d.get(Fields.MINOR_TAG);

			System.out.println("=======================================");
			System.out.println(String.format("Index : %d \nURL Major : %s \nURL Minor : %s", number, majorUrl, minorUrl));
			System.out.println(String.format("campusName : %s \nTag Major : [%s] \nTag Minor : [%s]", campusName,
					majorTag, minorTag));

			if (majorTag.trim().equals("filetext") == Boolean.TRUE) {
				Thread t1 = new Thread(new RunCampusFile(number, campusName, DEGREE.MAJOR));
				t1.start();
				threads.add(t1);
			} else if (majorTag.trim().equals("invalidurl") == Boolean.FALSE
					&& majorTag.trim().equals("json") == Boolean.FALSE) {
				Thread t2 = new Thread(new RunCampusUrl(number, campusName, majorUrl, majorTag, DEGREE.MAJOR));
				t2.start();
				threads.add(t2);

			}

			if (minorTag.trim().equals("filetext") == Boolean.TRUE) {
				Thread t3 = new Thread(new RunCampusFile(number, campusName, DEGREE.MINOR));
				t3.start();
				threads.add(t3);

			} else if (minorTag.trim().equals("invalidurl") == Boolean.FALSE
					&& minorTag.trim().equals("json") == Boolean.FALSE) {
				Thread t4 = new Thread(new RunCampusUrl(number, campusName, majorUrl, majorTag, DEGREE.MINOR));
				t4.start();
				threads.add(t4);

			}

			System.out.println("=======================================");

		}

		for (Thread t : threads)
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		System.out.println("===========================");
		System.out.println("done!");
		
	}
}


