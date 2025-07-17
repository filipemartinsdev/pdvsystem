package src.com.pdvsystem.io;

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
        char[]textAsCharArr;
        char[]outAsCharArr;

        if(text.length()>=this.maxLength){
            textAsCharArr = text.toCharArray();
            outAsCharArr = new char[text.length()-(text.length()-this.maxLength)-3];
        } else {
            return text;
//            outAsCharArr = new char[text.length()-(this.maxLength-text.length())-3];
        }

        int currentSize = 0;

        while(currentSize < maxLength - 3) {
            outAsCharArr[currentSize] = textAsCharArr[currentSize];
            currentSize++;
        }
        return String.copyValueOf(outAsCharArr)+"...";
    }
}