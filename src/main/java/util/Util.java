package util;

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
}
