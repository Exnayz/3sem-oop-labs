public class Point3D {
    // Параметры
    private double x;
    private double y;
    private double z;

    // Конструкторы
    public Point3D(double _x, double _y, double _z){
        x = _x;
        y = _y;
        z = _z;
    }
    public Point3D(){
        this(0,0,0);
    }

    // Методы
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public void setX(double _x){
        x = _x;
    }
    public void setY(double _y){
        y = _y;
    }
    public void setZ(double _z){
        z = _z;
    }
    public boolean isEqual(Point3D point3d){
        if (x != point3d.getX()) return false;
        if (y != point3d.getY()) return false;
        if (z != point3d.getZ()) return false;
        return true;
    }
    public double distanceTo(Point3D point3d){
        double distance = Math.sqrt(Math.pow(x-point3d.getX(), 2)
                + Math.pow(y-point3d.getY(), 2)
                + Math.pow(z-point3d.getZ(), 2));
        return distance;
    }
}