class symbolLookup {
	private symbolTable vTable;
	private symbolTable fTable;
	private syntaxToken syntax;

	public symbolLookup(syntaxToken _head) {
		vTable = new symbolTable();
		fTable = new symbolTable();
		syntax = _head;
		
		syntaxToken temp = syntax;
		while (temp != null) {
			//System.out.println(temp.toString());

			if (temp.input.equals("Assign")) {
				temp.formed = "w";
				temp = temp.next;
				syntaxToken store = temp;

				String varName = temp.input;
				temp = temp.next;
				temp = temp.next;
				String type = "";
				String check = "";

				//Checking if it is a var branch and then going down the branch
				if (temp.input.equals("Var_Branch")) {
					syntaxToken varBranch = temp;
					temp = temp.next;
					if (temp.input.equals("NumExpr")) {
						type = "Integer";
						check = "n";
						temp.next.formed = check;
						temp.formed = check;
						varBranch.formed = check;
					} else if (temp.type.equals("Short string")) {
						type = "String";
						check = "s";
						temp.formed = check;
						varBranch.formed = check;
					} else if (temp.type.equals("UserDefinedName")) {
						type = vTable.lookup(temp.input);
						if (type.equals("Integer")) {
							check = "n";
						} else 
							check = "s";
						if (type == null) {
							System.out.println("ERROR: Variable \'" + temp.input + "\' has not been defined. (VAR BRANCH)");
							System.exit(1);
						}
						varBranch.formed = check;
						temp.formed = check;
						temp.input = vTable.getNewName(temp.input, type);
					}
				} 

				//For debugging purposes
				//System.out.println("Adding Variable \'" + varName + "\' to vTable with a type of " + type);
				vTable.bind(varName, type);
				store.formed = check;
				store.input = vTable.getNewName(varName, type);
			} else if (temp.input.equals("input")) {
				temp = temp.next;

				String varName = temp.input;
				String type = "Integer";

				//For debugging purposes
				//System.out.println("Adding Variable \'" + varName + "\' to vTable with a type of " + type);
				vTable.bind(varName, type);
				temp.formed = "n";
				temp.input = vTable.getNewName(varName, type);
			} else if (temp.input.equals("proc")) {
				temp = temp.next;
				String varName = temp.input;
				String type = "Procedure";

				boolean p = fTable.lookup(varName, type);
				if (p) {
					System.out.println("ERROR: Procedure \'" + temp.input + "\' has already been defined.");
					System.exit(1);
				}

				//For debugging purposes
				//System.out.println("Adding Variable \'" + varName + "\' to fTable with a type of " + type);
				fTable.bind(varName, type);
				temp.formed = "p";
				temp.input = fTable.getNewName(varName, type);
			} else if (temp.input.equals("{")) {
				vTable.enterScope(); 
			} else if (temp.input.equals("}")) {
				vTable.exitScope();
			} else if (temp.input.equals("output")) {
				temp = temp.next;
				String varName = temp.input;
				String type = vTable.lookup(varName);
				if (type == null) {
					System.out.println("ERROR: Variable \'" + temp.input + "\' has not been defined.");
					System.exit(1);
				}
				temp.input = vTable.getNewName(temp.input,type);
			} else if (temp.input.equals("for")) {
				temp = temp.next;
				String varName = temp.input;
				String type =  "Integer";
				//For debugging purposes
				//System.out.println("Adding Variable \'" + varName + "\' to vTable with a type of " + type);
				vTable.bind(varName, type);
				temp.formed = "n";
				temp.input = vTable.getNewName(varName, type);
				temp = temp.next.next.next;

				boolean p = vTable.lookup(temp.input, type);
				if (!p) {
					System.out.println("ERROR: Variable \'" + temp.input + "\' of type \'" + type + "\' has not been defined. (H)");
					System.exit(1);
				}
				temp.input = vTable.getNewName(temp.input, type);
				temp = temp.next.next;
				p = vTable.lookup(temp.input, type);
				if (!p) {
					System.out.println("ERROR: Variable \'" + temp.input + "\' of type \'" + type + "\' has not been defined. (J)");
					System.exit(1);
				}
				temp.formed = "n";
				temp.input = vTable.getNewName(temp.input, type);
				temp = temp.next;
				temp.formed = "n";
				temp.input = vTable.getNewName(temp.input, type);
				temp = temp.next.next.next;
				temp.formed = "n";
				temp.input = vTable.getNewName(temp.input, type);
			} else if (temp.input.equals("Bool")) {
				///System.out.println("Booleans Incountered");
				temp.formed = "b";
				temp = temp.next;
				checkBoolean(temp);
			} else if (temp.input.equals("Calc")) {
				temp = temp.next.next.next;
				//System.out.println("TESTING " + temp.toString());
				if (!temp.type.equals("Integer")) {
					boolean p = vTable.lookup(temp.input, "Integer");
					if (!p) {
						System.out.println("ERROR: Variable \'" + temp.input + "\' of type \'" + "Integer" + "\' has not been defined.");
						System.exit(1);
					}
					temp.formed = "n";
					temp.input = vTable.getNewName(temp.input, "Integer");
				}
				
				temp = temp.next.next;
				if (!temp.type.equals("Integer")) {
					boolean p = vTable.lookup(temp.input, "Integer");
					if (!p) {
						System.out.println("ERROR: Variable \'" + temp.input + "\' of type \'" + "Integer" + "\' has not been defined.");
						System.exit(1);
					}
					temp.formed = "n";
					temp.input = vTable.getNewName(temp.input, "Integer");
				}
			} else if (temp.input.equals("Code") || temp.input.equals("Prog") || temp.input.equals("Instr") || temp.input.equals("Cond_Branch") 
				|| temp.input.equals("Else_Branch") || temp.input.equals("Cond_Loop") || temp.input.equals("Proc_2")
				|| temp.input.equals("IO") || temp.input.equals("Proc_Def")) {
				temp.formed = "w";
			} 

			temp = temp.next;
		}

		//Check procedures
		temp = syntax;
		while (temp != null) {
			if (temp.input.equals("Instr")) {
				temp = temp.next;
				if (temp.type.equals("UserDefinedName")) {
					boolean p = fTable.lookup(temp.input, "Procedure");
					temp.formed = "p";
					temp.input = fTable.getNewName(temp.input, "Procedure");
					if (!p) {
						System.out.println("ERROR: Procedure \'" + temp.input + "\' has not been defined.");
						System.exit(1);
					}
				}
			}

			temp = temp.next;
		}
	}

	private void checkBoolean(syntaxToken temp) {
		//System.out.println(temp.input);
		if (temp.type.equals("UserDefinedName")) {
			String x = vTable.lookup(temp.input);
			temp.formed = "n";
			if (x == null || !x.equals("Integer")) {
				System.out.println("ERROR: Variable \'" + temp.input + "\' of type Integer has not been defined. 1");
				System.exit(1);
			}
			temp = temp.next.next.next;
			//Not sure if variables must be of the same type
			//If they do then just afix this part (GG EZ)
			String y = vTable.lookup(temp.input);
			temp.formed = "n";
			if (y == null || !y.equals("Integer")) {
				System.out.println("ERROR: Variable \'" + temp.input + "\' of type Integer has not been defined. 2");
				System.exit(1);
			}
			temp = temp.next;
		} else if (temp.input.equals("eq")) {
			temp = temp.next;
			temp.formed = "o";
			String x = vTable.lookup(temp.input);
			if (x == null) {
				System.out.println("ERROR: Variable \'" + temp.input + "\' has not been defined. 1");
				System.exit(1);
			}
			temp = temp.next;
			temp.formed = "o";
			//Not sure if variables must be of the same type
			//If they do then just afix this part (GG EZ)
			String y = vTable.lookup(temp.input);
			if (y == null) {
				System.out.println("ERROR: Variable \'" + temp.input + "\' has not been defined. 2");
				System.exit(1);
			}
			temp = temp.next;
		}
	}

	public symbolTable getVTable() {
		return vTable;
	}

	public symbolTable getFTable() {
		return fTable;
	}
}