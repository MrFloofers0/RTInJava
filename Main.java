import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        int height = 300;
        double aspectRatio = 16.0/9.0;
        int width = (int) aspectRatio * height;

        int FOV = 90; //In Degrees


        Color[] row = new Color[width];
        ImageUtil img = new ImageUtil(width, height);
        img.initializeImage();


            System.out.println("Scanlines remaining:" + (height));
            System.out.print("\033[H\033[2J");
            System.out.flush();


    }


}
