class compiler {
	public static void main(String[] args) {
		//Will call both lexer and syntax from here

		lexer lexer = new lexer();
		lexer.parseFile("../Tests/" + args[0]);

	}
}