package src.com.pdvsystem.cashier;

public enum PaymentMethod {
    MONEY(0, "Dinheiro"),
    DEBIT(1, "Débito"),
    CREDIT(2, "Crédito"),
    PIX(3, "Pix"),
    MANUAL_CREDIT(4, "Crédito manual"),
    INVOICE(5, "Boleto"),
    TRADE_TICKET(6, "Vale troca");

    private final int id;
    private final String details;

    private PaymentMethod(int id, String details){
        this.id = id;
        this.details = details;
    }

    public int getId(){
        return this.id;
    }

    public String getDetails(){
        return this.details;
    }
}
