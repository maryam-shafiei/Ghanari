import java.sql.*;

public class Main{
    public static void main(String[] args){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ghanari", "root", "12345678");
            //GUI gui = new GUI(connection);
            UserInterface UI = new UserInterface(connection);
            UI.start();
        }catch (Exception e){
            System.out.println("Connection Mysql: "+e.getMessage());
        }

    }
}