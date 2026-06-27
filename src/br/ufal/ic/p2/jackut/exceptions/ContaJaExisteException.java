package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada durante o cadastro quando já existe uma conta utilizando o login informado.
 */
public class ContaJaExisteException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão de duplicidade de conta.
     */
    public ContaJaExisteException() {
        super("Conta com esse nome j\u00E1 existe.");
    }
}