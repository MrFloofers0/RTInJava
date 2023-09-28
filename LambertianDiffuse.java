public class LambertianDiffuse extends Material {

    VectorUtil u = new VectorUtil();
    private Color albedo;
    private double absorption;
    private Interval scatterClamp = new Interval(0, 1);

    /**
     * @param albedo        the color of the object
     * @param scatterChance the chance from 0.0-1.0 that a given ray hitting the object will be absorbed.
     */
    public LambertianDiffuse(Color albedo, double scatterChance) {
        this.albedo = albedo;
        absorption = scatterClamp.clamp(scatterChance);
    }

    @Override
    public MaterialData Scatter(Vector dirIn, Hittable rec, Color attenuation, Ray scattered) {
        boolean rayHits = true;
        Vector scatterDirection = u.add(rec.normal, u.randomUnitVector());
        if (u.length(scatterDirection) < 0.0001){
            scatterDirection = rec.normal;
        }
        Ray scatterRay = new Ray(rec.contactPoint, scatterDirection);
        if(Math.random() < absorption){
            rayHits = false;
        }

        return new MaterialData(rayHits, new Color(
                albedo.getRedDouble() - absorption,
                albedo.getGreenDouble() - absorption,
                albedo.getBlueDouble() - absorption),
                scatterRay);
    }
}
