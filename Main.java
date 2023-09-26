import java.io.IOException;
import java.lang.Math.*;

public class Main {
    static VectorUtil u = new VectorUtil();
    static double aspectRatio = 16.0 / 9.0;
    static int imageHeight = 240;

    static double viewportHeight = 2.0;
    static double focalLength = 1.0;

    static int samplesPerPixel = 10; // be careful with this! the time complexity scales as O(n^2)

    static int maxDepth = 1000; // time scales linearly here

    public static void main(String[] args) throws IOException {
        //Camera settings

        Vector cameraCenter = new Vector(0, 0, 0);
        Camera camera = new Camera(
                cameraCenter,
                viewportHeight,
                aspectRatio,
                imageHeight,
                focalLength

        );

        //The WHOLE UNIVERSE

        HittableList world = new HittableList(new Sphere[]{

                new Sphere(new Vector(0, -100.5, -1), 100),
                new Sphere(new Vector(0, 0, -1), 0.5)});


        ImageUtil img = new ImageUtil(camera.imageWidth, imageHeight);
        img.initializeImage();

        Color[] row = new Color[camera.imageWidth];
        for (int j = 0; j < imageHeight; j++) {

            System.out.println("Scanlines remaining:" + (imageHeight - j));

            for (int i = 0; i < camera.imageWidth; i++) {

                Vector pixelCenter = u.add(camera.pixel00, u.add(u.scalar(camera.pixelDeltaU, i), u.scalar(camera.pixelDeltaV, j)));
                //Translate the upper left pixel by pixel delta u and pixel delta v

                Vector rayDirection = u.subtract(pixelCenter, cameraCenter);
                Ray r = new Ray(cameraCenter, rayDirection);
                row[i] = subRay(samplesPerPixel, r, camera.pixelDeltaV, camera.pixelDeltaU, world);
            }
            img.writeRow(row);

        }


    }


    public static Color rayColor(Ray r, HittableList world, int depth, int maxDepth) {

        VectorUtil util = new VectorUtil();
        int currentDepth = depth + 1;
        if (world.hit(r, new Interval(0.00001, Double.POSITIVE_INFINITY), world) && (depth < maxDepth)) {
            /*return new Color(
                    (world.normal.x + 1) * 0.5,
                    (world.normal.y + 1) * 0.5,
                    (world.normal.z + 1) * 0.5);

             */
            Vector randDir = u.randomOnHemisphere2(world.normal);
            Color rc = rayColor(new Ray(world.p, randDir), world, currentDepth, maxDepth);
            double colorScalar = (0.3);
            return new Color(
                    (rc.getRedDouble()) * colorScalar,
                    (rc.getGreenDouble()) * colorScalar,
                    (rc.getBlueDouble()) * colorScalar
            );
        } else if(depth >= maxDepth){
            return new Color(0, 0, 0);
        }


        Vector unitDirection = util.unitVector(r.getDirection());
        double a = 0.5 * (unitDirection.y + 1.0);
        return new Color(
                (1.0 - a) + (a * 0.5),
                (1.0 - a) + (a * 0.7),
                (1.0 - a) + (a));
    }


    public static Color subRay(int samplesPerPixel, Ray r, Vector pixelDeltaV, Vector pixelDeltaU, HittableList world) {

        VectorUtil u = new VectorUtil();
        Vector tempDeltaU = u.scalar(pixelDeltaU, (1.0 / (double) samplesPerPixel));
        Vector tempDeltaV = u.scalar(pixelDeltaV, (1.0 / (double) samplesPerPixel));

        Vector tempUpperLeft = new Vector(
                r.getDirection().x - (0.5 * pixelDeltaU.x),
                r.getDirection().y - (0.5 * pixelDeltaV.y),
                r.getDirection().z);

        Vector tempP00 = u.add(tempUpperLeft, u.add(u.scalar(tempDeltaU, (1.0 / samplesPerPixel)), u.scalar(tempDeltaV, 1.0 / samplesPerPixel)));
        double tempRed = 0;
        double tempGreen = 0;
        double tempBlue = 0;

        Color tempColor;
        int samplesPerPixelSquared = samplesPerPixel * samplesPerPixel;
        for (int j = 1; j <= samplesPerPixel; j++) {
            for (int i = 1; i <= samplesPerPixel; i++) {

                Vector tempDirection = u.add(tempP00, u.add(u.scalar(tempDeltaU, i), u.scalar(tempDeltaV, j)));
                Ray tempR = new Ray(r.getOrigin(), tempDirection);

                tempColor = rayColor(tempR, world, 0, maxDepth);
                tempRed += tempColor.getRed();
                tempGreen += tempColor.getGreen();
                tempBlue += tempColor.getBlue();
            }
        }
        double spp2Inverse = 1.0 / (samplesPerPixelSquared * 255);

        return new Color((tempRed * spp2Inverse), (tempGreen * spp2Inverse), (tempBlue * spp2Inverse));
    }


}
