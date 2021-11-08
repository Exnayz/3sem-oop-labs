import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Block_4 {
    public static void main (String[] args){
        System.out.println("Первое задание: \t\n" + refactor(10, 7, "hello my name is Bessie and this is my essay"));
        System.out.println("Второе задание: \t" + split("((()))(())()()(()())"));
        System.out.println("Третье задание 1: \t" + toCamelCase("to_camel_case"));
        System.out.println("Третье задание 2: \t" + toSnakeCase("toSnakeCase"));
        System.out.println("Четвёртое задание:\t" + overTime(new float[]{13.25f, 15, 30, 1.5f}));
        System.out.println("Пятое задание:  \t" + BMI("154 pounds", "2 meters"));
        System.out.println("Шестое задание: \t" + bugger(999));
        System.out.println("Седьмое задание:\t" + toStarShorthand("77777geff"));
        System.out.println("Восьмое задание:\t" + doesRhyme("Sam I am!", "Green eggs and ham."));
        System.out.println("Девятое задание:\t" + trouble(666789, 12345667));
        System.out.println("Десятое задание:\t" + countUniqueBooks("AZYWABBCATTTA", 'A'));
    }

    /**
     * Задание 1.
     */
    public static String refactor(int numOfWords, int stringLength, String str){
        String[] arr = str.split(" ");
        str = "";
        int strLength = 0;

        for (int i = 0; i < numOfWords-1; i++){
            strLength += arr[i].length();
            if (strLength + arr[i+1].length() > stringLength) {
                arr[i] += "\n";
                strLength = 0;
            } else {
                arr[i] += " ";
            }
            str = str + arr[i];
        }
        return str += arr[numOfWords-1];
    }

    /**
     * Задание 2
     */
    public static String split(String str){
        ArrayList<StringBuilder> arrList = new ArrayList<>();
        int open = 0, close = 0;
        StringBuilder newStr = new StringBuilder();
        for (char i : str.toCharArray()) {
            newStr.append(i);
            if (i == '(') open++;
            if (i == ')') close++;
            if (open == close) {
                open = close = 0;
                arrList.add(newStr);
                newStr = new StringBuilder();
            }
        }
        return arrList.toString();
    }

    /**
     * Задание 3.1
     */
    public static String toCamelCase(String str){
        while (str.contains("_")) {
            str = str.replaceFirst("[_].", String.valueOf(Character.toUpperCase(str.charAt(str.indexOf("_")+1))));
        }
        return str;
    }

    /**
     * Задание 3.2
     */
    public static String toSnakeCase(String str){
        Pattern regex = Pattern.compile("[A-Z]");
        Matcher matcher = regex.matcher(str);
        for (; matcher.find(); matcher = regex.matcher(str)) {
            str = matcher.replaceFirst("_"+String.valueOf(Character.toLowerCase(str.charAt(matcher.start()))));
        }
        return str;
    }

    /**
     * Задание 4.
     */
    public static float overTime(float[] arr){
        float sum = 0;
        for(float i = arr[0]; i < arr[1]; i += 0.25){
            if (i < 17) {
                sum += 0.25 * arr[2];
            }
            else {
                sum += 0.25 * arr[2] * arr[3];
            }
        }
        return sum;
    }

    /**
     * Задание 5.
     */
    public static String BMI(String weight, String height) {
        double w = weight.split(" ")[1].equals("pounds") ? Double.parseDouble(weight.split(" ")[0]) * 0.453592 : Double.parseDouble(weight.split(" ")[0]);
        double h = height.split(" ")[1].equals("inches") ? Double.parseDouble(height.split(" ")[0]) * 0.0254 : Double.parseDouble(height.split(" ")[0]);
        double bmi = Math.round(w/(h*h) * 10) / 10.0;
        if (bmi < 18.5) return bmi + " Underweight";
        else if (bmi < 25) return bmi + " Normal weight";
        else if (bmi < 30) return bmi + " Overweight";
        else return bmi + " Obesity";
    }

    /**
     * Задание 6.
     */
    public static int bugger(int num) {
        if(num < 10) return 0;
        int newNum = 1;
        while(num > 0) {
            newNum *= num%10;
            num /= 10;
        }
        return bugger(newNum) + 1;
    }

    /**
     * Задание 7.
     */
    public static String toStarShorthand(String str) {
        String newStr = "";
        for(int i = 0, subStrIndex = 0; i < str.length(); i = subStrIndex){
            int count = 1;
            subStrIndex = i + 1;
            while(subStrIndex < str.length() && (String.valueOf(str.charAt(i)).equals(String.valueOf(str.charAt(subStrIndex))))){
                count++;
                subStrIndex++; /// 77777 g e ff
            }
            if (count > 1)
                newStr += (str.charAt(i) + "*" + count);
            else
                newStr += str.charAt(i);
        }
        return newStr;
    }

    /**
     * Задание 8.
     */
    public static boolean doesRhyme(String str1, String str2) {
        return str1.toLowerCase().replaceAll("^(\\w+\\s)+|[^euioa]", "").equals(str2.toLowerCase().replaceAll("^(\\w+\\s)+|[^euioa]", ""));
    }


    /**
     * Задание 9.
     */
    public static boolean trouble(long num1, long num2) {
        String str1 = String.valueOf(num1);
        String str2 = String.valueOf(num2);
        String triple = "";
        for (int i = 0; i < str1.length()-2; i++) {
            if (str1.charAt(i) == str1.charAt(i+1) && str1.charAt(i+1) == str1.charAt(i+2)) {
                triple += str1.charAt(i);
                break;
            }
        }
        if (triple.equals("")){
            return false;
        }
        return str2.contains(triple+triple);
    }


    /**
     * Задание 10.
     */
    public static int countUniqueBooks(String str, char bookEnd) {
        HashSet set = new HashSet();
        boolean betweenBook = false;
        for (char ch : str.toCharArray()) {
            if (ch == bookEnd)
                betweenBook = !betweenBook;
            else
                if (betweenBook) set.add(ch);
        }
        return set.size();
    }
}
