<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Usuario"%>
<%@page import="Classes.Administrador"%>
<%@page import="Classes.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Framework.Conexao"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@include file="head.html"%>
<%@include file="referencias.html"%>
<link href="css/estiloAdmin.css" rel="stylesheet">
<link href="css/fontawesome-free-5.15.3-web/css/all.css" rel="stylesheet">
<link href="css/alertas.css" rel="stylesheet">

<%
    Statement st = null;
    ResultSet rs = null;
%>

<%
    boolean mostrarMensagem = false;
    String descricaoMensagem = "";
    String tipoMensagem = "";
    Categoria categoria = new Categoria();
    ArrayList<Categoria> listaCategorias = new ArrayList<>();

    if (request.getAttribute("categoria") != null) {
        categoria = (Categoria) request.getAttribute("categoria");
    }

    if (request.getAttribute("listacategoria") != null) {
        listaCategorias = (ArrayList<Categoria>) request.getAttribute("listacategoria");
    }

    if (request.getAttribute("mostrarMensagem") != null) {
        mostrarMensagem = (boolean) request.getAttribute("mostrarMensagem");
        descricaoMensagem = (String) request.getAttribute("descricaoMensagem");
        tipoMensagem = (String) request.getAttribute("tipoMensagem");
    }
%>

<html>
    <body>

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="LoginController?action=retornaAdmin">Lista de Usuários</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="CategoriaController?action=retorna">Categorias</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"></a>
                        </li>
                        <li class="nav-item dropdown">
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="" tabindex="-1" aria-disabled="true"></a>
                        </li>
                    </ul>
                    <form class="form-inline my-2 my-lg-0">
                        <font color="white" size="5px"><i>
                            <span> 
                                <%
                                    String nome = (String) session.getAttribute("nome");
                                    out.print(nome);
                                %>
                                <span>
                                    </i></font>
                                    <a href="index.jsp" class="text-danger fa-"><i class="fas fa-sign-out-alt"></i></a>
                                    </form>
                                    </div>
                                    </div>
                                    </nav>



                                    <div class="container">

                                        <div class="d-grid gap-2 col-6 mx-auto" style="padding:20px 0px;">
                                            <button class="btn btn-primary btn-lg btn-dark"  data-bs-toggle="modal" data-bs-target="#modalCat">Nova Categoria</button>
                                        </div>

                                        <div class="table-responsive">
                                            <table class="table table-sm table-dark table-striped">
                                                <thead>
                                                    <tr>
                                                        <th style="color:#cff4fc">Categorias:</th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>Descrição</th>
                                                        <th style="text-align: center;">Editar</th>
                                                        <th style="text-align: center;">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>                                               


                                                    <% for (int i = 0; i < listaCategorias.size(); i++) {

                                                        Categoria categoriaTabela = listaCategorias.get(i);%>

                                                    <tr>                
                                                        <td><%=categoriaTabela.getDescricao()%></td>
                                                        <td style="text-align: center;">
                                                            <a href="CategoriaController?action=recuperar&id=<%=categoriaTabela.getId()%>" class="text-warning"><i class="fas fa-edit"></i></a>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <a href="CategoriaController?action=excluir&id=<%= categoriaTabela.getId()%>" class="text-danger"><i class="fas fa-trash-alt"></i></a>
                                                        </td>                                                     
                                                    </tr>   


                                                    <% } %>

                                                </tbody>
                                            </table>

                                        </div>
                                    </div>


                                    </body>
                                    </html>




                                    <!-- Modal -->

                                    <div class="modal fade" id="modalCat" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Cadastrar Categoria</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>

                                                <form id="cadastro-form" class="form" action="CategoriaController" method="POST">
                                                    <div class="modal-body">

                                                        <input type="text" name="txtIdCat" value="0" id="txtIdCat" class="form-control" style="visibility:hidden">

                                                        <div class="form-group">
                                                            <label for="username">Descrição:</label><br>
                                                            <input type="text" name="txtCat" id="txtCat" class="form-control" required>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-salvarCat" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>

                                    <%
                                        if (request.getParameter("funcao") != null && request.getParameter("funcao").equals("editarCat")) {
                                            out.print("<script>$(document).ready(function(){  $('#modalEditarCat').modal('show');});</script>");
                                        }
                                    %>

                                    <div class="modal fade" id="modalEditarCat" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Cadastrar Categoria</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>

                                                <form id="cadastro-form" class="form" action="CategoriaController" method="POST">
                                                    <div class="modal-body">

                                                        <input value="<%=categoria.getId()%>" type="hidden" name="txtIdCat" id="txtIdCat">

                                                        <div class="form-group">
                                                            <label for="username">Descrição:</label><br>
                                                            <input value="<%=categoria.getDescricao()%>" type="text" name="txtCat" id="txtCat" class="form-control" required>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-salvarCat" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>


                                    <%    if (request.getAttribute("mostrarMensagem") != null) {
                                            if (mostrarMensagem) {
                                    %> <center><div class="container"><div class="alert alert-<%=tipoMensagem%>" role="alert"><%=descricaoMensagem%></div></div></center> <%
                                            }
                                        }
                                            %>
