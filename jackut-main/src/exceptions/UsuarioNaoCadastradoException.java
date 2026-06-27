package exceptions;

/**
 * Exceção lançada quando uma operação tenta referenciar um login de usuário
 * que não existe no sistema.
 */
public class UsuarioNaoCadastradoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão para usuários inexistentes.
     */
    public UsuarioNaoCadastradoException() {
        super("Usu\u00E1rio n\u00E3o cadastrado.");
    }
}