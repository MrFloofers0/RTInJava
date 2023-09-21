public class Ray {
    private Vector direction;
    private Vector origin;

    public Ray(Vector direction, Vector origin) {
        this.direction = direction;
        this.origin = origin;
    }

    public Vector pt(double t) {
        return VectorUtil.add(
                origin,
                VectorUtil.scalar(direction, t)
        );
    }

    public Vector getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }
}
