import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    static VectorUtil u = new VectorUtil();
    static double aspectRatio = 16.0 / 9.0;
    static int imageHeight = 480;

    static double viewportHeight = 2.0;
    static double focalLength = 1.0;

    static int samplesPerPixel = 5; // be careful with this! the time complexity scales as O(n^2)

    static int maxDepth = 50; // time scales linearly here
    static int numberOfThreads = 1; //no touchy
    static Vector cameraCenter = new Vector(0, 0, -2);
    //Materials in the whole universe
    static Material ground = new LambertianDiffuse(new Color(0.8, 0.8, 0.0), 0);
    static Material center = new LambertianDiffuse(new Color(0.7, 0.3, 0.3), 0);
    static Material left = new Metal(new Color(1, 1, 1), 0);
    static Material right = new Dielectric(1.5, new Color(1, 1, 1));
    static Material back = new LambertianDiffuse(new Color(0.1, 0.1, 0.7), 0);
    static Camera camera = new Camera(cameraCenter, viewportHeight, aspectRatio, imageHeight, focalLength);

    //the WHOLE UNIVERSE

    static HittableList world = new HittableList(new Sphere[]{

            new Sphere(new Vector(0, -2005, -15), 2000, ground),
            //new Sphere(new Vector(0, 0, 0), 0.5, center),
            new Sphere(new Vector(-12, 0, -15), 5, left),
            new Sphere(new Vector(12, 0, -15), 5, right),
            new Sphere(new Vector(5, 0, -28), 5, back),
            new Sphere(new Vector(0, 0, -15), 5, center)

    });


    public static void main(String[] args) throws IOException, InterruptedException {


        ImageUtil img = new ImageUtil(camera.imageWidth, imageHeight);
        img.initializeImage();

        RenderThread[] renderThreads = new RenderThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            HittableList w = world;
            renderThreads[i] = new RenderThread((imageHeight * i) / numberOfThreads, imageHeight / numberOfThreads, camera, i, camera.imageWidth, w, cameraCenter, samplesPerPixel);
            System.out.println("Thread " + i + " starting");
            renderThreads[i].start();
        }


        double startTime = System.currentTimeMillis();

        boolean allDone = renderThreads[0].isDone;
        while (!allDone) {
            TimeUnit.MILLISECONDS.sleep(200);
            allDone = renderThreads[0].isDone;
            for (int i = 0; i < numberOfThreads; i++) {
                allDone = allDone && renderThreads[i].isDone;
            }
        }

        for (int i = 0; i < numberOfThreads; i++) {
            for (int j = 0; j < imageHeight / numberOfThreads; j++) {

                img.writeRow(renderThreads[i].partialImage[j]);

            }
        }
        System.out.println("Took " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
    }


}
