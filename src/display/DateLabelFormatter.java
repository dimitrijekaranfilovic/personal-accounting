package display;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Class which formats date selected in JDatePickerImpl
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */
public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    /**
     * pattern to be used when formatting and parsing
     * */
    private String datePattern = "dd.MM.yyyy.";

    /**
     * formatter
     * */
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Function that parses date's string representation
     * @param text text to be parsed
     * @return pared date
     * */
    @Override
    public Object stringToValue(String text) throws ParseException {
             return dateFormatter.parseObject(text);
    }

    /**
     * Function that formats date into string output
     * @param value date to be formatted
     * @return formatted date, if date is not null, else it returns empty string
     * */
    @Override
    public String valueToString(Object value) {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}

