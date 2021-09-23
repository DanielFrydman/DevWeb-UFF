package Servicos;

import DAO.CategoriaDAO;
import Framework.InterfaceServico;
import java.util.ArrayList;

public class CategoriaServico implements InterfaceServico {
    
    public CategoriaDAO categoriaDAO;
    
    public CategoriaServico() {
        categoriaDAO = new CategoriaDAO();
    }
    
    @Override
    public void Adiciona(Object Item) {
        categoriaDAO.Adiciona(Item);
    }
    
    public boolean VerificaExistente(String descricao){
        return categoriaDAO.VerificaExistente(descricao);
    }
    
    public boolean VerificaExistente(String descricao, int id){
        return categoriaDAO.VerificaExistente(descricao, id);
    }

    @Override
    public void Altera(Object Item) {
        categoriaDAO.Altera(Item);
    }

    @Override
    public void Deleta(int Id) {
        categoriaDAO.Deleta(Id);
    }

    @Override
    public Object Retorna(int Id) {
        return categoriaDAO.Retorna(Id);
    }
    
    public String RetornaNomeCategoria (int id_categoria){
        return categoriaDAO.RetornaNomeCategoria(id_categoria);
    }

    @Override
    public ArrayList RetornaTodos() {
        return categoriaDAO.RetornaTodos();
    }
    
}
