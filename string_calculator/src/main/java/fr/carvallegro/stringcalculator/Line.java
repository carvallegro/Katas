package fr.carvallegro.stringcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Line {

	static final int MAX_VALUE_TO_ADD = 1000;
	static final String DEFAULT_DELIMITER = ",";

	private String line;
	private String delimiter;
	private List<String> numbersToAdd;
	private Set<Integer> negativeNumbers;

	public Set<Integer> getNegativeNumbers() {
		return Optional.of(negativeNumbers).orElse(Collections.emptySet());
	}

	public Line(String line) {
		this.line = Optional.of(line).orElse("");
		this.delimiter = DEFAULT_DELIMITER;
	}

	String getLine() {
		return line;
	}

	public Integer calculate() {
		if (line.isEmpty()) {
			return 0;
		}

		replaceDelimiter();
		produceNumbersToAdd();
		checkForNegativesNumbers();

		return numbersToAdd.stream().mapToInt(s -> Integer.valueOf(s)).filter(i -> i < Line.MAX_VALUE_TO_ADD).sum();
	}

	private void replaceDelimiter() {
		line = line.replace(delimiter, DEFAULT_DELIMITER);
	}

	private void produceNumbersToAdd() {
		numbersToAdd = Arrays.asList(line.split(DEFAULT_DELIMITER));
	}

	private void checkForNegativesNumbers() {
		extractNegativeNumbers();
		if (!negativeNumbers.isEmpty()) {
			String listOfNegatives = negativeNumbers.stream().map(n1 -> String.valueOf(n1))
					.collect(Collectors.joining(","));
			throw new NumberFormatException("negatives not allowed : " + listOfNegatives);
		}
	}

	private void extractNegativeNumbers() {
		negativeNumbers = numbersToAdd.stream().mapToInt(s -> Integer.valueOf(s)).filter(i -> i < 0).boxed()
				.collect(Collectors.toSet());
	}

	public void setCustomDelimiter(String delimiter) {
		this.delimiter = Optional.of(delimiter).orElse(DEFAULT_DELIMITER);
	}

}
