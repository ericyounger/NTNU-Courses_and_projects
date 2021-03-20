package com.company;

import com.mysql.cj.protocol.Resultset;

import javax.xml.transform.Result;
import java.sql.*;

/* Ikke ferdig med regNyttEksemplar metoden,
har heller ikke lagt inn autocommit og transaksjonskontroll */

public class Database {
    private Connection con;
    private String databasenavn = "jdbc:mysql://mysql.stud.idi.ntnu.no:3306/ericy?user=ericy&password=kXQ5n8vA";

    public Database(){
        try {
            this.con = DriverManager.getConnection(databasenavn);
            System.out.println("Suksess connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void lukkForbindelse() throws SQLException {
            con.close();
    }

    public boolean regNyBok(Bok nyBok) throws SQLException {

        //søk etter bok for å se om den allerede er registrert
        String sok = "select * from boktittel where isbn=?";
        PreparedStatement prep = con.prepareStatement(sok);
        prep.setString(1,nyBok.getIsbn());
        ResultSet resultat = prep.executeQuery();
        int antResultat=0;
        while(resultat.next()){
            antResultat++;
        }
        if(antResultat!= 0){
            return false;
        }

        // Bok finnes ikke allerede, registerer den
        // Setter inn verdi for sqlsetning1
        String sqlSetning = "insert into boktittel(isbn,forfatter, tittel) values (?, ?, ?);";
        PreparedStatement setning = con.prepareStatement(sqlSetning);
        setning.setString(1, nyBok.getIsbn());
        setning.setString(2, nyBok.getForfatter());
        setning.setString(3, nyBok.getTittel());
        setning.executeUpdate();

        // Setter inn verdi for sqlsetning2
        String sqlSetning2 = "insert into eksemplar(isbn, eks_nr) values(?, 1);";
        PreparedStatement setning2 = con.prepareStatement(sqlSetning2);
        setning2.setString(1,nyBok.getIsbn());
        setning2.executeUpdate();
    return true;
    }



    public int regNyttEksemplar(String isbn) throws SQLException {

        turnOffAutoCommit();


        String nr_ant_eks = "select max(eks_nr) as max from eksemplar where isbn=?";
        PreparedStatement maxStatement = con.prepareStatement(nr_ant_eks);
        maxStatement.setString(1,isbn);
        ResultSet res = maxStatement.executeQuery();

        int maxReturnedNr=0;

        while(res.next()){
            if(res.getInt(1)== 0){
                con.rollback();
                turnOnAutoCommit();
                return 0;

            }
            maxReturnedNr = res.getInt(1) + 1; // Øker eksemplar med 1.

        }




        String insertEksemplar = "insert into eksemplar(isbn, eks_nr) values (?, ?);";
        PreparedStatement ps = con.prepareStatement(insertEksemplar);
        ps.setString(1,isbn);
        ps.setString(2, String.valueOf(maxReturnedNr));

        int resRows = ps.executeUpdate();
        con.commit();
        turnOnAutoCommit();

        return maxReturnedNr;
    }

    public boolean lånUtEksemplar(String isbn, String navn, int eksNr) throws SQLException {
        turnOffAutoCommit();
        String tallOmgjort = String.valueOf(eksNr);
        String utlan = "update eksemplar set laant_av =? where isbn=? and eks_nr =?;";
        PreparedStatement prepsy = con.prepareStatement(utlan);
        prepsy.setString(1,navn);
        prepsy.setString(2,isbn);
        prepsy.setString(3,tallOmgjort);

        int status = prepsy.executeUpdate();
        if(status == 0){
            con.rollback(); // ikke nødvendig egentlig, siden executeupdate returnerer 0 rader/tupler oppdatert.
            return false;
        }
        con.commit();
        turnOnAutoCommit();
        return true;
    }

    public void turnOffAutoCommit() throws SQLException {
        con.setAutoCommit(false);
    }

    public void rollBack() throws SQLException{
        con.rollback();
    }

    public void turnOnAutoCommit() throws SQLException {
        con.setAutoCommit(true);
    }
}
