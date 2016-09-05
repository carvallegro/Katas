package fr.carvallegro.stringcalculator;

import java.util.Arrays;
import java.util.List;

public class StringCalculatorV2 {
	static final String DEFINE_DELIMITER_PREFIX = "//";
	static final String LINE_BREAK_SPLIT_CHAR = "\n";
	static final String DEFAULT_SPLIT_CHAR = ",";
	
	public static int addV2(String string) {
		if(string.contains("-")){
			throw new IllegalArgumentException("-"+string.charAt(string.indexOf("-")+1));
		}
		
		if (string.isEmpty()) {
			return 0;
		}
		
		
		String delimiter = LINE_BREAK_SPLIT_CHAR;
		if (string.startsWith(DEFINE_DELIMITER_PREFIX)) {
			delimiter = string.substring(2,3);
			string = string.substring(4);
		}
		
		string = string.replace(delimiter, DEFAULT_SPLIT_CHAR);
		List<String> numbers = Arrays.asList(string.split(DEFAULT_SPLIT_CHAR));
		return numbers.stream().mapToInt(number -> Integer.valueOf(number)).sum();
	}

}
