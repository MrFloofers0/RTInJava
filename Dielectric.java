public class Dielectric extends Material {
    private VectorUtil u = new VectorUtil();
    private double indexOfRefraction;
    private double cloudiness;
    private Color tint;
    private double cosTheta;
    private double sinTheta;

    public Dielectric(double refractiveIndex, double cloudiness, Color tint) {
        this.indexOfRefraction = refractiveIndex;
        this.cloudiness = cloudiness;
        this.tint = tint;
    }

    Vector refract(Vector initial, Vector n, double etaiOverEtat) { // The variables here are named for Snells' Law!

        initial = u.unitVector(initial);
        n = u.unitVector(n);
        double cosThetai = u.dotProduct(u.scalar(initial, -1), n);


        double sinSquaredTheta = (etaiOverEtat * etaiOverEtat) * (1 - (cosThetai * cosThetai));

        Vector rOutParallel = u.scalar(u.add(initial, u.scalar(n, cosThetai)), etaiOverEtat);
        Vector rOutPerpendicular = u.scalar(n, Math.sqrt(1.0 - u.lengthSquared(rOutParallel)));
        Vector rOutFinal = u.add(u.scalar(initial, etaiOverEtat), u.subtract(rOutParallel, rOutPerpendicular));

        return rOutFinal;


    }

    double reflectance(double cosine, double refractRatio) {
        double r0 = Math.pow((1 - refractRatio) / (1 + refractRatio), 2);
        return r0 + (1 - r0) * Math.pow((1 - cosine), 5);
    }

    @Override
    public MaterialData Scatter(Vector dirIn, Hittable rec, Color attenuation, Ray scattered) {


        Vector reflected = u.unitVector(u.add(dirIn, u.scalar(rec.normal, 2.0)));
        Vector unitDirection = u.unitVector(dirIn);
        Vector vecOut;

        cosTheta = u.dotProduct(u.unitVector(dirIn), u.unitVector(rec.normal));
        sinTheta = Math.sqrt(1 - (cosTheta * cosTheta));
        double refractionRatio = rec.frontFace ? (1.0 / indexOfRefraction) : indexOfRefraction;

        Vector refracted = refract(unitDirection, rec.normal, refractionRatio);
        boolean cannotRefract = (refractionRatio * sinTheta) > 1.0;

        vecOut = (cannotRefract || (reflectance(cosTheta, refractionRatio) > Math.random())) ? reflected : refracted;
        boolean hit = true;


        return new MaterialData(hit, tint, new Ray(rec.contactPoint, vecOut));
    }
}
