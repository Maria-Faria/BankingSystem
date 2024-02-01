public class Conta {
    private String tipo;
    private double saldo;
    private String cpfCliente;

    public Conta(String tipo, double saldo, String cpfCliente) {
        this.tipo = tipo;
        this.saldo = saldo;
        this.cpfCliente = cpfCliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }
}
