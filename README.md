# 🏦 Banking System
🚧`em construção`🚧

# ✏️ Descrição do projeto
O objetivo desse projeto é colocar em prática os conhecimentos adquiridos no curso introdutório à linguagem Java da plataforma Alura, bem como relembrar conceitos vistos em aulas da faculdade de Análise e Desenvolvimento de Sistemas, por meio da criação de um sistema bancário com dados de clientes e suas contas.

# 🔨 Funcionalidades
`Criar arquivos`: o programa, logo em seu início, cria dois arquivos, um para armazenar os dados dos clientes (nome e CPF) e outro para armazenar as informações da conta de determinado cliente (tipo de conta, saldo disponível e CPF do proprietário);

  ![image](https://github.com/Maria-Faria/BankingSystem/assets/114308727/60886ad4-e9fa-4443-8614-f314b0daf47a)

`Ler arquivos`: depois de criados, os arquivos podem ser lidos, por meio da função _lerArquivo()_, que recebe como parâmetros dois arrays, um com as informações dos clientes e outros com as informações das contas, e cada array é populado nessa mesma função, com o objetivo de facilitar a exibição desses dados posteriormente;

  ![image](https://github.com/Maria-Faria/BankingSystem/assets/114308727/0bd7e948-e860-43a6-be69-a9da19084c48)

`Escrever nos arquivos`: ao executar o programa, é exibido um menu ao usuário, que permite que ele realize o cadastro de uma conta e, após inserir as informações necessárias, os dados serão transferidos aos arquivos correspondentes;

  ![image](https://github.com/Maria-Faria/BankingSystem/assets/114308727/5c665218-a586-4e19-ad21-c522b46e8acd)

`Cadastrar uma conta`: ao escolher a opção de cadastro de conta, o usuário deverá inserir seu nome e seu CPF - que serão atributos de um objeto da classe _Cliente_ -, bem como o tipo da conta a ser cadastrada e o saldo disponível na mesma - que, juntamente com o CPF do cliente, serão atributos da classe _Conta_ -, além disso, depois de coletar o CPF do cliente, o programa faz uma verificação para validar se o CPF informado já está cadastrado, por meio da leitura do arquivo a cada iteração do laço de repetição que possibilita ao usuário o menu de opções, assim, após realizada a devida validação, as informações coletadas serão escritas nos arquivos referentes aos clientes e às contas;

  ![image](https://github.com/Maria-Faria/BankingSystem/assets/114308727/eb84b631-5cc6-473b-b804-c6c34bc0e729)
