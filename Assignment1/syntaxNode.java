class syntaxNode {
		private String input = "";
		private String descrp = "";
		private String nodeType = "";
		private String id = "";
		private char type = '-';
		public syntaxNode next[];

		public syntaxNode(String _input, String _descrp, String _type, String _id, int _children) {
			input = _input;
			descrp = _descrp;
			nodeType = _type;
			id = _id;
			next = new syntaxNode[_children];
			for (int x = 0; x < _children; ++x) {
				next[x] = null;
			}
		}

		public String toString() {
			String temp = "[" + input + "," + descrp + "," + nodeType + "," + type + "," + next.length + "," + id + "]\n";
			return temp;
		}

		public void updateType(char _type) {
			type = _type;
		}

		public char getType() {
			return type;
		}

		public String getInput() {
			return input;
		}

		public String getID() {
			return id;
		}
	}