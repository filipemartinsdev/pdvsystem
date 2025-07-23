package src.com.pdvsystem.cashier;

import src.com.pdvsystem.db.Product;
import src.com.pdvsystem.io.InputHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class IOManager {
    public static void inputManager(String input){
        if (input.isBlank()){
            if (!FrontEndCashier.isSessionOn()){
                return;
            }

            Product lastProduct = FrontEndCashier.getCurrentSession().getLastProduct();

//            return IF unity lastProduct == KG
            if (lastProduct.getUnity().equals("kg")){
                return;
            }

//            increment lastProduct QTD
            FrontEndCashier.getCurrentSession().addItem(lastProduct.getId(), 1);
            return;
        }

        if(InputHandler.strIsLong(input)){
            FrontEndCashier.getCurrentSession().addItem(Long.parseLong(input), 1);
            return;
        }

        if(inputIsMultiplyFunction(input)){
            Map<Long, Float> productQtdMap = getMultiplyFunctionMap(input);
            long productId = 0;

//            check IF the product exists
            if (productQtdMap!=null){

//                check IF is the first product of Session, so init new session
                if(FrontEndCashier.getCurrentSession()==null){
                    FrontEndCashier.initNewSession();
                }

                for(long id : productQtdMap.keySet()){
                    productId = id;
                }

                FrontEndCashier.getCurrentSession().addItem(productId, productQtdMap.get(productId));
            }
            return;
        }

        else if(InputHandler.strHasOnlyText(input)){
            functionInputHandler(input);
            return;
        }

        System.out.println("[ERROR] Invalid input");
    }

    private static void functionInputHandler(String input){
        if (!InputHandler.strIsOneChar(input)){
            return;
        }

        if (input.equals("f")){
            if (FrontEndCashier.getCurrentSession()!=null){
                if(FrontEndCashier.getCurrentSession().finish()) {
                    FrontEndCashier.closeSession();
                }
            }

            else {
                FrontEndCashier.closeSession();
            }
        }

        else if(input.equals("c")){
            if (!FrontEndCashier.isSessionOn()){
                System.out.println("Nenhuma sessão iniciada.");
                return;
            }
            FrontEndCashier.getCurrentSession().cancelItem();
        }

        else if(input.equals("m")){
            PriceChecker.init();
        }
        else {
            System.out.println("[ERROR] Invalid function.");
        }
    }

//    check if input has the pattern "NxID"
//    where N == any number
//    AND ID == productCode

    private static boolean inputIsMultiplyFunction(String input){
        Pattern pattern = Pattern.compile("^[0-9.]+x[0-9]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

//    input:  "0.500x445"
//    out:    {445, 0.500}
//    out = Map<Long, Float>
//             <code,   qtd>
    private static Map<Long, Float> getMultiplyFunctionMap(String input){
//        INT multiply pattern
//        ex:   10x445
//        used to standard product multiply
        Pattern patternInt = Pattern.compile("^[0-9]+x[0-9]+$");

//        FLOAT multiply pattern
//        ex:   0.500x445
//        used to KG weight
        Pattern patternFloat = Pattern.compile("^[0-9]+\\.[0-9]+x[0-9]+$");

        Matcher matcherInt = patternInt.matcher(input);
        Matcher matcherFloat = patternFloat.matcher(input);

        Map<Long, Float> out = null;


//        IF multiply == INT
        if(matcherInt.find()){
//            Product Code
            long outLong;
            StringBuilder strLong = new StringBuilder();

//            Product QTD
            int outInt;
            StringBuilder strInt = new StringBuilder();


            int i = 0;
            char[] inputAsCharArr = input.toCharArray();
            char c = inputAsCharArr[i];

//            GET the productQTD in input
            while(c != 'x'){
                strInt.append(c);
                i++;
                c=inputAsCharArr[i];
            }
            i++; // skip the char 'x'

//            GET the productCode in input
            while(i<inputAsCharArr.length){
                c=inputAsCharArr[i];
                strLong.append(c);
                i++;
            }

            out = new HashMap<>();
            out.put(
                    Long.parseLong(strLong.toString()),
                    Float.parseFloat(Integer.parseInt(strInt.toString())+"")
            );
        }

//        IF multiply == FLOAT ( Kg )
        else if(matcherFloat.find()){

//            Product Code
            long outLong;
            StringBuilder strLong = new StringBuilder();

//            Product QTD
            int outFloat;
            StringBuilder strFloat = new StringBuilder();


            char[] inputAsCharArr = input.toCharArray();
            int i = 0;
            char c = inputAsCharArr[i];

//            GET the productQTD in input
            while(c != 'x'){
                strFloat.append(c);
                i++;
                c=inputAsCharArr[i];
            };
            i++; // skip the char 'x'

//            GET the productCode in input
            do {
                c=inputAsCharArr[i];
                strLong.append(c);
                i++;
            }
            while(i<inputAsCharArr.length);

            out = new HashMap<>();
            out.put(
                    Long.parseLong(strLong.toString()),
                    Float.parseFloat(strFloat.toString())
            );
        }

        return out;
    }
}
