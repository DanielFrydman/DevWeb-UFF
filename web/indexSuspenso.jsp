<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Framework.Conexao"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@include file="head.html"%>
<%@include file="referencias.html"%>
<link href="css/estilo.css" rel="stylesheet">

<!DOCTYPE html>
<html>

    <body>

        <div class="table-responsive">
            <div class="login-dark">
                <form action="LoginController" method="post">
                    <center>
                        <h2 class="sr-only">BANCO DIGITAL</h2>
                    </center>
                    <div class="illustration">
                        <i class="icon ion-ios-locked-outline"></i>
                    </div>
                    <div class="form-group">
                        <input class="form-control cpf" type="text" name="txtcpf" id="txtcpf" placeholder="CPF" required>
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="password" name="txtsenha" id="txtsenha" placeholder="Senha" required>
                    </div>
                    <center>
                        <div class="form-group">
                            <button class="btn btn-primary btn-block" type="submit">Entrar</button>
                        </div>
                    </center>
                    <center>
                        <p></p>
                        <h8>Conta Suspensa!</h8>
                        <p></p>
                        <h7>Entre com outra conta ou entre em contato com algum administrador.</h7>
                    </center>
                </form>


            </div>
        </div>

        <script>
            $(document).ready(function () {
                $('.cpf').mask('000.000.000-00', {reverse: true});
            })
        </script>



    </body>
</html>
