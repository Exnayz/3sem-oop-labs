import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Block_5 {
    public static void main (String[] args){
        System.out.println("Первое задание 1: \t" + Arrays.toString(encrypt("Sunshine")));
        System.out.println("Первое задание 2: \t" + decrypt(encrypt("Sunshine")));
        System.out.println("Второе задание: \t" + canMove("Rook", "A8", "H8"));
        System.out.println("Третье задание: \t" + canComplete("blt", "brltfd"));
        System.out.println("Четвёртое задание:\t" + sumDigProd(16, 28));
        System.out.println("Пятое задание:  \t" + Arrays.toString(sameVowelGroup(new String[]{"toe", "ocelot", "maniac"})));
        System.out.println("Шестое задание: \t" + validateCard(1234567890123452));
        System.out.println("Седьмое задание:\t");
        System.out.println("Восьмое задание:\t");
        System.out.println("Девятое задание:\t");
        System.out.println("Десятое задание:\t");
    }


    /**
    * Задание 1
    */
    public static int[] encrypt(String str) {
        int[] encoded = new int[str.length()];
        encoded[0] = str.charAt(0);
        for (int i = 1; i < encoded.length; i++) {
            encoded[i] = str.charAt(i) - str.charAt(i - 1);
        }
        return encoded;
    }

    public static String decrypt(int[] arr) {
        String decoded = String.valueOf((char) arr[0]);
        for (int i = 1; i < arr.length; i++) {
            decoded += (char)(arr[i] + decoded.charAt(i - 1));
        }
        return decoded;
    }

    /**
     * Задание 2.
     */
    public static boolean canMove(String figure, String current, String target) {
        current = current.toUpperCase();
        target = target.toUpperCase();

        char characterCurrent = current.charAt(0);
        char digitCurrent = current.charAt(1);
        char characterTarget = target.charAt(0);
        char digitTarget = target.charAt(1);

        int digitMove = Math.abs(digitTarget - digitCurrent);
        int characterMove = Math.abs(characterTarget - characterCurrent);

        if (characterMove + digitMove == 0) return true;
        switch (figure) {
            case "Pawn": //Пешка
                return characterMove == 0 && digitMove <= 2 && (digitCurrent == '2' || digitCurrent == '7' || digitMove != 2);
            case "Knight": //Конь
                return characterMove != 0 && digitMove != 0 && characterMove + digitMove == 3;
            case "Bishop":
                return characterMove == digitMove;
            case "Rook":
                return characterMove == 0 || digitMove == 0;
            case "Queen":
                return characterMove == digitMove || characterMove == 0 || digitMove == 0;
            case "King":
                return characterMove + digitMove == 1 || characterMove == digitMove && characterCurrent == 1;
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Задание 3.
     */
    public static boolean canComplete(String str, String word) {
        return word.matches(str.replaceAll(".", ".*$0") + ".*");
    }

    /**
     * Задание 4.
     */
    public static int sumDigProd(int... numbs) {
        int sum = 0;
        for (int num : numbs) sum += num;
        if (sum < 10) return sum;
        int pr = 1;
        while (sum > 0) {
            pr *= sum % 10;
            sum /= 10;
        }
        return sumDigProd(pr);
    }

    /**
     * Задание 5.
     */
    public static String[] sameVowelGroup(String[] words) {
        ArrayList<String> result = new ArrayList<>();
        String vowels = words[0].replaceAll("[^aeiou]","");
        for(String str : words) {
            if(str.replaceAll("[^aeiou]","").replaceAll("[" + vowels + "]","").length() == 0) {
                result.add(str);
            }
        }
        return result.toArray(new String[0]);
    }

    /**
     * Задание 6.
     */
    public static boolean validateCard(long num) {
        if (!String.valueOf(num).matches("\\d{14,19}")) return false;
        char[] charArr = new StringBuilder(String.valueOf(num/10)).reverse().toString().toCharArray();
        int sum = 0;
        for (int i = 0; i < charArr.length; i++) {
            System.out.println((i % 2 == 0) ? (charArr[i] - '0') * 2 / 10 + (charArr[i] - '0') * 2 % 10 : charArr[i] - '0');
            sum += (i % 2 == 0) ? (charArr[i] - '0') * 2 / 10 + (charArr[i] - '0') * 2 % 10 : charArr[i] - '0';
        }
        return 10 - sum % 10 == num % 10;
    }
}
