package fr.carvallegro.stringcalculator;

import static org.junit.Assert.*;

import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringCalculatorTestv2 {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void test() {
		String string = "";

		int sum = StringCalculatorV2.addV2(string);

		Assertions.assertThat(sum).isEqualTo(0);
	}

	@Test
	public void test1() {
		String string = "1";

		int sum = StringCalculatorV2.addV2(string);

		Assertions.assertThat(sum).isEqualTo(1);
	}

	@Test
	public void test2() throws Exception {
		Random r = new Random();
		int randomInt = Math.abs(r.nextInt());
		String string = String.valueOf(randomInt);

		int sum = StringCalculatorV2.addV2(string);

		Assertions.assertThat(sum).isEqualTo(randomInt);
	}

	@Test
	public void testSum() throws Exception {
		String string = "1,2";

		int sum = StringCalculatorV2.addV2(string);

		Assertions.assertThat(sum).isEqualTo(1 + 2);
	}

	@Test
	public void testSum2() throws Exception {
		String string = "2,2";

		int sum = StringCalculatorV2.addV2(string);

		Assertions.assertThat(sum).isEqualTo(2 + 2);
	}

	@Test
	public void testSumThreeNumbers() throws Exception {
		String string = "2,2,4";

		int sum = StringCalculatorV2.addV2(string);

		Assertions.assertThat(sum).isEqualTo(2 + 2 + 4);
	}

	@Test
	public void testSumLineBreak() throws Exception {
		String string = "1\n2";

		int sum = StringCalculatorV2.addV2(string);

		Assertions.assertThat(sum).isEqualTo(1 + 2);
	}

	@Test
	public void testSumLineBreakThreeNumbers() throws Exception {
		String string = "1\n2\n3";

		int sum = StringCalculatorV2.addV2(string);

		Assertions.assertThat(sum).isEqualTo(1 + 2 + 3);
	}
	
	@Test
	public void testSumLineBreakAndCommaThreeNumbers() throws Exception {
		String string = "1\n2,3";
		
		int sum = StringCalculatorV2.addV2(string);
		
		Assertions.assertThat(sum).isEqualTo(1 + 2 + 3);
	}
	
	@Test
	public void testSumLineBreakAndCommaOther() throws Exception {
		String string = "1,2,3\n3";
		
		int sum = StringCalculatorV2.addV2(string);
		
		Assertions.assertThat(sum).isEqualTo(1 + 2 + 3 + 3);
	}
	
	@Test
	public void testCustomDelimiterNoSum() throws Exception {
		String string = "//;\n1;2";
		
		int sum = StringCalculatorV2.addV2(string);
		
		Assertions.assertThat(sum).isEqualTo(1 + 2);
	}
	
	@Test
	public void testCustomDelimiter() throws Exception {
		String string = "//;\n4;5";
		
		int sum = StringCalculatorV2.addV2(string);
		
		Assertions.assertThat(sum).isEqualTo(4 + 5);
	}
	
	@Test
	public void testCustomDelimiter2() throws Exception {
		String string = "//!\n4!5";
		
		int sum = StringCalculatorV2.addV2(string);
		
		Assertions.assertThat(sum).isEqualTo(4 + 5);
	}

	@Test
	public void testCustomDelimiter3() throws Exception {
		String string = "//!\n4!5";
		
		int sum = StringCalculatorV2.addV2(string);
		
		Assertions.assertThat(sum).isEqualTo(4 + 5);
	}
	
	@Test
	public void testNegativeNumbrs() throws Exception {
		String string = "1,-2,3\n3";
		
		expectedException.expect(IllegalArgumentException.class);
		
		int sum = StringCalculatorV2.addV2(string);
	}
	

	
	@Test
	public void testNegativeNumbrsee() throws Exception {
		String string = "1,-2,3\n3";
		
		expectedException.expectMessage("-2");
		
		int sum = StringCalculatorV2.addV2(string);
	}
	
	@Test
	public void testNegativeNumbrseewtf() throws Exception {
		String string = "1,2,-3\n3";
		
		expectedException.expectMessage("-3");
		
		int sum = StringCalculatorV2.addV2(string);
	}
	

	
	@Test
	public void testNegativeNumbrseewtfd() throws Exception {
		String string = "1,-2,-3\n3";
		
		expectedException.expectMessage("-2,-3");
		
		int sum = StringCalculatorV2.addV2(string);
	}
}
