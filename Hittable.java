public abstract class Hittable {
    public abstract boolean hit(Ray r, Interval rayRange, Hittable rec);

    public VectorUtil u = new VectorUtil();
    public Vector contactPoint;
    public Vector normal;
    public double t;
    public boolean frontFace;
    Material mat;

    public void setFaceNormal(Ray r, Vector outNormal) {
        frontFace = u.dotProduct(r.getDirection(), outNormal) < 0;
        if (frontFace) {
            normal = outNormal;
        } else {
            normal = u.scalar(outNormal, -1.0);
        }

    }
}
