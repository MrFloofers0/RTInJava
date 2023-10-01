public class Vector {

    public double x;
    public double y;
    public double z;

    public Vector(double X, double Y, double Z) {
        this.x = X;
        this.y = Y;
        this.z = Z;
    }

    public double length() {
        return (Math.sqrt(x * x + y * y + z * z));
    }


}
