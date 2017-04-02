import java.util.*;


class syntaxParser {
 	
	token temp ;
	tokenList list;

 	public syntaxParser(tokenList tokens){
 		temp = tokens.pop();
 		list = tokens;
 		
 	}
 	public String parse(){
 		parseProg();
 		if(temp != null){
 			error("Unexpected End");
 		}
 		return "Parsed!!";
 	}

 	public void parseProg(){
		System.out.println("ParseProg");

 		if (temp == null) error("Unexpected End");
 		parseCode();
 		parseProc_2();
 	}

 	public void parseCode(){

		System.out.println("ParseCode");
 		if(temp == null)
 			return;

 		if(temp.getType().equals("UserDefinedName")){
 			parseInstr();
 			parseCode();
 		}
 		else if(temp.getInput().equals(";")){
 			eat(";");
 			parseCode();
 		}
 		else if(temp.getInput().equals("input") || temp.getInput().equals("output") ||temp.getInput().equals("halt") || temp.getInput().equals("if") || temp.getInput().equals("while") || temp.getInput().equals("for") ){
 			parseInstr();
 			parseCode();
 		}else{

 			return;
 		}
 	}


 	public void parseProc_2(){
 		if(temp == null)
 			return;
		System.out.println("ParseProc_2");

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
		System.out.println("ParseInstr");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("UserDefinedName")){
 			System.out.println(list.peek());
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
		System.out.println("ParseProc_Def");
		if(temp == null)
 			return;

		if(temp.getInput().equals("proc") ){
			parseProc();
			parseProc_Def();
		} else {
			return;
		}
 	}


 	public void parseProc(){
		System.out.println("ParseProc");

 		if(temp == null)
 			return;
 		if(temp.getInput().equals("proc") ){
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
		System.out.println("ParseCalc");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("Number operator") ){
 			eat("Number operator");
 			eat("(");
 			parseNumExpr();
 			eat(",");
 			parseNumExpr();
 			eat(")");
 		} else error("CALCE Expecting Number operator");
 	}


 	public void parseNumExpr(){
		System.out.println("ParseNumExpr");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("UserDefinedName") ){
 			eat("UserDefinedName");
 		} else if(temp.getType().equals("Number operator")){
 			parseCalc();
 		}else if(temp.getType().equals("Integer")){ //ADDED
 			eat("Integer");
 		}else error("NUMEXPR Expecting UDN , Number operator");
 	}


 	public void parseAssign(){
		System.out.println("ParseAssign");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("UserDefinedName") ){
 			eat("UserDefinedName");
 			eat("=");
 			parseVar_Branch();
 		}else error("ASSIGN Expecting UDN");
 	}


 	public void parseIO(){
		System.out.println("ParseIO");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("I/O command") ){
 			eat("I/O command");
 			eat("(");
 			eat("UserDefinedName");
 			eat(")");
 		}else error("IO Expecting IO command");
 	}


 	public void parseBool(){
		System.out.println("ParseBool");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getInput().equals("(") ){
 			eat("(");
 			eat("UserDefinedName");
 			parseComp_Symbol();
 			eat("UserDefinedName");
 			eat(")");
 		} else if(temp.getInput().equals("eq")){
 			eat("eq");
 			eat("(");
 			eat("UserDefinedName");
 			eat(",");
 			eat("UserDefinedName");
 			eat(")");
 		}else if(temp.getInput().equals("and") || temp.getInput().equals("or")){
 			eat("Boolean operator");
 			eat("(");
 			parseBool();
 			eat(",");
 			parseBool();
 			eat(")");
 		}else if(temp.getInput().equals("not")){
 			eat("not");
 			parseBool();
 		}else error("BOOL Expecting boolean def");

 	}

 	public void parseCond_Loop(){
		System.out.println("ParseCond_loop");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getInput().equals("while") ){
 			eat("while");
 			eat("(");
 			parseBool();
 			eat(")");
 			eat("{");
 			parseCode();
 			eat("}");

 		}else if(temp.getInput().equals("for") ){
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
		System.out.println("ParseElse_Branch");
 		if(temp == null)
 			return;
 		if(temp.getInput().equals("else") ){
 			eat("else");
 			eat("{");
 			parseCode();
 			eat("}");
 		}else return;
 	}


 	public void parseCond_Branch(){
		System.out.println("ParseCond_Branch");


		if(temp == null){
 			error("Unexpected End of file");
 		}		

 		if(temp.getInput().equals("if") ){
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
		System.out.println("ParseVar_Branch");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getType().equals("UserDefinedName") ){
 			eat("UserDefinedName");
 		}else if(temp.getType().equals("Short string")){
 			eat("Short string");
		}else if(temp.getType().equals("Integer") || temp.getType().equals("Number operator")){
			parseNumExpr();
		}else error("Var_BRANCH Expecting UDN , string, int");
 	}


 	public void parseProc_Def_2(){
		System.out.println("ParseProc_Def_2");
 		if(temp == null)
 			return;
 		if(temp.getInput().equals("proc") ){
 			parseProc_Def();
 		}else return;
 	}


 	public void parseComp_Symbol(){
		System.out.println("ParseComp_Symbol");

		if(temp == null){
 			error("Unexpected End of file");
 		}

 		if(temp.getInput().equals("<")){
 			eat("<");
 		} else if (temp.getInput().equals(">")){
 			eat(">");
 		}else error("COMP_SYMBOL Expecting <,>");
 	}


 	public void eat(String exp){
 		if(temp == null){
 			error("Unexpected End of file");
 			return;
 		}
 		if(exp.equals(temp.getType()) || exp.equals(temp.getInput())){
 			System.out.println("Eating: "  +  temp.getInput());
			temp = list.pop();
		} else error("Token is not expected ");
 	}
 	public void error(String err){
 		
 		if(temp != null){
 			System.out.println("ERROR: " + err + "-> "+ temp.getType() + ": " + temp.getInput());
 		}else{
 			System.out.println("Unexpected end of line");
 		}
 		System.exit(0);
 	}
}

