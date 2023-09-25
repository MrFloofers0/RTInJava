public class HittableList extends Hittable {
    int length;
    Hittable[] list = new Hittable[length];

    public HittableList(Hittable[] hitList) {
        length = hitList.length;
        list = hitList;
    }

    public boolean hit(Ray r, double rayTmin, double rayTmax, Hittable rec) {
        Hittable tempRecord = rec;
        boolean hitAnything = false;
        double currentClosest;

        for (Hittable hittable : list) {

            if (hittable.hit(r, rayTmin, rayTmax, tempRecord)) {
                hitAnything = true;
                currentClosest = tempRecord.t;
                rec = tempRecord;
                break;
            }
        }
        return hitAnything;
    }
}
