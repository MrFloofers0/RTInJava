import java.lang.Math.*;

public class VectorUtil {
    public Vector add(Vector first, Vector second) {
        return new Vector(
                first.x + second.x,
                first.y + second.y,
                first.z + second.z);
    }

    public Vector subtract(Vector src, Vector src2) {
        return new Vector(
                src.x - src2.x,
                src.y - src2.y,
                src.z - src2.z
        );
    }

    public Vector square(Vector src) {
        return new Vector(
                src.x * src.x,
                src.y * src.y,
                src.z * src.z
        );
    }

    public Vector scalar(Vector src, double scalar) {
        return new Vector(
                src.x * scalar,
                src.y * scalar,
                src.z * scalar
        );
    }

    public double dotProduct(Vector src, Vector src2) {
        return (
                (src.x * src2.x) +
                        (src.y * src2.y) +
                        (src.z * src2.z)
        );
    }

    public Vector crossProduct(Vector src, Vector src2) {
        return new Vector(
                (src.y * src2.z) - (src.z * src2.y),
                (src.z * src2.x) - (src.x * src2.z),
                (src.x * src2.y) - (src.y * src2.x)
        );
    }

    public Vector unitVector(Vector src) {
        double hyp = Math.sqrt((src.x * src.x) + (src.y * src.y) + (src.z * src.z));
        return new Vector(src.x/hyp, src.y/hyp, src.z/hyp);
    }

    public double length(Vector src){
        return Math.sqrt((src.x * src.x) + (src.y * src.y) + (src.z * src.z));
    }
}
