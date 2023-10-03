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
        Vector reflected = u.subtract(unitDirection, u.scalar(u.scalar(correctedNormal, u.dotProduct(unitDirection, correctedNormal)), 2.0));
        return new MaterialData(true, albedo, new Ray(rec.contactPoint, reflected));
    }
}
