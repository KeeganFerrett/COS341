class syntaxTreeList {
	public class syntaxToken {
		private String input = "";
		private String descrp = "";
		private String type = "";
		private String id = "";
		public syntaxToken next = null;

		public syntaxToken(String _input, String _descrp, String _type, String _id) {
			input = _input;
			descrp = _descrp;
			type = _type;
			id = _id;
		}

		public String toString() {
			String temp = "[" + input + "," + descrp + "," + type + "," + id + "]";
			return temp;
		}
	}

	private int idCounter;
	private syntaxTreeList head;

	public syntaxTreeList() {
		idCounter = 0;
		head = null;
	}

	public void addNode(String _input, String _descrp, String _type) {
		String id = String.format("%08d", tokenID++);
		syntaxToken temp = new syntaxToken(_input, _descrp, _type, id);

		if (head == null) {
			head = temp;
			return;
		}

		syntaxToken trans = head;
		while(trans.next != null) {
			trans = trans.next;
		}
		trans.next = temp;
	}
}