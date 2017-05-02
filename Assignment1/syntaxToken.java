public class syntaxToken {
	public String input = "";
	private String descrp = "";
	public String type = "";
	public String formed = "-";
	private String id = "";
	public syntaxToken next = null;
	public syntaxToken(String _input, String _descrp, String _type, String _id) {
		input = _input;
		descrp = _descrp;
		type = _type;
		id = _id;
	}
	public String toString() {
		String temp = "[" + input + "," + descrp + "," + type + "," + formed + "," + id + "]\n";
		return temp;
	}
}