package com.company;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static String filnavn="regnskap.txt";

    public static void fakturer(int manedsnr, String filnavn){

    }


    public static void skrivRegTilFil(String filnavn, String info) throws IOException{
        FileWriter fw = new FileWriter(filnavn, true);
        PrintWriter skriver = new PrintWriter(new BufferedWriter(fw));
        skriver.println(info);
        skriver.close();
    }


    public static void main(String[] args) throws SQLException {

        Database con = null;

        try{



            con = new Database();


            ResultSet rs = con.fakturer(10);
            String linje = "";
            while(rs.next()){
                int prosjekt_id = rs.getInt(1);
                String kunde = rs.getString(2);
                String tekst = rs.getString(3);
                double sumKostnad = rs.getDouble(4);
                linje += prosjekt_id + "; " + kunde + "; " + tekst + "; " +sumKostnad + ";\n";

            }

            skrivRegTilFil(filnavn, linje);
            System.out.println("Write to file success");

        }catch (SQLException e){
            con.rollBack();
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            con.turnOnAutoCommit();
            con.lukkForbindelse();
        }
    }
}
