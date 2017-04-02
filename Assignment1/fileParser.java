import java.io.*;
import java.nio.file.*;
import java.util.*;

class fileParser {
	private int currentIndex;
	private ArrayList<String> list;
	private String fileName;

	public fileParser(String _fileName) {
		regexList scanner = new regexList();

		fileName = _fileName;
		currentIndex = 0;
		list = new ArrayList<String>();
		try {
			//Gets Lines
			String code = "";
			for (String line : Files.readAllLines(Paths.get(_fileName))) {
				code += line + " ";
			}
				parseLine(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getList(){
		return list;
	}

	public void parseLine(String line){
		line = line.replaceAll("\\(", "-\\(-");
		line = line.replaceAll("\\)", "-\\)-");
		line = line.replaceAll("\\{", "-\\{-");
		line = line.replaceAll("\\}", "-\\}-");
		line = line.replaceAll("\\>", "-\\>-");
		line = line.replaceAll("\\<", "-\\<-");
		line = line.replaceAll(";", "-;-");
		line = line.replaceAll(",", "-,-");
		line = line.replaceAll("=", "-=-");
		line = line.replaceAll("\t", " ");
		//Goes through lines and picks up the tokens and adds them to token array list
		line = removeStringSpaces(line);
		// System.out.println("Whole Line: " + line);
		String[] tokens = line.split(" |-"); //NOT SURE ABOUT ,
		for(int t = 0; t < tokens.length; t++){
			if(!tokens[t].trim().isEmpty()){
				if(tokens[t].charAt(0) >=0 && tokens[t].charAt(0) == '\"'){
					tokens[t] = addStringSpaces(tokens[t]);	
				}
				// System.out.println(tokens[t]);
				list.add(tokens[t]);	
			}
		}
	}

	public static String addStringSpaces(String line){
		int index = line.indexOf("\"");
		while(index >= 0){
			
			int endIndex = line.indexOf("\"", index + 1);
			if (index != -1){
				if (endIndex != -1){
					for (int x = index; x < endIndex; x++){
						if(line.charAt(x) == '@'){
							line = line.substring(0,x) + ' ' + line.substring(x+1,line.length());
						}
					}
				}else{
					//syntax error...
					break;
				}
			}
			index = line.indexOf("\"",endIndex + 1);
		}
		return line;
	}

	public static String removeStringSpaces(String line){
		int index = line.indexOf("\"");
		while(index > 0){
			
			int endIndex = line.indexOf("\"", index + 1);
			if (index != -1){
				if (endIndex != -1){
					for (int x = index; x < endIndex; x++){
						if(line.charAt(x) == ' '){
							line = line.substring(0,x) + '@' + line.substring(x+1,line.length());
						}
					}
				}else{
					//syntax error...
					break;
				}
			}
			index = line.indexOf("\"",endIndex + 1);
		}
		return line;
	}

	public static void main(String[] args) {
		// fileParser fileOutput = new fileParser();
	}
}