import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Block_4 {
    public static void main (String[] args){
        System.out.println("Первое задание: \t\n" + refactor(10, 7, "hello my name is Bessie and this is my essay"));
        System.out.println("Второе задание: \t" + split("((()))(())()()(()())"));
        System.out.println("Третье задание 1: \t" + toCamelCase("to_camel_case"));
        System.out.println("Третье задание 2: \t" + toSnakeCase("toSnakeCase"));
        System.out.println("Четвёртое задание:\t");
        System.out.println("Пятое задание:  \t");
        System.out.println("Шестое задание: \t");
        System.out.println("Седьмое задание:\t");
        System.out.println("Восьмое задание:\t");
        System.out.println("Девятое задание:\t");
        System.out.println("Десятое задание:\t");
    }

    /**
     * Задание 1.
     * Красиво форматирует текст.
     * @param numOfWords Количество слов в начальной строке.
     * @param stringLength Длина подстроки.
     * @param str Начальная строка.
     * @return String
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

    /** Задание 2
     * Группирует скобки.
     * @param str строка из скобок.
     * @return String
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

    /** Задание 3.1
     * Метод перевода в camel case
     * @param str String
     * @return String
     */
    public static String toCamelCase(String str){
        while (str.contains("_")) {
            str = str.replaceFirst("[_].", String.valueOf(Character.toUpperCase(str.charAt(str.indexOf("_")+1))));
        }
        return str;
    }

    /** Задание 3.2
     * Метод перевода в snake case
     * @param str String
     * @return String
     */
    public static String toSnakeCase(String str){
        Pattern regex = Pattern.compile("[A-Z]");
        Matcher matcher = regex.matcher(str);
        for (; matcher.find(); matcher = regex.matcher(str)) {
            str = matcher.replaceFirst("_"+String.valueOf(Character.toLowerCase(str.charAt(matcher.start()))));
        }
        return str;
    }
/*
    public static float overTime(int[] arr){
        float workInTime, overWorkTime, workTime;
        if (arr[0] > arr[1]) arr[1] += 24;
        workTime = arr[1] - arr[0];
        if (workTime >= 17) {
            overWorkTime = workTime - 8;
            workInTime = 8;
            return 8 * arr[2] + arr[3]*overWorkTime*arr[2];
        }
    }*/
}
