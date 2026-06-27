package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando o login informado na criação da conta é nulo ou vazio.
 */
public class LoginInvalidoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão para formatos de login inválidos.
     */
    public LoginInvalidoException() {
        super("Login inv\u00E1lido.");
    }
}