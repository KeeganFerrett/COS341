import java.util.*;


class lexer {

	private regexList scanner;
	private tokenList tokens;

	public lexer() {
		scanner = new regexList();

		scanner.addReservedWord("eq", "Comparison symbol");
		scanner.addReservedWord("<", "Comparison symbol");
		scanner.addReservedWord(">", "Comparison symbol");

		scanner.addReservedWord("and", "Boolean operator");
		scanner.addReservedWord("not", "Boolean operator");
		scanner.addReservedWord("or", "Boolean operator");

		scanner.addReservedWord("add", "Number operator");
		scanner.addReservedWord("sub", "Number operator");
		scanner.addReservedWord("mult", "Number operator");

		//These are problems
		scanner.addReservedWord("\\(", "Grouping symbol");
		scanner.addReservedWord("\\)", "Grouping symbol");	
		scanner.addReservedWord("\\{", "Grouping symbol");	
		scanner.addReservedWord("\\}", "Grouping symbol");	
		scanner.addReservedWord(",", "Grouping symbol");	
		scanner.addReservedWord(";", "Grouping symbol");	

		scanner.addReservedWord("=", "Assignment operator");

		scanner.addReservedWord("if", "Control structure");	
		scanner.addReservedWord("then", "Control structure");	
		scanner.addReservedWord("else", "Control structure");	
		scanner.addReservedWord("while", "Control structure");	
		scanner.addReservedWord("for", "Control structure");

		scanner.addReservedWord("input", "I/O command");
		scanner.addReservedWord("output", "I/O command");

		scanner.addReservedWord("halt", "Special command");

		scanner.addReservedWord("proc", "Procedure definition");
	}

	public tokenList parseFile(String _fileName) {
		tokens = new tokenList();
		fileParser fileOutput = new fileParser(_fileName);
		ArrayList<String> list = fileOutput.getList();
		for (String token : list){
			String type = scanner.testString(token);
			// System.out.println(token + "  " + type);
			if(type != null){
				tokens.addToken(token, type);
			}else{
				System.out.println("Lexical Error: " + token);
				return null;
			}

		}
		//Reads a word from the fileParser and tests it, adds to list if valid
		return tokens;
	}


//Testing 
	public static void main(String[] args) {
		regexList scanner = new regexList();
		tokenList tokens = new tokenList();

		scanner.addReservedWord("eq", "Comparison symbol");
		scanner.addReservedWord("<", "Comparison symbol");
		scanner.addReservedWord(">", "Comparison symbol");

		scanner.addReservedWord("and", "Boolean operator");
		scanner.addReservedWord("not", "Boolean operator");
		scanner.addReservedWord("or", "Boolean operator");

		scanner.addReservedWord("add", "Number operator");
		scanner.addReservedWord("sub", "Number operator");
		scanner.addReservedWord("mult", "Number operator");

		//These are problems
		scanner.addReservedWord("\\(", "Grouping symbol");
		scanner.addReservedWord("\\)", "Grouping symbol");	
		scanner.addReservedWord("\\{", "Grouping symbol");	
		scanner.addReservedWord("\\}", "Grouping symbol");	
		scanner.addReservedWord(",", "Grouping symbol");	
		scanner.addReservedWord(";", "Grouping symbol");	

		scanner.addReservedWord("=", "Assignment operator");

		scanner.addReservedWord("if", "Control structure");	
		scanner.addReservedWord("then", "Control structure");	
		scanner.addReservedWord("else", "Control structure");	
		scanner.addReservedWord("while", "Control structure");	
		scanner.addReservedWord("for", "Control structure");

		scanner.addReservedWord("input", "I/O command");
		scanner.addReservedWord("output", "I/O command");

		scanner.addReservedWord("halt", "Special command");

		scanner.addReservedWord("proc", "Procedure definition");	

		if (scanner.testString("9kas") == null) {
			System.out.println("Lexer Error (Correct)");
		}
		if (scanner.testString("varas22") == null) {
			System.out.println("Lexer Error (Oops)");
		}
		String temp = "";
		temp = scanner.testString("varas22");
		tokens.addToken("varas22", temp);
		temp = scanner.testString("wwq");
		tokens.addToken("wwq", temp);
		temp = scanner.testString("8801");
		tokens.addToken("8801", temp);
		temp = scanner.testString("\"Hello\"");
		tokens.addToken("\"Hello\"", temp);
		if (scanner.testString("\"Hello") == null) {
			System.out.println("Lexer Error (Correct)");
		}
		temp = scanner.testString("true");
		temp = scanner.testString("eq");
		temp = scanner.testString("or");
		temp = scanner.testString("add");
		temp = scanner.testString(";");
		temp = scanner.testString("=");
		temp = scanner.testString("if");
		temp = scanner.testString("for");
		temp = scanner.testString("input");
		temp = scanner.testString("halt");
		temp = scanner.testString("proc");

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Test previous problem casses");

		scanner.testString("(");
		scanner.testString(")");
		scanner.testString("{");
		scanner.testString("}");

		System.out.println(tokens.toString());
	}
}