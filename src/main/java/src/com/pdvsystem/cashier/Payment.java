package src.com.pdvsystem.cashier;

import src.com.pdvsystem.io.InputHandler;
import src.com.pdvsystem.io.InputManager;

public class Payment {
    private final float SALE_VALUE;
    private boolean isComplete = false;

    public Payment(float value){
        this.SALE_VALUE = value;
    }

    public void init(){
        System.out.println(">> Tecle ENTER para voltar <<");
        printOptions();
        String input = InputManager.readString(">> ");

        if (input.isBlank()){return;}

        if (!InputHandler.strIsLong(input)){
            System.out.println("[ERROR] Opção inválida.");
            return;
        }

        int inputInt = Integer.parseInt(input);

        paymentInputManager(inputInt);
    }

    private void paymentInputManager(int option){
        PaymentMethod inputMethod = null;

//        GET PAYMENT METHOD
        for (PaymentMethod method : PaymentMethod.values()){
            if (method.getId() == option) {
                inputMethod = method;
            }
        }

        if (inputMethod == null){
            System.out.println("[ERROR] Opção inválida.");
            return;
        }

        paymentMethodHandler(inputMethod);
    }


    private void paymentMethodHandler(PaymentMethod option){
        if (option == PaymentMethod.MONEY) {
            initPaymentMoney();
        }

        else if(option == PaymentMethod.DEBIT){
            initPaymentDebit();
        }

        else if(option == PaymentMethod.CREDIT){
            initPaymentCredit();
        }

        else if(option == PaymentMethod.PIX){
            initPaymentPix();
        }

        else if(option == PaymentMethod.MANUAL_CREDIT){
            initPaymentManualCredit();
        }

        else if(option == PaymentMethod.INVOICE){
            initPaymentInvoice();
        }

        else if(option == PaymentMethod.TRADE_TICKET){
            initPaymentTradeTicket();
        }
    }

    private void printOptions(){
        System.out.println("Qual a forma de pagamento?");
        for (PaymentMethod method : PaymentMethod.values()){
            System.out.printf("   [%d] %s\n", method.getId(), method.getDetails());
        }
    }


    private void initPaymentMoney(){
        float enterValue = 0;
        String input;

        while(enterValue < this.SALE_VALUE) {
            input = InputManager.readString(">> Valor de entrada: ");

//            CHECAGEM DE TIPO
            if(!InputHandler.strIsFloat(input)) {
                System.out.println("[ERROR] Entrada inválida.");
                continue;
            }
            else {
                enterValue += Integer.parseInt(input);
            }

            if (enterValue<this.SALE_VALUE) {
                System.out.println("Entrada atual = "+enterValue);
            }
        }

        System.out.println("\nAGUARDE...\n");

        System.out.printf("TROCO = R$%.2f\n", enterValue - this.SALE_VALUE);

        this.isComplete = true;
    }

    private void initPaymentDebit(){
        System.out.println("Simulando pagamento debito...");
        System.out.println(">> Tecle ENTER para continuar <<");
        InputManager.readString("");

        this.isComplete = true;
    }

    private void initPaymentCredit(){
        System.out.println("Simulando pagamento credito...");
        System.out.println(">> Tecle ENTER para continuar <<");
        InputManager.readString("");

        this.isComplete = true;

    }

    private void initPaymentPix(){
        System.out.println("Simulando pagamento PIX...");
        System.out.println(">> Tecle ENTER para continuar <<");
        InputManager.readString("");

        this.isComplete = true;

    }

    private void initPaymentManualCredit(){
        System.out.println("Simulando pagamento credito manual...");
        System.out.println(">> Tecle ENTER para continuar <<");
        InputManager.readString("");

        this.isComplete = true;
    }

    private void initPaymentInvoice(){
        System.out.println("Simulando pagamento BOLETO...");
        System.out.println(">> Tecle ENTER para continuar <<");
        InputManager.readString("");

        this.isComplete = true;
    }

    private void initPaymentTradeTicket(){
        System.out.println("Simulando pagamento VALE TROCA...");
        System.out.println(">> Tecle ENTER para continuar <<");
        InputManager.readString("");

        this.isComplete = true;
    }

    public boolean isComplete() {
        return this.isComplete;
    }
}
