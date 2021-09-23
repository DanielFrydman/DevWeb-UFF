package DAO;

import Classes.Categoria;
import Framework.Conexao;
import Framework.InterfaceConexao;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoriaDAO implements InterfaceConexao{
    
    private Connection conexao = null;
    public ResultSet rs = null;
    public Statement st = null;
    
    public CategoriaDAO(){
        conexao = Conexao.CriaConexao();
    }
    
    public boolean VerificaExistente(String descricao){    
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT descricao FROM categorias WHERE descricao = '" + descricao + "'";
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
    
    public boolean VerificaExistente(String descricao, int id){      
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT descricao FROM categorias WHERE descricao = '" + descricao + "' AND id <> " +id;
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

        Categoria categoria = (Categoria) Item;
        
        String sql = "INSERT into categorias (descricao) values ('" + categoria.getDescricao() + "')";
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }  
        
    }

    @Override
    public void Altera(Object Item) {
        
        Categoria categoria = (Categoria) Item;
        
        String sql = "UPDATE categorias SET descricao = '" + categoria.getDescricao() + "' WHERE id = '" + categoria.getId() + "'";
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }      
        
    }

    @Override
    public void Deleta(int id) {
        
        String sql = "DELETE FROM categorias where id = " + id;
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }
        
    }

    @Override
    public Object Retorna(int id) {
        
        Categoria categoria = new Categoria();
        
        String sql = "SELECT * FROM categorias WHERE id = '" + id + "'";
        
        try{            
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
            while (rs.next() != false) {
                categoria.setId(rs.getInt(1));
                categoria.setDescricao(rs.getString(2));
            }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return categoria;
        
    }

    @Override
    public ArrayList<Categoria> RetornaTodos() {
        ArrayList<Categoria> listaCategoria = new ArrayList<>();
        
        String sql = "SELECT * FROM categorias";
        
        try{            
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);

                while(rs.next()){
                
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt(1));
                    categoria.setDescricao(rs.getString(2));
                    
                    listaCategoria.add(categoria);
                    
                }
            
                     
        } catch(SQLException e){
            out.println(e);
        }
        
        return listaCategoria;
    }
    
    
    public String RetornaNomeCategoria (int id_categoria){
        
        String sql = "SELECT descricao FROM categorias WHERE id = '" + id_categoria + "'";
        
        try{
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            return rs.getString(2);
            
                
        } catch(SQLException e){
            out.println(e);
        }
        
        return null;
    }
    
}
