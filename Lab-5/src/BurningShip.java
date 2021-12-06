import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    public int numIterations(double x, double y) {
        int iteration = 0;
        double real = 0, newReal;
        double imaginary = 0, newImaginary;
        while (iteration < MAX_ITERATIONS && real * real + imaginary * imaginary < 4) {
            newReal = real * real - imaginary * imaginary + x;
            newImaginary = 2 * Math.abs(real) * Math.abs(imaginary) + y;
            real = newReal;
            imaginary = newImaginary;
            iteration += 1;
        }
        if (iteration == MAX_ITERATIONS) return -1;
        return iteration;
    }

    public String toString() {
        int arr [] = int [5] new;
        int arr1 [] = new int [5];
        int [] arr2 = new int [5];
        int arr3 []; arr3 = new int [5];
        return "Burning Ship";
    }
}