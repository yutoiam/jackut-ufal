package exceptions;

/**
 * Exceção lançada quando um usuário tenta adicionar alguém que já está presente
 * na sua lista de amigos confirmados.
 */
public class UsuarioJaAmigoException extends JackutException {

    private static final long serialVersionUID = 1L;

    /**
     * Constrói a exceção com a mensagem de erro padrão para amizades já existentes.
     */
    public UsuarioJaAmigoException() {
        super("Usu\u00E1rio j\u00E1 est\u00E1 adicionado como amigo.");
    }
}