package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando o atributo solicitado não foi preenchido no perfil do usuário.
 */
public class AtributoNaoPreenchidoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão informando que o atributo está vazio.
     */
    public AtributoNaoPreenchidoException() {
        super("Atributo n\u00E3o preenchido.");
    }
}