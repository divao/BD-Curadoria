package DAO;

import Tabelas.Interage;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_Interage {
    public Connection getConnection(String connectionString, String login, String pwd){
        Connection con = null;
        try {
            con = DriverManager.getConnection(connectionString,login,pwd); 
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally{
            return con;
        }
    }
    
    public String insere(Connection con, Interage interage){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "interage(idUsuario, idMaterial) "
                    + "values (?,?)");
            insere.setInt(1, interage.getIdUsuario());
            insere.setInt(2, interage.getIdMaterial());
            //Executar o comando
            qtdeLinhasAfetadas = insere.executeUpdate();
            //Fechar o comando e a conex�o
            insere.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally{
            return "Quantidade de linhas afetadas: " + qtdeLinhasAfetadas; 
        }
    }
    
    public String remove(Connection con, int idUsuario, int idMaterial){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement remove;
            remove = con.prepareStatement("delete from "
                    + "interage where idUsuario = ? AND idMaterial = ?");
            remove.setInt(1, idUsuario);
            remove.setInt(2, idMaterial);
            //Executar o comando
            qtdeLinhasAfetadas = remove.executeUpdate();
            //Fechar o comando e a conex�o
            remove.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally{
            return "Quantidade de linhas afetadas: " + qtdeLinhasAfetadas; 
        }        
    }
    
    
    public void seleciona(Connection con){
        PreparedStatement stm = null;
	ResultSet rs = null;
	ResultSetMetaData md = null;
        try{
			//Criar o comando
			stm = con.prepareStatement("select * from interage");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<Interage> d = new ArrayList<Interage>();
			int idUsuario;
			int idMaterial;
			//Usar os dados e mostra-los
			while(rs.next()){
				idUsuario = rs.getInt("idUsuario");
				idMaterial = rs.getInt("idMaterial");
				Interage interage1 = new Interage();
				interage1.setIdUsuario(idUsuario);
				interage1.setIdMaterial(idMaterial);
				d.add(interage1);
			}
			
			for(Interage umInterage : d){
				System.out.printf("\n%d\t%d\n\n\n",
						umInterage.getIdUsuario(),
						umInterage.getIdMaterial());
			}
			//Fechar os objetos
			rs.close();
			stm.close();
			con.close();	
		}catch(SQLException e){
			System.err.println(e);
		}catch(Exception e){
			System.err.println(e);
		}
    }
}
