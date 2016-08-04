package fr.carvallegro.stringcalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Class for the StringCalculator Kata
 * @see //osherove.com/tdd-kata-1/
 */
public class StringCalculator {
    public int add(String number) throws IOException {
        if (number == null || number.isEmpty()) {
            return 0;
        }

        BufferedReader input = new BufferedReader(new StringReader(number));

        int res = 0;
        String delimiter = ",";
        String line;
        while ((line = input.readLine()) != null) {
            if (line.startsWith("//")) {
                delimiter = line.substring(2);
                continue;
            }
            
            for (String n : line.split(delimiter)) {
                Integer myInt = Integer.valueOf(n);
                
                if(myInt < 0) {
                    throw new NumberFormatException("negatives not allowed" );
                }
                
                res += myInt;
            }
        }
        return res;
    }
}