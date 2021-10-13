import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main_3 {
    public static void main (String[] args){
        System.out.println("Первое задание: \t" + solutions(1, 0, -1));
        System.out.println("Второе задание: \t" + findZip("all Zip files are zipped"));
        System.out.println("Третье задание: \t" + checkPerfect(496));
        System.out.println("Четвёртое задание:\t" + flipEndChars("?fdsf."));
        System.out.println("Пятое задание:  \t" + isValidHexCode("#f7823a"));
        System.out.println("Шестое задание: \t" + same(new int[]{1, 2, 3, 3, 3}, new int[]{4, 5, 8}));
        System.out.println("Седьмое задание:\t" + isKaprekar(3));
        System.out.println("Восьмое задание:\t" + longestZero("101"));
        System.out.println("Девятое задание:\t" + nextPrime(90));
        System.out.println("Десятое задание:\t" + rightTriangle(145, 105, 100));
    }

    /**
     * Задание 1.
     * Вычисляет количество решений квадратного уравнения.
     * @param a int
     * @param b int
     * @param c int
     * @return int
     */
    public static int solutions(int a, int b, int c) {
        double d = Math.pow(b, 2) - 4*a*c;
        if (d > 0) return 2;
        if (d == 0) return 1;
        return 0;
    }

    /**
     * Задание 2.
     * Ищет второе вхождение zip.
     * @param str String
     * @return int
     */
    public static int findZip(String str) {
        str = str.replaceFirst("[zZ][iI][pP]", "---");
        str = str.replaceFirst("[zZ][iI][pP]", "zip");
        return str.indexOf("zip");
    }

    /**
     * Задание 3.
     * Вычисляет, является ли число идеальным.
     * @param number int
     * @return boolean
     */
    public static boolean checkPerfect(int number) {
        int sum = 0;
        for (int i = 1; i < number; i++) {
            if (number%i==0) sum += i;
        }
        return sum == number;
    }

    /**
     * Задание 4.
     * Меняет в строке первый и последний символ.
     * @param str String
     * @return String
     */
    public static String flipEndChars(String str){
        if (str.length() == 1) return "Incompatible.";
        if (str.charAt(0) == str.charAt(str.length()-1)) return "Two's a pair.";
        String newStr = str.replaceFirst("^.", String.valueOf(str.charAt(str.length()-1)));
        newStr = newStr.replaceFirst(".$", String.valueOf(str.charAt(0)));
        return newStr;
    }

    /**
     * Задание 5.
     * Вычисляет, является ли hexCode правильным.
     * @param hexCode String
     * @return boolean
     */
    public static boolean isValidHexCode(String hexCode){
        String regex = "^#[0-9a-zA-Z]{6}$";
        return Pattern.matches(regex, hexCode);
    }

    /**
     * Задание 6.
     * Сравнивает количество уникальных элементов двух массивов.
     * @param arr1 int[]
     * @param arr2 int[]
     * @return boolean
     */
    public static boolean same(int[] arr1, int[]arr2){
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i : arr1) if (!list1.contains(i)) list1.add(i);
        for (int i : arr2) if (!list2.contains(i)) list2.add(i);
        return list1.size() == list2.size();
    }

    /**
     * Задание 7.
     * Проверяет, является ли число числом Капрекара.
     * @param num int
     * @return boolean
     */
    public static boolean isKaprekar(int num){
        String newNum = Integer.toString(num*num);
        int left, right;
        try {
            left = Integer.parseInt(newNum.substring(0, newNum.length() / 2));
        } catch (NumberFormatException e) {
            left = 0;
        }
        right = Integer.parseInt(newNum.substring(newNum.length()/2, newNum.length()));
        return left + right == num;
    }

    /**
     * Задание 8.
     * Вычисляет самую длунную последовательность 0.
     * @param str String
     * @return String
     */
    public static String longestZero(String str){
        String longestStr = "";
        Pattern pattern = Pattern.compile("00*");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            String tmpStr = str.substring(matcher.start(), matcher.end());
            if (longestStr.length() < tmpStr.length()) longestStr = tmpStr;
        }
        return longestStr;
    }

    /**
     * Задание 9.
     * Выводит следующее простое число.
     * @param num int
     * @return int
     */
    public static int nextPrime(int num){
        for (int i = 2; i <= Math.sqrt(Math.abs(num)); i++){
            if (num%i == 0) {
                num += 1;
                i = 1;
            }
        }
        return num;
    }

    /**
     * Задание 10.
     * Вычисляет, является ли треугольник правильным.
     * @param x int
     * @param y int
     * @param z int
     * @return boolean
     */
    public static boolean rightTriangle (int x, int y, int z){
        int[] arr = Arrays.stream(new int[]{x, y, z}).sorted().toArray();
        return Math.sqrt(Math.pow(arr[0], 2) + Math.pow(arr[1], 2)) == arr[2];
    }
}
