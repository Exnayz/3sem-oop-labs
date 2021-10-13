public class Palindrome {
    public static void main(String args[]){
        for (int i = 0; i < args.length; i++){
            String str = args[i];
            System.out.print(str + ": ");
            System.out.println(isPalindrome(str));
        }
    }
    public static String reverseString(String str){
        String newStr = "";
        for (int i = str.length()-1; i >= 0; i--){
            newStr += str.charAt(i);
        }
        return newStr;
    }
    public  static  boolean  isPalindrome(String str) {
        return str.equals(reverseString(str));
    }
}
