import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateImage {

    public void createImage(String FileType) {
        Long time = System.currentTimeMillis();
        try {
            File O = new File(time.toString() + FileType);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
