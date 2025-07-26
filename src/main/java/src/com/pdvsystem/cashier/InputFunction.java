package src.com.pdvsystem.cashier;

public enum InputFunction {
    FINISH_SESSION("f"),
    CANCEL_ITEM("c"),
    PRICE_CHECKER("m");

    private final String inputKey;

    InputFunction(String value){
        this.inputKey = value;
    }


    public String getInputKey(){
        return this.inputKey;
    }
}
