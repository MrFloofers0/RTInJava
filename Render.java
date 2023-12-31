public class Render extends Main{
    public Color rayColor(Ray r, HittableList world, int depth, int maxDepth) {

        VectorUtil util = new VectorUtil();
        int currentDepth = depth + 1;
        if (world.hit(r, new Interval(0.000001, Double.POSITIVE_INFINITY), world) && (depth < maxDepth)) {

            Color attenuation = null;
            Ray scattered = null;
            MaterialData mat = world.mat.Scatter(world.contactPoint, world, attenuation, scattered);
            if (!mat.hit) {
                return mat.materialColor;
            }
            Ray randDir = mat.bouncedRay;
            Color rc = rayColor(randDir, world, currentDepth, maxDepth);
            return new Color((rc.getRedDouble() * mat.materialColor.getRedDouble()), (rc.getGreenDouble() * mat.materialColor.getGreenDouble()), (rc.getBlueDouble() * mat.materialColor.getBlueDouble()));

        }
        Vector unitDirection = util.unitVector(r.getDirection());
        double a = 0.5 * (unitDirection.y + 1.0);
        return new Color((1.0 - a) + (a * 0.5), (1.0 - a) + (a * 0.7), (1.0 - a) + (a));


    }


    public Color subRay(int samplesPerPixel, Ray r, Vector pixelDeltaV, Vector pixelDeltaU, HittableList world) {

        VectorUtil u = new VectorUtil();
        Vector tempDeltaU = u.scalar(pixelDeltaU, (1.0 / (double) samplesPerPixel));
        Vector tempDeltaV = u.scalar(pixelDeltaV, (1.0 / (double) samplesPerPixel));

        Vector tempUpperLeft = new Vector(r.getDirection().x - (0.5 * tempDeltaU.x), r.getDirection().y - (0.5 * tempDeltaV.y), r.getDirection().z);

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
