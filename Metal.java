public class Metal extends Material{
    private VectorUtil u = new VectorUtil();
    private Color albedo;
    private double fuzz;
    public Metal(Color albedo, double fuzz){
        this.albedo = albedo;
        this.fuzz = fuzz;
    }


    @Override
    public MaterialData Scatter(Vector dirIn, Hittable rec, Color attenuation, Ray scattered) {
        Vector unitDirection = u.unitVector(dirIn);
        Vector correctedNormal = (u.dotProduct(unitDirection, rec.normal) > 0) && !rec.frontFace ? u.scalar(rec.normal, -1) : rec.normal;
        Vector reflected = u.add(unitDirection, u.scalar(u.scalar(correctedNormal, Math.abs(u.dotProduct(unitDirection, correctedNormal))), 2.0));
        Vector fuzzed = u.unitVector(u.add(reflected, u.scalar(u.randomUnitVector(), fuzz)));
        if(u.dotProduct(rec.normal, fuzzed) > 0) {
            return new MaterialData(true, albedo, new Ray(rec.contactPoint, fuzzed));
        }
        return new MaterialData(false, albedo, new Ray(rec.contactPoint, fuzzed));
    }
}
