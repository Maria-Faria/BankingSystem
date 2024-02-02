import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void lerArquivo(List<Cliente> clientes, List<Conta> contas) {
        Cliente c = new Cliente(null, null);
        Conta ct = new Conta(null, 0, null);

        try{

            BufferedReader clientesRead = new BufferedReader(new InputStreamReader(new FileInputStream("clientes.txt"), "UTF-8"));
            BufferedReader contasRead = new BufferedReader(new InputStreamReader(new FileInputStream("contas.txt"), "UTF-8"));

            String linha = null;
            String info[];

            while((linha = clientesRead.readLine()) != null) {
                info = linha.split(",");

                c.setNome(info[0]);
                c.setCpf(info[1]);
            }

            if(c.getCpf() != null) {
                clientes.add(c);
            }

            clientesRead.close();

            while((linha = contasRead.readLine()) != null) {
                info = linha.split(",");

                ct.setTipo(info[0]);
                ct.setSaldo(Double.parseDouble(info[1]));
                ct.setCpfCliente(info[2]);
            }

            if(ct.getCpfCliente() != null) {
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
                3. Sair
                """;

        System.out.println(menu);
        System.out.print("Digite a opção escolhida aqui: ");
        op = sc.nextInt();

        while(op != 1 && op != 2 && op != 3) {
            System.out.print("Opção inválida! Tente novamente: ");
            op = sc.nextInt();
        }

        return op;
    }

    public static void ExibirDados(List<Cliente> clientes, String cpfInput, List<Conta> contas) {
        System.out.println("\n*********************");
        if(clienteCadastrado(cpfInput, clientes)) {
            for(int i = 0; i < clientes.size(); i++) {
                if(clientes.get(i).getCpf().equals(cpfInput)) {
                    System.out.println(String.format("Nome: %s", clientes.get(i).getNome()));
                }
            }

            for(int i = 0; i < contas.size(); i++) {
                if(contas.get(i).getCpfCliente().equals(cpfInput)) {
                    System.out.println(String.format("Tipo da conta: %s", contas.get(i).getTipo()));
                    System.out.println(String.format("Saldo disponível: R$%.2f", contas.get(i).getSaldo()));
                }
            }
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

    public static void main(String[] args) throws FileNotFoundException {
        //criando arquivos que funcionarão como um banco de dados
        PrintWriter clientesFile = new PrintWriter(new FileOutputStream(new File("clientes.txt"), true));
        PrintWriter contasFile = new PrintWriter(new FileOutputStream(new File("contas.txt"), true));

        List<Cliente> clientes = new ArrayList<Cliente>();
        List<Conta> contas = new ArrayList<Conta>();

        int op;
        String nomeCliente, tipoConta, cpf, cpfInput;
        double saldo;
        boolean clienteExiste = false;

        Scanner sc = new Scanner(System.in);
        op = MenuInicial(sc);

        //lendo os arquivos e armazenando os arrays
        lerArquivo(clientes, contas);

        do {
            switch (op) {
                case 1:
                    sc.nextLine();
                    System.out.print("\nDigite seu nome: ");
                    nomeCliente = sc.nextLine();

                    System.out.print("Digite seu CPF: ");
                    cpf = sc.next();

                    for(int i = 0; i < clientes.size(); i++) {
                        if(clientes.get(i).getCpf().equals(cpf)) {
                            System.out.println("Conta já existe!");
                            ExibirDados(clientes, cpf, contas);
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

                        System.out.println("Cliente cadastrado!");
                    }

                    break;

                case 2:
                    sc.nextLine();
                    System.out.print("Informe o CPF proprietário da conta que deseja consultar: ");
                    cpfInput = sc.nextLine();

                    ExibirDados(clientes, cpfInput, contas);
                    break;

                case 3:
                    System.out.println("\n*************************");
                    System.out.println("Volte sempre!");
                    System.out.println("*************************");
                    break;
            }

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                System.out.println(e);
            }

            System.out.println("\n");
            op = MenuInicial(sc);

        }while(op != 3);

        contasFile.close();
        clientesFile.close();
        sc.close();
    }
}