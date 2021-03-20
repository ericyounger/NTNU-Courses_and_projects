import java.sql.DriverManager;
import java.sql.*;
import java.util.*;



class Bokfinner{
    public static void main(String[]args) throws Exception{
        String password = "kXQ5n8vA"; 
        String url = "jdbc:mysql://mysql.stud.idi.ntnu.no:3306/ericy?user=ericy&password=" + password;
        Scanner s = new Scanner(System.in);
        

        System.out.println("Skriv inn ISBN, så får du ut forfatter");
        String input = s.nextLine();

        try(Connection con = DriverManager.getConnection(url);){
            Statement stmt = con.createStatement();
            ResultSet res2 = stmt.executeQuery("SELECT forfatter, tittel FROM boktittel WHERE isbn='"+input+"'");


            
            
            while (res2.next()) {
                System.out.println("");
                System.out.println("Forfatter  = " + res2.getString("forfatter"));
                System.out.println("Tittel = " + res2.getString("tittel"));
            }
        } catch (SQLException sql) {
            System.out.println("SQL Exception");
            sql.printStackTrace();

        }
         
    }
}