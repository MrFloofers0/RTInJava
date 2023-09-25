public class HittableList extends Hittable {
    int length;
    Hittable[] list = new Hittable[length];

    public HittableList(Hittable[] hitList) {
        length = hitList.length;
        list = hitList;
    }

    public boolean hit(Ray r, Interval rayRange, Hittable rec) {
        Hittable tempRecord = rec;
        boolean hitAnything = false;
        double currentClosest = rayRange.max;

        for (Hittable hittable : list) {

            if (hittable.hit(r, new Interval(rayRange.min, currentClosest), tempRecord)) {
                hitAnything = true;
                currentClosest = tempRecord.t;
                rec = tempRecord;
            }
        }
        return hitAnything;
    }

}
