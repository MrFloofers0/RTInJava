import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageUtil {
    private long name;
    private String fileName;
    private int width;
    private int height;

    public ImageUtil(int imageWidth, int imageHeight) throws IOException {

        name = System.currentTimeMillis();
        fileName = name + ".ppm";

        width = imageWidth;
        height = imageHeight;

        File O = new File("../", fileName);
    }

    /**
     * @throws IOException
     */
    public void initializeImage() throws IOException {
        try {

            FileWriter f = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(f);

            bw.write("P3");
            bw.newLine();
            bw.write(width + " " + height);
            bw.newLine();
            bw.write(255 + "");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeRow(Color[] RGB) throws IOException {
        String lineToWrite = "";
        try {

            FileWriter f = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(f);

            for (int i = 0; i < RGB.length; i++) {
                bw.newLine();

                lineToWrite = (RGB[i].getRed() + " " + RGB[i].getGreen() + " " + RGB[i].getBlue());

                bw.write(lineToWrite);

            }
            bw.close();
            f.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
