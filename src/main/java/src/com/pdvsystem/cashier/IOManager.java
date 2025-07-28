package src.com.pdvsystem.cashier;

import src.com.pdvsystem.db.Product;
import src.com.pdvsystem.db.ProductRepository;
import src.com.pdvsystem.db.ProductRepositoryImpl;
import src.com.pdvsystem.io.InputHandler;
import src.com.pdvsystem.io.InputManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class IOManager {
    public static void inputManager(String input){
//        If input == BLANK: Increment the last added product
        if (input.isBlank()){
            if (!CashierApp.isSessionOn()){
                return;
            }

            Product lastProduct = CashierApp.getCurrentSession().getLastProduct();

//            return IF unity lastProduct == KG
            if (lastProduct.getUnity().equals("kg")){
                return;
            }

//            increment lastProduct QTD
            CashierApp.getCurrentSession().addItem(lastProduct.getId(), 1);
            return;
        }

//        Check if input == productID
        if(InputHandler.strIsLong(input)){
            ProductRepository productRepository = new ProductRepositoryImpl();
            Product product = productRepository.getProductById(Long.parseLong(input));

//            Check if product exists
            if (product==null){
                System.out.println("[ERROR] Código inválido");
                return;
            }

//            Init new Session if its have not
            if (CashierApp.getCurrentSession()==null){
                CashierApp.initNewSession();
            }

            CashierApp.getCurrentSession().addItem(Long.parseLong(input), 1);
            return;
        }

        if(inputIsMultiplyFunction(input)){
            Map<Long, Float> productQtdMap = getMultiplyFunctionMap(input);
            long productId = 0;

//            check IF the product exists
            if (productQtdMap!=null){

//                check IF is the first product of Session, so run new session
                if(CashierApp.getCurrentSession()==null){
                    CashierApp.initNewSession();
                }

                for(long id : productQtdMap.keySet()){
                    productId = id;
                }

                CashierApp.getCurrentSession().addItem(productId, productQtdMap.get(productId));
            }
            return;
        }

//        Check if input == user function
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

        if (input.equals(InputFunction.FINISH_SESSION.getInputKey())){
//            CHECK IF IT HAS NOT A SESSION ON
            if (CashierApp.getCurrentSession()==null){
                if (IOManager.confirmCloseApp()){
                    CashierApp.closeApp();
                }
            }

//            IF IT HAS AN OPEN SESSION
            else {
                if(CashierApp.getCurrentSession().finish()) {
                    CashierApp.closeSession();
                }
            }
        }

        else if(input.equals(InputFunction.CANCEL_ITEM.getInputKey())){
            if (!CashierApp.isSessionOn()){
                System.out.println("Nenhuma sessão iniciada.");
                return;
            }
            CashierApp.getCurrentSession().cancelItem();
        }

        else if(input.equals(InputFunction.PRICE_CHECKER.getInputKey())){
            PriceChecker.init();
        }
        else {
            System.out.println("[ERROR] Invalid function.");
        }
    }

    private static boolean confirmCloseApp(){
        System.out.println("    Confirmar saída?");
        System.out.println("    [1] Sim");
        System.out.println("    [2] Não");

        String input = InputManager.readString("  >> ");

        if (input.equals("1")){
            return true;
        }

        else if (input.equals("2")){
            return false;
        }

        else {
            System.out.println("[ERROR] Opção inválida");
            return false;
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