package Controllers;

import Classes.Categoria;
import Servicos.CategoriaServico;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoriaController", urlPatterns = {"/CategoriaController"})
public class CategoriaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch(action){
             
            case "excluir":
                ExcluirCategoria(request, response);
                break;  
            
            case "recuperar":
                RecuperarCategoria(request, response);
                break;
                
            case "retorna":
                RetornaCats(request, response);
                break;
                
        }
        
    }
    
    protected void RetornaCats(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                        
        try{
            
            CategoriaServico service = new CategoriaServico();   
            
            ArrayList<Categoria> listaCategoria = (ArrayList<Categoria>) service.RetornaTodos();
            
            request.setAttribute("listacategoria", listaCategoria);
            String nextJSP = "/categorias.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);            
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    
    protected void RecuperarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        int id = Integer.parseInt(request.getParameter("id"));
        
        try{
            
            CategoriaServico service = new CategoriaServico();   
            
            request.setAttribute("listacategoria", service.RetornaTodos());
            request.setAttribute("categoria", service.Retorna(id));
            String nextJSP = "/categorias.jsp?funcao=editarCat";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }

    
    protected void ExcluirCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        int id = Integer.parseInt(request.getParameter("id"));
        
        try{
            
            CategoriaServico service = new CategoriaServico();
            
            service.Deleta(id);
            request.setAttribute("listacategoria", service.RetornaTodos());
            String nextJSP = "/categorias.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Categoria categoria = new Categoria(Integer.parseInt(request.getParameter("txtIdCat")),
                                      request.getParameter("txtCat"));
        
        if(categoria.getId() == 0){
            Adicionar(request, response, categoria);
        }else{
            Alterar(request, response, categoria);
        }
        
    }

    protected void Adicionar(HttpServletRequest request, HttpServletResponse response, Categoria categoria)
            throws ServletException, IOException {
                        
        try{
            
            CategoriaServico service = new CategoriaServico();
            
            if(!service.VerificaExistente(categoria.getDescricao())){
                service.Adiciona(categoria);
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Categoria cadastrada com sucesso!");
                request.setAttribute("tipoMensagem", "success");
            } else {
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Categoria inserida já cadastrada!");
                request.setAttribute("tipoMensagem", "danger");
            }                 

            request.setAttribute("listacategoria", service.RetornaTodos());
            String nextJSP = "/categorias.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    
    protected void Alterar(HttpServletRequest request, HttpServletResponse response, Categoria categoria)
            throws ServletException, IOException {
                        
        try{
            
            CategoriaServico service = new CategoriaServico();
            
            if(!service.VerificaExistente(categoria.getDescricao())){
                service.Altera(categoria);
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Categoria alterada com sucesso!");
                request.setAttribute("tipoMensagem", "success");
            } else {
                request.setAttribute("mostrarMensagem", true);
                request.setAttribute("descricaoMensagem", "Categoria inserida já cadastrada!");
                request.setAttribute("tipoMensagem", "danger");
            } 
            
            request.setAttribute("listacategoria", service.RetornaTodos());
            String nextJSP = "/categorias.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);
            
        } catch(IOException | ServletException e){
            out.println(e);
        }
        
    }
    
    
}
