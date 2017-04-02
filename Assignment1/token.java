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

	public String getType() {
		return inputType;
	}

	public String getInput() {
		return input;
	}
}