import java.lang.Math;

public class Sphere extends Hittable {
    private Vector center;
    private double radius;
    private final VectorUtil u = new VectorUtil();

    private Material mat;
    public Sphere(Vector center, double radius, Material mat) {
        this.center = center;
        this.radius = radius;
        this.mat = mat;
    }

    @Override
    public boolean hit(Ray r, Interval tRange, Hittable rec) {
        Vector aMinusC = u.subtract(r.getOrigin(), center);

        double dirLength = u.length(r.getDirection());
        double a = dirLength * dirLength;
        double halfB = u.dotProduct(r.getDirection(), aMinusC);
        double acLength = u.length(aMinusC);
        double c = (acLength * acLength) - (radius * radius);

        double discriminant = halfB * halfB - (a * c);

        if (discriminant < 0) {
            return false;
        }

        double sqrtd = Math.sqrt(discriminant);

        double root = (-halfB - sqrtd) / a;
        if (tRange.surrounds(root)) {

            root = (-halfB + sqrtd) / a;

            if (tRange.surrounds((root))) {

                return false;

            }
        }

        rec.t = root;
        rec.contactPoint = r.pt(rec.t);
        rec.normal = u.unitVector(u.subtract(rec.contactPoint, center));
        rec.setFaceNormal(r, rec.normal);
        rec.mat = mat;
        return true;
    }
}
