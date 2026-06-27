package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando um usuário tenta adicionar a si mesmo como amigo.
 */
public class UsuarioNaoPodeAdicionarASiMesmoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public UsuarioNaoPodeAdicionarASiMesmoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
