public class HittableList extends Hittable {
    int length;
    Hittable[] list = new Hittable[length];

    public HittableList(Hittable[] hitList) {
        length = hitList.length;
        list = hitList;
    }

    public boolean hit(Ray r, double rayTmin, double rayTmax, hitRecord rec) {
        hitRecord tempRecord = rec;
        boolean hitAnything = false;
        double currentClosest = rayTmax;

        for (Hittable hittable : list) {

            if (hittable.hit(r, rayTmin, currentClosest, tempRecord)) {
                hitAnything = true;
                currentClosest = tempRecord.t;
                rec = tempRecord;
                break;
            }
        }
        return hitAnything;
    }
}
