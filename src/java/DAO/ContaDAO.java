package DAO;

import Classes.Conta;
import Framework.Conexao;
import Framework.InterfaceConexao;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ContaDAO implements InterfaceConexao{
    
    private Connection conexao = null;
    public ResultSet rs = null;
    public Statement st = null;
    
    public ContaDAO() {
        conexao = Conexao.CriaConexao();
    }
    
    public boolean VerificaExistente(String banco, String agencia, String conta_corrente){      
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery("SELECT * FROM contas WHERE banco = '" + banco + "' AND agencia = '" + agencia + "' AND conta_corrente = '" + conta_corrente + "'");
            
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
    
    public boolean VerificaExistente(String banco, String agencia, String conta_corrente, int id_conta){      
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery("SELECT * FROM contas WHERE (banco = '" + banco + "' AND agencia = '" + agencia + "' AND conta_corrente = '" + conta_corrente + "') AND id <> " +id_conta);
            
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
        
        Conta conta = (Conta) Item;
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate("INSERT into contas (id_usuario, nome_conta, banco, agencia, conta_corrente) values ('" + conta.getId_usuario() + "', '" + conta.getNome_conta() + "', '" + conta.getBanco() + "', '" + conta.getAgencia() + "', '" + conta.getConta_corrente() + "')");
        } catch(SQLException e){
            out.println(e);
        }  
        
    }

    @Override
    public void Altera(Object Item) {
        
        Conta conta = (Conta) Item;
        
        String sql = "UPDATE contas SET nome_conta = '" + conta.getNome_conta() + "', banco = '" + conta.getBanco() + "', agencia = '" + conta.getAgencia() + "', conta_corrente = '" + conta.getConta_corrente() + "' where id = '" + conta.getId() + "'";
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        } 
        
    }

    @Override
    public void Deleta(int id) {
        
        String sql = "DELETE FROM contas where id = '" + id + "'";
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }
        
    }

    @Override
    public Object Retorna(int id) {
        
        Conta conta = new Conta();
        
        String sql = "SELECT * FROM contas WHERE id = '" + id + "'";
        
        try{            
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
            while (rs.next() != false) {
                conta.setId(rs.getInt(1));
                conta.setId_usuario(rs.getInt(2));
                conta.setNome_conta(rs.getString(3));
                conta.setBanco(rs.getString(4));
                conta.setAgencia(rs.getString(5));
                conta.setConta_corrente(rs.getString(6));
            }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return conta;
    }

    public ArrayList<Conta> RetornaContas(int id_usuario) {
        
        ArrayList<Conta> listaConta = new ArrayList<>();
        
        String sql = "SELECT * FROM contas WHERE id_usuario = '" + id_usuario + "'";
        
        try{
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
                while(rs.next()){
                
                    Conta conta = new Conta();
                    conta.setId(rs.getInt(1));
                    conta.setId_usuario(rs.getInt(2));
                    conta.setNome_conta(rs.getString(3));
                    conta.setBanco(rs.getString(4));
                    conta.setAgencia(rs.getString(5));
                    conta.setConta_corrente(rs.getString(6));
                    listaConta.add(conta);
                    
                }
        } catch(SQLException e){
            out.println(e);
        }
        
        return listaConta;
    }

    @Override
    public ArrayList RetornaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
