package utility;

public class StringUtility {
    //to replace the .repeat() method as it is not supported in java 8
    public static String repeatString(String str, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(str);
        }
        return result.toString();
    }
} 