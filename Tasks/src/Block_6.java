import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Block_6 {
    public static void main (String[] args) {
        System.out.println("Первое задание: \t" + bell(4));
        System.out.println("Второе задание 1: \t" + translateWord("have."));
        System.out.println("Второе задание 2: \t" + translateSentence("I like to eat honey waffles."));
        System.out.println("Третье задание: \t" + validColor("rgba(255, 100, 0, 0)"));
        System.out.println("Четвёртое задание:\t" + stripUrlParams("http://127.0.0.1?a=1&b=2&a=2", new String[]{"b"}));
        System.out.println("Пятое задание:  \t" + Arrays.toString(getHashTags("How the Avocado Became the Fruit of the Global Trade")));
        System.out.println("Шестое задание: \t" + ulam(5));
        System.out.println("Седьмое задание:\t" + longestNonrepeatingSubstring("asfasasdf"));
        System.out.println("Восьмое задание:\t" + convertToRoman(99));
        System.out.println("Девятое задание:\t" + formula("(15 + 1) * 10 = 160"));
        System.out.println("Десятое задание:\t" + palindromeDescendant(11211230));
    }

    /**
     * Задание 1.
     */
    public static int bell(int num) {
        int[][] bell = new int[num][num];
        bell[0][0] = 1;
        for(int i = 1; i < num; i++) {
            bell[i][0] = bell[i - 1][i - 1];
            for(int j = 1; j <= i; j++) {
                bell[i][j] = bell[i - 1][j - 1] + bell[i][j - 1];
            }
        }
        return bell[num - 1][num - 1];
    }


    /**
     * Задание 2.1.
     */
    public static String translateWord(String word) {
        if (word == "") return word;
        String sign = word.replaceAll("\\w", "");
        word = word.replaceAll("\\W", "");
        if (isVowel(Character.toLowerCase(word.charAt(0)))) return word + "yay" + sign;
        if (Character.isUpperCase(word.charAt(0))) {
            for (int i = 0; i < word.length(); i++) {
                if (isVowel(Character.toLowerCase(word.charAt(i))))
                    return word.substring(i , i+1).toUpperCase() + word.substring(i+1) + word.substring(0, i).toLowerCase() + "ay" + sign;
            }
        }
        else {
            for (int i = 0; i < word.length(); i++) {
                if (isVowel(Character.toLowerCase(word.charAt(i))))
                    return word.substring(i) + word.substring(0, i) + "ay" + sign;
            }
        }
        return word;
    }

    static public boolean isVowel(char ch){
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    /**
     * Задание 2.2.
     */
    public static String translateSentence(String sentence) {
        return Arrays.stream(sentence.split(" ")).map(Block_6::translateWord).collect(Collectors.joining(" "));
    }


    /**
     * Задание 3.
     */
    public static boolean validColor(String color) {
        String minToMax = "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])";
        return color.matches("rgb\\((\\s*" + minToMax + "\\s*,){2}\\s*" + minToMax + "\\s*\\)|rgba\\((\\s*" + minToMax + "\\s*,){3}\\s*(0|0?\\.\\d+|1)\\s*\\)");
    }

    /**
     * Задание 4.
     */
    public static String stripUrlParams(String url, String[] paramsToStrip) {
        if (!url.contains("?")) return url;

        String[] surl = url.split("\\?");
        String[] params = surl[1].split("\\&");
        HashMap<String, String> dict = new HashMap<>();
        for (String param : params) {
            dict.put(param.substring(0, 1), param);
        }
        if (paramsToStrip != null) for (String param : paramsToStrip) {
            dict.remove(param);
        }
        return surl[0] + "?" + String.join("&", dict.values());
    }

    /**
     * Задание 5.
     */
    public static String[] getHashTags(String str) {
        return Arrays.stream(str.toLowerCase().replaceAll("\\p{Punct}", "").split(" "))
                .sorted().limit(3).map(s -> "#" + s).toArray(String[]::new);
    }

    /**
     * Задание 6.
     */
    public static int ulam(int num) {
        TreeSet<Integer> ulamNums = new TreeSet<Integer>();
        ulamNums.add(1);
        ulamNums.add(2);
        ulamNums.add(3);
        int currentNum = 3;

        for(int i = 3; i < num;)
        {
            currentNum++;
            int numAdds = 0;
            for(int n : ulamNums) {
                int checkNum = currentNum - n;
                if (checkNum <= n) {
                    break;
                }
                if (ulamNums.contains(checkNum)) {
                    numAdds++;
                    if (numAdds > 1) {
                        break;
                    }
                }
            }
            if  (numAdds == 1) {
                ulamNums.add(currentNum);
                i++;
            }
        }
        return currentNum;
    }

    /**
     * Задание 7.
     */
    public static String longestNonrepeatingSubstring(String str) {
        String longestStr = "";
        for (int i = 0; i < str.length()-1; i++) {
            String tmpStr = "";
            for (int j = i; j < str.length(); j++)
                if (!tmpStr.contains(str.substring(j, j+1)))
                    tmpStr += str.substring(j, j+1);
                else
                    break;
            if (tmpStr.length() > longestStr.length())
                longestStr = tmpStr;
        }
        return longestStr;
    }

    /**
     * Задание 8.
     */
    public static String convertToRoman(int num) {
        int[] decimalValue = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanValue = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        String output = "";
        for(int i = 0; i < decimalValue.length; i++){
            while(decimalValue[i] <= num){
                output += romanValue[i];
                num -= decimalValue[i];
            }
        }
        return output;
    }

    /**
     * Задание 9.
     */
    public static boolean formula(String str) {
        str = str.replaceAll(" ", "");
        String equation = "";
        for (int i = 0; i < str.length(); i++) {
            if(str.substring(i, i+1).matches("[^\\+\\-\\*\\/\\=]"))
                equation += String.valueOf(str.charAt(i));
            else
                equation += " " + str.charAt(i) + " ";
        }
        String[] parts = equation.split("=");
        int solution = solve(parts[0]);
        return Arrays.stream(parts).allMatch(s -> solve(s) == solution);
    }

    public static int solve(String equation) {
        equation = equation.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] allNum = equation.trim().split(" ");
        if (allNum.length == 1) return Integer.parseInt(allNum[0]);
        int result = Integer.parseInt(allNum[0]);
        for (int i = 1; i < allNum.length; i += 2) {
            switch(allNum[i]) {
                case "*":
                    result *= Integer.parseInt(allNum[i+1]);
                    break;
                case "+":
                    result += Integer.parseInt(allNum[i+1]);
                    break;
                case "-":
                    result -= Integer.parseInt(allNum[i+1]);
                    break;
                case "/":
                    result /= Integer.parseInt(allNum[i+1]);
                    break;
            }
        }
        return result;
    }
    /**
     * Задание 10.
     */
    public static boolean palindromeDescendant(int num) {
        String numStr = Integer.toString(num);
        String result = "";
        for(int i = 0; i < numStr.length(); i++)
            result = numStr.charAt(i) + result;

        for(int i = 0; numStr.length() >= 2; i++){
            if(numStr.equals(result))
                return true;
            numStr = "";
            for(int j = result.length() - 1; j > 0; j -= 2)
                numStr += Character.getNumericValue(result.charAt(j)) + Character.getNumericValue(result.charAt(j - 1));
            result = "";
            for(int j = 0; j < numStr.length(); j++)
                result = numStr.charAt(j) + result;
        }
        return false;
    }
}
