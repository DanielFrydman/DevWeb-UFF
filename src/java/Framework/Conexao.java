/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework;

import java.sql.*;
/**
 *
 * @author Daniel
 */
public class Conexao {
    
  
    public Connection conectar() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/financeiro?userTimezone=true&serverTimezone=UTC&user=root&password=");
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    
    
    private static Connection conexao = null;

    public static Connection CriaConexao(){

        if(conexao == null){

            try{
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection("jdbc:mysql://localhost/financeiro?userTimezone=true&serverTimezone=UTC&user=root&password=");
            } 
            catch(Exception e){
                e.printStackTrace();
            }

        }

        return conexao;
    }
    
}
