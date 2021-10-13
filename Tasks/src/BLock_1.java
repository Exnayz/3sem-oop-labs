public class BLock_1 {
    public static void main(String[] args) {
        System.out.println("Задание 1: " + convert(2));
        System.out.println("Задание 2: " + points(13, 12));
        System.out.println("Задание 3: " + football_points(3, 4, 2));
        System.out.println("Задание 4: " + divisible_by_five(-55));
        System.out.println("Задание 5: " + and(true, false));
        System.out.println("Задание 6: " + how_many_walls(100, 4, 5));
        System.out.println("Задание 7: " + squared(5));
        System.out.println("Задание 8: " + profitable_gamble(0.2f, 50, 9));
        System.out.println("Задание 9: " + frames(10, 25));
        System.out.println("Задание 10: " + mod(218, 5));
    }
    public static int convert(int x){
        return x*60;
    }
    public static int points(int x2, int x3){
        return x2*2+x3*3;
    }
    public static int football_points(int x3, int x1, int x0){
        return x3*3 + x1;
    }
    public static boolean divisible_by_five(int x){
        return (x%5==0);
    }
    public static boolean and(boolean x, boolean y){
        return x&&y;
    }
    public static int how_many_walls(int n, int w, int h){
        return n/(w*h);
    }
    public static int squared(int b) {
        return b*b;
    }
    public static boolean profitable_gamble(float prod, int prize, int pay){
        return prod*prize > pay;
    }
    public static int frames(int minutes, int frame_rate){
        return minutes*frame_rate*60;
    }
    public static int mod(int x, int y){
        return x - (x/y)*y;
    }
}
