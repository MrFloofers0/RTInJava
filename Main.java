import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        int width = 256;
        int height = 256;

        Pixel[] row = new Pixel[width];
        ImageUtil img = new ImageUtil(width, height);

        img.initializeImage();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                row[j] = new Pixel((short) i, (short) j, (short) 0);
            }
            img.writeRow(row);
        }

    }


}
