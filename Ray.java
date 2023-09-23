public class Ray {
    private Vector direction;
    private Vector origin;
    private Color raycolor = new Color((short) 0, (short) 0, (short) 0);

    private VectorUtil u = new VectorUtil();
    public Ray(Vector origin, Vector direction) {
        this.direction = direction;
        this.origin = origin;
    }

    public Vector pt(double t) {
        return u.add(
                origin,
                u.scalar(direction, t)
        );
    }

    public Vector getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

    public Color rayColor(){
        return raycolor;
    }

    public void setColor(Color c){
        raycolor = c;
    }
}
