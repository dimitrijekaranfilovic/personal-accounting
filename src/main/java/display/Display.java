package display;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class used for formatting various outputs.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class Display {

    /**
     * Function that formats amount(amount is internally saved as an integer and this displays parts
     * before and after the decimal point)
     * @param amount amount to be parsed
     * @return amount's string representation
     * */
    public static String amountDisplay(int amount){
        StringBuilder sb = new StringBuilder();
        sb.append(amount / 100);
        int mod = amount % 100;
        if(mod < 0){
            mod *= -1;
        }
        if(mod > 0 && mod < 10){
            sb.append(".0");
        }
        else{
            sb.append(".");
        }
        sb.append(mod);
        return sb.toString();
    }

    /**
     * Function that formats LocalDateTime.
     * @param time time to be formatted
     * @return date's string representation
     * */

    public static String dateDisplay(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        return time.format(formatter);
    }

    /**
     * Displays day, month and year(hours and minutes are omitted)
     * @param time time to be formatted
     * @return date's string representation
     * */
    public static String shortenedDisplay(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return time.format(formatter);
    }
}