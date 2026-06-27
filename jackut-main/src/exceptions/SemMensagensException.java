package exceptions;

/**
 * Exceção lançada quando um usuário tenta ler uma mensagem de comunidade, mas sua caixa está vazia.
 */
public class SemMensagensException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão indicando ausência de mensagens.
     */
    public SemMensagensException() {
        super("Não há mensagens.");
    }
}