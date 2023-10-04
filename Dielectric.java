public class Dielectric extends Material {
    private VectorUtil u = new VectorUtil();

    private double refIndex;
    private Color tint;
    private boolean inverted;

    public Dielectric(double refractiveIndex, Color tint, boolean inverted) {
        this.refIndex = refractiveIndex;
        this.tint = tint;
        this.inverted = inverted;
    }

    public static double calculateSchlickApproximation(double n1, double n2, double cosI) {
        double r0 = Math.pow((n1 - n2) / (n1 + n2), 2);
        return r0 + (1 - r0) * Math.pow(1 - cosI, 5);
    }

    @Override
    public MaterialData Scatter(Vector dirIn, Hittable rec, Color attenuation, Ray scattered) {

        Vector unitDirection = u.unitVector(rec.contactPoint);
        double etaRatio;
        Vector correctedNormal;
        if(!inverted) {
            etaRatio = u.dotProduct(rec.normal, unitDirection) > 0 ? (1 / refIndex) : refIndex;
            correctedNormal = (u.dotProduct(unitDirection, rec.normal) > 0) ? u.scalar(rec.normal, -1) : rec.normal;
        } else {
            etaRatio = u.dotProduct(rec.normal, unitDirection) > 0 ? refIndex : (1 / refIndex);
            correctedNormal = u.dotProduct(unitDirection, rec.normal) > 0 ? rec.normal : u.scalar(rec.normal, -1);
        }
        double cosI = u.dotProduct(correctedNormal, u.scalar(unitDirection, -1));
        double sinI = Math.sqrt(1 - cosI * cosI);
        double cosT = Math.sqrt(1 - ((etaRatio * etaRatio) * (sinI * sinI)));


        double n1 = u.dotProduct(rec.normal, unitDirection) < 0 ? 1.0 : refIndex;
        double n2 = u.dotProduct(rec.normal, unitDirection) < 0 ? refIndex : 1.0;
        double reflectancePerpendicular = Math.pow(((n1 * cosI) - (n2 * cosT)) / ((n1 * cosI) + (n2 * cosT)), 2);
        double reflectanceParallel = Math.pow(((n2 * cosI) - (n1 * cosT)) / ((n2 * cosI) + (n1 * cosT)), 2);

        double reflectance = calculateSchlickApproximation(n1, n2, cosI);

        Vector rPrime = refract(rec.normal, rec.normal, etaRatio);
        Vector reflected = u.subtract(unitDirection, u.scalar(u.scalar(correctedNormal, u.dotProduct(unitDirection, correctedNormal)), 2.0));

        Vector returnVector;

        if ((sinI * etaRatio) > 1.0 && n1 > n2) {
            // Must reflect
            returnVector = reflected;
        } else {
            // Can refract

            returnVector = reflectance > Math.random() ? reflected : rPrime;
            //returnVector = rPrime;
        }
        if(n1 < n2) {
            return new MaterialData(true, tint, new Ray(rec.contactPoint, returnVector));
        } else {
            return new MaterialData(true, new Color(1.0,1.0, 1.0), new Ray(rec.contactPoint, returnVector));
        }
    }

    public Vector refract(Vector initial, Vector n, double nt) {
        double cosI = u.dotProduct(u.scalar(initial, -1), n);
        double temp = 1.0 - nt * nt * (1.0 - cosI * cosI);


        return u.add(u.scalar(initial, nt), u.scalar(n, nt * cosI - Math.sqrt(Math.abs(temp))));
    }
}
