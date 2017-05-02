//u15132847 and u15011713
//Keegan Ferrett and Nikki Constancon
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class compiler {

	private static final String FILENAME1 = "../Output/lexer";
	private static final String FILENAME2 = "../Output/syntax";
	private static final String FILENAME3 = "../Output/vTable";
	private static final String FILENAME4 = "../Output/fTable";

	public static void main(String[] args) {
		//Will call both lexer and syntax from here
		tokenList tokens;
		syntaxParser parser;
		int abtractionLevel = 0;

		if (args.length == 0) {
			System.out.println("Error, no file given. Exiting now.");
			return;
		}

		if (args.length == 3 && args[1].equals("-abstract")) {
			abtractionLevel = Integer.parseInt(args[2]);
			System.out.println("Abstract Level of " + abtractionLevel + " requested for syntax parsing");
		}

		abtractionLevel = 2;

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

			//System.out.println(lexTokens);
			System.out.println("Lexing Complete");
			//System.out.println();

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

		parser = new syntaxParser(tokens, abtractionLevel);
		System.out.println("Parsing Complete");
		String ret = parser.parse();

		//syntaxTree realTree = parser.getTree();
		//realTree.printTreeBreath();
		//System.out.println(realTree.getDepthFirst());

		symbolLookup sl = new symbolLookup(parser.getList().head);
		symbolTable vTable = sl.getVTable();
		symbolTable fTable = sl.getFTable();

		try{
			fw = new FileWriter(FILENAME2);
			bw = new BufferedWriter(fw);

			//System.out.println(ret);
			
			//System.out.println();

			bw.write(parser.getList().toString());
		
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
		try{
			fw = new FileWriter(FILENAME3);
			bw = new BufferedWriter(fw);
			bw.write(vTable.toString());
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
		try{
			fw = new FileWriter(FILENAME4);
			bw = new BufferedWriter(fw);
			bw.write(fTable.toString());

			//System.out.println(ret);
			System.out.println("Type and Scope Checking Complete");
			//System.out.println();

		
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
		System.out.println("Compiler Finished");
	}
}