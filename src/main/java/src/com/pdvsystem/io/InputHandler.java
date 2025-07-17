package src.com.pdvsystem.io;

import src.com.pdvsystem.cashier.PriceChecker;
import src.com.pdvsystem.db.Product;
import src.com.pdvsystem.cashier.Session;
import src.com.pdvsystem.cashier.FrontEndCashier;

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
            if(session.finish()){
                FrontEndCashier.closeSession();
            };
        }
        else if(input.equals("c")){
            session.cancelItem();
        }
        else if(input.equals("m")){
            PriceChecker.init();
        }
        else {
            System.out.println("[ERROR] Invalid function.");
        }
    }

    public static void manager(String input, Session session){
        if (input.isBlank()){
            Product lastProduct = session.getLastProduct();

            if (lastProduct.getUnity().equals("kg")){
                return;
            }

            session.addItem(lastProduct.getId());
            return;
        }

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
            FrontEndCashier.closeApp();
            return;
        }
        else if(input.equals("m")){
            PriceChecker.init();
            return;
        }

        System.out.println("[ERROR] Invalid code.");
    }
}
