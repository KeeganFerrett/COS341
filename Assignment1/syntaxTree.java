class syntaxTree {
	private int idCounter;
	private syntaxNode head;

	public syntaxTree() {
		idCounter = 0;
		head = null;
	}

	public syntaxNode geatHead() {
		return head;
	}

	public void addNode(String _input, String _descrp, String _type, int _children) {
		String id = String.format("%08d", idCounter++);
		syntaxNode temp = new syntaxNode(_input, _descrp, _type, id, _children);
		//System.out.println(temp.toString());
		if (head == null) {
			head = temp;
			return;
		}

		boolean p = enterIntoTree(head, temp);

		if (!p) {
			//System.out.println(temp.toString());
			//System.out.println("Could not add node to tree!!");
			//System.exit(0);
		}
	}

	private boolean enterIntoTree(syntaxNode start, syntaxNode _temp) {
		//System.out.println("Entered Node: " + start.getInput() + ", " + start.getID());
		for (int x = 0; x < start.next.length; ++x) {
			if (start.next[x] == null) {
				start.next[x] = _temp;
				return true;
			} else {
				boolean p = enterIntoTree(start.next[x], _temp);
				if (p) {
					return true;
				}
			}
		}

		return false;
	}

	public void printTreeBreath() {
		printNode(head);
	}

	private void printNode(syntaxNode _node) {
		System.out.println("Current Node: " + _node.getInput() + ", " + _node.getID());

		if (_node.next.length == 0) {
			System.out.println("Children:[]");
			return;
		}

		System.out.print("Children: [");
		for (int x = 0; x < _node.next.length; ++x) {
			if (_node.next[x] != null) {
				System.out.print(_node.next[x].getInput() + ",");
			} else {
				System.out.print("NULL,");
			}
		}
		System.out.print("\b]");
		System.out.println();

		for (int x = 0; x < _node.next.length; ++x) {
			if (_node.next[x] != null) {
				printNode(_node.next[x]);

			} 
		}
	}

	public String getDepthFirst() {
		String ret = diveDeep(head);
		
		return ret;
	}

	private String diveDeep(syntaxNode _node) {
		if (_node == null) {
			return "NULL\n";
		}

		String ret = _node.toString();

		for (int x = 0; x < _node.next.length; ++x) {
			ret = ret + diveDeep(_node.next[x]);
		}

		return ret;
	}

	public static void main(String[] args) {
		syntaxTree realTree = new syntaxTree();

		realTree.addNode("Prog", "Non-Terminal", "-", 2);
		realTree.addNode("Code", "Non-Terminal", "-", 2);
		realTree.addNode("Instr", "Non-Terminal", "-", 1);
		realTree.addNode("IO", "Non-Terminal", "-", 2);
		realTree.addNode("input", "Terminal", "-", 0);
		realTree.addNode("a", "Terminal", "-", 0);
		realTree.addNode("Code", "Non-Terminal", "-", 2);
		realTree.addNode("Code", "Non-Terminal", "-", 2);
		realTree.addNode("Instr", "Non-Terminal", "-", 1);
		realTree.addNode("procedure12", "Terminal", "-", 0);
		realTree.addNode("Code", "Non-Terminal", "-", 0);
		realTree.addNode("Code", "Non-Terminal", "-", 0);
		realTree.addNode("$", "Terminal", "-", 0);

		//realTree.printTreeBreath();
		String output = realTree.getDepthFirst();
		System.out.println(output);
	}
}