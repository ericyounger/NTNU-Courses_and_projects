package sample;

public class Valuta {
    private String valutanavn;
    private double kurs;
    private int enhet;

    public Valuta(String valutanavn, double kurs, int enhet){
        this.valutanavn = valutanavn;
        this.kurs = kurs;
        this.enhet = enhet;
    }

    public String getValutanavn() {
        return valutanavn;
    }

    public void setValutanavn(String valutanavn) {
        this.valutanavn = valutanavn;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public int getEnhet() {
        return enhet;
    }

    public void setEnhet(int enhet) {
        this.enhet = enhet;
    }
}
