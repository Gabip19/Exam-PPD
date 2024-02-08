package domain;

public class Entry {
    private int id_agent;
    private int id_apel;
    private int dificultate;

    public Entry(int id_agent, int id_apel, int dificultate) {
        this.id_agent = id_agent;
        this.id_apel = id_apel;
        this.dificultate = dificultate;
    }

    public int getId_agent() {
        return id_agent;
    }

    public void setId_agent(int id_agent) {
        this.id_agent = id_agent;
    }

    public int getId_apel() {
        return id_apel;
    }

    public void setId_apel(int id_apel) {
        this.id_apel = id_apel;
    }

    public int getDificultate() {
        return dificultate;
    }

    public void setDificultate(int dificultate) {
        this.dificultate = dificultate;
    }

    @Override
    public String toString() {
        return "id_agent: " + id_agent + " id_apel: " + id_apel + " dificultate: " + dificultate;
    }
}
