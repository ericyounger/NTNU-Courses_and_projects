package com.company;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

public class Database {
    private Connection con;
    private String databasenavn = "jdbc:mysql://mysql.stud.idi.ntnu.no:3306/ericy?user=ericy&password=kXQ5n8vA";

    public Database() throws SQLException {
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



    public ResultSet fakturer(int mndNr) throws SQLException {
        turnOffAutoCommit();
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        String sqlsetning = "select p.prosj_id, 'timer', kunde,sum(timefaktor*timelønn*ant_timer) as sum_kostnad from prosjekt\n" +
                "      inner join prosjektarbeid p on prosjekt.prosj_id = p.prosj_id\n" +
                "      inner join ansatt a on p.ans_id = a.ans_id\n" +
                "      where faktura_sendt is null\n" +
                "      and\n" +
                "      month(dato)=? group by p.prosj_id\n" +
                "union\n" +
                "select distinct p.prosj_id, tekst, kunde, beløp from prosjekt\n" +
                "     inner join prosjektarbeid p on prosjekt.prosj_id = p.prosj_id\n" +
                "     inner join prosjektkostnader p2 on prosjekt.prosj_id = p2.prosj_id\n" +
                "     inner join ansatt a on p.ans_id = a.ans_id where month(p.dato)=? and p2.faktura_sendt is null\n";
        PreparedStatement ps = con.prepareStatement(sqlsetning);
        ps.setInt(1,mndNr);
        ps.setInt(2,mndNr);
        ResultSet rs = ps.executeQuery();


        String updatesetning = "update prosjektkostnader set faktura_sendt = current_date where faktura_sendt is null and month(dato)=?;";
        String updatesetning2= "update prosjektarbeid set faktura_sendt = current_date where faktura_sendt is null and month(dato)=?;";
        PreparedStatement ps2 = con.prepareStatement(updatesetning);
        PreparedStatement ps3 = con.prepareStatement(updatesetning2);
        ps2.setInt(1,mndNr);
        ps3.setInt(1,mndNr);

        int updatedRows = ps2.executeUpdate();
        int updatedRows2 = ps3.executeUpdate();
        con.commit();
        turnOnAutoCommit();
        return rs;

    }


    public void turnOffAutoCommit() throws SQLException {
        con.setAutoCommit(false);
    }

    public void turnOnAutoCommit() throws SQLException {
        con.setAutoCommit(true);
    }

    public void rollBack() throws SQLException{
        con.rollback();
    }

}
