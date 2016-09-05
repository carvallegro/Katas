package fr.carvallegro.stringcalculator;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import fr.carvallegro.stringcalculator.StringCalculator;

@RunWith(JUnitQuickcheck.class)
public class StringCalculatorTest {

	private static final String DELIMITER_REGEX = "\\[(.*)\\]*";

	static final String NEGATIVES_EXCEPTION = "negatives not allowed : ";

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private static final StringCalculator sc = new StringCalculator();

	@Test
	public void should_return_0_when_empty_string() throws Exception {
		assertThat(sc.add("")).isEqualTo(0);
	}

	@Test
	public void return_1_when_1() throws Exception {
		assertThat(sc.add("1")).isEqualTo(1);
	}

	@Test
	public void return_2_when_2() throws Exception {
		assertThat(sc.add("2")).isEqualTo(2);
	}

	@Test
	public void return_3_when_1_plus_2() throws Exception {
		assertThat(sc.add("1,2")).isEqualTo(3);
	}

	@Test
	public void return_5_when_2_plus_3() throws Exception {
		assertThat(sc.add("2,3")).isEqualTo(5);
	}

	public void return_correct_value_when_random_values(int... n) throws Exception {
		String param = Arrays.stream(n).mapToObj(n1 -> String.valueOf(n1 > 0 ? n1 : 0))
				.collect(Collectors.joining(","));
		int expected = Arrays.stream(n).sum();

		assertThat(sc.add(param)).isEqualTo(expected);
	}

	@Test
	public void should_return_5_when_2_plus_3_with_newLineFeed() throws Exception {
		assertThat(sc.add("1,2\n3")).isEqualTo(6);
	}

	@Test
	public void should_return_correct_value_when_special_delimiter() throws Exception {
		assertThat(sc.add("//;\n1;3"));
	}

	@Test(expected = NumberFormatException.class)
	public void should_throw_exception_when_param_contains_one_negative() throws Exception {
		sc.add("1,-2");
	}

	@Test
	public void should_contains_right_exception_message() throws Exception {
		exception.expectMessage(NEGATIVES_EXCEPTION + "-2");
		sc.add("1,-2");
	}

	@Test
	public void should_throw_exc_when_negatives_and_custom_delimiter() throws Exception {
		exception.expectMessage(NEGATIVES_EXCEPTION + "-2");
		sc.add("//;\n1;-2;3");
	}

	@Test
	public void should_return_3_when_1_plus_2_plus_1000() throws Exception {
		assertThat(sc.add("1,2,1002")).isEqualTo(3);
	}

	@Test
	public void should_return_correct_value_when_custom_delimiter_is_three_stars() throws Exception {
		assertThat(sc.add("//[***]\n1***2***3")).isEqualTo(6);
	}

	@Test
	public void testName() throws Exception {
		String test = "[***][---][+++]";
		Pattern p = Pattern.compile(DELIMITER_REGEX);

		assertThat(test.matches(DELIMITER_REGEX)).isTrue();
		assertThat(p.matches(DELIMITER_REGEX, test)).isTrue();
		Matcher m = p.matcher(test);
		System.out.println(m.toString());
		String result = String.join(" , ", p.split(test));
		System.out.println("result is : " + result);
	}
}
