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
        return new Vector(src.x / hyp, src.y / hyp, src.z / hyp);
    }

    public Vector randomDirection() {
        Vector rand = new Vector(Math.random(), Math.random(), Math.random());
        return unitVector(rand);
    }
    public Vector randomInUnitSphere(){
        while (true) {
            Vector rand = new Vector(Math.random(), Math.random(), Math.random());
            if(lengthSquared(rand) < 1){
                return rand;
            }
        }

    }

    public Vector randomUnitVector(){
        return unitVector(randomInUnitSphere());
    }

    public Vector randomOnHemisphere2(Vector normal){
        Vector onUsp = randomUnitVector();
        if(dotProduct( normal, onUsp) > 0){
            return onUsp;
        }
        return scalar(onUsp, -1);
    }

    public double length(Vector src) {
        return Math.sqrt((src.x * src.x) + (src.y * src.y) + (src.z * src.z));
    }

    public double lengthSquared(Vector src){
        double l = length(src);
        return l * l;
    }

    public Vector randomOnHemisphere(Vector normal) {
        Vector tempDir = randomDirection();
        double temp = dotProduct(normal, tempDir);

        if (temp < 0) {

            return scalar(tempDir, -1.0);
        }
        return unitVector(tempDir);
    }
}
