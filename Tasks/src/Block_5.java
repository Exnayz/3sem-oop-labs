import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Block_5 {
    public static void main (String[] args){
        System.out.println("Первое задание 1: \t" + Arrays.toString(encrypt("Sunshine")));
        System.out.println("Первое задание 2: \t" + decrypt(encrypt("Sunshine")));
        System.out.println("Второе задание: \t" + canMove("Rook", "A8", "H8"));
        System.out.println("Третье задание: \t" + canComplete("blt", "brltfd"));
        System.out.println("Четвёртое задание:\t" + sumDigProd(16, 28));
        System.out.println("Пятое задание:  \t" + Arrays.toString(sameVowelGroup(new String[]{"toe", "ocelot", "maniac"})));
        System.out.println("Шестое задание: \t" + validateCard(1234567890123452L));
        System.out.println("Седьмое задание 1:\t" + numToEng(120));
        System.out.println("Седьмое задание 2:\t" + numToRus(120));
        System.out.println("Восьмое задание:\t" + getSha256Hash("password123"));
        System.out.println("Девятое задание:\t" + correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println("Десятое задание:\n" + hexLattice(7));
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
            case "Pawn": // Пешка
                return characterMove == 0 && digitMove <= 2 && (digitCurrent == '2' || digitCurrent == '7' || digitMove != 2);
            case "Knight": // Конь
                return characterMove != 0 && digitMove != 0 && characterMove + digitMove == 3;
            case "Bishop": // Слон
                return characterMove == digitMove;
            case "Rook": // Ладья
                return characterMove == 0 || digitMove == 0;
            case "Queen": // Королева
                return characterMove == digitMove || characterMove == 0 || digitMove == 0;
            case "King": // Король
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
            sum += (i % 2 == 0) ? (charArr[i] - '0') * 2 / 10 + (charArr[i] - '0') * 2 % 10 : charArr[i] - '0';
        }
        return 10 - sum % 10 == num % 10;
    }


    /**
     * Задание 7.1.
     */
    public static String numToEng(int num) {
        final String[] NUMS = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        final String[] TENS1 = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        final String[] TENS2 = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

        String answer = "";

        if (num == 0) return NUMS[0];

        if (num >= 100) {
            answer += NUMS[num/100] + " hundred";
            num %= 100;
            if (num != 0) {
                answer += " ";
            }
        }
        if (num > 19) {
            answer += TENS2[num/10];
            num %= 10;
            if (num != 0) {
                answer += " ";
            }
        }
        else if (num > 9) {
            answer += TENS1[num%10];
            num = 0;
        }
        if (num > 0) {
            answer += NUMS[num];
        }
        return answer;
    }

    /**
     * Задание 7.2
     */
    public static String numToRus(int num) {
        final String[] NUMS = {"Ноль", "Один", "Два", "Три", "Четыре", "Пять", "Шесть", "Семь", "Восемь", "Девять"};
        final String[] TENS1 = {"Десять", "Одиннадцать", "Двенадцать", "Тринадцать", "Четырнадцать", "Пятнадцать", "Шестнадцать", "Семнадцать", "Восемнадцать", "Девятнадцать"};
        final String[] TENS2 = {"", "", "Двадцать", "Тридцать", "Сорок", "Пятьдесят", "Шестьдесят", "Семьдесят", "Восемьдесят", "Девяносто"};
        final String[] HUNDRED = {"", "Сто", "Двести", "Триста", "Четыреста", "Пятьсот", "Шестьсот", "Семьсот", "Восемьсот", "Девятьсот"};

        String answer = "";

        if (num == 0) return NUMS[0];

        if (num >= 100) {
            answer += HUNDRED[num/100];
            num %= 100;
            if (num != 0) {
                answer += " ";
            }
        }
        if (num > 19) {
            answer += TENS2[num/10];
            num %= 10;
            if (num != 0) {
                answer += " ";
            }
        }
        else if (num > 9) {
            answer += TENS1[num%10];
            num = 0;
        }
        if (num > 0) {
            answer += NUMS[num];
        }
        return answer;
    }

    /**
     * Задание 8.
     */
    public static String getSha256Hash(String str) {
        String result = "";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashes = messageDigest.digest(str.getBytes());
            for (byte hash : hashes) {
                String hex = Integer.toHexString(0xff & hash);
                if (hex.length() == 1) result += 0;
                result += hex;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Задние 9.
     */
    public static String correctTitle(String str){
        str = str.toLowerCase().replaceFirst("^.", String.valueOf(str.charAt(0)).toUpperCase());

        Pattern regex = Pattern.compile("(\\s[a-z])");
        Matcher matcher = regex.matcher(str);
        for (; matcher.find(); matcher = regex.matcher(str))
            str = matcher.replaceFirst(" " + String.valueOf(Character.toUpperCase(str.charAt(matcher.start()+1))));

        regex = Pattern.compile("(In)|(The)|(And)|(Of)");
        matcher = regex.matcher(str);
        for (; matcher.find(); matcher = regex.matcher(str))
            str = matcher.replaceFirst(str.substring(matcher.start(), matcher.end()).toLowerCase());
        return str;
    }

    /**
     * Задание 10.
     */
    public static String hexLattice(int num) {
        if (num == 1) return " o ";

        int min = 2, max = 3, all = 7;

        for (int i = 2; ; i++) {
            if (all == num) break;
            if (all > num) return "Invalid";
            min++;
            max += 2;
            all += i * 6;
        }

        String answer = "";
        for (int i = min; i < max; i++)
             answer += draw(" ",max - i) + draw(" o", i) + "\n";
        for (int i = max; i >= min; i--)
            answer += draw(" ", max - i) + draw(" o", i) + (i != min ? "\n" : "");
        return answer;
    }

    private static String draw(String str, int num) {
        StringBuilder out = new StringBuilder();
        while (num-- > 0)
            out.append(str);
        return out.toString();
    }
}
