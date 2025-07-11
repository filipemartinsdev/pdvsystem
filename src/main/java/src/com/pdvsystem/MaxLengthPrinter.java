package src.com.pdvsystem;

public class MaxLengthPrinter {
    private final int maxLength;

    public MaxLengthPrinter(int max){
        this.maxLength = max;
    }

    public void print(String text){
        char[]textAsCharArr = text.toCharArray();
        int currentSize = 0;

        while(currentSize < maxLength - 3) {
            System.out.print(textAsCharArr[currentSize]);
            currentSize++;
        }

        System.out.println("...");
    }

    public String get(String text){
        if(text.length()<=this.maxLength){
            return text;
        }
        char[]textAsCharArr = text.toCharArray();
        char[]outAsCharArr;

        if(text.length()>=this.maxLength){
            outAsCharArr = new char[text.length()-(text.length()-this.maxLength)-3];
        } else {
            outAsCharArr = new char[text.length()-(this.maxLength-text.length())-3];
        }

        int currentSize = 0;

        while(currentSize < maxLength - 3) {
            outAsCharArr[currentSize] = textAsCharArr[currentSize];
            currentSize++;
        }
        return String.copyValueOf(outAsCharArr)+"...";
    }
}