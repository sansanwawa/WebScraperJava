package file;

import java.io.File;

public class FileSize {

	public static Double inMB(File file) {
		return (double) file.length() / (1024 * 1024);
	}
	
	public static Double inKB(File file) {
		return (double) file.length() / 1024;
	}

	public static Long inB(File file) {
		return file.length();
	}
}
