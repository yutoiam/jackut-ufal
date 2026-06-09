package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando um usuário não está cadastrado no sistema,
 * ou quando uma sessão inválida é utilizada (sessão inexistente ou expirada).
 */
public class UsuarioNaoCadastradoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
