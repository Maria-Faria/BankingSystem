import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void lerArquivo(List<Cliente> clientes, List<Conta> contas) {
        try{

            BufferedReader clientesRead = new BufferedReader(new InputStreamReader(new FileInputStream("clientes.txt"), "UTF-8"));
            BufferedReader contasRead = new BufferedReader(new InputStreamReader(new FileInputStream("contas.txt"), "UTF-8"));

            String linha = null;
            String info[];

            while((linha = clientesRead.readLine()) != null) {
                info = linha.split(",");

                Cliente c = new Cliente(info[0], info[1]);

                clientes.add(c);
            }

            clientesRead.close();

            while((linha = contasRead.readLine()) != null) {
                info = linha.split(",");

                Conta ct = new Conta(info[0], Double.parseDouble(info[1]), info[2]);

                contas.add(ct);
            }

            contasRead.close();

        }catch(IOException e) {
            e.printStackTrace();
        }

    }
    public static int MenuInicial(Scanner sc) {
        int op;

        String menu = """
                O que você deseja fazer?
                
                1. Cadastrar uma conta
                2. Consultar uma conta
                3. Transferir valor
                4. Depositar valor
                5. Sair
                """;

        System.out.println(menu);
        System.out.print("Digite a opção escolhida aqui: ");
        op = sc.nextInt();

        while(op < 1 || op > 5) {
            System.out.print("Opção inválida! Tente novamente: ");
            op = sc.nextInt();
        }

        return op;
    }

    public static void ExibirDados(List<Cliente> clientes, String cpfInput, List<Conta> contas) {
        String nome = null, cpf = null, tipoConta = null;
        double saldo = 0;

        System.out.println("\n*********************");
        if(clienteCadastrado(cpfInput, clientes)) {
            for(int i = 0; i < clientes.size(); i++) {
                if(clientes.get(i).getCpf().equals(cpfInput)) {
                    nome = clientes.get(i).getNome();
                    cpf = clientes.get(i).getCpf();
                }
            }

            for(int i = 0; i < contas.size(); i++) {
                if(contas.get(i).getCpfCliente().equals(cpfInput)) {
                    tipoConta = contas.get(i).getTipo();
                    saldo = contas.get(i).getSaldo();
                }
            }

            System.out.println("Nome: " + nome);
            System.out.println("CPF:" + cpf);
            System.out.println("Tipo da conta: " + tipoConta);
            System.out.println(String.format("Saldo disponível: R$%.2f", saldo));

        }else {
            System.out.println("Cliente não encontrado!");
        }

        System.out.println("********************");
    }

    public static boolean clienteCadastrado(String cpfCliente, List<Cliente> clientes) {
        for(int i = 0; i < clientes.size(); i++) {
            if(clientes.get(i).getCpf().equals(cpfCliente)) {
                return true;
            }
        }
        return false;
    }

    public static void transferirValor(String cpfClienteTransfere, String cpfClienteRecebe, List<Conta> contas, double valor)  {
        double saldoAntes = 0, saldoDepois = 0;
        boolean flag = false;

        for(int i = 0; i < contas.size(); i++) {
            if(contas.get(i).getCpfCliente().equals(cpfClienteTransfere)) {
                saldoAntes = contas.get(i).getSaldo();

                if(valor <= contas.get(i).getSaldo()) {
                    contas.get(i).setSaldo(contas.get(i).getSaldo() - valor); //subtraindo valor de quem está transferindo
                    saldoDepois = contas.get(i).getSaldo();
                }else {
                    System.out.println("\nSaldo insuficiente!");
                    flag = true;
                    break;
                }
            }

            if(contas.get(i).getCpfCliente().equals(cpfClienteRecebe)) {
                contas.get(i).setSaldo(contas.get(i).getSaldo() + valor); //adicionando valor no saldo de quem recebeu a transferência
            }
        }

        try {
            reescreverArquivo(contas);

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        if(!flag) {
            System.out.println("\nTransferência concluída!");

            System.out.println("\nSeu saldo antes da transferência: " + saldoAntes);
            System.out.println("Seu saldo após a transferência: " + saldoDepois);
        }
    }

    public static void depositarValor(String cpfCliente, List<Conta> contas, double valor) {
        double saldoAntes = 0, saldoDepois = 0;

        for(int i = 0; i < contas.size(); i++) {
            if(contas.get(i).getCpfCliente().equals(cpfCliente)) {
                saldoAntes = contas.get(i).getSaldo();
                contas.get(i).setSaldo(saldoAntes + valor);
                saldoDepois = contas.get(i).getSaldo();
            }
        }

        try {
            reescreverArquivo(contas);

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        System.out.println("\nDepósito concluído!!");

        System.out.println("\nSeu saldo antes do depósito: " + saldoAntes);
        System.out.println("Seu saldo após o depósito: " + saldoDepois);
    }
    public static void reescreverArquivo(List<Conta> contas) throws FileNotFoundException {
        //reescrevendo o arquivo
        PrintWriter contasFile = new PrintWriter(new FileOutputStream(new File("contas.txt")));

        for(int i = 0; i < contas.size(); i++) {
            contasFile.println(contas.get(i).getTipo() + "," + contas.get(i).getSaldo() + "," + contas.get(i).getCpfCliente());
        }

        contasFile.close();
    }
    public static void main(String[] args) throws FileNotFoundException {

        List<Cliente> clientes = new ArrayList<Cliente>();
        List<Conta> contas = new ArrayList<Conta>();

        int op;
        String nomeCliente, tipoConta, cpf, cpfInput;
        double saldo;
        boolean clienteExiste = false;

        Scanner sc = new Scanner(System.in);
        op = MenuInicial(sc);

        do {
            //criando arquivos que funcionarão como um banco de dados
            PrintWriter clientesFile = new PrintWriter(new FileOutputStream(new File("clientes.txt"), true));
            PrintWriter contasFile = new PrintWriter(new FileOutputStream(new File("contas.txt"), true));

            //lendo os arquivos e armazenando os arrays
            lerArquivo(clientes, contas);
            switch (op) {
                case 1:
                    sc.nextLine();
                    System.out.print("\nDigite seu nome: ");
                    nomeCliente = sc.nextLine();

                    System.out.print("Digite seu CPF: ");
                    cpf = sc.next();

                    for(int i = 0; i < clientes.size(); i++) {
                        if(clientes.get(i).getCpf().equals(cpf)) {
                            clienteExiste = true;
                        }
                    }

                    if(!clienteExiste) {
                        Cliente cliente = new Cliente(nomeCliente, cpf);
                        clientesFile.println(cliente.getNome() + "," + cliente.getCpf());

                        sc.nextLine();
                        System.out.print("\nDigite o tipo da conta: ");
                        tipoConta = sc.next();

                        sc.nextLine();
                        System.out.print("Digite o saldo disponível na conta: ");
                        saldo = sc.nextDouble();

                        Conta conta = new Conta(tipoConta, saldo, cpf);
                        contasFile.println(conta.getTipo() + "," + conta.getSaldo() + "," + conta.getCpfCliente());

                        System.out.println("\nCliente cadastrado!");

                    }else {
                        System.out.println("\nConta já existe!");
                        ExibirDados(clientes, cpf, contas);
                    }

                    break;

                case 2:
                    sc.nextLine();
                    System.out.print("Informe o CPF proprietário da conta que deseja consultar: ");
                    cpfInput = sc.nextLine();

                    ExibirDados(clientes, cpfInput, contas);
                    break;

                case 3:
                    sc.nextLine();
                    System.out.print("Digite seu CPF: ");
                    String cpfTransfere = sc.next();

                    sc.nextLine();
                    System.out.print("Digite o CPF do proprietário da conta que irá receber a transferência: ");
                    String cpfRecebe = sc.next();

                    if(clienteCadastrado(cpfTransfere, clientes) && clienteCadastrado(cpfRecebe, clientes)) {
                        System.out.print("Digite o valor a ser transferido: ");
                        double valor = sc.nextDouble();

                        transferirValor(cpfTransfere, cpfRecebe, contas, valor);

                    }else {
                        System.out.println("Cliente(s) não encontrado(s)!");
                    }
                    break;

                case 4:
                    sc.nextLine();
                    System.out.print("Digite seu CPF: ");
                    String cpfDeposito = sc.next();

                    if(clienteCadastrado(cpfDeposito, clientes)) {
                        sc.nextLine();
                        System.out.print("Digite o valor a ser depositado: ");
                        double valor = sc.nextDouble();

                        depositarValor(cpfDeposito, contas, valor);

                    }else {
                        System.out.println("Cliente não encontrado!");
                    }

                    break;

                case 5:
                    System.out.println("\n*************************");
                    System.out.println("Volte sempre!");
                    System.out.println("*************************");
                    break;

            }

            contasFile.close();
            clientesFile.close();

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                System.out.println(e);
            }

            System.out.println("\n");
            op = MenuInicial(sc);

        }while(op != 5);

        sc.close();
    }
}