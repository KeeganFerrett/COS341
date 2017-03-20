class tokenList {

	class token {
		private String input = "";
		private String inputType = "";
		private String tokenID = "";
		public token next = null;

		public token(String _input, String _inputType, String _tokenID) {
			input = _input;
			inputType = _inputType;
			tokenID = _tokenID;
		}

		public String toString() {
			String temp = "[" + input + "," + inputType + "," + tokenID + "]";
			return temp;
		}
	}
	int tokenID;
	token head;

	public tokenList() {
		tokenID = 0;
		head = null;
	}

	public void addToken(String _input, String _inputType) {
		String id = String.format("%08d", tokenID++);

		token temp = new token(_input, _inputType, id);

		if (head  == null) {
			head = temp;
			return;
		}

		token trans = head;

		while (trans.next != null) {
			trans = trans.next;
		}

		trans.next = temp;
	}

	public String toString() {
		String returnVal = "";

		token trans = head;

		while (trans != null) {
			returnVal = returnVal + trans.toString() + "\n";
			trans = trans.next;
		}

		return returnVal;
	}

	public static void main(String[] args) {
		tokenList tokens = new tokenList();

		tokens.addToken("varas22", "Variable");
		tokens.addToken("518433", "Integer");
		tokens.addToken("FuckME", "String");

		String print = tokens.toString();
		System.out.println(print);
	}
}