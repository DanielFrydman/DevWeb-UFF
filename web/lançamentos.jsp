<%@page import="Classes.Saldo"%>
<%@page import="Classes.BalanceteTabela"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="Classes.LancamentoTabela"%>
<%@page import="Classes.Categoria"%>
<%@page import="Classes.Conta"%>
<%@page import="Classes.Lançamento"%>
<%@page import="java.util.ArrayList"%>
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
    boolean mostrarMensagem = false;
    String descricaoMensagem = "";
    String tipoMensagem = "";
    Lançamento lançamento = new Lançamento();
    ArrayList<LancamentoTabela> listaLançamentos = new ArrayList<>();
    Conta conta = new Conta();
    ArrayList<Conta> listaContas = new ArrayList<>();
    Categoria categoria = new Categoria();
    ArrayList<Categoria> listaCategorias = new ArrayList<>();
    ArrayList<BalanceteTabela> listaBalancete = new ArrayList<>();
    ArrayList<Saldo> listaSaldo = new ArrayList<>();
    
    if (request.getAttribute("listasaldo") != null) {
        listaSaldo = (ArrayList<Saldo>) request.getAttribute("listasaldo");
    }

    if (request.getAttribute("lançamento") != null) {
        lançamento = (Lançamento) request.getAttribute("lançamento");
    }
    
    if (request.getAttribute("listabalancete") != null) {
        listaBalancete = (ArrayList<BalanceteTabela>) request.getAttribute("listabalancete");
    }

    if (request.getAttribute("listalançamento") != null) {
        listaLançamentos = (ArrayList<LancamentoTabela>) request.getAttribute("listalançamento");
    }

    if (request.getAttribute("conta") != null) {
        conta = (Conta) request.getAttribute("conta");
    }

    if (request.getAttribute("listaconta") != null) {
        listaContas = (ArrayList<Conta>) request.getAttribute("listaconta");
    }

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
                <a class="navbar-brand" href="LoginController?action=retornaUser">Suas Contas</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="LançamentoController?action=retorna">Lançamentos</a>
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
                                    NumberFormat formato = NumberFormat.getInstance();
                                    formato.setMaximumFractionDigits(2);
                                    double saldoFinal = 0;

                                    for (int i = 0; i < listaSaldo.size(); i++) {
                                        Saldo saldo = listaSaldo.get(i);
                                        if ("D".equals(saldo.getOperacao())) {
                                            saldoFinal -= saldo.getValor();
                                        } else {
                                            saldoFinal += saldo.getValor();
                                        }
                                    }
                                %>
                                    Saldo: R$ 
                                    <%=formato.format(saldoFinal)%> - 
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

                                        <center>
                                            <div class="d-grid gap-2 col-6 mx-auto" style="padding:20px 0px;">
                                                <button class="btn btn-primary btn-lg btn-dark"  data-bs-toggle="modal" data-bs-target="#modalLançamento">Novo Lançamento</button>
                                            </div>
                                        </center>

                                        <div class="table-responsive">
                                            <table class="table table-sm table-dark table-striped">
                                                <thead>
                                                    <tr>
                                                        <th style="color:#cff4fc">Lançamentos</th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>Banco</th>
                                                        <th>Categoria</th>
                                                        <th>Valor</th>
                                                        <th>Operação</th>
                                                        <th>Data</th>
                                                        <th>Descrição</th>
                                                        <th style="text-align: center;">Editar</th>
                                                        <th style="text-align: center;">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <%  for (int i = 0; i < listaLançamentos.size(); i++) {

                                                            LancamentoTabela lançamentoTabela = listaLançamentos.get(i);
                                                    %>
                                                    <tr>
                                                        <td><%=lançamentoTabela.getBanco()%></td>
                                                        <td><%=lançamentoTabela.getCategoria()%></td>
                                                        <td><%=lançamentoTabela.getValor()%></td>
                                                        <td><%=lançamentoTabela.getOperacao()%></td>
                                                        <td><%=lançamentoTabela.getData()%></td>
                                                        <td><%=lançamentoTabela.getDescricao()%></td>
                                                        <td style="text-align: center;">
                                                            <a href="LançamentoController?action=recuperar&id=<%=lançamentoTabela.getId()%>" class="text-warning"><i class="fas fa-edit"></i></a>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <a href="LançamentoController?action=excluir&id=<%=lançamentoTabela.getId()%>" class="text-danger"><i class="fas fa-trash-alt"></i></a>
                                                        </td>
                                                    </tr>
                                                    <% } %>

                                                </tbody>


                                            </table>

                                        </div>
                                    </div>

                                    </body>
                                    </html>


                                    <div class="modal fade" id="modalLançamento" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Cadastrar Lançamento</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>

                                                <form id="cadastro-form" class="form" action="LançamentoController" method="POST">
                                                    <div class="modal-body">      

                                                        <input type="text" name="txtIdLancamento" value="0" id="txtIdLancamento" class="form-control" style="visibility:hidden"> 

                                                        <div class="form-group">
                                                            <label for="username">Conta:</label>
                                                            <select class="form-control" name="txtIdConta" id="txtIdConta">
                                                                <% for (int i = 0; i < listaContas.size(); i++) {

                                                                        Conta contaTabela = listaContas.get(i);%>
                                                                <option value="<%=contaTabela.getId()%>"><%=contaTabela.getNome_conta()%></option>;
                                                                <% } %>

                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Categoria:</label>
                                                            <select class="form-control" name="txtIdCategoria" id="txtIdCategoria">
                                                                <% for (int i = 0; i < listaCategorias.size(); i++) {

                                                                        Categoria categoriaTabela = listaCategorias.get(i);%>
                                                                <option value="<%=categoriaTabela.getId()%>"><%=categoriaTabela.getDescricao()%></option>;
                                                                <% }%>

                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Valor:</label><br>
                                                            <input type="number" min="0" step="any" name="txtValor" id="txtValor" class="form-control"  required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Operação:</label>
                                                            <select class="form-control" name="txtOperacao" id="txtOperacao">
                                                                <option value="D">D</option>
                                                                <option value="C">C</option>
                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Data: </label>
                                                            <div></div>
                                                            <input type="date" max="3000-01-01" onfocus="this.max = new Date().toISOString().split('T')[0]" class="date" id="data" name="data" pattern="yyyy/MM/dd" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Descrição:</label><br>
                                                            <input type="text" name="txtDescricao" id="txtDescricao" class="form-control" >
                                                        </div>


                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-salvarLancamento" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>



                                    <%
                                        if (request.getParameter("funcao") != null && request.getParameter("funcao").equals("editarLancamento")) {
                                            out.print("<script>$(document).ready(function(){  $('#modalEditarLançamento').modal('show');});</script>");
                                        }
                                    %>

                                    <div class="modal fade" id="modalEditarLançamento" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Editar Lançamento</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>      

                                                <form id="cadastro-form" class="form" action="LançamentoController" method="POST">
                                                    <div class="modal-body">

                                                    <input type="text" name="txtIdLancamento" value="<%=lançamento.getId()%>" id="txtIdLancamento" class="form-control" style="visibility:hidden">


                                                    <div class="form-group">
                                                        <label for="username">Conta:</label>
                                                        <select class="form-control" name="txtIdConta" id="txtIdConta">

                                                            <% for (int i = 0; i < listaContas.size(); i++) {

                                                                Conta contaTabela = listaContas.get(i);
                                                                if (listaContas.get(i).getId() == conta.getId()) {%>
                                                            <option selected value="<%=listaContas.get(i).getId()%>"><%=contaTabela.getNome_conta()%></option>
                                                            <% } else {%>
                                                            <option value="<%=contaTabela.getId()%>"><%=contaTabela.getNome_conta()%></option>
                                                            <% } %>                                                                                                     

                                                            <% }%>

                                                        </select>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="username">Categoria:</label>
                                                        <select class="form-control" name="txtIdCategoria" id="txtIdCategoria">
                                                            <option value="<%=categoria.getId()%>"><%=categoria.getDescricao()%></option>
                                                            <% for (int i = 0; i < listaCategorias.size(); i++) {

                                                                    Categoria categoriaTabela = listaCategorias.get(i);

                                                                    if (categoriaTabela.getId() != conta.getId()) {
                                                            %><option value="<%=categoriaTabela.getId()%>"><%=categoriaTabela.getDescricao()%></option><%
                                                                }
                                                            }%>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="username">Valor:</label><br>
                                                        <input type="number" value="<%=lançamento.getValor()%>" min="0" step="any" name="txtValor" id="txtValor" class="form-control"  required>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="username">Operação:</label>
                                                        <select class="form-control" name="txtOperacao" id="txtOperacao">
                                                            <option value="<%=lançamento.getOperacao()%>"><%=lançamento.getOperacao()%></option>
                                                            <%
                                                                if (lançamento.getOperacao().equals("D")) {
                                                                    out.print("<option>C</option>");
                                                                }
                                                                if (lançamento.getOperacao().equals("C")) {
                                                                    out.print("<option>D</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="username">Data:</label>
                                                        <div></div>
                                                        <input type="date" value="<%=lançamento.getData()%>" max="3000-01-01" onfocus="this.max = new Date().toISOString().split('T')[0]" class="date" id="data" name="data" pattern="yyyy/MM/dd" required>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="username">Descrição:</label><br>
                                                        <input type="text" value="<%=lançamento.getDescricao()%>" name="txtDescricao" id="txtDescricao" class="form-control">
                                                    </div>
                                                    </div>

                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-editarLancamento" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>