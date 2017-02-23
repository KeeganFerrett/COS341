import java.util.regex.*;
import java.util.*;

class regexList {
	//Varaibles
	regexTest stringPattern;
	regexTest charPattern;
	regexTest decimalPattern;
	regexTest floatPattern;
	regexTest booleanPattern;

	regexTest userDefined;
	ArrayList<regexTest> keyWords;

	public regexList() {
		//Variables
		stringPattern = new regexTest("\"[\\W\\w]{0,8}\"", "Short string");
		decimalPattern = new regexTest("-?[1-9][0-9]*","Integer");
		//Kinda just wanted to do these for shits gigs
		charPattern = new regexTest("\"[\\W\\w]\"", "Character");
		booleanPattern = new regexTest("(true|false|1|0)", "Boolean");
		floatPattern = new regexTest("-?[0-9]*\\.[0-9]*", "Float");
		userDefined = new regexTest("[a-z][a-z0-9]*","User-defined Name");
		//Key words list
		keyWords = new ArrayList<regexTest>();
	}

	public boolean addReservedWord(String _regex, String type) {
		regexTest temp = new regexTest(_regex, type);
		return keyWords.add(temp);
	}

	public String testString(String _test) {
		String type;
		//First Testing for keywords
		for (int x = 0;  x < keyWords.size(); ++x) {
			regexTest temp = keyWords.get(x);
			type = temp.testString(_test);
			if (type != null) {
				System.out.println(type);
				return type;
			}
		}

		//Then test for boolean even though will probably have to delete this later
		type = stringPattern.testString(_test);
		if (type != null) {
			System.out.println(type);
			return type;
		}

		//Testing for integer
		type = decimalPattern.testString(_test);
		if (type != null) {
			System.out.println(type);
			return type;
		}

		//Testing for char
		type = charPattern.testString(_test);
		if (type != null) {
			System.out.println(type);
			return type;
		}

		//Testing for boolean
		type = booleanPattern.testString(_test);
		if (type != null) {
			System.out.println(type);
			return type;
		}

		type = floatPattern.testString(_test);
		if (type != null) {
			System.out.println(type);
			return type;
		}

		type = userDefined.testString(_test);
		if (type != null) {
			System.out.println(type);
			return type;
		}

		//Tested everything and can't find a match for the token so time to give up
		return null;
	}
}