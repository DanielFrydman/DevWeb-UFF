<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Usuario"%>
<%@page import="Classes.Administrador"%>
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
    Administrador administrador = new Administrador();
    Usuario usuario = new Usuario();
    ArrayList<Administrador> listaAdministradores = new ArrayList<>();
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    if (request.getAttribute("administrador") != null) {
        administrador = (Administrador) request.getAttribute("administrador");
    }

    if (request.getAttribute("usuario") != null) {
        usuario = (Usuario) request.getAttribute("usuario");
    }

    if (request.getAttribute("listaadministrador") != null) {
        listaAdministradores = (ArrayList<Administrador>) request.getAttribute("listaadministrador");
    }

    if (request.getAttribute("listausuario") != null) {
        listaUsuarios = (ArrayList<Usuario>) request.getAttribute("listausuario");
    }

    if (request.getAttribute("mostrarMensagem") != null) {
        mostrarMensagem = (boolean) request.getAttribute("mostrarMensagem");
        descricaoMensagem = (String) request.getAttribute("descricaoMensagem");
        tipoMensagem = (String) request.getAttribute("tipoMensagem");
    }
%>

<!DOCTYPE html>
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
                                            <button class="btn btn-primary btn-lg btn-dark"  data-bs-toggle="modal" data-bs-target="#modalUser">Novo Usuário</button>
                                            <button class="btn btn-primary btn-lg btn-dark"  data-bs-toggle="modal" data-bs-target="#modalAdmin">Novo Administrador</button>
                                        </div>

                                        <div class="table-responsive">
                                            <table class="table table-sm table-dark table-striped">
                                                <thead>
                                                    <tr>
                                                        <th style="color:#cff4fc">Administradores:</th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>Nome</th>
                                                        <th>CPF</th>
                                                        <th style="text-align: center;">Suspenso</th>
                                                        <th style="text-align: center;">Editar</th>
                                                        <th style="text-align: center;">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <% for (int i = 0; i < listaAdministradores.size(); i++) {

                                                        Administrador administradorTabela = listaAdministradores.get(i);%>

                                                    <tr> 
                                                        <td><%=administradorTabela.getNome()%></td>
                                                        <td><%=administradorTabela.getCpf()%></td>
                                                        <td style="text-align: center;">-</td>
                                                        <td style="text-align: center;">
                                                            <a href="AdministradorController?action=recuperar&id=<%=administradorTabela.getId()%>" class="text-warning"><i class="fas fa-edit"></i></a>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <a href="AdministradorController?action=excluir&id=<%=administradorTabela.getId()%>" class="text-danger"><i class="fas fa-trash-alt"></i></a>
                                                        </td>
                                                    </tr>
                                                </tbody>

                                                <% } %>

                                                <thead>
                                                    <tr>
                                                        <th>⠀</th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                </thead>

                                                <thead>
                                                    <tr>
                                                        <th style="color:#cff4fc">Usuários:</th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>Nome</th>
                                                        <th>CPF</th>
                                                        <th style="text-align: center;">Suspenso</th>
                                                        <th style="text-align: center;">Editar</th>
                                                        <th style="text-align: center;">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <% for (int i = 0; i < listaUsuarios.size(); i++) {

                                                        Usuario usuarioTabela = listaUsuarios.get(i);%>

                                                    <tr> 
                                                        <td><%=usuarioTabela.getNome()%></td>
                                                        <td><%=usuarioTabela.getCpf()%></td>
                                                        <td style="text-align: center;"><%=usuarioTabela.getSuspenso()%></td>
                                                        <td style="text-align: center;">
                                                            <a href="UsuarioController?action=recuperar&id=<%=usuarioTabela.getId()%>" class="text-warning mr-2"><i class="fas fa-edit"></i></a>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <a href="UsuarioController?action=excluir&id=<%=usuarioTabela.getId()%>" class="text-danger mr-2"><i class="fas fa-trash-alt"></i></a>
                                                        </td>
                                                    </tr>
                                                </tbody>

                                                <% } %>

                                            </table>

                                        </div>
                                    </div>


                                    </body>
                                    </html>



                                    <%    if (request.getAttribute("mostrarMensagem") != null) {
                                            if (mostrarMensagem) {
                                    %> <center><div class="container"><div class="alert alert-<%=tipoMensagem%>" role="alert"><%=descricaoMensagem%></div></div></center> <%
                                            }
                                        }
                                            %>


                                    <!-- Modal -->
                                    <div class="modal fade" id="modalAdmin" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Cadastrar Administrador</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>

                                                <form id="cadastro-form" class="form" action="AdministradorController" method="POST">
                                                    <div class="modal-body">      

                                                        <input type="text" name="txtIdAdmin" value="0" id="txtIdAdmin" class="form-control" style="visibility:hidden"> 

                                                        <div class="form-group">
                                                            <label for="username">Nome:</label><br>
                                                            <input type="text" name="txtnomeAdmin" id="txtnomeAdmin" class="form-control" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">CPF:</label><br>
                                                            <input type="text" name="txtcpfAdmin" id="txtcpfAdmin" class="form-control cpf" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="password">Senha:</label><br>
                                                            <input type="text" name="txtsenhaAdmin" id="txtsenhaAdmin" class="form-control" required>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-salvarAdmin" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        $(document).ready(function () {
                                            $('.cpf').mask('000.000.000-00', {reverse: true});
                                        })
                                    </script>




                                    <div class="modal fade" id="modalUser" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Cadastrar Usuário</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>

                                                <form id="cadastro-form" class="form" action="UsuarioController" method="POST">
                                                    <div class="modal-body">      

                                                        <input type="text" name="txtIdUser" value="0" id="txtIdUser" class="form-control" style="visibility:hidden"> 

                                                        <div class="form-group">
                                                            <label for="username">Nome:</label><br>
                                                            <input type="text" name="txtnomeUser" id="txtnomeUser" class="form-control" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">CPF:</label><br>
                                                            <input type="text" name="txtcpfUser" id="txtcpfUser" class="form-control cpf" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="password">Senha:</label><br>
                                                            <input type="text" name="txtsenhaUser" id="txtsenhaUser" class="form-control" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="exampleFormControlSelect2">Suspenso:</label>
                                                            <select class="form-control" name="txtsuspensoUser" id="txtsuspenso">
                                                                <option>N</option>
                                                                <option>S</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-salvarUser" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        $(document).ready(function () {
                                            $('.cpf').mask('000.000.000-00', {reverse: true});
                                        })
                                    </script>




                                    <%
                                        if (request.getParameter("funcao") != null && request.getParameter("funcao").equals("editarAdmin")) {
                                            out.print("<script>$(document).ready(function(){  $('#modalEditarAdmin').modal('show');});</script>");
                                        }
                                    %>

                                    <div class="modal fade" id="modalEditarAdmin" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Editar Administrador</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>      


                                                <form id="cadastro-form" class="form" action="AdministradorController" method="POST">
                                                    <div class="modal-body">

                                                        <input value="<%=administrador.getCpf()%>" type="hidden" name="txtCPFUserAntigo1" id="txtCPFUserAntigo1">

                                                        <input value="<%=administrador.getId()%>" type="hidden" name="txtIdAdmin" id="txtIdAdmin">

                                                        <div class="form-group">
                                                            <label for="username">Nome:</label><br>
                                                            <input value="<%=administrador.getNome()%>" type="text" name="txtnomeAdmin" id="txtnomeAdmin" class="form-control" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">CPF:</label><br>
                                                            <input value="<%=administrador.getCpf()%>" type="text" name="txtcpfAdmin" id="txtcpfAdmin" class="form-control cpf" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="password">Senha:</label><br>
                                                            <input type="password" value="<%=administrador.getSenha()%>" type="text" name="txtsenhaAdmin" id="txtsenhaAdmin" class="form-control" required>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-editarAdmin" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        $(document).ready(function () {
                                            $('.cpf').mask('000.000.000-00', {reverse: true});
                                        })
                                    </script>




                                    <%
                                        if (request.getParameter("funcao") != null && request.getParameter("funcao").equals("editarUser")) {
                                            out.print("<script>$(document).ready(function(){  $('#modalEditarUser').modal('show');});</script>");
                                        }
                                    %>                                   

                                    <div class="modal fade" id="modalEditarUser" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Editar Usuário</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>

                                                <form id="cadastro-form" class="form" action="UsuarioController" method="POST">
                                                    <div class="modal-body">

                                                        <input value="<%=usuario.getCpf()%>" type="hidden" name="txtCPFUserAntigo2" id="txtCPFUserAntigo2">

                                                        <input value="<%=usuario.getId()%>" type="hidden" name="txtIdUser" id="txtIdUser">

                                                        <div class="form-group">
                                                            <label for="username">Nome:</label><br>
                                                            <input value="<%=usuario.getNome()%>" type="text" name="txtnomeUser" id="txtnomeUser" class="form-control" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">CPF:</label><br>
                                                            <input value="<%=usuario.getCpf()%>" type="text" name="txtcpfUser" id="txtcpfUser" class="form-control cpf" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="password">Senha:</label><br>
                                                            <input type="password" value="<%=usuario.getSenha()%>" type="text" name="txtsenhaUser" id="txtsenhaUser" class="form-control" required>
                                                        </div>

                                                        <%

                                                            if ((usuario.getSuspenso() != null) && (usuario.getSuspenso() != "")) {
                                                        %>

                                                        <div class="form-group">
                                                            <label for="exampleFormControlSelect2">Suspenso</label>
                                                            <select class="form-control" name="txtsuspensoUser" id="txtsuspenso">
                                                                <option value="<%=usuario.getSuspenso()%>"><%=usuario.getSuspenso()%></option>
                                                                <%
                                                                    if (usuario.getSuspenso().equals("N")) {
                                                                        out.print("<option>S</option>");
                                                                    }
                                                                    if (usuario.getSuspenso().equals("S")) {
                                                                        out.print("<option>N</option>");
                                                                    }
                                                                %>
                                                            </select>
                                                        </div>

                                                        <%
                                                            }

                                                        %>

                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-editarUser" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        $(document).ready(function () {
                                            $('.cpf').mask('000.000.000-00', {reverse: true});
                                        })
                                    </script>              






