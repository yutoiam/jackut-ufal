package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando a senha fornecida para criação de conta é inválida
 * (nula ou vazia).
 */
public class SenhaInvalidaException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
