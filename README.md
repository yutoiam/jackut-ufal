# Jackut - Rede de Relacionamentos

Projeto desenvolvido para a disciplina de **Programação 2** da Universidade Federal de Alagoas (UFAL).

O **Jackut** é uma rede social inspirada no antigo Orkut, permitindo o gerenciamento de usuários, comunidades, relacionamentos e recados. O sistema foi desenvolvido em Java seguindo uma arquitetura em camadas, com persistência de dados e testes automatizados utilizando a biblioteca EasyAccept.

---

# Funcionalidades

O sistema permite:

- Cadastro de usuários;
- Gerenciamento de perfis;
- Criação e administração de comunidades;
- Participação em comunidades;
- Envio e recebimento de recados;
- Gerenciamento de diferentes tipos de relacionamento:
    - Amizades;
    - Fãs;
    - Paqueras;
    - Inimigos;
- Persistência automática dos dados;
- Execução de testes de aceitação através do EasyAccept.

---

# Estrutura do Projeto

```
Jackut/
│
├── src/
│   └── br/
│       └── ufal/
│           └── ic/
│               └── p2/
│                   └── jackut/
│                       ├── controllers/
│                       ├── entities/
│                       ├── exceptions/
│                       ├── Main.java
│                       └── ...
│
├── lib/
│   └── easyaccept.jar
│
├── testes/
│   └── *.txt
│
├── data/
│   └── arquivos de persistência (.dat)
│
├── bin/
│   └── arquivos compilados (.class)
│
└── README.md
```

### Diretórios

| Pasta | Descrição |
|--------|-----------|
| **src/** | Código-fonte do projeto |
| **controllers/** | Camada responsável pelas regras de negócio |
| **entities/** | Classes que representam as entidades do sistema |
| **exceptions/** | Exceções personalizadas |
| **lib/** | Bibliotecas externas utilizadas pelo projeto |
| **testes/** | Scripts do EasyAccept para validação das User Stories |
| **data/** | Arquivos de persistência gerados durante a execução |
| **bin/** | Arquivos compilados (.class) |

---

# Tecnologias Utilizadas

- Java
- EasyAccept
- Programação Orientada a Objetos (POO)
- Persistência em arquivos
- Arquitetura em camadas

---

# Pré-requisitos

Antes de executar o projeto, é necessário possuir:

- Java Development Kit (JDK) 1.5 ou superior;
- Prompt de Comando (Windows) ou terminal equivalente;
- Estar na pasta raiz do projeto.

---

# Como Compilar

Compile todo o projeto utilizando:

```cmd
javac -encoding UTF-8 -cp "lib\easyaccept.jar" -d bin ^
src\br\ufal\ic\p2\jackut\exceptions\*.java ^
src\br\ufal\ic\p2\jackut\entities\*.java ^
src\br\ufal\ic\p2\jackut\controllers\*.java ^
src\br\ufal\ic\p2\jackut\*.java
```

Caso utilize Linux ou macOS, substitua `;` por `:` no classpath e adapte os separadores de diretório (`/`).

---

# Como Executar

Após a compilação, execute:

```cmd
java -cp "bin;lib\easyaccept.jar" br.ufal.ic.p2.jackut.Main
```

O sistema iniciará automaticamente a execução dos testes configurados na pasta `testes`.

---

# Persistência de Dados

Os dados são armazenados automaticamente na pasta:

```
data/
```

Os arquivos de persistência são recriados ou atualizados durante a execução do sistema.

---

# Testes

O projeto utiliza a biblioteca **EasyAccept** para validar automaticamente as funcionalidades implementadas.

Os roteiros de teste encontram-se na pasta:

```
testes/
```

Ao executar a classe `Main`, todos os testes configurados são executados automaticamente.

---

# Arquitetura

O projeto está organizado em camadas:

### Entities

Responsáveis por representar as entidades do domínio, como usuários, comunidades e relacionamentos.

### Controllers

Implementam toda a lógica de negócio e fazem a comunicação entre as entidades.

### Exceptions

Contêm exceções específicas do sistema para tratamento de erros.

### Main

Classe responsável por iniciar o sistema e executar os testes de aceitação.

---

# Organização do Código

O código segue princípios de orientação a objetos, buscando:

- Encapsulamento;
- Separação de responsabilidades;
- Reutilização de código;
- Facilidade de manutenção;
- Organização em pacotes.

---

# Desenvolvido para

Disciplina de **Programação 2**

Universidade Federal de Alagoas (UFAL)

Milestone 2 — Projeto Jackut

---

# Licença

Projeto desenvolvido exclusivamente para fins acadêmicos.