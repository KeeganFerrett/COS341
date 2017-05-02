class symbolTable {
	public class symbolEntry {
		public String symbol;
		public String type;
		public String newName;
		public symbolEntry next;

		public symbolEntry(String _sym, String _type) {
			symbol = _sym;
			type = _type;
			newName = type + "_" + symbol;
			next = null;
		}

		public symbolEntry(String _sym) {
			symbol = _sym;
			type = "----";
			newName = "----";
			next = null;
		}

		public String toString() {
			String ret = "[" + symbol + "," + type + "," + newName + "]";
			return ret;
		}
	}

	symbolEntry head;

	//Empty symbol table
	public symbolTable() { head = null; }

	//Bind function
	public void bind(String _symbol, String _type) {
		symbolEntry temp = new symbolEntry(_symbol, _type);
		temp.next = head;
		head = temp;
	}

	//Lookup Function (Does exsit)
	//Looks if a variable of type _type exists
	public boolean lookup(String _symbol, String _type) {
		symbolEntry temp = head;

		while(temp != null) {
			int scopeCount = 0;
			if (temp.symbol.equals("Exit")) {
				++scopeCount;
				temp = temp.next;
				while (scopeCount > 0) {
					if (temp.symbol.equals("Exit")) {
						++scopeCount;
					} else if (temp.symbol.equals("Enter")) {
						--scopeCount;
					}

					temp = temp.next;
				}
			}
			if (temp.symbol.equals(_symbol) && temp.type.equals(_type)) {
				//System.out.println("Found Symbol");
				return true;
			}

			temp = temp.next;
		}

		return false;
	}

	//Looup Function (Type)
	//Finds last defined variable with name _symbol
	public String lookup(String _symbol) {
		symbolEntry temp = head;

		while(temp != null) {
			int scopeCount = 0;
			if (temp.symbol.equals("Exit")) {
				++scopeCount;
				temp = temp.next;
				while (scopeCount > 0) {
					if (temp.symbol.equals("Exit")) {
						++scopeCount;
					} else if (temp.symbol.equals("Enter")) {
						--scopeCount;
					}

					temp = temp.next;
				}
			}
			if (temp.symbol.equals(_symbol)) {
				//System.out.println("Found Symbol");
				return temp.type;
			}

			temp = temp.next;
		}

		return null;
	}

	public String getNewName(String _symbol, String _type) {
		symbolEntry temp = head;

		while(temp != null) {
			int scopeCount = 0;
			if (temp.symbol.equals("Exit")) {
				++scopeCount;
				temp = temp.next;
				while (scopeCount > 0) {
					if (temp.symbol.equals("Exit")) {
						++scopeCount;
					} else if (temp.symbol.equals("Enter")) {
						--scopeCount;
					}

					temp = temp.next;
				}
			}
			if (temp.symbol.equals(_symbol) && temp.type.equals(_type)) {
				//System.out.println("Found Symbol");
				return temp.newName;
			}

			temp = temp.next;
		}

		return null;
	}


	//Enter Scope Function
	public void enterScope() {
		symbolEntry temp = new symbolEntry("Enter");
		temp.next = head;
		head = temp;
	}

	//Exit Scope Function
	public void exitScope() {
		symbolEntry temp = new symbolEntry("Exit");
		temp.next = head;
		head = temp;
	}

	public String toString() {
		String ret = "";
		symbolEntry trans = head;

		while (trans != null) {
			ret =  trans.toString() + "\n" + ret;
			trans = trans.next;
		}

		return ret;
	}

	public static void main(String[] args) {
		System.out.println("TESTING OF SYMBOL TABLE CODE");
		System.out.println("============================");

		symbolTable vTable = new symbolTable();
		vTable.bind("x", "Integer");
		vTable.bind("y", "Integer");
		vTable.bind("z", "Integer");
		vTable.bind("a", "String");
		vTable.enterScope();
		vTable.enterScope();
		vTable.bind("x", "String");
		vTable.exitScope();
		vTable.exitScope();

		System.out.println("Printing table after 7 entries: ");
		System.out.println(vTable.toString());

		System.out.println("Finding symbol x (should return Integer)");
		System.out.println(vTable.lookup("x"));

		System.out.println("Finding symbol z of type Integer");
		System.out.println(vTable.lookup("z", "Integer"));
	}
}