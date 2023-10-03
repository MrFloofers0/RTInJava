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

        Vector unitDirection = u.unitVector(rec.contactPoint);
        double etaRatio = u.dotProduct(rec.normal, unitDirection) > 0 ? (1 / refIndex) : refIndex;
        Vector correctedNormal = (u.dotProduct(unitDirection, rec.normal) > 0) ? u.scalar(rec.normal, -1) : rec.normal;

        double cosI = u.dotProduct(correctedNormal, u.scalar(unitDirection, -1));
        double sinI = Math.sqrt(1 - cosI * cosI);
        double cosT = Math.sqrt(1 - ((etaRatio * etaRatio) * (sinI * sinI)));


        double n1 = u.dotProduct(rec.normal, unitDirection) < 0 ? 1.0 : refIndex;
        double n2 = u.dotProduct(rec.normal, unitDirection) < 0 ? refIndex : 1.0;
        double reflectancePerpendicular = Math.pow(((n1 * cosI) - (n2 * cosT)) / ((n1 * cosI) + (n2 * cosT)), 2);
        double reflectanceParallel = Math.pow(((n2 * cosI) - (n1 * cosT)) / ((n2 * cosI) + (n1 * cosT)), 2);

        double reflectance = (reflectancePerpendicular + reflectanceParallel) / 2;

        Vector rPrime = refract(rec.normal, rec.normal, etaRatio);
        Vector reflected = u.add(unitDirection, u.scalar(u.scalar(correctedNormal, u.dotProduct(unitDirection, correctedNormal)), 2.0));
        //Vector returnVector = (sinI * etaRatio) > 1.0? reflected : rPrime;
        Vector returnVector;

        if ((sinI * etaRatio) > 1.0 && (u.dotProduct(unitDirection, rec.normal) > 0)) {
            // Must reflect
            returnVector = reflected;
        } else {
            // Can refract
            //returnVector = cosI < Math.random() ? reflected : rPrime;
            //returnVector = rPrime;
        }
        returnVector = reflected;


        return new MaterialData(true, tint, new Ray(rec.contactPoint, returnVector));
    }


    Vector reflect(Vector v, Vector n) {
        return u.subtract(v, u.scalar(n, 2.0 * u.dotProduct(v, n)));
        //return v - 2 * dot(v, n)*n;
    }

    public Vector refract(Vector initial, Vector n, double nt) {
        double cosI = u.dotProduct(u.scalar(initial, -1), n);
        double temp = 1.0 - nt * nt * (1.0 - cosI * cosI);

        return u.scalar(u.add(initial, u.scalar(n, nt * cosI - Math.sqrt(temp))), nt);

    }
}
