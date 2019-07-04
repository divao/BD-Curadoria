package DAO;

import Tabelas.MaterialComentarios;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_MaterialComentarios {
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
    
    public String insere(Connection con, MaterialComentarios materialComentarios){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "materialComentarios(idMaterial, comentario) "
                    + "values (?,?)");
            insere.setInt(1, materialComentarios.getIdMaterial());
            insere.setString(2, materialComentarios.getComentario());
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
                    + "materialComentarios where idMaterial = ?");
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
			stm = con.prepareStatement("select * from materialComentarios");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<MaterialComentarios> d = new ArrayList<MaterialComentarios>();
			int idMaterial;
			String comentario;
			//Usar os dados e mostra-los
			while(rs.next()){
				idMaterial = rs.getInt("idMaterial");
				comentario = rs.getString("comentario");
				MaterialComentarios materialComentarios1 = new MaterialComentarios();
				materialComentarios1.setIdMaterial(idMaterial);
				materialComentarios1.setComentario(comentario);
				d.add(materialComentarios1);
			}
			
			for(MaterialComentarios umMaterialComentarios : d){
				System.out.printf("\n%d\t%s\n\n\n",
						umMaterialComentarios.getIdMaterial(),
						umMaterialComentarios.getComentario());
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
