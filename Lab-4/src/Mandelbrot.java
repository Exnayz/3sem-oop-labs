import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator{
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    public int numIterations(double x, double y) {
        int iteration = 0;
        double real = 0, newReal;
        double imaginary = 0, newImaginary;
        while(iteration < MAX_ITERATIONS && real * real - imaginary * imaginary < 4){
            newReal = real * real - imaginary * imaginary + x;
            newImaginary = 2 * real * imaginary + y;
            real = newReal;
            imaginary = newImaginary;
            iteration += 1;
        }
        if (iteration == MAX_ITERATIONS) return -1;
        return  iteration;
    }
}
