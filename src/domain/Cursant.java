package domain;

public class Cursant {
    private int id;
    private float medie;

    public Cursant(int id, float medie) {
        this.id = id;
        this.medie = medie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMedie() {
        return medie;
    }

    public void setMedie(float medie) {
        this.medie = medie;
    }

    @Override
    public String toString() {
        return "ID: " + id + " MEDIE: " + medie;
    }
}
