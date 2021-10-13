public class Point2D {
    // Параметры
    private double x;
    private double y;

    // Конструкторы
    public Point2D(double _x, double _y){
        x = _x;
        y = _y;
    }
    public Point2D(){
        this(0,0);
    }

    // Методы
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setX(double _x){
        x = _x;
    }
    public void setY(double _y) {
        y = _y;
    }
}
