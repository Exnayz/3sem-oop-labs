import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Main_4 {
    public static void main (String[] args){
        System.out.println("Первое задание: \t\n" + refactor(10, 7, "hello my name is Bessie and this is my essay"));
        System.out.println("Второе задание: \t" + split("((()))(())()()(()())"));
        System.out.println("Третье задание: \t");
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


}
