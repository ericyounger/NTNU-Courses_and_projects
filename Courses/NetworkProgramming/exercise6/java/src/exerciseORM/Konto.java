package exerciseORM;
import java.util.*;
import javax.persistence.*;
import java.io.*;



@Entity

public class Konto implements Serializable {
    @TableGenerator(
            name = "yourTableGenerator",
            allocationSize = 1,
            initialValue = 1)
    @Id
    @GeneratedValue(
            strategy=GenerationType.TABLE,
            generator="yourTableGenerator")
    private int id;
    @Version // Lock for task 4.
    private int laasingsFelt; // Lock for task 4.
    private String navn;
    private String kontonr;
    private double saldo;


    public Konto(){}

    public Konto(String navn, String kontonr, double saldo){
        this.navn = navn;
        this.kontonr = kontonr;
        this.saldo = saldo;
    }



    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getKontonr() {
        return kontonr;
    }

    public void setKontonr(String kontonr) {
        this.kontonr = kontonr;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    public void trekk(double belop){
        this.saldo -= belop;
    }

    public void settInn(double belop){this.saldo += belop;}

    public String toString(){
        return "Navn: " +navn + " Kontonr: " + kontonr + " Saldo: " + saldo;
    }



}
