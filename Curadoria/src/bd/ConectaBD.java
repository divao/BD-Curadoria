package bd;

import Tabelas.Usuario;
import DAO.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConectaBD {
    
    static final String DATABASE_URL = "jdbc:postgresql://127.0.0.1:5432/Curadoria";
    
    public static Connection conectar() throws ClassNotFoundException{
        try{
            Class.forName("org.postgresql.Driver");
            Connection con;
            con = DriverManager.getConnection(DATABASE_URL, "postgres", "postgres");
            return con;
            
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
            return null;
        }
    }
}
