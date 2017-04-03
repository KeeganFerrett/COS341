import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class compiler {

	private static final String FILENAME1 = "../Output/lexer";
	private static final String FILENAME2 = "../Output/syntax";

	public static void main(String[] args) {
		//Will call both lexer and syntax from here
		tokenList tokens;
		syntaxParser parser;

		if (args.length =< 0) {
			System.out.println("Error, no file given. Exiting now.");
			return;
		}

		lexer lexer = new lexer();
		tokens = lexer.parseFile("../Tests/" + args[0]);
		if(tokens == null){
			return;
		}

		String lexTokens = tokens.toString();

		BufferedWriter bw = null;
		FileWriter fw = null;

		try{
			fw = new FileWriter(FILENAME1);
			bw = new BufferedWriter(fw);

			System.out.println(lexTokens);
			System.out.println("Lexing Complete");
			System.out.println();

			bw.write(lexTokens);
		
		} catch (IOException e) {

			e.printStackTrace();

		}finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		parser = new syntaxParser(tokens);

		String ret = parser.parse();


		try{
			fw = new FileWriter(FILENAME2);
			bw = new BufferedWriter(fw);

			System.out.println(ret);
			System.out.println("Parsing Complete");
			System.out.println();

			bw.write(ret);
		
		} catch (IOException e) {

			e.printStackTrace();

		}finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
}