package br.ufal.ic.p2.jackut.exception;

/**
 * Exceção lançada quando se tenta ler um atributo do perfil de um usuário
 * que ainda não foi preenchido.
 */
public class AtributoNaoPreenchidoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem padrão do sistema.
     */
    public AtributoNaoPreenchidoException() {
        super("Atributo não preenchido.");
    }
}
