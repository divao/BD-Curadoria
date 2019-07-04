package DAO;

import Tabelas.Area;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_Area {
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
    
    public String insere(Connection con, Area area){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "area(idArea,nome,seguidores,numMateriais) "
                    + "values (?,?,?,?)");
            insere.setInt(1, area.getIdArea());
            insere.setString(2, area.getNome());
            insere.setInt(3, area.getSeguidores());
            insere.setInt(4, area.getNumMateriais());
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
    
    public String remove(Connection con, int idArea){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement remove;
            remove = con.prepareStatement("delete from "
                    + "area where idArea = ?");
            remove.setInt(1, idArea);
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
			stm = con.prepareStatement("select * from area");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<Area> d = new ArrayList<Area>();
			int idArea;
			String nome;
			int seguidores;
			int numMateriais;
			//Usar os dados e mostra-los
			while(rs.next()){
				idArea = rs.getInt("idArea");
				nome = rs.getString("nome");
				seguidores = rs.getInt("seguidores");
				numMateriais = rs.getInt("numMateriais");
				Area area1 = new Area();
				area1.setIdArea(idArea);
				area1.setNome(nome);
				area1.setSeguidores(seguidores);
				area1.setNumMateriais(numMateriais);
				d.add(area1);
			}
			
			for(Area umArea : d){
				System.out.printf("\n%d\t\t\t%s\t\t%d\t%d\n\n\n",
						umArea.getIdArea(),
						umArea.getNome(),
						umArea.getSeguidores(),
						umArea.getNumMateriais());
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
