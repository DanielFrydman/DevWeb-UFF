package Classes;

public class Saldo {
    
    private double valor;
    private String operacao;
    
    public Saldo (double valor, String operacao) {
        this.valor = valor;
        this.operacao = operacao;
    }
    
    public Saldo(){
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
