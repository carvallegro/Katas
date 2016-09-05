package fr.carvallegro.stringcalculator;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LineTest {

	private Line line;
	
	@Rule
    public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void calculate_should_return_0_when_emptyLine() throws Exception {
		// Given
		initializeLine("");
		
		// Then
		assertThat(line.calculate()).isEqualTo(0);
	}
	
	@Test
	public void calculate_should_return_3_when_1_and_2() throws Exception {
		// Given
		initializeLine("1,2");

		// Then
		assertThat(line.calculate()).isEqualTo(3);

	}

	@Test
	public void calculate_should_return_9_when_2_3_4_and_custom_delimiter() throws Exception {
		// Given 
		initializeLine("2,3;4");
		
		// When
		line.setCustomDelimiter(";");
		
		// Then
		assertThat(line.calculate()).isEqualTo(9);
	}

	@Test
	public void calculateLine_should_throw_NumberFormatException_when_negative_numbers() throws Exception {
		// Given
		initializeLine("-10");
		
		// Then
		exception.expectMessage(StringCalculatorTest.NEGATIVES_EXCEPTION + "-10");
		line.calculate();
	}
	
	@Test
	public void calculateLine_should_return_2_when_2_plus_1002() throws Exception {
		// Given
		initializeLine("2,1002");
		
		// Then
		assertThat(line.calculate()).isEqualTo(2);
	}
	
	private void initializeLine(String stringLine) {
		line = new Line(stringLine);
	}

}
