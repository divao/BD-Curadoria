package DAO;

import Tabelas.Curador;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_Curador {
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
    
    public String insere(Connection con, Curador curador){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "curador(idCurador, nivel) "
                    + "values (?,?)");
            insere.setInt(1, curador.getIdCurador());
            insere.setInt(2, curador.getNivel());
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
    
    public String remove(Connection con, int idCurador){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement remove;
            remove = con.prepareStatement("delete from "
                    + "curador where idCurador = ?");
            remove.setInt(1, idCurador);
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
			stm = con.prepareStatement("select * from curador");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<Curador> d = new ArrayList<Curador>();
			int idCurador;
			int nivel;
			//Usar os dados e mostra-los
			while(rs.next()){
				idCurador = rs.getInt("idCurador");
				nivel = rs.getInt("nivel");
				Curador curador1 = new Curador();
				curador1.setIdCurador(idCurador);
				curador1.setNivel(nivel);
				d.add(curador1);
			}
			
			for(Curador umCurador : d){
				System.out.printf("\n%d\t%d\n\n\n",
						umCurador.getIdCurador(),
						umCurador.getNivel());
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
