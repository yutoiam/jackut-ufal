package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando o usuário tenta adicionar a si mesmo na própria lista de amigos.
 */
public class AutoAmizadeException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão impedindo a auto-amizade.
     */
    public AutoAmizadeException() {
        super("Usu\u00E1rio n\u00E3o pode adicionar a si mesmo como amigo.");
    }
}