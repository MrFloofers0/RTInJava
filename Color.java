public class Color {

    private short red;
    private short blue;
    private short green;

    public Color(short red, short green, short blue){
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public Color(double red, double green, double blue){
        this.red = (short)(red * 255.999);
        this.green = (short)(green * 255.999);
        this.blue = (short)(blue * 255.999);
    }

    public short getRed() {
        return red;
    }

    public short getGreen() {return green;}

    public short getBlue() {
        return blue;
    }


}
