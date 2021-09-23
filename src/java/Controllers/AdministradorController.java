package Controllers;

import Classes.Administrador;
import Servicos.AdministradorServico;
import Servicos.UsuarioServico;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdministradorController", urlPatterns = {"/AdministradorController"})
public class AdministradorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch(action){
             
            case "excluir":
                ExcluirAdministrador(request, response);
                break;  
                
            case "recuperar":
                RecuperarAdministrador(request, response);
                break;
                
            case "retorna":
                RetornaAdmins(request, response);
                break;
                
        }
        
    }
    
    protected void RetornaAdmins(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                        
        try{
            
            AdministradorServico service = new AdministradorServico();   
            
            ArrayList<Administrador> listaAdministrador = (ArrayList<Administrador>) service.RetornaTodos();
            
            request.setAttribute("listaadministrador", listaAdministrador);
            String nextJSP = "/categorias.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);            
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    protected void RecuperarAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        int id = Integer.parseInt(request.getParameter("id"));
        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();  
            
            request.setAttribute("listaadministrador", serviceAdmin.RetornaTodos());
            request.setAttribute("listausuario", serviceUser.RetornaTodos());
            request.setAttribute("administrador", serviceAdmin.Retorna(id));
            String nextJSP = "/administradores.jsp?funcao=editarAdmin";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    protected void ExcluirAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        int id = Integer.parseInt(request.getParameter("id"));
        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();
            
            serviceAdmin.Deleta(id);
            request.setAttribute("listaadministrador", serviceAdmin.RetornaTodos());
            request.setAttribute("listausuario", serviceUser.RetornaTodos());
            String nextJSP = "/administradores.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        Administrador administrador = new Administrador(Integer.parseInt(request.getParameter("txtIdAdmin")),
                                      request.getParameter("txtnomeAdmin"),
                                      request.getParameter("txtcpfAdmin"),
                                      request.getParameter("txtsenhaAdmin"));
        
        if(administrador.getId() == 0){
            Adicionar(request, response, administrador);
        }else{
            Alterar(request, response, administrador);
        }
    }
    
    protected void Adicionar(HttpServletRequest request, HttpServletResponse response, Administrador administrador)
            throws ServletException, IOException {
                        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();
            
            if(!serviceAdmin.VerificaExistente(administrador.getCpf())){
                serviceAdmin.Adiciona(administrador);
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Administrador cadastrado com sucesso!");
                request.setAttribute("tipoMensagem", "success");
            } else {
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "CPF inserido já cadastrado!");
                request.setAttribute("tipoMensagem", "danger");
            }                 
            
            request.setAttribute("listaadministrador", serviceAdmin.RetornaTodos());
            request.setAttribute("listausuario", serviceUser.RetornaTodos());
            String nextJSP = "/administradores.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    protected void Alterar(HttpServletRequest request, HttpServletResponse response, Administrador administrador)
            throws ServletException, IOException {
                        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();         
            
            if(!serviceAdmin.VerificaExistente(administrador.getCpf(), administrador.getId())){
                serviceAdmin.Altera(administrador);
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Administrador alterado com sucesso!");
                request.setAttribute("tipoMensagem", "success");
            } else {
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "CPF inserido já cadastrado!");
                request.setAttribute("tipoMensagem", "danger");
            } 
            
            request.setAttribute("listaadministrador", serviceAdmin.RetornaTodos());
            request.setAttribute("listausuario", serviceUser.RetornaTodos());
            String nextJSP = "/administradores.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }


}
