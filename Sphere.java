import java.lang.Math;

public class Sphere extends Hittable {
    private Vector center;
    private double radius;
    private VectorUtil u = new VectorUtil();

    public Sphere(Vector center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public boolean hit(Ray r, double rayTmin, double rayTmax, hitRecord rec) {
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
        if (root <= rayTmin || rayTmax <= root) {

            root = (-halfB + sqrtd) / a;

            if (root <= rayTmin || rayTmax <= root) {

                return false;

            }
        }

        rec.t = root;
        rec.p = r.pt(rec.t);
        rec.normal = u.scalar(u.subtract(rec.p, center), (1.0 / radius));
        Vector outNormal = u.scalar(u.subtract(rec.p, center), (1.0 / radius));
        rec.setFaceNormal(r, outNormal);

        return true;
    }
}
