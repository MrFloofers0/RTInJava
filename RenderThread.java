public class RenderThread extends Thread {

    Camera camera;
    int ID;
    int startingLine;
    int linesToDo;
    boolean isDone = false;
    int imageWidth;
    Color[][] partialImage;
    HittableList theWholeWorld;
    VectorUtil u = new VectorUtil();
    Vector cameraCenter;
    int samplesPerPixel;

    public RenderThread(int startingLine, int linesToDo, Camera camera, int ID, int imageWidth, HittableList theWholeWorld, Vector cameraCenter, int samplesPerPixel) {
        this.camera = camera;
        this.ID = ID;
        this.startingLine = startingLine;
        this.linesToDo = linesToDo;
        this.imageWidth = imageWidth;
        this.theWholeWorld = theWholeWorld;
        this.cameraCenter = cameraCenter;
        this.samplesPerPixel = samplesPerPixel;
    }

    @Override
    public void run() {
        Render render = new Render();
        System.out.println("Thread " + ID + " started " + startingLine + " " + linesToDo);
        partialImage = new Color[linesToDo][imageWidth];
        for (int j = 0; j < (linesToDo); j++) {

            System.out.println("Thread " + ID + ": Scanlines remaining:" + (linesToDo - j));

            for (int i = 0; i < camera.imageWidth; i++) {

                Vector pixelCenter = u.add(camera.pixel00, u.add(u.scalar(camera.pixelDeltaU, i), u.scalar(camera.pixelDeltaV, (j + startingLine))));
                //Translate the upper left pixel by pixel delta u and pixel delta v

                Vector rayDirection = u.subtract(pixelCenter, cameraCenter);
                Ray r = new Ray(cameraCenter, rayDirection);
                partialImage[j][i] = render.subRay(samplesPerPixel, r, camera.pixelDeltaV, camera.pixelDeltaU, theWholeWorld);
            }

        }
        System.out.println(ID + " Is done");
        isDone = true;


    }
}
