/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Framework.Conexao;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Daniel
 */
public class LoginDAO {
    
    private Connection conexao = null;
    public ResultSet rs = null;
    public Statement st = null;
    
    public LoginDAO(){
        conexao = Conexao.CriaConexao();
    }
    
    public String FazLogin(String cpf, String senha){        
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery("SELECT * FROM administradores where cpf = '" + cpf + "' and senha = '" + senha + "'");
            
            if(rs.next()){
                return rs.getString(2);
            } else {
                st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("SELECT * FROM usuarios where cpf = '" + cpf + "' and senha = '" + senha + "'");
                if(rs.next()){
                    return rs.getString(2);
                } else {
                    return "";
                }
            }
            
        } catch(SQLException e){
            out.println(e);  
            return "";
        }
        
    }

    public String VerificaLogin(String cpf) {
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery("SELECT * FROM administradores where cpf = '" + cpf + "'");
            
            if(rs.next()){
                return "admin";
            } else {
                st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                rs = st.executeQuery("SELECT * FROM usuarios where cpf = '" + cpf + "'");
                if(rs.next()){
                    return "user";
                } else {
                    return "";
                }
            }
            
        } catch(SQLException e){
            out.println(e);  
            return "";
        }
        
    }
    
    public int VerificaID(String cpf) {
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery("SELECT * FROM usuarios where cpf = '" + cpf + "'");
            if(rs.next()){
                return rs.getInt(1);
            } else {
                return 0;
            }
            
        } catch(SQLException e){
            out.println(e);  
            return 0;
        }
    }
    
    public String VerificaSuspenso(String cpf) {
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery("SELECT * FROM usuarios where cpf = '" + cpf + "'");
            if(rs.next()){
                return rs.getString(5);
            } else {
                return "";
            }
            
        } catch(SQLException e){
            out.println(e);  
            return "";
        }
    }
    
}
