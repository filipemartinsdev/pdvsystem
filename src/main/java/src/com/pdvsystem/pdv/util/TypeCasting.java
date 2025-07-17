package src.com.pdvsystem.pdv.util;

public class TypeCasting {
    public static int[] toIntArray(Integer[] integerArray){
        int length = integerArray.length;
        int[] intArray = new int[length];

        for(int i = 0; i<length; i++){
            intArray[i] = integerArray[i];
        }

        return intArray;
    }
}
