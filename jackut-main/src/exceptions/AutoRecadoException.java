package exceptions;

/**
 * Exceção lançada quando o usuário tenta enviar um recado privado para si mesmo.
 */
public class AutoRecadoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão impedindo o auto-recado.
     */
    public AutoRecadoException() {
        super("Usu\u00E1rio n\u00E3o pode enviar recado para si mesmo.");
    }
}