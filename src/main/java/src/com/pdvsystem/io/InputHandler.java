package src.com.pdvsystem.io;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class InputHandler {
//    public static Map<Char, Functions> functionsMap;
//
//    static {
//        functionsMap = new HashMap<>();
//    }

//    INPUT HANDLER METHODS

    public static boolean strIsLong(String input){
        if (input.isBlank()) return false;

        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(input);
        return !matcher.find();
    }

    public static boolean strIsFloat(String input){
        Pattern pattern = Pattern.compile("^[0-9]+\\.[0-9]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean strHasOnlyText(String input){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(input);
        return !matcher.find();
    }

    public static boolean strIsOneChar(String input){
        Pattern pattern = Pattern.compile("[a-zA-Z\s]");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() && input.length()==1;
    }
}
