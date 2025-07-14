package src.com.pdvsystem;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class InputHandler {
//    public static Map<Char, Functions> functionsMap;
//
//    static {
//        functionsMap = new HashMap<>();
//    }

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

    public static void functionInputHandler(String input, Session session){
        if (!strIsOneChar(input)){
            return;
        }

        if (input.equals("f")){
            session.finish();
            App.closeSession();
        }
        else if(input.equals("c")){
            session.cancelItem();
        }
        else {
            System.out.println("[ERROR] Invalid function.");
        }
    }

    public static void manager(String input, Session session){
        if(strIsLong(input)){
            session.addItem(Long.parseLong(input));
            return;
        }
        else if(strHasOnlyText(input)){
            functionInputHandler(input, session);
            return;
        }
        System.out.println("[ERROR] Invalid input");
    }

    public static void homeManager(String input){
        if(input.equals("f")){
            App.closeApp();
            return;
        }

        System.out.println("[ERROR] Invalid code.");
    }
}
