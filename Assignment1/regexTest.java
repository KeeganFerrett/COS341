//u15132847 and u15011713
//Keegan Ferrett and Nikki Constancon
import java.util.regex.*;

class regexTest {

	//Variables
	private Pattern p;
	private String regex;
	private String type;

	//Functions
	public regexTest(String _regex, String _type) {
		regex = _regex;
		p = Pattern.compile(regex);
		type= _type;
	}

	public String returnPattern() {
		return regex;
	}

	public String testString(String _test) {
		Matcher m = p.matcher(_test);
		boolean b = m.matches();
		if (b) {
			return type;
		} else
			return null;
	}

	public static void main(String[] args) {
		String testVar;
		if (args.length > 0) {
			testVar = args[0];
		} else {
			testVar = "\"az_12	AZ\"";
		}

		regexTest t = new regexTest("-?[0-9]*\\.[0-9]*", "test");
		if (t.testString(testVar) != null) {
			System.out.println(testVar + " is a match.");
		} else {
			System.out.println("ERORR: " + testVar + " is not a match.");
		}
	}
	
}
