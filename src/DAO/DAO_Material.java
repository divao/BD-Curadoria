package DAO;

import Tabelas.Material;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_Material {
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
    
    public String insere(Connection con, Material material){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "java.dvd(idMaterial,idArea,tipo,nome,link) "
                    + "values (?,?,?,?,?)");
            insere.setInt(1, material.getIdMaterial());
            insere.setInt(2, material.getIdArea());
            insere.setString(3, material.getTipo());
            insere.setString(6, material.getNome());
            insere.setString(7, material.getLink());
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
    
    public String remove(Connection con, int idMaterial){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement remove;
            remove = con.prepareStatement("delete from "
                    + "material where idMaterial = ?");
            remove.setInt(1, idMaterial);
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
			stm = con.prepareStatement("select * from material");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<Material> d = new ArrayList<Material>();
			int idMaterial;
                        int idArea;
                        String tipo;
                        String nome;
                        String link;
			//Usar os dados e mostra-los
			while(rs.next()){
				idMaterial = rs.getInt("idMaterial");
				idArea = rs.getInt("idArea");
				tipo = rs.getString("tipo");
				nome = rs.getString("nome");
                                link = rs.getString("link");
                                Material material1 = new Material();
				material1.setIdMaterial(idMaterial);
				material1.setIdArea(idArea);
				material1.setTipo(tipo);
				material1.setNome(nome);
                                material1.setLink(link);
				d.add(material1);
			}
			
			for(Material umMaterial : d){
				System.out.printf("\n%d\t\t\t\t%d\t\t\t%s\t\t%s\t%s\n\n\n",
						umMaterial.getIdMaterial(),
						umMaterial.getIdArea(),
						umMaterial.getTipo(),
						umMaterial.getNome(),
                                                umMaterial.getLink());
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
