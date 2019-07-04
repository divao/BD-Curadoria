package DAO;

import Tabelas.Segue;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_Segue {
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
    
    public String insere(Connection con, Segue segue){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "segue(idUsuario, idArea) "
                    + "values (?,?)");
            insere.setInt(1, segue.getIdUsuario());
            insere.setInt(2, segue.getIdArea());
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
    
    public String remove(Connection con, int idUsuario, int idArea){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement remove;
            remove = con.prepareStatement("delete from "
                    + "interage where idUsuario = ? AND idArea = ?");
            remove.setInt(1, idUsuario);
            remove.setInt(2, idArea);
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
			stm = con.prepareStatement("select * from segue");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<Segue> d = new ArrayList<Segue>();
			int idUsuario;
			int idArea;
			//Usar os dados e mostra-los
			while(rs.next()){
				idUsuario = rs.getInt("idUsuario");
				idArea = rs.getInt("idArea");
				Segue segue1 = new Segue();
				segue1.setIdUsuario(idUsuario);
				segue1.setIdArea(idArea);
				d.add(segue1);
			}
			
			for(Segue umSegue : d){
				System.out.printf("\n%d\t%d\n\n\n",
						umSegue.getIdUsuario(),
						umSegue.getIdArea());
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
