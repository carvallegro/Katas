package fr.carvallegro.stringcalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

/**
 * Class for the StringCalculator Kata
 * 
 * @see //osherove.com/tdd-kata-1/
 */
public class StringCalculator {
	static final String DEFINE_DELIMITER_PREFIX = "//";
	static final String LINE_BREAK_SPLIT_CHAR = "\n";

	private String delimiter;
	private Set<Integer> negativeNumbers;
	private int sumOfPositivesNumbers;
	private String bufferedLine;

	public int add(String number) throws IOException {
		if (number.isEmpty()) {
			return 0;
		}

		resetMembers();
		BufferedReader input = new BufferedReader(new StringReader(number));
		while ((bufferedLine = input.readLine()) != null) {
			if (hasCustomDelimiter(bufferedLine)) {
				extractLineDelimiter(bufferedLine);
				continue;
			}

			Line line = new Line(bufferedLine);
			line.setCustomDelimiter(delimiter);
			sumOfPositivesNumbers += calculate(line);
			negativeNumbers.addAll(line.getNegativeNumbers());
		}

		if (negativeNumbers.isEmpty()) {
			return sumOfPositivesNumbers;
		}

		String listOfNegatives = getListOfNegativesAsString();
		throw new NumberFormatException("negatives not allowed : " + listOfNegatives);
	}

	private void resetMembers() {
		sumOfPositivesNumbers = 0;
		delimiter = Line.DEFAULT_DELIMITER;
		negativeNumbers = new HashSet<Integer>();
	}

	private boolean hasCustomDelimiter(String line) {
		return line.startsWith(DEFINE_DELIMITER_PREFIX);
	}

	private void extractLineDelimiter(String line) {
		delimiter = line.substring(2);
		if (delimiter.length() > 1) {
			delimiter = delimiter.substring(1, delimiter.length() - 1);
		}
	}

	private String getListOfNegativesAsString() {
		return negativeNumbers.stream().map(n1 -> String.valueOf(n1)).collect(Collectors.joining(","));
	}

	private Integer calculate(Line line) {
		try {
			return line.calculate();
		} catch (NumberFormatException e) {
			System.err.println(String.format("line : [%s]", bufferedLine));
			return 0;
		}
	}

}