<%@page import="Classes.Saldo"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="Classes.BalanceteTabela"%>
<%@page import="Classes.Conta"%>
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
    Conta conta = new Conta();
    ArrayList<Conta> listaContas = new ArrayList<>();
    ArrayList<BalanceteTabela> listaBalancete = new ArrayList<>();
    ArrayList<Saldo> listaSaldo = new ArrayList<>();
    
    if (request.getAttribute("listasaldo") != null) {
        listaSaldo = (ArrayList<Saldo>) request.getAttribute("listasaldo");
    }

    if (request.getAttribute("conta") != null) {
        conta = (Conta) request.getAttribute("conta");
    }

    if (request.getAttribute("listabalancete") != null) {
        listaBalancete = (ArrayList<BalanceteTabela>) request.getAttribute("listabalancete");
    }

    if (request.getAttribute("listaconta") != null) {
        listaContas = (ArrayList<Conta>) request.getAttribute("listaconta");
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
                                    
                                    if(saldoFinal < 0){%>
                                            <script>alert("O Saldo total está negativo!");</script>
                                        <%
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
                                                <button class="btn btn-primary btn-lg btn-dark"  data-bs-toggle="modal" data-bs-target="#modalConta">Nova Conta</button>
                                            </div>
                                        </center>

                                        <div class="table-responsive">
                                            <table class="table table-sm table-dark table-striped">
                                                <thead>
                                                    <tr>
                                                        <th style="color:#cff4fc">Contas:</th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>Nome Conta</th>
                                                        <th style="text-align: center;">Banco</th>
                                                        <th style="text-align: center;">Agência</th>
                                                        <th style="text-align: center;">Conta Corrente</th>
                                                        <th style="text-align: center;">Balancete</th>
                                                        <th style="text-align: center;">Editar</th>
                                                        <th style="text-align: center;">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <% for (int i = 0; i < listaContas.size(); i++) {

                                                            Conta contaTabela = listaContas.get(i);%>

                                                    <tr>
                                                        <td><%=contaTabela.getNome_conta()%></td>
                                                        <td style="text-align: center;"><%=contaTabela.getBanco()%></td>
                                                        <td style="text-align: center;"><%=contaTabela.getAgencia()%></td>
                                                        <td style="text-align: center;"><%=contaTabela.getConta_corrente()%></td>
                                                        <th style="text-align: center;">
                                                            <a href="ContaController?action=balancete&id=<%=contaTabela.getId()%>" class="text-info"><i class="fas fa-file-invoice"></i></a>
                                                        </th>
                                                        <td style="text-align: center;">
                                                            <a href="ContaController?action=recuperar&id=<%=contaTabela.getId()%>" class="text-warning"><i class="fas fa-edit"></i></a>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <a href="ContaController?action=excluir&id=<%=contaTabela.getId()%>" class="text-danger"><i class="fas fa-trash-alt"></i></a>
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
                                    <div class="modal fade" id="modalConta" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Cadastrar Conta</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>

                                                <form id="cadastro-form" class="form" action="ContaController" method="POST">
                                                    <div class="modal-body">      

                                                        <input type="text" name="txtIdConta" value="0" id="txtIdConta" class="form-control" style="visibility:hidden"> 

                                                        <div class="form-group">
                                                            <label for="username">Nome Conta:</label><br>
                                                            <input type="text" name="txtNome_contaConta" id="txtNome_contaConta" class="form-control" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Banco:</label><br>
                                                            <input type="text" name="txtBancoConta" id="txtBancoConta" class="form-control" maxlength="3" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Agência:</label><br>
                                                            <input type="text" name="txtAgenciaConta" id="txtAgenciaConta" class="form-control" maxlength="6" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Conta Corrente:</label><br>
                                                            <input type="text" name="txtConta_correnteConta" id="txtConta_correnteConta" class="form-control CC" required>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-salvarConta" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        $(document).ready(function () {
                                            $('.CC').mask('0000-0', {reverse: true});
                                        })
                                    </script>


                                    <%
                                        if (request.getParameter("funcao") != null && request.getParameter("funcao").equals("editarConta")) {
                                            out.print("<script>$(document).ready(function(){  $('#modalEditarConta').modal('show');});</script>");
                                        }
                                    %>

                                    <div class="modal fade" id="modalEditarConta" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Editar Conta</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>      


                                                <form id="cadastro-form" class="form" action="ContaController" method="POST">
                                                    <div class="modal-body">

                                                        <input value="<%=conta.getId()%>" type="hidden" name="txtIdConta" id="txtIdConta">

                                                        <input value="<%=conta.getBanco()%>" type="hidden" name="txtBancoAntigo" id="txtBancoAntigo">

                                                        <input value="<%=conta.getAgencia()%>" type="hidden" name="txtAgenciaAntiga" id="txtAgenciaAntiga">

                                                        <input value="<%=conta.getConta_corrente()%>" type="hidden" name="txtContaCorrenteAntiga" id="txtContaCorrenteAntiga">


                                                        <div class="form-group">
                                                            <label for="username">Nome Conta:</label><br>
                                                            <input value="<%=conta.getNome_conta()%>" type="text" name="txtNome_contaConta" id="txtNome_contaConta" class="form-control" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Banco:</label><br>
                                                            <input value="<%=conta.getBanco()%>" type="text" name="txtBancoConta" id="txtBancoConta" class="form-control" maxlength="3" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Agência:</label><br>
                                                            <input value="<%=conta.getAgencia()%>" type="text" name="txtAgenciaConta" id="txtAgenciaConta" class="form-control" maxlength="6" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="username">Conta Corrente:</label><br>
                                                            <input value="<%=conta.getConta_corrente()%>" type="text" name="txtConta_correnteConta" id="txtConta_correnteConta" class="form-control CC" required>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                        <button type="submit" name="btn-editarConta" class="btn btn-primary">Salvar</button>
                                                    </div>
                                                </form>

                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        $(document).ready(function () {
                                            $('.CC').mask('0000-0', {reverse: true});
                                        })
                                    </script>

                                    <%
                                        if (request.getParameter("funcao") != null && request.getParameter("funcao").equals("balancete")) {
                                            out.print("<script>$(document).ready(function(){  $('#modalBalancete').modal('show');});</script>");
                                        }
                                    %>


                                    <div class="modal fade" id="modalBalancete" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Balancete</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>      

                                                <div class="modal-body">

                                                    <table class="table">
                                                        <thead>
                                                            <tr>
                                                                <th><i class="fas fa-book"></i></th>
                                                                <th><center><i class="fas fa-minus-circle"></i></center>                                                                    </th>
                                                        <th><center><i class="fas fa-plus-circle"></i></center></th>
                                                        <th><center><i class="fas fa-minus-circle"> <i class="fas fa-percentage"></i></center></th>
                                                        <th><center><i class="fas fa-plus-circle"> <i class="fas fa-percentage"></i></center></th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                            <%
                                                                double cont = 0;
                                                                double porcDeb = 0;
                                                                double porcCred = 0;

                                                                for (int i = 0; i < listaBalancete.size(); i++) {
                                                                    BalanceteTabela balanceteTabela = listaBalancete.get(i);
                                                                    cont += balanceteTabela.getValor();
                                                                }

                                                                for (int i = 0; i < listaBalancete.size(); i++) {

                                                                    BalanceteTabela balanceteTabela = listaBalancete.get(i);
                                                                    double porcentagem = 0;

                                                                    if ("D".equals(balanceteTabela.getOperacao())) {
                                                                        porcentagem = (balanceteTabela.getValor() / cont) * 100;
                                                                        porcDeb += porcentagem;
                                                            %>
                                                            <tr>
                                                                <th><%=balanceteTabela.getCategoria()%></th>
                                                                <th><center><%=formato.format(balanceteTabela.getValor())%></center></th>
                                                        <th><center>0</center></th>
                                                        <th><center><%=formato.format(porcentagem)%></center></th>
                                                        <th><center>0</center></th>
                                                        </tr>
                                                        <% }
                                                            if ("C".equals(balanceteTabela.getOperacao())) {
                                                                porcentagem = (balanceteTabela.getValor() / cont) * 100;
                                                                porcCred += porcentagem;
                                                        %>
                                                        <tr>
                                                            <th><%=balanceteTabela.getCategoria()%></th>
                                                            <th><center>0</center></th>
                                                        <th><center><%=formato.format(balanceteTabela.getValor())%></center></th>
                                                        <th><center>0</center></th>
                                                        <th><center><%=formato.format(porcentagem)%></center></th>
                                                        </tr>
                                                        <% } %>
                                                        <% }%>
                                                        <tr>
                                                            <th>Valor Total: </th>
                                                            <th><center><%=formato.format(cont)%></center></th>
                                                        <th></th>
                                                        <th><center><%=formato.format(porcDeb)%></center></th>
                                                        <th><center><%=formato.format(porcCred)%></center></th>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>

                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                                                </div>

                                            </div>
                                        </div>
                                    </div>