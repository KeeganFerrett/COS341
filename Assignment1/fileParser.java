import java.io.*;
import java.nio.file.*;
import java.util.*;

class fileParser {
	private int currentIndex;
	private ArrayList<String> tokens;
	private String fileName;

	public fileParser(String _fileName) {
		fileName = _fileName;
		currentIndex = 0;
		tokens = new ArrayList<String>();
		try {
			//Gets Lines

			for (String line : Files.readAllLines(Paths.get(_fileName))) {
				//Goes through lines and picks up the tokens and adds them to token array list
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		fileParser fileOutput = new fileParser("/home/keegan/Documents/COS 341/Assignment1/test.txt");
	}
}