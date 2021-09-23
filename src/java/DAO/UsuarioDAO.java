package DAO;

import Classes.Usuario;
import Framework.Conexao;
import Framework.InterfaceConexao;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO implements InterfaceConexao {
    
    private Connection conexao = null;
    public ResultSet rs = null;
    public Statement st = null;
    
    public UsuarioDAO(){
        conexao = Conexao.CriaConexao();
    }
    
    public boolean VerificaExistente(String cpf){      
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //st = conexao.createStatement();
            rs = st.executeQuery("SELECT administradores.cpf, usuarios.cpf FROM administradores, usuarios WHERE administradores.cpf = '" + cpf + "' OR usuarios.cpf = '" + cpf + "'");
            
            if(rs.next()){
                return true;
            } else {
                return false;
            }
            
        } catch(SQLException e){
            out.println(e);
            return true;
        } 
        
    } 
    
    public boolean VerificaExistente(String cpf, int id){      
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //st = conexao.createStatement();
            String sql = "SELECT administradores.cpf, usuarios.cpf FROM administradores, usuarios WHERE (administradores.cpf = '" + cpf + "' OR usuarios.cpf = '" + cpf + "') AND usuarios.id <> " +id;
            rs = st.executeQuery(sql);
            
            if(rs.next()){
                return true;
            } else {
                return false;
            }
            
        } catch(SQLException e){
            out.println(e);
            return true;
        } 
        
    }

    @Override
    public void Adiciona(Object Item) {
        
        Usuario usuario = (Usuario) Item;
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate("INSERT into usuarios (nome, cpf, senha, suspenso) values ('" + usuario.getNome() + "', '" + usuario.getCpf() + "', '" + usuario.getSenha() + "', '" + usuario.getSuspenso() + "')");
        } catch(SQLException e){
            out.println(e);
        }  
        
    }

    @Override
    public void Altera(Object Item) {
        
        Usuario usuario = (Usuario) Item;
        
        String sql = "UPDATE usuarios SET nome = '" + usuario.getNome() + "', cpf = '" + usuario.getCpf() + "', senha = '" + usuario.getSenha() + "', suspenso = '" + usuario.getSuspenso() + "' where id = '" + usuario.getId() + "'";
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        } 
    
    }

    @Override
    public void Deleta(int id) {
        
        String sql = "DELETE FROM usuarios where id = '" + id + "'";
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }
        
    }

    @Override
    public Object Retorna(int id) {
        Usuario usuario = new Usuario();
        
        String sql = "SELECT * FROM usuarios where id = '" + id + "'";
        
        try{            
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
            while (rs.next() != false) {
                usuario.setId(rs.getInt(1));
                usuario.setNome(rs.getString(2));
                usuario.setCpf(rs.getString(3));
                usuario.setSenha(rs.getString(4));
                usuario.setSuspenso(rs.getString(5));
            }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return usuario;
    }

    @Override
    public ArrayList<Usuario> RetornaTodos() {
        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        
        String sql = "SELECT * FROM usuarios";
        
        try{            
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
                while(rs.next()){
                
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt(1));
                    usuario.setNome(rs.getString(2));
                    usuario.setCpf(rs.getString(3));
                    usuario.setSenha(rs.getString(4));
                    usuario.setSuspenso(rs.getString(5));
                    
                    listaUsuario.add(usuario);
                    
                }
                   
        } catch(SQLException e){
            out.println(e);
        }
        
        return listaUsuario;
    }
    
}
