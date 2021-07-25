package util;


/**
 * Class that has static util methods for parsing and formatting.
 * @author Dimitrije Karanfilovic
 * @since 25.07.2021.
 * */
public class Util {


    public static String formatStringForQuery(String s){
        return s.replace("!", "!!")
                .replace("%", "%%")
                .replace("_", "!_")
                .replace("[", "![");
    }

    public static int parseString(String s){
        int amountNum = 0;
        String[] tokens = s.split("\\.");
        amountNum += 100 * Integer.parseInt(tokens[0]);
        if(tokens.length == 2){
            String parts = tokens[1];
            if(parts.length() == 1){
                amountNum += Integer.parseInt(parts) * 10;
            }
            else if(parts.length() == 2){
                amountNum += Integer.parseInt(parts);
            }
        }
        return amountNum;
    }

    /**
     * checks whether the given string is a number
     * @param s string to be checked
     * @return indicator whether a given string is a number
     * */
    public static boolean isNumeric(String s){
        try{
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * checks whether the given string is  not a number
     * @param s string to be checked
     * @return indicator whether a given string is not a number
     * */
    public static boolean isNotNumeric(String s){
        return !isNumeric(s);
    }

}
