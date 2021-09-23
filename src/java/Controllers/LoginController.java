package Controllers;

import Classes.Conta;
import Servicos.AdministradorServico;
import Servicos.ContaServico;
import Servicos.LançamentoServico;
import Servicos.LoginServico;
import Servicos.UsuarioServico;
import java.io.IOException;
import static java.lang.System.out;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch(action){
             
            case "retornaAdmin":
                RetornaAdmin(request, response);
                break;
                
            case "retornaUser":
                RetornaUser(request, response);
                break;
                
        }
    }

    protected void RetornaAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                        
        try{
            
            AdministradorServico serviceAdmin = new AdministradorServico(); 
            UsuarioServico serviceUser = new UsuarioServico();
        
            request.setAttribute("listaadministrador", serviceAdmin.RetornaTodos());
            request.setAttribute("listausuario", serviceUser.RetornaTodos());
            String nextJSP = "/administradores.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);            
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    protected void RetornaUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
                        
        try{
            
            ContaServico serviceConta = new ContaServico();
            
            int id_usuario = (int) session.getAttribute("id_usuario");
            LançamentoServico serviceLançamento = new LançamentoServico();

            request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            String nextJSP = "/usuarios.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);            
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String cpf = request.getParameter("txtcpf");
        String senha = request.getParameter("txtsenha");
        
        HttpSession session = request.getSession();
        
        LoginServico service = new LoginServico();
        AdministradorServico serviceAdmin = new AdministradorServico(); 
        UsuarioServico serviceUser = new UsuarioServico();
        ContaServico serviceConta = new ContaServico();
        LançamentoServico serviceLançamento = new LançamentoServico();
        
        String nome = service.FazLogin(cpf, senha);
        
        if(nome != ""){
            if("admin".equals(service.VerificaLogin(cpf))){
                session.setAttribute("nome", nome);
                request.setAttribute("listaadministrador", serviceAdmin.RetornaTodos());
                request.setAttribute("listausuario", serviceUser.RetornaTodos());
                String nextJSP = "/administradores.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                dispatcher.forward(request,response);
            } else if ("user".equals(service.VerificaLogin(cpf))) {
                if("S".equals(service.VerificaSuspenso(cpf))){
                    String nextJSP = "/logoutSuspenso.jsp";
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                    dispatcher.forward(request,response);
                } else {
                    int id_usuario = service.VerificaID(cpf);
                    session.setAttribute("id_usuario", id_usuario);
                    session.setAttribute("nome", nome);
                    request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
                    request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
                    String nextJSP = "/usuarios.jsp";
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
                    dispatcher.forward(request,response);
                }
            }
        } else {
            String nextJSP = "/logout.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        }  
        
    }
    

}