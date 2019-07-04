package DAO;

import Tabelas.UsuarioAreasInt;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_UsuarioAreasInt {
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
    
    public String insere(Connection con, UsuarioAreasInt usuarioAreasInt){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "usuarioAreasInt(idUsuario, areasInt) "
                    + "values (?,?)");
            insere.setInt(1, usuarioAreasInt.getIdUsuario());
            insere.setString(2, usuarioAreasInt.getAreasInt());
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
    
    public String remove(Connection con, int idUsuario){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement remove;
            remove = con.prepareStatement("delete from "
                    + "usuarioAreasInt where idUsuario = ?");
            remove.setInt(1, idUsuario);
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
			stm = con.prepareStatement("select * from usuarioAreasInt");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<UsuarioAreasInt> d = new ArrayList<UsuarioAreasInt>();
			int idUsuario;
			String areasInt;
			//Usar os dados e mostra-los
			while(rs.next()){
				idUsuario = rs.getInt("idUsuario");
				areasInt = rs.getString("areasInt");
				UsuarioAreasInt usuarioAreasInt1 = new UsuarioAreasInt();
				usuarioAreasInt1.setIdUsuario(idUsuario);
				usuarioAreasInt1.setAreasInt(areasInt);
				d.add(usuarioAreasInt1);
			}
			
			for(UsuarioAreasInt umUsuarioAreasInt : d){
				System.out.printf("\n%d\t%s\n\n\n",
						umUsuarioAreasInt.getIdUsuario(),
						umUsuarioAreasInt.getAreasInt());
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
