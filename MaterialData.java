public class MaterialData {

    public boolean hit;
    public Color materialColor;
    public Ray bouncedRay;


    public MaterialData(boolean hit, Color materialColor, Ray bouncedRay) {
        this.hit = hit;
        this.materialColor = materialColor;
        this.bouncedRay = bouncedRay;
    }
}
