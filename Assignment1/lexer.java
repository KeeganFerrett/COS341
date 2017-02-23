class lexer {
	public static void main(String[] args) {
		regexList scanner = new regexList();

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
		scanner.testString("varAS22");
		scanner.testString("wwq");
		scanner.testString("8801");
		scanner.testString("\"Hello\"");
		if (scanner.testString("\"Hello") == null) {
			System.out.println("Lexer Error (Correct)");
		}
		scanner.testString("true");
		scanner.testString("eq");
		scanner.testString("or");
		scanner.testString("add");
		scanner.testString(";");
		scanner.testString("=");
		scanner.testString("if");
		scanner.testString("for");
		scanner.testString("input");
		scanner.testString("halt");
		scanner.testString("proc");

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Test previous problem casses");

		scanner.testString("(");
		scanner.testString(")");
		scanner.testString("{");
		scanner.testString("}");
	}
}