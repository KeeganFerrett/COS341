//u15132847 and u15011713
//Keegan Ferrett and Nikki Constancon
import java.util.*;


class syntaxParser {
 	
	token temp ;
	tokenList list;
	//Used as a flag to determine the level of abstraction
	//0 = no abstraction
	//1 = low abstraction (Just missing semi colons)
	//2 = high abstraction (All grouping symbols gone)
	int abstractionLevel;

	//Will be returned in depth first order
	syntaxTreeList tree;
	//The physical syntax Tree
	syntaxTree realTree;

 	public syntaxParser(tokenList tokens, int _abstractionLevel){
 		temp = tokens.pop();
 		list = tokens;

 		abstractionLevel = _abstractionLevel;
 		
 		tree = new syntaxTreeList();
 		realTree = new syntaxTree();
 	}

 	public syntaxTree getTree() {
 		return realTree;
 	}

 	public syntaxTreeList getList() {
 		return tree;
 	}

 	public String parse(){
 		//check f it starts with proc
 		if(temp.getInput().equals("proc")){
 			error("Incorrect start of file");
 		}

 		parseProg();
 		if(temp != null){
 			error("Unexpected End, Empty Lexer List");
 		}

 		tree.addNode("$", "End Symbol", "-");
 		realTree.addNode("$", "End Symbol", "-", 0);
 		return tree.toString();
 	}

 	public void parseProg(){
		//System.out.println("ParseProg");
		//parseProg();

 		if (temp == null) error("Unexpected End");
 		tree.addNode("Prog", "Non-Terminal", "-");
 		realTree.addNode("Prog", "Non-Terminal", "-", 2);
 		parseCode();
 		parseProc_2();
 	}

 	public void parseCode(){
 		tree.addNode("Code", "Non-Terminal", "-");
		//System.out.println("ParseCode");
 		
 		if(temp == null) {
 			realTree.addNode("Proc_2", "Non-Terminal", "-", 0);
 			return;
 		}

 		if(temp.getType().equals("UserDefinedName")){
 			realTree.addNode("Code", "Non-Terminal", "-", 2);
 			parseInstr();
 			parseCode();
 		}
 		else if(temp.getInput().equals(";")){
 			realTree.addNode("Code", "Non-Terminal", "-", 1);
 			eat(";");
 			parseCode();
 		}
 		else if(temp.getInput().equals("input") || temp.getInput().equals("output") ||temp.getInput().equals("halt") || temp.getInput().equals("if") || temp.getInput().equals("while") || temp.getInput().equals("for") ){
 			realTree.addNode("Code", "Non-Terminal", "-", 2);
 			parseInstr();
 			parseCode();
 		}else{
 			realTree.addNode("Code", "Non-Terminal", "-", 0);
 			return;
 		}
 	}


 	public void parseProc_2(){
 		if(temp == null) {
 			return;
 		}
		//System.out.println("ParseProc_2");
		tree.addNode("Proc_2", "Non-Terminal", "-");
		realTree.addNode("Proc_2", "Non-Terminal", "-", 1);

 		if(temp.getInput().equals(";")){
 			eat(";");
 			parseProc();
 		} if(temp.getInput().equals("proc")){
 			parseProc_Def();
 		}else {
 			return;
 		}
 	}

 	public void parseInstr(){
		//System.out.println("ParseInstr");
		tree.addNode("Instr", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}
		realTree.addNode("Instr", "Non-Terminal", "-", 1);

 		if(temp.getType().equals("UserDefinedName")){
 			//System.out.println(list.peek());
 			// System.out.println("INSTR : UserDefinedName");
 			if(list.peek() != null && list.peek().getInput().equals("=") ){
 				parseAssign();
 			} else{
 				eat("UserDefinedName");
 			}
 		} else if(temp.getInput().equals("halt") ){
 			eat("halt");
 		} else if(temp.getType().equals("I/O command") ){
 			// System.out.println("INSTR : IO");
 			parseIO();
 		} else if(temp.getInput().equals("if") ){
 			// System.out.println("INSTR : if");
 			parseCond_Branch();
 		} else if(temp.getInput().equals("while") || temp.getInput().equals("for")){
 			// System.out.println("INSTR : while/for");
 			parseCond_Loop();
 		}else 
 			error("INSTR Expecting UDN, halt, I/O, if, while or for");
 	}

 	public void parseProc_Def(){
		//System.out.println("ParseProc_Def");
		tree.addNode("Proc_Def", "Non-Terminal", "-");

		if(temp == null) {
 			realTree.addNode("Proc_Def", "Non-Terminal", "-", 0);
 			return;
		}

		if(temp.getInput().equals("proc") ){
			realTree.addNode("Proc_Def", "Non-Terminal", "-", 2);
			parseProc();
			parseProc_Def();
		} else {
			return;
		}
 	}


 	public void parseProc(){
		//System.out.println("ParseProc");
		tree.addNode("Proc", "Non-Terminal", "-");

 		if(temp == null) {
 			return;
 		}

 		if(temp.getInput().equals("proc") ){
 			realTree.addNode("Proc", "Non-Terminal", "-", 3);
			eat("proc");
			eat("UserDefinedName");
			eat("{");
			parseProg();
			eat("}");
		} else {
			error("PROC Expecting proc");
		}
 	}


 	public void parseCalc(){
		//System.out.println("ParseCalc");
		tree.addNode("Calc", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("Number operator") ){
 			realTree.addNode("Calc", "Non-Terminal", "-", 3);
 			eat("Number operator");
 			eat("(");
 			parseNumExpr();
 			eat(",");
 			parseNumExpr();
 			eat(")");
 		} else error("CALCE Expecting Number operator");
 	}


 	public void parseNumExpr(){
		//System.out.println("ParseNumExpr");
		tree.addNode("NumExpr", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("UserDefinedName") ){
 			realTree.addNode("NumExpr", "Non-Terminal", "-", 1);
 			eat("UserDefinedName");
 		} else if(temp.getType().equals("Number operator")){
 			realTree.addNode("NumExpr", "Non-Terminal", "-", 1);
 			parseCalc();
 		}else if(temp.getType().equals("Integer")){ //ADDED
 			realTree.addNode("NumExpr", "Non-Terminal", "-", 1);
 			eat("Integer");
 		}else error("NUMEXPR Expecting UDN , Number operator");
 	}


 	public void parseAssign(){
		//System.out.println("ParseAssign");
		tree.addNode("Assign", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("UserDefinedName") ){
 			realTree.addNode("Assign", "Non-Terminal", "-", 3);
 			eat("UserDefinedName");
 			eat("=");
 			parseVar_Branch();
 		}else error("ASSIGN Expecting UDN");
 	}


 	public void parseIO(){
		//System.out.println("ParseIO");
		tree.addNode("IO", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("I/O command") ){
 			realTree.addNode("IO", "Non-Terminal", "-", 2);
 			eat("I/O command");
 			eat("(");
 			eat("UserDefinedName");
 			eat(")");
 		}else error("IO Expecting IO command");
 	}


 	public void parseBool(){
		//System.out.println("ParseBool");
		tree.addNode("Bool", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getInput().equals("(") ){
 			realTree.addNode("Boolean", "Non-Terminal", "-", 3);
 			eat("(");
 			eat("UserDefinedName");
 			parseComp_Symbol();
 			eat("UserDefinedName");
 			eat(")");
 		} else if(temp.getInput().equals("eq")){
 			realTree.addNode("Boolean", "Non-Terminal", "-", 3);
 			eat("eq");
 			eat("(");
 			eat("UserDefinedName");
 			eat(",");
 			eat("UserDefinedName");
 			eat(")");
 		}else if(temp.getInput().equals("and") || temp.getInput().equals("or")){
 			realTree.addNode("Boolean", "Non-Terminal", "-", 3);
 			eat("Boolean operator");
 			eat("(");
 			parseBool();
 			eat(",");
 			parseBool();
 			eat(")");
 		}else if(temp.getInput().equals("not")){
 			realTree.addNode("Boolean", "Non-Terminal", "-", 2);
 			eat("not");
 			parseBool();
 		}else error("BOOL Expecting boolean def");

 	}

 	public void parseCond_Loop(){
		//System.out.println("ParseCond_loop");
		tree.addNode("Cond_Loop", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getInput().equals("while") ){
 			realTree.addNode("Cond_Loop", "Non-Terminal", "-", 3);
 			eat("while");
 			eat("(");
 			parseBool();
 			eat(")");
 			eat("{");
 			parseCode();
 			eat("}");

 		}else if(temp.getInput().equals("for") ){
 			realTree.addNode("Cond_Loop", "Non-Terminal", "-", 13);
 			eat("for");
 			eat("(");
 			eat("UserDefinedName");
 			eat("=");
 			eat("0");
 			eat(";");
 			eat("UserDefinedName");
 			eat("<");
 			eat("UserDefinedName");
 			eat(";");
 			eat("UserDefinedName");
 			eat("=");
 			eat("add");
 			eat("(");
 			eat("UserDefinedName");
 			eat(",");
 			eat("1");
 			eat(")");
 			eat(")");

 			eat("{");
 			parseCode();
 			eat("}");

 		}else error("COND_LOOP Expecting while,for");
 	}


 	public void parseElse_Branch(){
		//System.out.println("ParseElse_Branch");
 		tree.addNode("Else_Branch", "Non-Terminal", "-");

 		if(temp == null) {
 			realTree.addNode("Else_Branch", "Non-Terminal", "-", 0);
 			return;
 		}
 		if(temp.getInput().equals("else") ){
 			realTree.addNode("Else_Branch", "Non-Terminal", "-", 2);
 			eat("else");
 			eat("{");
 			parseCode();
 			eat("}");
 		}else return;
 	}


 	public void parseCond_Branch(){
		//System.out.println("ParseCond_Branch");
 		tree.addNode("Cond_Branch", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}		

 		if(temp.getInput().equals("if") ){
 			realTree.addNode("Cond_Branch", "Non-Terminal", "-", 5);
 			eat("if");
 			eat("(");
 			parseBool();
 			eat(")");
 			eat("then");
 			eat("{");
 			parseCode();
 			eat("}");
 			parseElse_Branch();

 		}else error("COND_BRANCH Expecting if");
 	}

 	public void parseVar_Branch(){
		//System.out.println("ParseVar_Branch");
		tree.addNode("Var_Branch", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("UserDefinedName") ){
 			realTree.addNode("Var_Branch", "Non-Terminal", "-", 1);
 			eat("UserDefinedName");
 		}else if(temp.getType().equals("Short string")){
 			realTree.addNode("Var_Branch", "Non-Terminal", "-", 1);
 			eat("Short string");
		}else if(temp.getType().equals("Integer") || temp.getType().equals("Number operator")){
			realTree.addNode("Var_Branch", "Non-Terminal", "-", 1);
			parseNumExpr();
		}else error("Var_BRANCH Expecting UDN , string, int");
 	}


 	public void parseProc_Def_2(){
		//System.out.println("ParseProc_Def_2");
		tree.addNode("Proc_Def_2", "Non-Terminal", "-");

 		if(temp == null)
 			return;
 		if(temp.getInput().equals("proc") ){
 			realTree.addNode("Proc_Def_2", "Non-Terminal", "-", 1);
 			parseProc_Def();
 		}else return;
 	}


 	public void parseComp_Symbol(){
		//System.out.println("ParseComp_Symbol");
		tree.addNode("Comp_Symbol", "Non-Terminal", "-");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getInput().equals("<")){
 			realTree.addNode("Comp_Symbol", "Non-Terminal", "-", 1);
 			eat("<");
 		} else if (temp.getInput().equals(">")){
 			realTree.addNode("Comp_Symbol", "Non-Terminal", "-", 1);
 			eat(">");
 		}else error("COMP_SYMBOL Expecting <,>");
 	}


 	public void eat(String exp){
 		if(temp == null){
 			error("Unexpected End of file");
 			return;
 		}
 		if(exp.equals(temp.getType()) || exp.equals(temp.getInput())){
 			//System.out.println("Eating: "  +  temp.getInput());
 			
 			if ((abstractionLevel == 0) || (abstractionLevel == 1 && !temp.getInput().equals(";")) || (abstractionLevel == 2 && !temp.getType().equals("Grouping symbol"))) {	
 				tree.addNode(temp.getInput(), "Terminal", temp.getType());
 				realTree.addNode(temp.getInput(), "Terminal", temp.getType(), 0);
 			}
			temp = list.pop();
		} else error("Token is not expected ");
 	}
 	public void error(String err){
 		
 		if(temp != null){
 			System.out.println("SYNTAX ERROR: " + err + "-> "+ temp.getType() + ": " + temp.getInput());
 		}else{
 			System.out.println("Unexpected end of line");
 		}
 		System.exit(0);
 	}
}

