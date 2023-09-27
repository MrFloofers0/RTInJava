import java.lang.Math;

public class Color {

    private short red;
    private short blue;
    private short green;
    Interval enclose = new Interval(0, 255.999);

    private double deLinearize(double num){
        return num;
    }

    public Color(short red, short green, short blue) {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public Color(double red, double green, double blue) {
        this.red = (short) enclose.clamp(deLinearize(red) * 255.999);
        this.green = (short) enclose.clamp(deLinearize(green) * 255.999);
        this.blue = (short) enclose.clamp(deLinearize(blue) * 255.999);
    }

    public Color(Color[] toAvg) {

        double tempRed = 0;
        double tempGreen = 0;
        double tempBlue = 0;

        for (Color color : toAvg) {
            tempRed += color.getRed();
            tempGreen += color.getGreen();
            tempBlue += color.getBlue();
        }

        this.red = (short) (tempRed / toAvg.length);
        this.green = (short) (tempBlue / toAvg.length);
        this.blue = (short) (tempBlue / toAvg.length);

    }

    public short getRed() {
        return red;
    }

    public short getGreen() {
        return green;
    }

    public short getBlue() {
        return blue;
    }

    public double getRedDouble() {
        return ((double) red / 255);
    }

    public double getGreenDouble() {
        return ((double) green / 255);
    }

    public double getBlueDouble() {
        return ((double) blue / 255);
    }


}
