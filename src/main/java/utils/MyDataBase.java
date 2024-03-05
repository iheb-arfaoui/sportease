package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDataBase {


        private static MyDataBase instance ;
        private final String URL ="jdbc:mysql://127.0.0.1:3308/3a22";
        private final String USERNAME="root";
        private final String PASSWORD ="";

        Connection cnx ;

        Statement ste;

        private MyDataBase(){

            try {
                cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);

                System.out.println("Connected ...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("___not connected___ ");

            }

        }

        public static MyDataBase getInstance(){
            if (instance == null)
                instance = new MyDataBase();

            return instance;
        }
        public Connection getCnx(){
            return cnx;
        }

}
