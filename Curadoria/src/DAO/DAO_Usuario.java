package DAO;

import Tabelas.Usuario;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DAO_Usuario {
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
    
    public String insere(Connection con, Usuario usuario){
        int qtdeLinhasAfetadas = 0;
        try {
            PreparedStatement insere;
            insere = con.prepareStatement("insert into "
                    + "usuario(nome,idade,email) "
                    + "values (?,?,?)");
            insere.setString(1, usuario.getNome());
            insere.setString(2, usuario.getIdade());
            insere.setString(3, usuario.getEmail());
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
                    + "usuario where idUsuario = ?");
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
			stm = con.prepareStatement("select * from usuario");
			rs = stm.executeQuery();
			//Criar o metadado da tabela
			md = rs.getMetaData();
			int nroColunas = md.getColumnCount();
			//Exibir os metadados/dados
			for(int i = 1; i <= nroColunas; i++)
				System.out.printf("%s\t\t",
						md.getColumnName(i));
			
			ArrayList<Usuario> d = new ArrayList<Usuario>();
			int idUsuario;
			String nome;
			String idade;
			String email;
			//Usar os dados e mostra-los
			while(rs.next()){
				idUsuario = rs.getInt("idUsuario");
				nome = rs.getString("nome");
				idade = rs.getString("idade");
				email = rs.getString("email");
				Usuario usuario1 = new Usuario();
				usuario1.setIdUsuario(idUsuario);
				usuario1.setNome(nome);
				usuario1.setIdade(idade);
				usuario1.setEmail(email);
				d.add(usuario1);
			}
			
			for(Usuario umUsuario : d){
				System.out.printf("\n%d\t\t\t%s\t\t%s\t%s\n\n\n",
						umUsuario.getIdUsuario(),
						umUsuario.getNome(),
						umUsuario.getIdade(),
						umUsuario.getEmail());
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
