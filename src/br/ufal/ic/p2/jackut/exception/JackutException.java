package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção base para todas as exceções de negócio do sistema Jackut.
 * Toda exceção específica do domínio deve estender esta classe.
 */
public class JackutException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói uma exceção com a mensagem fornecida.
     * @param message a mensagem de erro a ser exibida
     */
    public JackutException(String message) {
        super(message);
    }
}
