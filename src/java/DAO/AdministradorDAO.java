package DAO;

import Classes.Administrador;
import Framework.Conexao;
import Framework.InterfaceConexao;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdministradorDAO implements InterfaceConexao {
    
    private Connection conexao = null;
    public ResultSet rs = null;
    public Statement st = null;
    
    public AdministradorDAO(){
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
            String sql = "SELECT administradores.cpf, usuarios.cpf FROM administradores, usuarios WHERE (administradores.cpf = '" + cpf + "' OR usuarios.cpf = '" + cpf + "') AND administradores.id <> " +id;
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
        
        Administrador administrador = (Administrador) Item;
        
        String sql = "INSERT into administradores (nome, cpf, senha) values ('" + administrador.getNome() + "', '" + administrador.getCpf() + "', '" + administrador.getSenha() + "')";
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }   
        
    }

    @Override
    public void Altera(Object Item) {
        
        Administrador administrador = (Administrador) Item;
        
        String sql = "UPDATE administradores SET nome = '" + administrador.getNome() + "', cpf = '" + administrador.getCpf() + "', senha = '" + administrador.getSenha() + "' WHERE id = '" + administrador.getId() + "'";
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }           

    }

    @Override
    public void Deleta(int id) {
        
        String sql = "DELETE FROM administradores where id = '" + id + "'";
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }   
        
    }

    @Override
    public Object Retorna(int id) {
        
        Administrador administrador = new Administrador();
        
        String sql = "SELECT * FROM administradores where id = '" + id + "'";
        
        try{            
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
            while (rs.next() != false) {
                administrador.setId(rs.getInt(1));
                administrador.setNome(rs.getString(2));
                administrador.setCpf(rs.getString(3));
                administrador.setSenha(rs.getString(4));
            }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return administrador;
       
    }

    @Override
    public ArrayList<Administrador> RetornaTodos() {
        ArrayList<Administrador> listaAdministradores = new ArrayList<>();
        
        String sql = "SELECT * FROM administradores";
        
        try{            
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
                while(rs.next()){
                
                    Administrador administrador = new Administrador();
                    administrador.setId(rs.getInt(1));
                    administrador.setNome(rs.getString(2));
                    administrador.setCpf(rs.getString(3));
                    administrador.setSenha(rs.getString(4));
                    
                    listaAdministradores.add(administrador);
                    
                }
                   
        } catch(SQLException e){
            out.println(e);
        }
        
        return listaAdministradores;
    }
    
}
