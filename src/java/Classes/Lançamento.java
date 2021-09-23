package Classes;

import java.util.Date;

public class Lançamento {
    
    private int id;
    private int id_conta;
    private int id_categoria;
    private double valor;
    private String operacao;
    private String data;
    private String descricao;
    
    public Lançamento(int id, int id_conta, int id_categoria, double valor, String operacao, String data, String descricao){
        this.id = id;
        this.id_conta = id_conta;
        this.id_categoria = id_categoria;
        this.valor = valor;
        this.operacao = operacao;
        this.data = data;
        this.descricao = descricao;
    }
    
    public Lançamento(){
    }
    
    public int getId(){
        return id;
    }
   
    public void setId(int id) {
        this.id = id;
    }
   
    public int getId_conta(){
	return id_conta;
    }

    public void setId_conta(int id_conta) {
        this.id_conta = id_conta;
    }
    
    public int getId_categoria(){
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
    
    public double getValor(){
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getOperacao(){
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getData(){
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
