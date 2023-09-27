public abstract class Material {

    public abstract MaterialData Scatter(
            Vector dirIn,
            Hittable rec,
            Color attenuation,
            Ray scattered
    );
}
