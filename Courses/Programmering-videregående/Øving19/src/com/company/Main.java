package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Database con = null;

        try {

            con = new Database();
            // Registrerer en ny bok
            Bok sult = new Bok("8205248931","Sult","Knut Hamsun");

            //status på om boken er registrert allerede.
            System.out.println("Bok registrert: " + con.regNyBok(sult));

            // Låner ut bok
            System.out.println("Test 1: " +con.lånUtEksemplar("8205248931", "Eric Younger",1));
            //tester med feil eksnr
            System.out.println("Test 2: " + con.lånUtEksemplar("8205248931", "Eric Younger",25));
            // tester med feil isbn nr
            System.out.println("Test 3: " + con.lånUtEksemplar("8205248939", "Eric Younger",1));


            // Registrer nye eksempler av en bok
            // tester regNyttEksemplar
            System.out.println("Test 4: " + con.regNyttEksemplar("8205248931"));
            // tester et isbn som ikke finnes med metoden regNyttEksemplar
            System.out.println("Test 5: " + con.regNyttEksemplar("2"));

        } catch(SQLException e){
            System.out.println("Epic fail Exception");
            con.rollBack();
            con.turnOnAutoCommit();
            e.printStackTrace();
        } finally {
            con.lukkForbindelse();
        }

    }
}
