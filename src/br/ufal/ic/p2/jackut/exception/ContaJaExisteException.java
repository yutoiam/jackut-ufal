package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando se tenta criar uma conta com um login
 * que já está cadastrado no sistema.
 */
public class ContaJaExisteException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public ContaJaExisteException() {
        super("Conta com esse nome já existe.");
    }
}
