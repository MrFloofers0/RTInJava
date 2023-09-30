public class Dielectric extends Material {
    private VectorUtil u = new VectorUtil();

    private double refIndex;
    private Color tint;

    public Dielectric(double refractiveIndex, Color tint) {
        this.refIndex = refractiveIndex;
        this.tint = tint;
    }

    @Override
    public MaterialData Scatter(Vector dirIn, Hittable rec, Color attenuation, Ray scattered) {

        double etaRatio = rec.frontFace ? (1/refIndex) : refIndex;
        Vector refracted = refract(u.unitVector(rec.contactPoint), rec.normal, etaRatio);
        return new MaterialData(true, tint, new Ray(rec.contactPoint, refracted));
    }

    public Vector refract(Vector directionInitial, Vector normalVector, double etaRatio) {

        double cosThetaInitial = Math.min(u.dotProduct(u.scalar(directionInitial, -1), normalVector), 1.0);
        Vector tPerpendicular = u.scalar(u.add(u.scalar(normalVector, cosThetaInitial), directionInitial), etaRatio);
        Vector tParallel = u.scalar(normalVector, -Math.sqrt(Math.abs(1.0 - u.lengthSquared(tPerpendicular))));
        return u.add(tParallel, tPerpendicular);

    }
}

