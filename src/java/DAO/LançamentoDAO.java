package DAO;

import Classes.BalanceteTabela;
import Classes.LancamentoTabela;
import Classes.Lançamento;
import Classes.Saldo;
import Framework.Conexao;
import Framework.InterfaceConexao;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LançamentoDAO implements InterfaceConexao {
    
    private Connection conexao = null;
    public ResultSet rs = null;
    public Statement st = null;
    
    public LançamentoDAO(){
        conexao = Conexao.CriaConexao();
    }

    @Override
    public void Adiciona(Object Item) {
        
        Lançamento lançamento = (Lançamento) Item;
        
        String sql = "INSERT into lancamentos (id_conta, id_categoria, valor, operacao, data, descricao) values ('" + lançamento.getId_conta() + "','" + lançamento.getId_categoria() + "','" + lançamento.getValor() + "', '" + lançamento.getOperacao() + "', '" + lançamento.getData() + "', '" + lançamento.getDescricao() + "')";
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }  
        
    }

    @Override
    public void Altera(Object Item) {
        
        Lançamento lançamento = (Lançamento) Item;
        
        String sql = "UPDATE lancamentos SET id_conta = '" + lançamento.getId_conta() + "', id_categoria = '" + lançamento.getId_categoria() + "', valor = '" + lançamento.getValor() + "', operacao = '" + lançamento.getOperacao() + "', data = '" + lançamento.getData() + "', descricao = '" + lançamento.getDescricao() + "' WHERE id = '" + lançamento.getId() + "'";
        
        try{
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        } 
        
    }

    @Override
    public void Deleta(int id) {
        
        String sql = "DELETE FROM lancamentos WHERE id = '" + id + "'";
        
        try{            
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);            
            st.executeUpdate(sql);
        } catch(SQLException e){
            out.println(e);
        }
        
    }

    @Override
    public Object Retorna(int id) {
        
        Lançamento lançamento = new Lançamento();
        
        String sql = "SELECT * FROM lancamentos WHERE id = '" + id + "'";
        
        try{            
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
            while (rs.next() != false) {
                lançamento.setId(rs.getInt(1));
                lançamento.setId_conta(rs.getInt(2));
                lançamento.setId_categoria(rs.getInt(3));
                lançamento.setValor(rs.getDouble(4));
                lançamento.setOperacao(rs.getString(5));
                lançamento.setData(rs.getString(6));
                lançamento.setDescricao(rs.getString(7));
            }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return lançamento;
        
    }
    
    public ArrayList<LancamentoTabela> RetornaLançamentos(int id_usuario) {
        
        ArrayList<LancamentoTabela> listaLancamentoTabela = new ArrayList<>();
        
        String sql = " SELECT lancamentos.id as id, contas.nome_conta AS Banco, categorias.descricao AS Categoria,\n" +
                    "lancamentos.valor AS Valor, lancamentos.operacao AS Operacao, lancamentos.data AS Data, lancamentos.descricao AS Descricao\n" +
                    "FROM lancamentos\n" +
                    "INNER JOIN contas\n" +
                    "ON lancamentos.id_conta = contas.id\n" +
                    "INNER JOIN categorias\n" +
                    "ON categorias.id = lancamentos.id_categoria WHERE contas.id_usuario = '" + id_usuario + "' ";
        
        try{
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
                while(rs.next()){
                
                    LancamentoTabela lancamento = new LancamentoTabela();
                    lancamento.setId(rs.getInt(1));
                    lancamento.setBanco(rs.getString(2));
                    lancamento.setCategoria(rs.getString(3));
                    lancamento.setValor(rs.getDouble(4));
                    lancamento.setOperacao(rs.getString(5));
                    lancamento.setData(rs.getString(6));
                    lancamento.setDescricao(rs.getString(7));
                    listaLancamentoTabela.add(lancamento);
                    
                }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return listaLancamentoTabela;
    }
    
    public ArrayList<Lançamento> Lançamentos(int id_conta) {
        
        ArrayList<Lançamento> listaLan = new ArrayList<>();
        
        String sql = " SELECT * FROM lancamentos WHERE id_conta = '" + id_conta + "' ";
        
        try{
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
                while(rs.next()){
                
                    Lançamento lancamento = new Lançamento();
                    lancamento.setId(rs.getInt(1));
                    lancamento.setId_conta(rs.getInt(2));
                    lancamento.setId_categoria(rs.getInt(3));
                    lancamento.setValor(rs.getDouble(4));
                    lancamento.setOperacao(rs.getString(5));
                    lancamento.setData(rs.getString(6));
                    lancamento.setDescricao(rs.getString(7));
                    listaLan.add(lancamento);
                    
                }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return listaLan;
    }
    
    public ArrayList<BalanceteTabela> RetornaBalancete(int id_conta) {
        
        ArrayList<BalanceteTabela> listaBalanceteTabela = new ArrayList<>();
        
        String sql = "SELECT categorias.descricao, lancamentos.valor, lancamentos.operacao\n" +
                    "FROM lancamentos\n" +
                    "INNER JOIN contas\n" +
                    "ON lancamentos.id_conta = contas.id\n" +
                    "INNER JOIN categorias\n" +
                    "ON categorias.id = lancamentos.id_categoria WHERE contas.id = '" + id_conta + "' ";
        
        try{
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
                while(rs.next()){
                
                    BalanceteTabela lancamento = new BalanceteTabela();
                    lancamento.setCategoria(rs.getString(1));
                    lancamento.setValor(rs.getDouble(2));
                    lancamento.setOperacao(rs.getString(3));
                    listaBalanceteTabela.add(lancamento);
                    
                }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return listaBalanceteTabela;
    }
    
    public ArrayList<Saldo> Saldo(int id_usuario) {
        
        ArrayList<Saldo> listaSaldo = new ArrayList<>();
        
        String sql = "SELECT lancamentos.valor, lancamentos.operacao\n" +
                    "FROM lancamentos\n" +
                    "INNER JOIN contas\n" +
                    "ON lancamentos.id_conta = contas.id\n" +
                    "WHERE contas.id_usuario = '" + id_usuario + "' ";
        
        try{
            st = new Conexao().conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery(sql);
            
                while(rs.next()){
                
                    Saldo saldo = new Saldo();
                    saldo.setValor(rs.getDouble(1));
                    saldo.setOperacao(rs.getString(2));
                    listaSaldo.add(saldo);
                    
                }
            
        } catch(SQLException e){
            out.println(e);
        }
        
        return listaSaldo;
    }

    @Override
    public ArrayList RetornaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
