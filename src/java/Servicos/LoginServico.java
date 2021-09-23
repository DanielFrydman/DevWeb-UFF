package Servicos;

import DAO.LoginDAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class LoginServico {
    
    public LoginDAO loginDAO;
    
    public LoginServico(){
        loginDAO = new LoginDAO();
    }
    
    public String FazLogin(String cpf, String senha){
        return loginDAO.FazLogin(cpf, senha);
    }
    
    public String VerificaLogin(String cpf){
        return loginDAO.VerificaLogin(cpf);
    }
    
    public int VerificaID(String cpf){
        return loginDAO.VerificaID(cpf);
    }
    
    public String VerificaSuspenso(String cpf){
        return loginDAO.VerificaSuspenso(cpf);
    }
    
}
