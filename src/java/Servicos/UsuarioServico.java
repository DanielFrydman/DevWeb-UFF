package Servicos;

import Classes.Conta;
import DAO.UsuarioDAO;
import Framework.InterfaceServico;
import java.util.ArrayList;

public class UsuarioServico implements InterfaceServico {

    public UsuarioDAO usuarioDAO;
    public ContaServico serviceConta;

    public UsuarioServico() {
        usuarioDAO = new UsuarioDAO();
        serviceConta = new ContaServico();
    }

    @Override
    public void Adiciona(Object Item) {
        usuarioDAO.Adiciona(Item);
    }

    public boolean VerificaExistente(String cpf) {
        return usuarioDAO.VerificaExistente(cpf);
    }

    public boolean VerificaExistente(String cpf, int id) {
        return usuarioDAO.VerificaExistente(cpf, id);
    }

    @Override
    public void Altera(Object Item) {
        usuarioDAO.Altera(Item);
    }

    @Override
    public void Deleta(int Id) {

        try {
            serviceConta.RetornaContas(Id).forEach(((item) -> {
                try {
                    Conta conta = (Conta) item;
                    
                    serviceConta.Deleta(conta.getId());
                }
                
                catch (Exception e) {}
            })); 
        
        } catch (Exception e) {
        }

        usuarioDAO.Deleta(Id);
    }

    @Override
    public Object Retorna(int Id) {
        return usuarioDAO.Retorna(Id);
    }

    @Override
    public ArrayList RetornaTodos() {
        return usuarioDAO.RetornaTodos();
    }

}
