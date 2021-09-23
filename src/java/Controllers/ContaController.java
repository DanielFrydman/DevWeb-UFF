package Controllers;

import Classes.Conta;
import Servicos.AdministradorServico;
import Servicos.ContaServico;
import Servicos.LançamentoServico;
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

@WebServlet(name = "ContaController", urlPatterns = {"/ContaController"})
public class ContaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch(action){
             
            case "excluir":
                ExcluirConta(request, response);
                break;  
                
            case "recuperar":
                RecuperarConta(request, response);
                break;
                
            case "balancete":
                Balancete(request, response);
                break;
                
        }
        
    }
    
    private void Balancete(HttpServletRequest request, HttpServletResponse response) {
        
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        
        try{
            
            LançamentoServico serviceLançamento = new LançamentoServico();
            ContaServico serviceConta = new ContaServico();
            int id_usuario = (int) session.getAttribute("id_usuario");

            request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            request.setAttribute("listabalancete", serviceLançamento.RetornaBalancete(id));
            String nextJSP = "/usuarios.jsp?funcao=balancete";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }

    protected void RecuperarConta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        
        try{
            
            LançamentoServico serviceLançamento = new LançamentoServico();
            ContaServico serviceConta = new ContaServico();
            int id_usuario = (int) session.getAttribute("id_usuario");
            
            request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            request.setAttribute("conta", serviceConta.Retorna(id));
            String nextJSP = "/usuarios.jsp?funcao=editarConta";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    protected void ExcluirConta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        
        try{
            
            ContaServico serviceConta = new ContaServico();
            LançamentoServico serviceLançamento = new LançamentoServico();
            int id_usuario = (int) session.getAttribute("id_usuario");
            
            serviceConta.Deleta(id);
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
        
        HttpSession session = request.getSession();
        
        Conta conta = new Conta(Integer.parseInt(request.getParameter("txtIdConta")),
                                ((int) session.getAttribute("id_usuario")),
                                request.getParameter("txtNome_contaConta"),
                                request.getParameter("txtBancoConta"),
                                request.getParameter("txtAgenciaConta"),
                                request.getParameter("txtConta_correnteConta"));
        
        if(conta.getId() == 0){
            Adicionar(request, response, conta);
        }else{
            Alterar(request, response, conta);
        }
        
    }

    protected void Adicionar(HttpServletRequest request, HttpServletResponse response, Conta conta)
            throws ServletException, IOException {
        
        try{
            
            ContaServico serviceConta = new ContaServico();
            LançamentoServico serviceLançamento = new LançamentoServico();
            
            if(!serviceConta.VerificaExistente(conta.getBanco(), conta.getAgencia(), conta.getConta_corrente())){
                serviceConta.Adiciona(conta);
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Nova conta cadastrada com sucesso!");
                request.setAttribute("tipoMensagem", "success");
            } else {
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Conjunto Banco / Agencia / Conta inserida já cadastrada!");
                request.setAttribute("tipoMensagem", "danger");
            }           
            
            request.setAttribute("listasaldo", serviceLançamento.Saldo(conta.getId_usuario()));
            request.setAttribute("listaconta", serviceConta.RetornaContas(conta.getId_usuario()));
            String nextJSP = "/usuarios.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    
    protected void Alterar(HttpServletRequest request, HttpServletResponse response, Conta conta)
            throws ServletException, IOException {
        
        try{
            
            ContaServico serviceConta = new ContaServico();
            LançamentoServico serviceLançamento = new LançamentoServico();
            
            if(!serviceConta.VerificaExistente(conta.getBanco(), conta.getAgencia(), conta.getConta_corrente(), conta.getId())){
                serviceConta.Altera(conta);
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Conta alterada com sucesso!");
                request.setAttribute("tipoMensagem", "success");
            } else {
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Conjunto Banco / Agencia / Conta inserida já cadastrada!");
                request.setAttribute("tipoMensagem", "danger");
            }
            
            request.setAttribute("listasaldo", serviceLançamento.Saldo(conta.getId_usuario()));
            request.setAttribute("listaconta", serviceConta.RetornaContas(conta.getId_usuario()));
            String nextJSP = "/usuarios.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
}
