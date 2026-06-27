package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada durante a autenticação quando o login não existe ou a senha está incorreta.
 */
public class LoginOuSenhaInvalidosException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem genérica de falha na autenticação.
     */
    public LoginOuSenhaInvalidosException() {
        super("Login ou senha inv\u00E1lidos.");
    }
}