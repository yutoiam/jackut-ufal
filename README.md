# Jackut - Rede de Relacionamentos

Este repositório contém o código-fonte referente ao **Milestone 1** do projeto Jackut, desenvolvido para a disciplina de Programação 2.

## Sobre o Projeto
O Jackut é um sistema de rede social desenvolvido em Java, arquitetado utilizando o padrão **Facade** para a integração direta com os testes de aceitação via EasyAccept. O design do sistema foi focado em garantir alta coesão, baixo acoplamento e o encapsulamento estrito das entidades e regras de negócio.

## Como compilar e rodar
1. Compilação
Utilize o javac para compilar o código-fonte apontando para a biblioteca do EasyAccept:

"javac -cp "lib/easyaccept.jar" -sourcepath src -d out src/Main.java src/br/ufal/ic/p2/jackut/Facade.java"

3. Execução (Testes)
Após a compilação, execute a classe Main para rodar a bateria de testes:

No Windows:

"java -cp "out;lib/easyaccept.jar" Main"

No Linux ou Mac:

"java -cp "out:lib/easyaccept.jar" Main"

## Estrutura do Repositório
* `src/`: Contém o código-fonte da aplicação estruturado em pacotes (Controllers, Models, Exceptions e Facade).
* `tests/`: Scripts `.txt` com os testes de aceitação e User Stories originais.
* `lib/`: Biblioteca `easyaccept.jar` necessária para rodar os testes.
* `relatorio-milestone1.pdf`: Documentação técnica com as decisões arquiteturais e o Diagrama de Classes UML.

## Autor
* Yuri Raphael Mota De Araújo Barbosa
