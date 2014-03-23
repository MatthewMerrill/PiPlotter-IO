package mattmerr47.piplot.io.drawdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {
	
	/**
	 * Reads a PathFile from a file
	 * @param file = location of PathFile
	 * @return Returns PathFile found at given location, or null if none is found.
	 */
	public static DrawData readFrom(File file) {
		//FileInputStream fin = new FileInputStream(file);
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			Object data = ois.readObject();
			ois.close();
			
			if (data instanceof DrawData) {
				return (DrawData) data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Write a PathFile to a file.
	 * @param file = target file
	 * @param pathFile = PathFile to write
	 * @return Returns true if write was successful
	 */
	public static boolean savePathFile(File file, DrawData pathFile) {
		try {
			
			FileOutputStream fin = new FileOutputStream(file);
			ObjectOutputStream ois = new ObjectOutputStream(fin);
			ois.writeObject(pathFile);
			ois.close();
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}
	}

}
