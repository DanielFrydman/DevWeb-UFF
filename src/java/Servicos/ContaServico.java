package Servicos;

import Classes.Lançamento;
import DAO.ContaDAO;
import Framework.InterfaceServico;
import java.util.ArrayList;

public class ContaServico implements InterfaceServico {

    public ContaDAO contaDAO;
    public LançamentoServico serviceLançamento;
    
    public ContaServico(){
        contaDAO = new ContaDAO();
        serviceLançamento = new LançamentoServico();
    }
    
    @Override
    public void Adiciona(Object Item) {
        contaDAO.Adiciona(Item);
    }
    
    public boolean VerificaExistente(String banco, String agencia, String conta_corrente ){
        return contaDAO.VerificaExistente(banco, agencia, conta_corrente);
    }
    
    public boolean VerificaExistente(String banco, String agencia, String conta_corrente, int id_conta){
        return contaDAO.VerificaExistente(banco, agencia, conta_corrente, id_conta);
    }
    
    @Override
    public void Altera(Object Item) {
        contaDAO.Altera(Item);
    }

    @Override
    public void Deleta(int Id) {
        try {
            serviceLançamento.Lançamentos(Id).forEach(((item) -> {
                try {
                    Lançamento lançamento = (Lançamento) item;
                    
                    serviceLançamento.Deleta(lançamento.getId());
                }
                
                catch (Exception e) {}
            })); 
        
        } catch (Exception e) {
        }
        
        
        contaDAO.Deleta(Id);
    }

    @Override
    public Object Retorna(int Id) {
        return contaDAO.Retorna(Id);
    }
    
    public ArrayList RetornaContas(int id_usuario) {
        return contaDAO.RetornaContas(id_usuario);
    }

    @Override
    public ArrayList RetornaTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
