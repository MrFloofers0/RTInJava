public class Interval {
    public double min = Double.NEGATIVE_INFINITY;
    public double max = Double.POSITIVE_INFINITY;

    public Interval() {

    }

    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    boolean contains(double num) {
        return (num <= max && min <= num);
    }

    boolean surrounds(double num) {
        return !contains(num);
    }

    double clamp(double num) {
        if (num < min) {
            return min;
        } else if (num > max) {
            return max;
        }
        return num;
    }
}
