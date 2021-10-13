import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        // Ввод переменных
        Scanner console = new Scanner(System.in);
        System.out.println("Введите координаты первой точки:");
        Point3D point1 = new Point3D(console.nextDouble(), console.nextDouble(), console.nextDouble());
        System.out.println("Введите координаты второй точки:");
        Point3D point2 = new Point3D(console.nextDouble(), console.nextDouble(), console.nextDouble());
        System.out.println("Введите координаты третьей точки:");
        Point3D point3 = new Point3D(console.nextDouble(), console.nextDouble(), console.nextDouble());

        // Вывод
        if (point1.isEqual(point2) || point1.isEqual(point3) || point2.isEqual(point3)){
            System.out.println("\nТочки имеют одинаковые координаты");
        }
        else {
            System.out.printf("\nПлощадь треугольника равна %.2f.",  computeArea(point1, point2, point3));
        }
    }
    public static double computeArea(Point3D point1, Point3D point2, Point3D point3) {
        // Формула Герона
        double a, b, c, P, S;
        a = point1.distanceTo(point2);
        b = point1.distanceTo(point3);
        c = point2.distanceTo(point3);
        P = 0.5*(a+b+c);
        S = Math.sqrt(P*(P-a)*(P-b)*(P-c));
        return S;
    }
}