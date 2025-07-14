package src.com.pdvsystem;

public class Payment {
    public static void manager(int inputOption){
        mock();
    }

    public static void debitPayment(){}

    public static void creditPayment(){}

    public static void pixPayment(){}

    public static void manualCreditPayment(){}

    public static void nfPayment(){}

    public static void tradeTicketPayment(){}

    public static void mock(){
        System.out.println("Simulando pagamento...");
        System.out.println("Tecle enter");
        App.scan.nextLine();
    }

    public static void printOptions(){
        System.out.println("Qual a forma de pagamento?");
        System.out.print(
                "   [1] Debito\n"+
                "   [2] Credito\n"+
                "   [3] Pix\n"+
                "   [4] Credito manul\n"+
                "   [5] Boleto\n"+
                "   [6] Vale troca\n"
        );
    }
}
