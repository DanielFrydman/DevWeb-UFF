package Classes;

public class BalanceteTabela {
    
    private String categoria;
    private double valor;
    private String operacao;
    
    public BalanceteTabela(String categoria, double valor, String operacao) {
        this.categoria = categoria;
        this.valor = valor;
        this.operacao = operacao;
    }
    
    public BalanceteTabela(){
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

}
