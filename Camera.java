public class Camera {

    public int imageWidth;
    public double viewportWidth;
    public double viewPortHeight;
    public double focalLength;
    public double imageHeight;
    public double aspectRatio;
    public Vector cameraCenter;

    public Vector viewportUpperLeft;
    public Vector pixel00;
    public Vector pixelDeltaU; //Change in width
    public Vector pixelDeltaV; // Change in height
    public Camera(Vector cameraCenter, double FOV, double aspectRatio, int imageHeight, double focalLength) {
        VectorUtil u = new VectorUtil();

        imageWidth = (int) (aspectRatio * imageHeight);
        double theta = Math.toRadians(FOV);
        double heightFromAngle = Math.tan(theta / 2);
        double viewportHeight = 2 * heightFromAngle * focalLength;
        viewportWidth = viewportHeight * ((double) imageWidth / imageHeight);
        this.aspectRatio = aspectRatio;
        this.focalLength = focalLength;
        this.cameraCenter = cameraCenter;

        //Vectors along the horizontal and vertical edges of the viewport
        Vector viewport_u = new Vector(viewportWidth, 0, 0);
        Vector viewport_v = new Vector(0, -viewportHeight, 0);

        //The change in distance from pixel to pixel

        pixelDeltaU = u.scalar(viewport_u, (1.0 / imageWidth));

        pixelDeltaV = u.scalar(viewport_v, (1.0 / imageHeight));

        //The position of the upper left pixel, or (0,0)
        viewportUpperLeft = u.add(new Vector(-viewportWidth * 0.5, viewportHeight * 0.5, -focalLength), cameraCenter);
        // What this is actually saying is viewportUpperLeft + (pixelDeltaU + pixelDeltaV) * 0.5
        pixel00 = u.add(viewportUpperLeft, u.scalar(u.add(pixelDeltaU, pixelDeltaV), 0.5));
    }
}
