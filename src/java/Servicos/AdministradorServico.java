package Servicos;

import DAO.AdministradorDAO;
import Framework.InterfaceServico;
import java.util.ArrayList;

public class AdministradorServico implements InterfaceServico {
    
    public AdministradorDAO administradorDAO;
    
    public AdministradorServico(){
        administradorDAO = new AdministradorDAO();
    }

    @Override
    public void Adiciona(Object Item) {
        administradorDAO.Adiciona(Item);
    }
    
    public boolean VerificaExistente(String cpf){
        return administradorDAO.VerificaExistente(cpf);
    }
    
    public boolean VerificaExistente(String cpf, int id){
        return administradorDAO.VerificaExistente(cpf, id);
    }

    @Override
    public void Altera(Object Item) {
        administradorDAO.Altera(Item);
    }

    @Override
    public void Deleta(int Id) {
        administradorDAO.Deleta(Id);
    }

    @Override
    public Object Retorna(int Id) {
        return administradorDAO.Retorna(Id);
    }

    @Override
    public ArrayList RetornaTodos() {
        return administradorDAO.RetornaTodos();
    }
    
}
