# E‑Commerce Console App

O **E‑Commerce Console App** é um projeto em **Java** para gerenciamento de clientes, produtos e pedidos, com validação de dados e persistência em arquivos locais.  
O sistema é totalmente operado via terminal e segue um modelo de menus interativos.

---

## Tecnologias utilizadas

- **Java 17+**
- **Paradigma Orientado a Objetos**
- **Padrões de Projeto**: Factory, Service, Repository
- **Arquitetura inspirada em DDD (Domain-Driven Design)**
- **Princípios SOLID**
- **Persistência** em arquivos locais (`.data`)
- **Coleções Java** (`List`, `Map`)
- **Validações com Regex** e regras de negócio no domínio

---

## Funcionalidades atuais

- **Clientes**
  - Cadastro com validação de:
    - Nome
    - CPF ou CNPJ (com dígitos verificadores)
    - E‑mail (formato válido)
  - Listagem com:
    - ID incremental
    - UUID abreviado
    - Documento, tipo e e‑mail

- **Produtos**
  - Cadastro com validação de:
    - Nome e descrição
    - Preço maior que zero
    - Status (ativo/inativo)
  - Listagem com:
    - ID incremental
    - UUID abreviado
    - Preço formatado
    - Status ativo/inativo

- **Pedidos**
  - Criação de pedido para cliente existente
  - Adição de itens:
    - Bloqueio de produtos inativos
    - Quantidade e preço unitário validados
  - Alteração de quantidade de item
  - Remoção de item
  - Finalização, pagamento, entrega e cancelamento
  - Listagem de pedidos com:
    - Cliente
    - Data formatada
    - Status
    - Produtos do pedido (nome, preço, status)

- **Validações**
  - CPF e CNPJ com cálculo de dígitos verificadores
  - E‑mail com regex
  - Nome e descrição com tamanho mínimo
  - Preço positivo
  - Produto ativo para inclusão em pedido

---

## Funcionalidades futuras

- Relatórios de vendas e faturamento
- Busca de clientes e produtos por nome ou documento
- Exportação de dados para CSV/JSON
- Autenticação de usuários para acesso ao sistema

---

## Arquitetura e Padrões

O projeto segue uma arquitetura inspirada no **Domain-Driven Design (DDD)**, com separação clara entre:

- **Domínio**: Entidades, enums, validadores, fábricas, interfaces de repositório e serviços de domínio.
- **Aplicação**: Serviços que orquestram casos de uso.
- **Infraestrutura**: Repositórios e serviços concretos, utilitários de persistência.
- **Interface (UI)**: Menu de console e ações.

Também aplica princípios **SOLID**:

- **SRP** (Single Responsibility Principle): Cada classe tem uma única responsabilidade.
- **OCP** (Open/Closed Principle): Fácil extensão de validadores e funcionalidades sem alterar código existente.
- **LSP** (Liskov Substitution Principle): Uso de abstrações que permitem substituição de implementações.
- **ISP** (Interface Segregation Principle): Interfaces pequenas e específicas.
- **DIP** (Dependency Inversion Principle): Camadas superiores dependem de abstrações, não de implementações concretas.

---

## Estrutura do Projeto
```
 src
    └── main
        └── java
            └── br
                └── com
                    └── tech
                        └── ecommerce
                            ├── application
                            │   ├── ClienteAppService.java
                            │   ├── PedidoAppService.java
                            │   └── ProdutoAppService.java
                            ├── domain
                            │   ├── factory
                            │   │   ├── ClienteFactory.java
                            │   │   ├── ItemPedidoFactory.java
                            │   │   ├── PedidoFactory.java
                            │   │   └── ProdutoFactory.java
                            │   ├── model
                            │   │   ├── Cliente.java
                            │   │   ├── ItemPedido.java
                            │   │   ├── Pedido.java
                            │   │   ├── Produto.java
                            │   │   └── enums
                            │   │       ├── StatusPagamento.java
                            │   │       ├── StatusPedido.java
                            │   │       └── TipoDocumento.java
                            │   ├── repository
                            │   │   ├── ClienteRepositorio.java
                            │   │   ├── PedidoRepositorio.java
                            │   │   └── ProdutoRepositorio.java
                            │   ├── service
                            │   │   ├── NotificacaoServico.java
                            │   │   └── PedidoServico.java
                            │   └── validator
                            │       ├── DocumentoCNPJValidator.java
                            │       ├── DocumentoCPFValidator.java
                            │       ├── EmailValidator.java
                            │       ├── NomeValidator.java
                            │       └── TipoDocumentoValidator.java
                            ├── infra
                            │   ├── repository
                            │   │   ├── ClienteRepositorioArquivo.java
                            │   │   ├── PedidoRepositorioArquivo.java
                            │   │   └── ProdutoRepositorioArquivo.java
                            │   ├── service
                            │   │   └── NotificacaoServicoConsole.java
                            │   └── util
                            │       └── ArquivoUtil.java
                            └── ui
                                └── console
                                    ├── EcommerceConsoleApp.java
                                    ├── VoltarMenuPrincipalException.java
                                    └── menu
                                        ├── MenuAction.java
                                        ├── MenuController.java
                                        ├── MenuControllerImpl.java
                                        └── actions
                                            ├── CadastrarClienteAction.java
                                            ├── CadastrarProdutoAction.java
                                            ├── CriarPedidoAction.java
                                            ├── ListarClientesAction.java
                                            ├── ListarPedidosAction.java
                                            ├── ListarProdutosAction.java
                                            └── pedido
                                                ├── AdicionarItemPedidoAction.java
                                                ├── AlterarQuantidadeItemPedidoAction.java
                                                ├── CancelarPedidoAction.java
                                                ├── EntregarPedidoAction.java
                                                ├── FinalizarPedidoAction.java
                                                ├── PagarPedidoAction.java
                                                ├── RemoverItemPedidoAction.java
                                                └── VerDetalhesPedidoAction.java

```
---

## Como usar

O programa é executado no terminal.  
Ao iniciar, o menu principal é exibido com as opções de gerenciamento de clientes, produtos e pedidos.

Exemplo de execução:

```bash
javac -d out $(find src -name "*.java")
java -cp out src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp
```
Regras de Validação
- CPF: 11 dígitos, sem sequências repetidas, com dígitos verificadores válidos.
- CNPJ: 14 dígitos, sem sequências repetidas, com dígitos verificadores válidos.
- E‑mail: Deve conter @ e domínio válido.
- Nome: Mínimo de 2 caracteres.
- Descrição: Mínimo de 5 caracteres.
- Preço: Maior que zero.
- Produto inativo: Não pode ser adicionado a pedidos.

## Compilação
Para compilar:
```
javac -d out $(find src -name "*.java")
```
Para executar:
```
java -cp out src.main.java.br.com.tech.ecommerce.ui.console.EcommerceConsoleApp

