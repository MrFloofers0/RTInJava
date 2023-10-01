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
        double etaRatio = rec.frontFace ? (1 / refIndex) : refIndex;
        double cosI = u.dotProduct(rec.normal, u.scalar(unitDirection, -1));
        double sinI = Math.sqrt(1 - cosI * cosI);
        double cosT =   Math.sqrt(1 - ((etaRatio * etaRatio) * (1 - cosI * cosI)));


        double n1 = rec.frontFace ? 1.0 : refIndex;
        double n2 = rec.frontFace ? refIndex : 1.0;
        double reflectancePerpendicular = Math.pow(((n1 * cosI) - (n2 * cosT)) / ((n1 * cosI) + (n2 * cosT)), 2);
        double reflectanceParallel = Math.pow(((n2 * cosI) - (n1 * cosT)) / ((n2 * cosI) + (n1 * cosT)), 2);
        double reflectance = (reflectancePerpendicular + reflectanceParallel) / 2;

        Vector rPrime = refract(u.unitVector(rec.normal), rec.normal, etaRatio);
        Vector reflected = u.add(unitDirection, u.scalar(rec.normal, u.dotProduct(unitDirection, rec.normal)));

        System.out.println(((n1 * cosI) + (n2 * cosT))  );
        Vector returnVector = (sinI * etaRatio) > 1.0 || reflectance > Math.random() ? reflected : rPrime;
        return new MaterialData(true, tint, new Ray(rec.contactPoint, returnVector));
    }


    Vector reflect(Vector v, Vector n) {
        return u.subtract(v, u.scalar(n, 2.0 * u.dotProduct(v, n)));
        //return v - 2 * dot(v, n)*n;
    }

    public Vector refract(Vector initial, Vector n, double nt) {
        Vector unitinitial = u.unitVector(initial);
        double cosI = -1.0 * u.dotProduct(unitinitial, n);
        double temp = 1.0 - nt * nt * (1.0 - cosI * cosI);

        return u.scalar(u.add(initial, u.scalar(n, nt * cosI - Math.sqrt(temp))), nt);

    }
}
