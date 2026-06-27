package exceptions;

/**
 * Exceção lançada quando a senha informada na criação da conta é nula ou vazia.
 */
public class SenhaInvalidaException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão para formatos de senha inválidos.
     */
    public SenhaInvalidaException() {
        super("Senha inv\u00E1lida.");
    }
}