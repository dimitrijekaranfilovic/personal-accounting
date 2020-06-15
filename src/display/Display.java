package display;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Display {

    public static String amountDisplay(int amount){
        StringBuilder sb = new StringBuilder();

        sb.append(amount / 100);
        int mod = amount % 100;
        if(mod < 0){
            mod *= -1;
        }
        if(mod > 0 && mod < 10){
            sb.append(".0");
            sb.append(mod);
        }
        else{
            sb.append(".");
            sb.append(mod);
        }
        return sb.toString();
    }

    public static String dateDisplay(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return time.format(formatter);
    }
}
