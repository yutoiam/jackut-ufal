package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando as credenciais fornecidas ao abrir uma sessão
 * são inválidas (login inexistente, senha incorreta, ou campos vazios).
 */
public class LoginOuSenhaInvalidosException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public LoginOuSenhaInvalidosException() {
        super("Login ou senha inválidos.");
    }
}
