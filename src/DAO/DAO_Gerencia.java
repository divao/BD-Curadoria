package DAO;

import Tabelas.Gerencia;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_Gerencia {
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
    
    public String insere(Connection con, Gerencia gerencia){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "gerencia(idCurador, idArea) "
                    + "values (?,?)");
            insere.setInt(1, gerencia.getIdCurador());
            insere.setInt(2, gerencia.getIdArea());
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
    
    public String remove(Connection con, int idCurador, int idArea){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement remove;
            remove = con.prepareStatement("delete from "
                    + "interage where idCurador = ? AND idArea = ?");
            remove.setInt(1, idCurador);
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
			stm = con.prepareStatement("select * from gerencia");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<Gerencia> d = new ArrayList<Gerencia>();
			int idCurador;
			int idArea;
			//Usar os dados e mostra-los
			while(rs.next()){
				idCurador = rs.getInt("idCurador");
				idArea = rs.getInt("idArea");
				Gerencia gerencia1 = new Gerencia();
				gerencia1.setIdCurador(idCurador);
				gerencia1.setIdArea(idArea);
				d.add(gerencia1);
			}
			
			for(Gerencia umGerencia : d){
				System.out.printf("\n%d\t%d\n\n\n",
						umGerencia.getIdCurador(),
						umGerencia.getIdArea());
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
