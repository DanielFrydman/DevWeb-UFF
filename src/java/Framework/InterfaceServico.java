/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public interface InterfaceServico<T> {
    
    public void Adiciona(T Item);

    public void Altera(T Item);

    public void Deleta(int Id);

    public T Retorna(int Id);

    public ArrayList<T> RetornaTodos();
    
}
