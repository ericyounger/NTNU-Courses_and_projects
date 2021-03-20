import java.sql.*;

class Boklaner{
    public static void main(String[]args){
        String password = "kXQ5n8vA";
        String url= "jdbc:mysql://mysql.stud.idi.ntnu.no:3306/ericy?user=ericy&password=" + password;

        System.out.println("Skriv inn navn");
        String navn = s.nextLine();
        System.out.println("\nSkriv inn isbn nr");
        String isbn = s.nextLine();
        System.out.println("\nSkriv inn eksemplar:");
        int eksemplar = s.nextInt();


        try(Connection con = DriverManager.getConnection(url);){
            Statement stmt = con.createStatement();
            ResultSet res2 = stmt.executeQuery("UPDATE eksemplar SET laant_av = '" + navn + "''WHERE isbn = '" + isbn+ "' AND eks_nr = " + eksemplar + " AND laant_av is null;");
            System.out.println(res2.executeUpdate());



        }catch(SQLException sql){
            sql.printStackTrace();
        }




    }
}