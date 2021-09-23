package Controllers;

import Classes.Usuario;
import Servicos.AdministradorServico;
import Servicos.LançamentoServico;
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

@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch(action){
             
            case "excluir":
                ExcluirUsuario(request, response);
                break;  
                
            case "recuperar":
                RecuperarUsuario(request, response);
                break;
                
                
        }
    }
    
    protected void RecuperarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        int id = Integer.parseInt(request.getParameter("id"));
        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();
            
            request.setAttribute("listaadministrador", serviceAdmin.RetornaTodos());
            request.setAttribute("listausuario", serviceUser.RetornaTodos());
            request.setAttribute("usuario", serviceUser.Retorna(id));
            String nextJSP = "/administradores.jsp?funcao=editarUser";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    protected void ExcluirUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();
            
            serviceUser.Deleta(id);
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
        
        Usuario usuario = new Usuario(Integer.parseInt(request.getParameter("txtIdUser")),
                                      request.getParameter("txtnomeUser"),
                                      request.getParameter("txtcpfUser"),
                                      request.getParameter("txtsenhaUser"),
                                      request.getParameter("txtsuspensoUser"));
        
        if(usuario.getId() == 0){
            Adicionar(request, response, usuario);
        }else{
            Alterar(request, response, usuario);
        }
        
    }
    
    protected void Adicionar(HttpServletRequest request, HttpServletResponse response, Usuario usuario)
            throws ServletException, IOException {
        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();
            
            if(!serviceUser.VerificaExistente(usuario.getCpf())){
                serviceUser.Adiciona(usuario);
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Usuário cadastrado com sucesso!");
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
    
    protected void Alterar(HttpServletRequest request, HttpServletResponse response, Usuario usuario)
            throws ServletException, IOException {
        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();
            
            if(!serviceUser.VerificaExistente(usuario.getCpf(), usuario.getId())){
                serviceUser.Altera(usuario);
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Usuário alterado com sucesso!");
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
