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

        double etaRatio = rec.frontFace ? (1.0 / refIndex) : refIndex;
        double cosThetaI = Math.min(
                u.dotProduct(u.scalar(u.unitVector(rec.contactPoint), -1.0), u.unitVector(rec.normal)),
                1.0
        );
        double sinSquaredThetaT = Math.pow(etaRatio, 2) * (1 - Math.pow(cosThetaI, 2));
        double cosineThetaT = Math.sqrt(1 - sinSquaredThetaT);
        double sinThetaI = Math.sqrt(1 - (cosThetaI * cosThetaI));

        Vector unitDir = u.unitVector(rec.contactPoint);
        Vector unitNorm = u.unitVector(rec.normal);
        double n1 = rec.frontFace ? 1.0 : refIndex;
        double n2 = rec.frontFace ? refIndex : 1.0;

        double reflectPerp = Math.pow((n1 * cosThetaI - n2 * cosineThetaT) / (n1 * cosThetaI + n2 * cosineThetaT), 2);
        double reflectPara = Math.pow((n2 * cosThetaI - n1 * cosineThetaT) / (n2 * cosThetaI + n1 * cosineThetaT), 2);

        double reflectOverall = (reflectPara + reflectPerp) / 2;
        double transmittance = 1 - reflectOverall;

        Vector reflected = u.unitVector(u.add(unitDir, u.scalar(unitNorm, 2.0)));
        Vector refracted = refract(unitDir, unitNorm, etaRatio);

        Vector returnVector = transmittance < (Math.random() * 0.5) ? reflected : refracted;
        //Vector returnVector = refracted;
        return new MaterialData(true, tint, new Ray(rec.contactPoint, returnVector));
    }

    public Vector refract(Vector directionVector, Vector normalVector, double etaRatio) {

        double cosTheta = Math.min(u.dotProduct(u.scalar(directionVector, -1.0), normalVector), 1.0);
        Vector rOutPerp = u.scalar(u.add(directionVector, u.scalar(normalVector, cosTheta)), etaRatio);
        Vector rOutPara = u.scalar(normalVector, -Math.sqrt(Math.abs(1 - (u.length(rOutPerp) * u.length(rOutPerp)))));

        return u.add(rOutPerp, u.scalar(rOutPara, 100000)); // do NOT FUCKING TOUCH THIS. DON'T FUCKING THINK ABOUT IT. DO NOT FUCKING TOUCH
        //I HAVE NO IDEA WHY THIS WORKS BUT I SPENT 6 HOURS TROUBLESHOOTING AND THIS WORKED


    }
}

