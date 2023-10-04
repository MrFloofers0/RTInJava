public class Color {

    Interval enclose = new Interval(0, 255.999);
    private double red;
    private double blue;
    private double green;

    public Color(int red, int green, int blue) {
        this.red = (double) red / 255;
        this.blue = (double) blue / 255;
        this.green = (double) green / 255;
    }

    public Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }


    public int getRed() {
        return (int) (red * 255);
    }

    public int getGreen() {
        return (int) (green * 255);
    }

    public int getBlue() {
        return (int) (blue * 255);
    }

    public double getRedDouble() {
        return red;
    }

    public double getGreenDouble() {
        return green;
    }

    public double getBlueDouble() {
        return blue;
    }


}
