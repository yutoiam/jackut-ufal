package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando o login fornecido para criação de conta é inválido
 * (nulo, vazio ou em formato incorreto).
 */
public class LoginInvalidoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
