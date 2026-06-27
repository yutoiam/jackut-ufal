package exceptions;

/**
 * Exceção lançada quando um usuário tenta enviar um convite de amizade para alguém
 * que já possui um convite seu aguardando aceitação.
 */
public class ConvitePendenteException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão de convite duplicado.
     */
    public ConvitePendenteException() {
        super("Usu\u00E1rio j\u00E1 est\u00E1 adicionado como amigo, esperando aceita\u00E7\u00E3o do convite.");
    }
}