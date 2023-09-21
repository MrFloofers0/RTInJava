import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        VectorUtil u = new VectorUtil();
        double aspectRatio = 16.0 / 9.0;
        int imageHeight = 300;
        int imageWidth = (int) (aspectRatio * imageHeight);
        double viewportHeight = 2.0;
        double viewportWidth = viewportHeight * ((double) imageWidth / imageHeight);

        double focalLength = 1.0;
        Vector cameraCenter = new Vector(0, 0, 0);

        //Vectors along the horizontal and vertical edges of the viewport
        Vector viewport_u = new Vector(viewportWidth, 0, 0);
        Vector viewport_v = new Vector(0, -1*(viewportHeight), 0);

        //The change in distance from pixel to pixel
        Vector pixelDeltaU = u.scalar(viewport_u, (1.0 / imageWidth)); //Change in width
        Vector pixelDeltaV = u.scalar(viewport_v, (1.0 / imageHeight)); // Change in height

        //The position of the upper left pixel, or (0,0)
        // What this is actually saying is cameraCenter - Vector(0, 0, focalLength) - viewport_u/2 - viewport_v/2
        Vector uvTemp = u.subtract(u.scalar(viewport_u, 0.5), u.scalar(viewport_v, 0.5));
        Vector viewportUpperLeft = u.subtract(cameraCenter, u.subtract(new Vector(0,0,focalLength), uvTemp));
        // What this is actually saying is viewportUpperLeft + (pixelDeltaU + pixelDeltaV) * 0.5
        Vector pixel00 = u.add(viewportUpperLeft, u.scalar(u.add(pixelDeltaU, pixelDeltaV), 0.5));

        ImageUtil img = new ImageUtil(imageWidth, imageHeight);
        img.initializeImage();

        Color[] row = new Color[imageWidth];
        for (int j = 0; j < imageHeight; j++) {

            System.out.println("Scanlines remaining:" + (imageHeight - j));

            for (int i = 0; i < imageWidth; i++) {

                //Translate the upper left pixel by pixel delta u and pixel delta v
                Vector pixelCenter = u.add(pixel00, u.add(u.scalar(pixelDeltaU, i), u.scalar(pixelDeltaV, j)));
                Vector rayDirection = u.subtract(pixelCenter, cameraCenter);
                Ray r = new Ray(cameraCenter, rayDirection);
                row[i] = rayColor(r);
            }
            img.writeRow(row);

        }


    }

    public static Color rayColor(Ray r) { //lerp
        VectorUtil u;
        Vector unitDirection = r.getDirection();
        double a = 0.5 * (unitDirection.y + 1.0);
        return new Color(
                (1.0 - a) + (a * 0.5),
                (1.0 - a) + (a * 0.7),
                (1.0 - a) + (a));
    }

    public static boolean hitSphere(){
        return true;
    }
}
