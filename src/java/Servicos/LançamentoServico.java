package Servicos;

import Classes.BalanceteTabela;
import Classes.Lançamento;
import Classes.Saldo;
import DAO.LançamentoDAO;
import Framework.InterfaceServico;
import java.util.ArrayList;

public class LançamentoServico implements InterfaceServico{
    
    public LançamentoDAO lançamentoDAO;
    
    public LançamentoServico(){
        lançamentoDAO = new LançamentoDAO();
    }

    @Override
    public void Adiciona(Object Item) {
        lançamentoDAO.Adiciona(Item);
    }

    @Override
    public void Altera(Object Item) {
        lançamentoDAO.Altera(Item);
    }

    @Override
    public void Deleta(int Id) {
        lançamentoDAO.Deleta(Id);
    }

    @Override
    public Object Retorna(int Id) {
        return lançamentoDAO.Retorna(Id);
    }
    
    public ArrayList RetornaLançamentos(int id_conta) {
        return lançamentoDAO.RetornaLançamentos(id_conta);
    }
    
    public ArrayList<BalanceteTabela> RetornaBalancete(int id_conta) {
        return lançamentoDAO.RetornaBalancete(id_conta);
    }
    
    public ArrayList<Saldo> Saldo(int id_usuario) {
        return lançamentoDAO.Saldo(id_usuario);
    }
    
    public ArrayList<Lançamento> Lançamentos(int id_conta) {
        return lançamentoDAO.Lançamentos(id_conta);
    }

    @Override
    public ArrayList RetornaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
