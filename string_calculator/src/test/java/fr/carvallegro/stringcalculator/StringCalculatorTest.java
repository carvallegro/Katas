package fr.carvallegro.stringcalculator;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import fr.carvallegro.stringcalculator.StringCalculator;

@RunWith(JUnitQuickcheck.class)
public class StringCalculatorTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    private static final StringCalculator sc = new StringCalculator();

    @Test
    public void should_return_0_when_empty_string() throws Exception {
        Assertions.assertThat(sc.add("")).isEqualTo(0);
    }

    @Test
    public void return_0_whe_null() throws Exception {
        Assertions.assertThat(sc.add(null)).isEqualTo(0);
    }

    @Test
    public void return_1_when_1() throws Exception {
        Assertions.assertThat(sc.add("1")).isEqualTo(1);
    }

    @Test
    public void return_2_when_2() throws Exception {
        Assertions.assertThat(sc.add("2")).isEqualTo(2);
    }

    @Test
    public void return_3_when_1_plus_2() throws Exception {
        Assertions.assertThat(sc.add("1,2")).isEqualTo(3);
    }

    @Test
    public void return_5_when_2_plus_3() throws Exception {
        Assertions.assertThat(sc.add("2,3")).isEqualTo(5);
    }

    public void return_correct_value_when_random_values(int... n) throws Exception {
        String param = Arrays.stream(n).mapToObj(n1 -> String.valueOf(n1 > 0 ? n1 : 0)).collect(Collectors.joining(","));
        int expected = Arrays.stream(n).sum();
        
        Assertions.assertThat(sc.add(param)).isEqualTo(expected);
    }
    
    @Test
    public void should_return_5_when_2_plus_3_with_newLineFeed() throws Exception {
        Assertions.assertThat(sc.add("1,2\n3")).isEqualTo(6);
    }
    
    @Test
    public void should_return_correct_value_when_special_delimiter() throws Exception {
        Assertions.assertThat(sc.add("//;\n1;3"));
    }
    
    @Test(expected=NumberFormatException.class)
    public void should_throw_exception_when_param_contains_one_negative() throws Exception {
        sc.add("1,-2");        
    }
    
    @Test
    public void should_contains_right_exception_message() throws Exception {
        exception.expectMessage("negatives not allowed");
        sc.add("1,-2");
    }
}
