package Controllers;

import Classes.Categoria;
import Classes.Lançamento;
import Servicos.CategoriaServico;
import Servicos.ContaServico;
import Servicos.LançamentoServico;
import java.io.IOException;
import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LançamentoController", urlPatterns = {"/LançamentoController"})
public class LançamentoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch(action){
             
            case "excluir":
                ExcluirLançamento(request, response);
                break;  
                
            case "recuperar":
                RecuperarLançamento(request, response);
                break;
                
            case "retorna":
                RetornaLançamento(request, response);
                break;
                
            
                
        }
        
    }
    
    protected void RetornaLançamento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
                        
        try{
            
            CategoriaServico serviceCategoria = new CategoriaServico(); 
            ContaServico serviceConta = new ContaServico();
            LançamentoServico serviceLançamento = new LançamentoServico();
            int id_usuario = (int) session.getAttribute("id_usuario");
            
            ArrayList<Categoria> listaCategoria = (ArrayList<Categoria>) serviceCategoria.RetornaTodos();

            request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
            request.setAttribute("listalançamento", serviceLançamento.RetornaLançamentos(id_usuario));
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            request.setAttribute("listacategoria", listaCategoria);
            String nextJSP = "/lançamentos.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);            
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    protected void RecuperarLançamento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        
        try{
            
            CategoriaServico serviceCategoria = new CategoriaServico(); 
            LançamentoServico serviceLançamento = new LançamentoServico();
            ContaServico serviceConta = new ContaServico();
            int id_usuario = (int) session.getAttribute("id_usuario");
            
            ArrayList<Categoria> listaCategoria = (ArrayList<Categoria>) serviceCategoria.RetornaTodos();
            
            request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
            request.setAttribute("lançamento", serviceLançamento.Retorna(id));
            Lançamento lancamento = (Lançamento) request.getAttribute("lançamento");
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            request.setAttribute("categoria", serviceCategoria.Retorna(lancamento.getId_categoria()));
            request.setAttribute("conta", serviceConta.Retorna(lancamento.getId_conta()));
            request.setAttribute("listalançamento", serviceLançamento.RetornaLançamentos(id_usuario));
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            request.setAttribute("listacategoria", listaCategoria);
            String nextJSP = "/lançamentos.jsp?funcao=editarLancamento";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    protected void ExcluirLançamento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        
        try{
            
            CategoriaServico serviceCategoria = new CategoriaServico(); 
            ContaServico serviceConta = new ContaServico();
            LançamentoServico serviceLançamento = new LançamentoServico();
            int id_usuario = (int) session.getAttribute("id_usuario");
            
            ArrayList<Categoria> listaCategoria = (ArrayList<Categoria>) serviceCategoria.RetornaTodos();
            
            serviceLançamento.Deleta(id);
            
            request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
            request.setAttribute("listalançamento", serviceLançamento.RetornaLançamentos(id_usuario));
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            request.setAttribute("listacategoria", listaCategoria);
            String nextJSP = "/lançamentos.jsp";
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
        
        try {
            
                
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd");
            String dateConvert = request.getParameter("data").replace("/", "-");
                 
            
            Lançamento lançamento = new Lançamento(Integer.parseInt(request.getParameter("txtIdLancamento")),
                                Integer.parseInt(request.getParameter("txtIdConta")),
                                Integer.parseInt(request.getParameter("txtIdCategoria")),
                                Double.parseDouble(request.getParameter("txtValor")),
                                request.getParameter("txtOperacao"),
                                dateConvert,
                                request.getParameter("txtDescricao"));
        
        if(lançamento.getId() == 0){
            Adicionar(request, response, lançamento);
        }else{
            Alterar(request, response, lançamento);
        }
            
        } catch (IOException | NumberFormatException | ServletException e) {
            out.println(e);
        }
        
    }
    
    protected void Adicionar(HttpServletRequest request, HttpServletResponse response, Lançamento lançamento)
            throws ServletException, IOException {
        
         HttpSession session = request.getSession();
        
        try{
            
            int id_usuario = (int) session.getAttribute("id_usuario");
            
            LançamentoServico serviceLançamento = new LançamentoServico();
            ContaServico serviceConta = new ContaServico();
            CategoriaServico serviceCategoria = new CategoriaServico(); 
            ArrayList<Categoria> listaCategoria = (ArrayList<Categoria>) serviceCategoria.RetornaTodos();
            
            serviceLançamento.Adiciona(lançamento);
            request.setAttribute("mostrarMensagem", true);
            request.setAttribute("descricaoMensagem", "Novo lançamento cadastrado com sucesso!");
            request.setAttribute("tipoMensagem", "success");
            
            request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
            request.setAttribute("listalançamento", serviceLançamento.RetornaLançamentos(id_usuario));
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            request.setAttribute("listacategoria", listaCategoria);
            String nextJSP = "/lançamentos.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    
    protected void Alterar(HttpServletRequest request, HttpServletResponse response, Lançamento lançamento)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try{
            
            int id_usuario = (int) session.getAttribute("id_usuario");
            
            LançamentoServico serviceLançamento = new LançamentoServico();
            CategoriaServico serviceCategoria = new CategoriaServico(); 
            ContaServico serviceConta = new ContaServico();
            
            serviceLançamento.Altera(lançamento);
            request.setAttribute("mostrarMensagem", true);
            request.setAttribute("descricaoMensagem", "Lançamento alterado com sucesso!");
            request.setAttribute("tipoMensagem", "success");            
     
            ArrayList<Categoria> listaCategoria = (ArrayList<Categoria>) serviceCategoria.RetornaTodos();

            request.setAttribute("listasaldo", serviceLançamento.Saldo(id_usuario));
            request.setAttribute("listalançamento", serviceLançamento.RetornaLançamentos(id_usuario));
            request.setAttribute("listaconta", serviceConta.RetornaContas(id_usuario));
            request.setAttribute("listacategoria", listaCategoria);
            String nextJSP = "/lançamentos.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);     
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }

}
